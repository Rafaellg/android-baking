package com.rafaelguimas.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.dummy.DummyContent;

import java.util.List;

/**
 * Created by Rafael on 05/07/2017.
 */

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;
    private boolean mTwoPane;
    private OnItemClick mOnItemClick;

    public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, boolean isTwoPane, OnItemClick onItemClick) {
        mValues = items;
        mTwoPane = isTwoPane;
        mOnItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.notifyOnItemClick(holder.mItem.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface OnItemClick {
        void notifyOnItemClick(String itemId);
    }
}