package com.example.min.myshopping_list.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.min.myshopping_list.R;
import com.example.min.myshopping_list.data.DAO.ItemDao;

public class ItemActivity extends AppCompatActivity {

    public static final String EXTRA_ItemKey = "ItemActivityKey";

    private Fragment mfragment;

    public int mListId;
    public static Intent newIntent(Context packageContext, int listId) {
        Intent intent = new Intent(packageContext, ItemActivity.class);
        intent.putExtra(EXTRA_ItemKey, listId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListId = (int) getIntent().getSerializableExtra(EXTRA_ItemKey);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




//        FragmentManager fm=getSupportFragmentManager();
//        mfragment= fm.findFragmentById(R.id.fragment_container);
//        if (mfragment == null){
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            mfragment = ItemActivityFragment.newInstance(mListId);
//            fragmentTransaction.add(R.id.fragment_container, mfragment);
//            fragmentTransaction.commit();
//        }

    }

    }
