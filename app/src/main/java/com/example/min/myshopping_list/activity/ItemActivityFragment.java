package com.example.min.myshopping_list.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.min.myshopping_list.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemActivityFragment extends Fragment {

    public ItemActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }
}
