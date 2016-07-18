package com.vallco.downjoz;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vallco.downjoz.Fragments.AllData_Fr;
import com.vallco.downjoz.Fragments.News_Fr;
import com.vallco.downjoz.Fragments.ShouldRead_Fr;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private int PAGE_COUNT;
    private final int SecondTab = 1;
    private final int FirstTab = 0;
    private final int ThirdTab = 2;
    private ViewPager pager;
    private String[] tabTitles;
    private int currentTab = 0;
    public static FragmentManager fragmentManager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initBundle();
        prepareViewPager();


        fragmentManager = getSupportFragmentManager();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        showToast("One");
                        break;
                    case 1:


                        break;
                    case 2:
                        showToast("Three");

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    // *****************************************************************************************************************
    // FragmentPagerAdapter
    // *****************************************************************************************************************

    private void initBundle() {
        tabTitles = new String[]{getString(R.string.first_tab), getString(R.string.second_tab), getString(R.string.third_tab)};
        currentTab = 1;
        PAGE_COUNT = 3;
        int[] tabID = new int[PAGE_COUNT];
        tabID[0] = FirstTab;
        tabID[1] = SecondTab;
        tabID[2] = ThirdTab;
    }

    private void prepareViewPager() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        /** Getting a reference to the ViewPager defined the layout file */
        pager = (ViewPager) findViewById(R.id.tabanim_viewpager);

        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();

        /** Instantiating FragmentPagerAdapter */
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm);

        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);

        pager.setOffscreenPageLimit(PAGE_COUNT);
        pager.setCurrentItem(currentTab);
        setSupportActionBar(mToolbar);

        mToolbar.setOnClickListener(this);


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTabLayout.setupWithViewPager(pager);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_drawer:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.toolbar_search:
                showToast("toolbar_search");
                break;

        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case FirstTab:
                    return News_Fr.newInstance();

                case SecondTab:
                    return AllData_Fr.newInstance();

                case ThirdTab:
                    return ShouldRead_Fr.newInstance();

                default:
                    return null;
            }
        }

        /**
         * Returns the number of pages
         */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}/*AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button search_btn ;
    private EditText inputSearchText;
        private String tag_json_obj = "json_obj_req";
        private ProgressDialog pDialog;
        String url = "link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariable();


    }
    private void searchEvent(String searchText){
        // Tag used to cancel the request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchText", "searchText");
                return params;
            }

        };
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
    private void initVariable(){
        pDialog = new ProgressDialog(this);
        search_btn = (Button) findViewById(R.id.active_search_btn);
        inputSearchText = (EditText) findViewById(R.id.inputTextSearch_et);

        search_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.active_search_btn:
                searchEvent(inputSearchText.getText().toString());
                break;
        }
    }
}
*/