package com.rafaelguimas.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.models.Ingredient;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafae on 08/07/2017.
 */

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.ViewHolder> {

    private List<Ingredient> mValues;

    public IngredientsListAdapter(List<Ingredient> mValues) {
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mValues.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        holder.tvIngredient.setText(String.valueOf(decimalFormat.format(ingredient.getQuantity())) + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient)
        TextView tvIngredient;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
