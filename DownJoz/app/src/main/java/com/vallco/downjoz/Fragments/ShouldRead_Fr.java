package com.vallco.downjoz.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vallco.downjoz.R;

/**
 * Created by Javad on 7/13/2016.
 */
public class ShouldRead_Fr  extends Fragment {

    private View currentView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_should_read, container, false);





        return currentView;
    }
    public static Fragment newInstance() {
        return new ShouldRead_Fr();
    }
}
