package com.example.venson.soho.home_page.search_page;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.venson.soho.R;
import com.example.venson.soho.home_page.HomeFragment;
import com.example.venson.soho.member_page.CaseOverViewFragment;
import com.example.venson.soho.member_page.MemberFragment;
import com.example.venson.soho.message_page.MessageFragment;
import com.example.venson.soho.setting_page.SettingsFragment;

import java.lang.reflect.Field;


public class BottomNavigationFragment extends Fragment {
    private BottomNavigationView bottomNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener
            onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.cag_item_Home:
                    fragment = new HomeFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.cag_item_Mail:
                    fragment = new MessageFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.cag_item_case:
                    fragment = new CaseOverViewFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.cag_item_Member:
                    fragment = new MemberFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.cag_item_Settimg:
                    fragment = new SettingsFragment();
                    switchFragment(fragment);
                    return true;
                default:
                    initContent();
                    break;
            }
            return false;
        }
    };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.bottom_layout, container, false);
            bottomNavigation = view.findViewById(R.id.bottomNavigation_id);
            bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
            disableShiftMode(bottomNavigation);
            initContent();
            return view;
        }
    
        private void initContent() {
            Fragment fragment = new HomeFragment();
            switchFragment(fragment);
        }

        private void switchFragment(Fragment fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        //固定bottomNavigationView
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(android.support.design.widget.BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }


    }


