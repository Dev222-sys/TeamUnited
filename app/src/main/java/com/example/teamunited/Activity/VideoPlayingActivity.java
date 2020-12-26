package com.example.teamunited.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamunited.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoPlayingActivity extends AppCompatActivity {
    String videoUrl;

    LinearLayout playBtn;
    SimpleExoPlayerView simpleExoPlayerView;

    private SimpleExoPlayer simpleExoPlayer;
    private AdaptiveTrackSelection.Factory factory;

    private ProgressBar progressBar;
    private TrackSelector trackSelector;
    private LoadControl loadControl;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private LoopingMediaSource loopingMediaSource;
    private int lastSongIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playing);
        playBtn = findViewById(R.id.play_btn);
        simpleExoPlayerView=findViewById(R.id.exo_player);
        progressBar=findViewById(R.id.progressBar);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        playMedia();

                //Toast.makeText(VideoPlayingActivity.this, "hdlkas", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void playMedia() {

        playBtn.setVisibility(View.GONE);
       videoUrl ="https://firebasestorage.googleapis.com/v0/b/login-286eb.appspot.com/o/TestVideo%2FEnd%20Screen%20-%2025104.mp4?alt=media&token=cb165685-d7c3-412b-ba7b-9d80dcaf651d";
            defaultBandwidthMeter = new DefaultBandwidthMeter();
            factory = new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
            trackSelector = new DefaultTrackSelector(factory);
            loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            DefaultBandwidthMeter dBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "com.exoplayerdemo"), dBandwidthMeter);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // below line you can pass video url
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                    dataSourceFactory, extractorsFactory, null, null);

            loopingMediaSource = new LoopingMediaSource(mediaSource);
            simpleExoPlayer.prepare(mediaSource);

            simpleExoPlayer.setPlayWhenReady(true);

            simpleExoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                    Toast.makeText(VideoPlayingActivity.this, "one", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                    Toast.makeText(VideoPlayingActivity.this, "two", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoadingChanged(boolean isLoading) {
                    Toast.makeText(VideoPlayingActivity.this, "three", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING) {
                      progressBar.setVisibility(View.VISIBLE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                    if (playbackState == ExoPlayer.STATE_ENDED) {
                        lastSongIndex++;
                       // playBtn.setVisibility(View.VISIBLE);
                      //playMedia();
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {
                    Toast.makeText(VideoPlayingActivity.this, "four", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                    Toast.makeText(VideoPlayingActivity.this, "five", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    if (!TextUtils.isEmpty(error.getMessage())) {
                        Log.d("ERROR::", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    int latestSongIndex = simpleExoPlayer.getCurrentWindowIndex();
                    if (latestSongIndex != lastSongIndex) {
                        lastSongIndex = latestSongIndex;
                    }
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                    Toast.makeText(VideoPlayingActivity.this, "six", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSeekProcessed() {

                    Toast.makeText(VideoPlayingActivity.this, "Seven", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }







