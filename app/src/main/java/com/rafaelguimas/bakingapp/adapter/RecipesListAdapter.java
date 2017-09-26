package com.rafaelguimas.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rafael on 05/07/2017.
 */

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> {

    private final List<Recipe> mValues;
    private OnItemClick mOnItemClick;

    public RecipesListAdapter(List<Recipe> items, OnItemClick onItemClick) {
        mValues = items;
        mOnItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTvRecipeTitle.setText(mValues.get(position).getName());
        Glide.with(holder.itemView.getContext())
                .load(holder.mItem.getImage())
                .placeholder(R.drawable.img_food_placeholder_repeat)
                .into(holder.ivBackground);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.notifyOnRecipeItemClick(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe_title)
        TextView mTvRecipeTitle;
        @BindView(R.id.iv_background)
        ImageView ivBackground;

        public Recipe mItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClick {
        void notifyOnRecipeItemClick(Recipe recipe);
    }
}