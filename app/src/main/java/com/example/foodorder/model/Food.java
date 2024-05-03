package com.example.foodorder.model;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.foodorder.BR;
import com.example.foodorder.R;
import com.example.foodorder.constant.Constant;
import com.example.foodorder.constant.GlobalFuntion;
import com.example.foodorder.listener.IClickItemCartListener;
import com.example.foodorder.utils.GlideUtils;
import com.example.foodorder.view.activity.FoodDetailActivity;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "food")
public class Food extends BaseObservable implements Serializable {

    @PrimaryKey
    private int id;
    private String name;
    private String image;
    private String banner;
    private String description;
    private int price;
    private int sale;
    private int count;
    private int totalPrice;
    private boolean popular;
    @Ignore
    private List<Image> images;
    @Ignore
    private int adapterPosition;
    @Ignore
    private IClickItemCartListener iClickItemCartListener;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRealPrice() {
        if (sale <= 0) {
            return price;
        }
        return price - (price * sale / 100);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public IClickItemCartListener getClickItemCartListener() {
        return iClickItemCartListener;
    }

    public void setClickItemCartListener(IClickItemCartListener iClickItemCartListener) {
        this.iClickItemCartListener = iClickItemCartListener;
    }

    @BindingAdapter({"banner_image"})
    public static void loadImageBannerFromUrl(ImageView imageView, String url) {
        GlideUtils.loadUrlBanner(url, imageView);
    }

    @BindingAdapter({"normal_image"})
    public static void loadImageFromUrl(ImageView imageView, String url) {
        GlideUtils.loadUrl(url, imageView);
    }

    public String getStringSale(TextView textView) {
        return textView.getContext().getString(R.string.label_discount) + " " + getSale() + "%";
    }

    public boolean isSaleOff() {
        return getSale() > 0;
    }

    public String getStringOldPrice(TextView textview) {
        textview.setPaintFlags(textview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        return getPrice() + Constant.CURRENCY;
    }

    public String getStringRealPrice() {
        if (isSaleOff()) {
            return getRealPrice() + Constant.CURRENCY;
        } else {
            return getPrice() + Constant.CURRENCY;
        }
    }

    public String getStringCount () {
        return String.valueOf(getCount());
    }

    public void goToFoodDetail(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_INTENT_FOOD_OBJECT, this);
        GlobalFuntion.startActivity(view.getContext(), FoodDetailActivity.class, bundle);
    }

    public void onClickButtonSubtract(View view) {
        int count = getCount();
        if (count <= 1) {
            return;
        }
        int newCount = count - 1;

        int totalPrice = getRealPrice() * newCount;
        setCount(newCount);
        setTotalPrice(totalPrice);

        iClickItemCartListener.updateItemFood(view.getContext(), this, getAdapterPosition());
    }

    public void onClickButtonAdd(View view) {
        int newCount = getCount() + 1;

        int totalPrice = getRealPrice() * newCount;
        setCount(newCount);
        setTotalPrice(totalPrice);

        iClickItemCartListener.updateItemFood(view.getContext(), this, getAdapterPosition());
    }

    public void onClickButtonDelete(View view) {
        iClickItemCartListener.clickDeteteFood(view.getContext(), this, getAdapterPosition());
    }
}
