package com.backingnd.mohamedali.bakingnd.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment,container,false);
        TextView shortDescriptionTV = view.findViewById(R.id.short_description);
        TextView descriptionTV = view.findViewById(R.id.description);

        playerView = view.findViewById(R.id.stepExoPlayerView);

        context = container.getContext();

        if (savedInstanceState != null && savedInstanceState.containsKey(ConstantUtils.STEP)){
            step = savedInstanceState.getParcelable(ConstantUtils.STEP);
        }


        /**
         * Handling the data and The player if video exist
         */

        if (step != null){
            shortDescriptionTV.setText(step.getShortDescription());
            descriptionTV.setText(step.getDescription());

            if (!step.getVideoURL().equals("")){
                Uri uri = Uri.parse(step.getVideoURL());
                intializePlayer(uri);
            }else if (!step.getThumbnailURL().equals("")){
                Uri uri = Uri.parse(step.getThumbnailURL());
                intializePlayer(uri);
            }else{
                playerView.setVisibility(View.GONE);
            }
        }

        return view;
    }


    /**
     * Initializing  Exoplayer
     * @param mediaUri the uri of video
     */
    public void intializePlayer(Uri mediaUri){
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
    }

    /**
     * Releasing Exoplayer
     */
    public void releasePlayer(){
        if (exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
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

    /**
     * Save instance of the step
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(ConstantUtils.STEP,step);
    }
}
