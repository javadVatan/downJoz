package com.vallco.downjoz.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vallco.downjoz.Adapter.AllDataAdapter;
import com.vallco.downjoz.App.AppController;
import com.vallco.downjoz.NetWork.NetworkUtil;
import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.Const;
import com.vallco.downjoz.utils.ShowDialog;
import com.vallco.downjoz.utils.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Javad on 7/13/2016.
 */
public class AllData_Fr extends Fragment implements ShowDialog.ConfirmationDialogEventHandler {
    // JSON Node names
    private final String TAG_DOWN_JOZ_ALL_DATA = "downjoz_alldata";
    private final String TAG_BOOK_NAME = "bookName";
    private final String TAG_ID = "id";
    private final String TAG_TYPE_VERSION = "TypeVersion";
    private final String TAG_BOOK_POSTER = "url";
    private final String TAG_TYPE_PRODUCT = "TypeProduct";
    private final String TAG_PRICE_PRODUCT = "priceProduct";
    private final String TAG_PRODUCT_DESCRIPTION = "productDescription";
    private final String TAG_DOWNLOAD_COUNT = "DownloadCount";
    private final String TAG_AUTHOR = "Author";

    //----------------------------------------------------------------
    private RecyclerView mRecycler;
    private AllDataAdapter mAdapter;
    private ArrayList<ViewModel> dataList = new ArrayList<ViewModel>();
    //-----------------------------------------------------------------
    private static final String TAG = AllData_Fr.class.getSimpleName();
    private String tag_json_all_data = "tag_json_all_data";
    private ProgressDialog pDialog;
    private Context mContext;
    private View currentView;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    public void onResume() {
        super.onResume();
        loadJsonAllData();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_all_data, container, false);

        initVariable();

        SwipeRefresh();

        return currentView;
    }


    private void initVariable() {
        mContext = getContext();
        pDialog = new ProgressDialog(mContext);

        mSwipeRefresh = (SwipeRefreshLayout) currentView.findViewById(R.id.fr_all_data_swipe_refresh);
        mRecycler = (RecyclerView) currentView.findViewById(R.id.fr_all_data_recycler);
        mAdapter = new AllDataAdapter(mContext, dataList);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    public void loadJsonAllData() {

        if (NetworkUtil.isConnect(mContext)) {
            pDialog.setMessage("در حال اتصال به سرور...");
            pDialog.show();
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    Const.URL_JSON_ALL_DATA, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.hide();
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq, tag_json_all_data);

        } else {
            ShowDialog showDialog = new ShowDialog(mContext);
            showDialog.setErrorDialog(this, "خطا", "خطا در اتصال به اینترنت", "بیخیال", "تنظیمات شبکه");
        }
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray responseAllVideoArray = response.getJSONArray(TAG_DOWN_JOZ_ALL_DATA);

            for (int i = 0; i < responseAllVideoArray.length(); i++) {

                JSONObject c = responseAllVideoArray.getJSONObject(i);

                ViewModel viewModel = new ViewModel(Integer.parseInt(c.getString(TAG_ID)), c.getString(TAG_BOOK_NAME), c.getString(TAG_BOOK_POSTER),
                        c.getString(TAG_AUTHOR), c.getString(TAG_TYPE_VERSION), c.getString(TAG_TYPE_PRODUCT), c.getString(TAG_PRODUCT_DESCRIPTION),
                        c.getString(TAG_DOWNLOAD_COUNT), c.getString(TAG_PRICE_PRODUCT));

                dataList.add(viewModel);
                mAdapter.notifyDataSetChanged();
            }
            pDialog.hide();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().getRequestQueue().cancelAll(tag_json_all_data);
    }

    private void SwipeRefresh() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJsonAllData();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    public static Fragment newInstance() {
        return new AllData_Fr();
    }

    @Override
    public void positivePressed() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void negativePressed() {

    }
}



