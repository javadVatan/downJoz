package com.vallco.downjoz.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.vallco.downjoz.Adapter.RecyclerAdapter;
import com.vallco.downjoz.App.AppController;
import com.vallco.downjoz.MainActivity;
import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.Const;
import com.vallco.downjoz.utils.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Javad on 7/13/2016.
 */
public class AllData_Fr extends Fragment {
    // JSON Node names
    private final String TAG_MOST_VIDEOS = "mostviewedvideos";
    private final String TAG_TITLE = "title";
    private final String TAG_ID = "id";
    private final String TAG_USERNAME = "username";
    private final String TAG_SMALL_POSTER = "small_poster";
    private final String TAG_BIG_POSTER = "big_poster";
    private final String TAG_PROFILE_PHOTO = "profilePhoto";
    private final String TAG_DURATION = "duration";
    private final String TAG_SDATE = "sdate";
    //----------------------------------------------------------------
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ArrayList<ViewModel> dataList = new ArrayList<ViewModel>();
    //-----------------------------------------------------------------
    private static final String TAG = AllData_Fr.class.getSimpleName();
    private String tag_json_arry = "json_obj_arry";
    private ProgressDialog pDialog;
    private Context mContext;
    private View currentView;
    private SwipeRefreshLayout mSwipeRefresh;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_all_data, container, false);

        initVariable();

        loadJsonAllData();

        SwipeRefresh();

        return currentView;
    }

    public static Fragment newInstance() {
        return new AllData_Fr();
    }

    private void initVariable() {
        mContext = getContext();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        mSwipeRefresh= (SwipeRefreshLayout) currentView.findViewById(R.id.fr_all_data_swipe_refresh);
        mRecyclerView = (RecyclerView) currentView.findViewById(R.id.fr_all_data_recycler);
        mAdapter = new RecyclerAdapter(mContext,dataList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    public void loadJsonAllData() {

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
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray responseAllVideoArray = response.getJSONArray(TAG_MOST_VIDEOS);

            for (int i = 0; i < responseAllVideoArray.length(); i++) {
                int ir = responseAllVideoArray.length();
                JSONObject c = responseAllVideoArray.getJSONObject(i);

                ViewModel viewModel = new ViewModel(c.getString(TAG_ID),
                        c.getString(TAG_TITLE), c.getString(TAG_SMALL_POSTER));
                dataList.add(viewModel);
                mAdapter.notifyDataSetChanged();
            }
            pDialog.hide();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SwipeRefresh(){
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJsonAllData();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}



