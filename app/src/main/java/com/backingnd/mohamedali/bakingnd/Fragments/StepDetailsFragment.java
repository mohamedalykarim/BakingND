package com.backingnd.mohamedali.bakingnd.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.Utilities.ConstantUtils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class StepDetailsFragment extends Fragment {
    /**
     * this fragment shows the step details
     * Short description
     * Video if exists
     * Description
     */

    SimpleExoPlayer exoPlayer;
    PlayerView playerView;
    Context context;

    RecipeStep step;

    Uri uri;

    TextView shortDescriptionTV;
    TextView descriptionTV;

    ImageView detailsImageView;

    private long mediaPosition;
    boolean isGetPlayWhenReady;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment,container,false);
        shortDescriptionTV = view.findViewById(R.id.short_description);
        descriptionTV = view.findViewById(R.id.description);

        detailsImageView = view.findViewById(R.id.detailsImageView);

        playerView = view.findViewById(R.id.stepExoPlayerView);

        context = container.getContext();



        if (savedInstanceState != null){

            // get the step
            if (savedInstanceState.containsKey(ConstantUtils.STEP)){
                step = savedInstanceState.getParcelable(ConstantUtils.STEP);
            }

            // get the last player position and state
            if (savedInstanceState.containsKey(ConstantUtils.MEDIA_POSITION)){
                mediaPosition = savedInstanceState.getLong(ConstantUtils.MEDIA_POSITION);
                isGetPlayWhenReady = savedInstanceState.getBoolean(ConstantUtils.IS_PLAY_WHEN_READY);
            }


        }


        shortDescriptionTV.setText(step.getShortDescription());
        descriptionTV.setText(step.getDescription());

        return view;
    }


    /**
     * Handling The player if video exist
     */

    public void  handlingTheVideo(){

            if (!step.getVideoURL().equals("")){
                uri = Uri.parse(step.getVideoURL());
                initializePlayer(uri);

            }else if (!step.getThumbnailURL().equals("")){
                uri = Uri.parse(step.getThumbnailURL());

                // check if there is video in thumbnail
                if (getMimeType(step.getThumbnailURL()).equals(ConstantUtils.MP4_MIME_TYPE)){
                    initializePlayer(uri);
                }
                else{
                    Picasso.get().load(uri).into(detailsImageView);
                }

            }else{
                playerView.setVisibility(View.GONE);
            }

    }




    /**
     * Initializing  Exoplayer
     * @param mediaUri the uri of video
     */
    public void initializePlayer(Uri mediaUri){
        /**
         * Creating the player
         */
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        /**
         * attaching the player to the view
         */

        playerView.setPlayer(exoPlayer);

        /**
         * preparing the player
         */
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context,ConstantUtils.PLAYER_NAME),
                defaultBandwidthMeter
        );

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaUri);
        exoPlayer.prepare(videoSource);
        exoPlayer.setPlayWhenReady(true);

        if (mediaPosition> 0) exoPlayer.seekTo(mediaPosition);
        exoPlayer.setPlayWhenReady(isGetPlayWhenReady);
    }

    /**
     * Releasing Exoplayer
     */
    public void releasePlayer(){
        if (exoPlayer != null){
            mediaPosition = exoPlayer.getContentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();


        if (Util.SDK_INT <= 23 && exoPlayer != null) {
            savePlayerState();
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23 && exoPlayer != null) {
            savePlayerState();
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 ) {
            handlingTheVideo();
            exoPlayer.seekTo(mediaPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            handlingTheVideo();
            if (exoPlayer != null)
            exoPlayer.seekTo(mediaPosition);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void setStep(RecipeStep step) {
        this.step = step;
    }


    private void savePlayerState() {
        if (exoPlayer != null) {
            isGetPlayWhenReady = exoPlayer.getPlayWhenReady();
            mediaPosition = exoPlayer.getCurrentPosition();
        }
    }



    /**
     * Save instance of the step
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        savePlayerState();
        outState.putParcelable(ConstantUtils.STEP,step);
        outState.putLong(ConstantUtils.MEDIA_POSITION, mediaPosition);
        outState.putBoolean(ConstantUtils.IS_PLAY_WHEN_READY,isGetPlayWhenReady);
    }


    /**
     * method source:
     * https://stackoverflow.com/questions/8589645/how-to-determine-mime-type-of-file-in-android?answertab=votes#tab-top
     */


    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

}
