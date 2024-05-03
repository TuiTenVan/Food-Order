package com.example.foodorder.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.adapter.ContactAdapter;
import com.example.foodorder.constant.AboutUsConfig;
import com.example.foodorder.model.Contact;

public class ContactViewModel {

    private Context mContext;
    public ObservableList<Contact> listContacts = new ObservableArrayList<>();
    public ObservableField<String> aboutUsTitle = new ObservableField<>();
    public ObservableField<String> aboutUsContent = new ObservableField<>();
    public ObservableField<String> aboutUsWebsite = new ObservableField<>();

    public ContactViewModel(Context mContext) {
        this.mContext = mContext;
        aboutUsTitle.set(AboutUsConfig.ABOUT_US_TITLE);
        aboutUsContent.set(AboutUsConfig.ABOUT_US_CONTENT);
        aboutUsWebsite.set(AboutUsConfig.ABOUT_US_WEBSITE_TITLE);
        getListContacts();
    }

    public void getListContacts() {
        listContacts.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook, mContext.getString(R.string.label_facebook)));
        listContacts.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline, mContext.getString(R.string.label_call)));
        listContacts.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail, mContext.getString(R.string.label_gmail)));
        listContacts.add(new Contact(Contact.SKYPE, R.drawable.ic_skype, mContext.getString(R.string.label_skype)));
        listContacts.add(new Contact(Contact.YOUTUBE, R.drawable.ic_youtube, mContext.getString(R.string.label_youtube)));
        listContacts.add(new Contact(Contact.ZALO, R.drawable.ic_zalo, mContext.getString(R.string.label_zalo)));
    }

    @BindingAdapter({"list_data"})
    public static void loadListContacts(RecyclerView recyclerView, ObservableList<Contact> list) {
        GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        ContactAdapter contactAdapter = new ContactAdapter(list);
        recyclerView.setAdapter(contactAdapter);
    }

    public void onClickWebsite() {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AboutUsConfig.WEBSITE)));
    }

    public void release() {
        mContext = null;
    }
}
