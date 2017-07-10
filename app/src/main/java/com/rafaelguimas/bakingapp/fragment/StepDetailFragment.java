package com.rafaelguimas.bakingapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    private static final String ARG_ITEM = "item_step";

    @BindView(R.id.exoplayer)
    SimpleExoPlayerView exoplayer;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_previously_step)
    TextView tvPreviouslyStep;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    private Step mItem;
    private SimpleExoPlayer mPlayer;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public static StepDetailFragment newInstance(Step item) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mItem = getArguments().getParcelable(ARG_ITEM);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayer.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPlayer.release();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        if (mItem != null) {
            tvDescription.setText(mItem.getDescription());

            initializePlayer();
        }

        tvPreviouslyStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Volta para item anterior
            }
        });

        tvNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vai para item seguinte
            }
        });

        return view;
    }

    private void initializePlayer() {
        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create the player
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        // Bind the player to the view.
        exoplayer.setPlayer(mPlayer);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(mItem.getVideoURL()), dataSourceFactory, extractorsFactory, null, null);
        LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        // Prepare the player with the source.
        mPlayer.prepare(loopingSource);
        mPlayer.setPlayWhenReady(true);
    }

}
