package com.rafaelguimas.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 05/07/2017.
 */

public class Ingredient implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    private Double quantity;
    private String measure;
    private String ingredient;

    public Ingredient(Double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        quantity = in.readByte() == 0x00 ? null : in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    static Ingredient mockObject() {
        return new Ingredient(2.5, "G", "salt");
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (quantity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(quantity);
        }
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}