package com.rafaelguimas.bakingapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class StepDetailFragment extends Fragment {

    private static final String ARG_STEP_LIST = "arg_step_list";
    private static final String ARG_SELECTED_POSITION = "arg_selected_position";

    @BindView(R.id.exoplayer)
    SimpleExoPlayerView exoplayer;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @Nullable
    @BindView(R.id.tv_previously_step)
    TextView tvPreviouslyStep;
    @Nullable
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    private List<Step> mStepList;
    private int mSelectedPosition;
    private SimpleExoPlayer mPlayer;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public static StepDetailFragment newInstance(ArrayList<Step> stepList, int selectedPosition) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_STEP_LIST, stepList);
        args.putInt(ARG_SELECTED_POSITION, selectedPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mStepList = getArguments().getParcelableArrayList(ARG_STEP_LIST);
            mSelectedPosition = getArguments().getInt(ARG_SELECTED_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        setupView();

        return view;
    }

    private void setupView() {
        tvDescription.setText(mStepList.get(mSelectedPosition).getDescription());

        if (tvPreviouslyStep != null && tvNextStep != null) {
            tvPreviouslyStep.setVisibility(mSelectedPosition == 0 ? View.GONE : View.VISIBLE);
            tvNextStep.setVisibility(mSelectedPosition == mStepList.size() - 1 ? View.GONE : View.VISIBLE);
        }

        if (!mStepList.get(mSelectedPosition).getVideoURL().equals("")) {
            initializePlayer();
        } else {
            exoplayer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer() {
        exoplayer.setVisibility(View.VISIBLE);

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create the player
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(mStepList.get(mSelectedPosition).getVideoURL()), dataSourceFactory, extractorsFactory, null, null);
        LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        // Prepare the player with the source.
        mPlayer.prepare(loopingSource);
        mPlayer.setPlayWhenReady(true);

        // Bind the player to the view.
        exoplayer.setPlayer(mPlayer);
        exoplayer.setUseController(false);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    @Optional
    @OnClick(R.id.tv_previously_step)
    public void onPreviouslyStepClick() {
        if (mSelectedPosition > 0) {
            --mSelectedPosition;
            releasePlayer();
            setupView();
        }
    }

    @Optional
    @OnClick(R.id.tv_next_step)
    public void onNextStepClick() {
        if (mSelectedPosition < mStepList.size()) {
            mSelectedPosition++;
            releasePlayer();
            setupView();
        }
    }

}
