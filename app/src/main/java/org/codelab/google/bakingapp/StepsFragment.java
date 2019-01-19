package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

public class StepsFragment extends Fragment {

    private MainViewModel viewModel;
    private SimpleExoPlayer player;
    private long playBackPosition;
    private int currentWindow;
    private PlayerView playerView;
    private boolean playWhenReady;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.steps_fragment, container, false);
        final TextView instructions = v.findViewById(R.id.instructions);
        final TextView stepTitle = v.findViewById(R.id.step_name);
        playerView = v.findViewById(R.id.video_view);
        playWhenReady = true;

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getCurrentStep().observe(this, new Observer<Steps>() {
            @Override
            public void onChanged(@Nullable Steps step) {
                if (step.getVideoURL().equals("")) {
                    playerView.setVisibility(View.INVISIBLE);
                }
                initializePlayer(step.getVideoURL());
                instructions.setText(step.getDescription());
                stepTitle.setText(step.getShortDescription());
            }
        });
        return v;
    }
    //If the screen orientation changes, set the trigger in the ViewModel to show the Details
    //fragment
    @Override
    public void onDetach() {
        super.onDetach();
        viewModel.select("Details");
    }

    private void initializePlayer(String url) {
        player = ExoPlayerFactory.newSimpleInstance(getActivity());
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playBackPosition);
        MediaSource mediaSource = buildMediaSource(Uri.parse(url));
        player.prepare(mediaSource, true, false);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory("exoplayer"))
                .createMediaSource(uri);
    }
}
