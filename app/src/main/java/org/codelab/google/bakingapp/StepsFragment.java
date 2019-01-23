package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.android.exoplayer2.util.Util;

import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

public class StepsFragment extends Fragment {

    private MainViewModel viewModel;
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private PlayerView playerView;
    private boolean playWhenReady;
    private static final String TAG = StepsFragment.class.getSimpleName();


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
        Log.i(TAG, "from onCreate: " + viewModel.getSelected().getValue());
        viewModel.getCurrentStep().observe(this, new Observer<Steps>() {
            @Override
            public void onChanged(@Nullable Steps step) {
                if (step.getVideoURL().equals("")) {
                    playerView.setVisibility(View.INVISIBLE);
                }

                instructions.setText(step.getDescription());
                stepTitle.setText(step.getShortDescription());
            }
        });
        return v;
    }

    private void initializePlayer(String url) {
        player = ExoPlayerFactory.newSimpleInstance(getActivity());
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        MediaSource mediaSource = buildMediaSource(Uri.parse(url));
        player.prepare(mediaSource, true, false);



    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory("exoplayer"))
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getCurrentStep().observe(this, new Observer<Steps>() {
            @Override
            public void onChanged(@Nullable Steps steps) {
                if (Util.SDK_INT > 23) {
                    if (steps != null) {
                        initializePlayer(steps.getVideoURL());
                        Log.i(TAG, "video url: " + steps.getVideoURL());
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getCurrentStep().observe(this, new Observer<Steps>() {
            @Override
            public void onChanged(@Nullable Steps steps) {
                if ((Util.SDK_INT <= 23 || player == null)) {
                    if (steps != null) {
                        initializePlayer(steps.getVideoURL());
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
