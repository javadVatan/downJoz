package com.vallco.downjoz.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vallco.downjoz.Adapter.AllDataAdapter;
import com.vallco.downjoz.Adapter.FavoriteAdapter;
import com.vallco.downjoz.DataBase.DbHandler;
import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.ViewModel;

import java.util.ArrayList;

/**
 * Created by Javad on 7/13/2016.
 */
public class Favorite_Fr extends Fragment {

    private View currentView;
    private Context mContext;
    private DbHandler dbHandler;
    private RecyclerView mRecycler;
    private FavoriteAdapter mFavoriteAdapter;
    private ArrayList<ViewModel> dataList;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_favorite, container, false);

        initVariable();
        initList();

        return currentView;
    }

    public static Fragment newInstance() {
        return new Favorite_Fr();
    }

    private void initVariable() {
        mContext = getContext();
        dbHandler = new DbHandler(mContext);
        dataList = new ArrayList<ViewModel>();
        mRecycler = (RecyclerView) currentView.findViewById(R.id.frg_should_read_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initList() {
        dbHandler.openDataBase();
        dataList=dbHandler.getAllData();
        mFavoriteAdapter = new FavoriteAdapter(mContext, dataList);
        mRecycler.setAdapter(mFavoriteAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }
}
