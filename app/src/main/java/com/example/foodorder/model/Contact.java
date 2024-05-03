package com.example.foodorder.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.foodorder.constant.GlobalFuntion;

public class Contact {

    public static final int FACEBOOK = 0;
    public static final int HOTLINE = 1;
    public static final int GMAIL = 2;
    public static final int SKYPE = 3;
    public static final int YOUTUBE = 4;
    public static final int ZALO = 5;

    private int id;
    private int image;
    private String name;

    public Contact(int id, int image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public void clickContactItem(View view) {
        Context context = view.getContext();
        switch (id) {
            case Contact.FACEBOOK:
                GlobalFuntion.onClickOpenFacebook(context);
                break;

            case Contact.HOTLINE:
                GlobalFuntion.callPhoneNumber((Activity) context);
                break;

            case Contact.GMAIL:
                GlobalFuntion.onClickOpenGmail(context);
                break;

            case Contact.SKYPE:
                GlobalFuntion.onClickOpenSkype(context);
                break;

            case Contact.YOUTUBE:
                GlobalFuntion.onClickOpenYoutubeChannel(context);
                break;

            case Contact.ZALO:
                GlobalFuntion.onClickOpenZalo(context);
                break;
        }
    }
}
