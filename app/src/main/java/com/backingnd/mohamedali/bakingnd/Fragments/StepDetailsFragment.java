package com.backingnd.mohamedali.bakingnd.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StepDetailsFragment extends Fragment {
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView playerView;
    Context context;

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

        RecipeStep step = null;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
           step = bundle.getParcelable(ConstantUtils.STEP);
        }

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



    public void intializePlayer(Uri mediaUri){
        if (exoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    context,
                    trackSelector,
                    loadControl
            );
            playerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(context,ConstantUtils.PLAYER_NAME);
            MediaSource mediaSource = new ExtractorMediaSource(
              mediaUri,
              new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

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
}