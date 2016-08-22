package com.vallco.downjoz;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.vallco.downjoz.Adapter.DrawerAdapter;
import com.vallco.downjoz.Fragments.AllData_Fr;
import com.vallco.downjoz.Fragments.Favorite_Fr;
import com.vallco.downjoz.Fragments.News_Fr;
import com.vallco.downjoz.utils.DrawerViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;
    private ListView drawerList;
    private DrawerAdapter drawerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initBundle();
        prepareViewPager();
        initDrawer();


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment;
                switch (position) {
                    case 0:
                        fragment = ((MyFragmentPagerAdapter) pager.getAdapter()).getFragment(position);
                        if (fragment != null) {
                            fragment.onResume();
                        }

                        break;
                    case 1:
                        fragment = ((MyFragmentPagerAdapter) pager.getAdapter()).getFragment(position);
                        if (fragment != null) {
                            fragment.onResume();
                        }
                        break;

                    case 2:
                        fragment = ((MyFragmentPagerAdapter) pager.getAdapter()).getFragment(position);
                        if (fragment != null) {
                            fragment.onResume();
                        }

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
        fragmentManager = getSupportFragmentManager();
        mFragmentTags = new HashMap<Integer, String>();
        tabTitles = new String[]{getString(R.string.first_tab), getString(R.string.second_tab), getString(R.string.third_tab)};
        currentTab = 1;
        PAGE_COUNT = 3;
        int[] tabID = new int[PAGE_COUNT];
        tabID[0] = FirstTab;
        tabID[1] = SecondTab;
        tabID[2] = ThirdTab;
    }

    private void initDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.mainDrawerList);

        ArrayList<DrawerViewModel> listData = new ArrayList<DrawerViewModel>();
        int[] allIconDrawer = {R.drawable.ic_file_download_36dp, R.drawable.ic_book_black_36dp,
                R.drawable.ic_help_36dp, R.drawable.ic_bug_report_36dp};

        String[] allNameDrawer = {"دانلود", "کتاب های خریداری شده", "راهنما", "پشتیبانی"};

        for (int i = 0; i < allIconDrawer.length; i++) {
            DrawerViewModel drawerViewModel = new DrawerViewModel(allNameDrawer[i], allIconDrawer[i]);
            listData.add(drawerViewModel);
        }

        DrawerAdapter.DrawerItemOnclick drawerOnclick = new DrawerAdapter.DrawerItemOnclick() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        };

        drawerAdapter = new DrawerAdapter(this, drawerOnclick, listData);
        drawerList.setAdapter(drawerAdapter);

    }

    private void prepareViewPager() {

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

                break;

        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                //record the fragment tag here
                Fragment fragment = (Fragment) obj;
                String tag = fragment.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null) return null;
            return mFragmentManager.findFragmentByTag(tag);

        }

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof AllData_Fr) {
                ((AllData_Fr) object).loadJsonAllData();
            }

            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case FirstTab:
                    return News_Fr.newInstance();

                case SecondTab:
                    return AllData_Fr.newInstance();

                case ThirdTab:
                    return Favorite_Fr.newInstance();

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
        switch (v.checkId()){
            case R.id.active_search_btn:
                searchEvent(inputSearchText.getText().toString());
                break;
        }
    }
}
*/