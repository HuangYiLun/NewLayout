package com.example.venson.soho.home_page;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.venson.soho.HttpTask;
import com.example.venson.soho.obj_classes.Case;
import com.example.venson.soho.Common;
import com.example.venson.soho.R;

import com.example.venson.soho.obj_classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private Toolbar toolbar;
    private TabLayout caseTabLayout, caseMemberTabLayout;
    //    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView homeRecyclerView;
    //constant
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    //Servlet Name
    private final static String CASE_SERVLET = "/CaseServlet";
    private final static String USER_SERVLET = "/UserServlet";
    //Actions
    private final static String FIND_ALL = "findAll";
    private final static String FIND_BY_CATEGORY = "findByCategory";

    //adapters
    private CaseAdapter caseAdapter;
    private MemberAdapter memberAdapter;

    //indications
    private MyAdapterManager[] adapterType;
    private static final Class[] TAB_TYPE = {Case.class, User.class};
    private static final String[] SERVLET_TYPE = {CASE_SERVLET, USER_SERVLET};
    private static final String[] ACTION_TYPE = {FIND_ALL, FIND_BY_CATEGORY};
    private static final int[] CATEGORY = {0, 1, 2, 3, 4, 5, 6};
    //indicators
    private static int typeIndicator = 0;
    private static int actionIndicator = 0;
    private static int categoryIndicator = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_layout, container, false);
        findView(view);
        setHasOptionsMenu(true);
        toolbar.setTitle("");
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        caseAdapter = new CaseAdapter(new ArrayList<Case>(), getActivity());
        memberAdapter = new MemberAdapter(new ArrayList<User>(), getActivity());
        adapterType = new MyAdapterManager[2];
        adapterType[0] = caseAdapter;
        adapterType[1] = memberAdapter;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getCaseTab();
        getCaseMemberTab();
        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
        homeRecyclerView.setAdapter(adapterType[typeIndicator]);
        adapterType[typeIndicator].notifyDataSetChanged();
        return view;
    }

    private void findView(View view) {
        toolbar = view.findViewById(R.id.tool_bar);
        caseTabLayout = view.findViewById(R.id.cag_tablayout);
        caseMemberTabLayout = view.findViewById(R.id.case_mamber_tabLayout);
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        homeRecyclerView = view.findViewById(R.id.rvCase);
    }

//    private void setAdapterDataList() {
//        List<T> list = getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], type);
//        String name=adapterClass[i].getName();
//        Class<C extends Class.forName(name)> c= null;
//        try {
//            c = Class.forName(name);
//            if (adapter instanceof c)
//        } catch (ClassNotFoundException e) {
//            Log.e(TAG,e.toString());
//        }
//
//        adapter.notifyDataSetChanged();

//        List<? extends cl> list = getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]);
//        if (adapterType[typeIndicator] instanceof CaseAdapter && cl.isInstance(Case.class)) {
//            ((CaseAdapter) adapterType[typeIndicator]).setItemList((List<Case>) list);
//            adapterType[typeIndicator].notifyDataSetChanged();
//        } else if (adapterType[typeIndicator] instanceof MemberAdapter && cl.isInstance(User.class)) {
//            ((MemberAdapter) adapterType[typeIndicator]).setItemList((List<User>) list);
//            adapterType[typeIndicator].notifyDataSetChanged();
//        }
//    }

    // work category
    public TabLayout getCaseTab() {

        caseTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        actionIndicator = 0;
                        categoryIndicator = 0;
                        Toast.makeText(getActivity(), tab.getText(), Toast.LENGTH_SHORT).show();
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();
                        return;
                    case 1:
                        actionIndicator = 1;
                        categoryIndicator = 1;
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();
                        return;
                    case 2:
                        actionIndicator = 1;
                        categoryIndicator = 2;
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();

                        return;
                    case 3:
                        actionIndicator = 1;
                        categoryIndicator = 3;
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();
                        return;
                    case 4:
                        actionIndicator = 1;
                        categoryIndicator = 4;
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();
                        return;
                    case 5:
                        actionIndicator = 1;
                        categoryIndicator = 5;
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();
                        return;
                    case 6:
                        actionIndicator = 1;
                        categoryIndicator = 6;
                        adapterType[typeIndicator].setItemList(getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], SERVLET_TYPE[typeIndicator], TAB_TYPE[typeIndicator]));
                        adapterType[typeIndicator].notifyDataSetChanged();
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return caseTabLayout;
    }

    // member and case tab
    public TabLayout getCaseMemberTab() {
        caseMemberTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        homeRecyclerView.setAdapter(caseAdapter);
                        typeIndicator = 0;
                        List<Case> caseList = getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], CASE_SERVLET, TAB_TYPE[0]);
                        caseAdapter.setItemList(caseList);
                        caseAdapter.notifyDataSetChanged();
                        return;
                    case 1:
                        homeRecyclerView.setAdapter(memberAdapter);
                        typeIndicator = 1;
                        List<User> userList = getDataList(ACTION_TYPE[actionIndicator], CATEGORY[categoryIndicator], USER_SERVLET, TAB_TYPE[1]);
                        memberAdapter.setItemList(userList);
                        memberAdapter.notifyDataSetChanged();
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return caseMemberTabLayout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_item, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.toolbar_search:
                return true;

            case R.id.toolbar_item_add:
                Fragment fragment = new CaseInsertFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;

            case R.id.toolbar_item_filter:
                return true;

            default:
                break;
        }
        return false;
    }

    private <T> List<T> getDataList(String action, int category, String servletName, Class<T> type) {

        // T (type) defined for Data type of Gson parsing from servlet

        List<T> list = new ArrayList<>();
        //category=1,2,3,4,5,6 ; 0 = all category
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + servletName;
            //servlet URL usage
            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", action);//action defined for servlet determination of dao action
            jsonObject.addProperty("category", category);//param for SQL search
            String jsonOut = jsonObject.toString();
            HttpTask getDataTask = new HttpTask(url, jsonOut);
            try {
                String jsonIn = getDataTask.execute().get();
                Log.d(TAG, jsonIn);
                if (jsonIn == "504") {
                    Common.showToast(getContext(), R.string.msg_server_not_available);
                    return list;
                } else {
                    //gson generic parsing
                    JsonParser jsonParser = new JsonParser();
                    if (jsonParser.parse(jsonIn) != null) {
//
                        JsonArray array = jsonParser.parse(jsonIn).getAsJsonArray();
                        for (final JsonElement elem : array) {
                            list.add(gson.fromJson(elem, type));
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (list.isEmpty()) {
                Common.showToast(getActivity(), "查無資料");

            } else {
                int count = list.size();
                Common.showToast(getActivity(), "共" + count + "筆資料");
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
        return list;
    }


} //end
