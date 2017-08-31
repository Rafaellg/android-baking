package com.rafaelguimas.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafae on 08/07/2017.
 */

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.ViewHolder> {

    private List<Step> mValues;
    private OnStepItemClick mListener;

    public StepsListAdapter(List<Step> mValues, OnStepItemClick mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Step step = mValues.get(position);

        holder.tvId.setText(String.valueOf(step.getId()));
        holder.tvShortDescription.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.notifyOnStepItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_short_description)
        TextView tvShortDescription;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnStepItemClick {
        void notifyOnStepItemClick(int selectedPosition);
    }
}
