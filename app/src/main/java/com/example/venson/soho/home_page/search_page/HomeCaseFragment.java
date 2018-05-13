package com.example.venson.soho.home_page.search_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.venson.soho.home_page.category_tab.GetAllFragment;
import com.example.venson.soho.R;

public class HomeCaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_case_fragment, container, false);

        initContent();
        return view;
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.case_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initContent() {
        Fragment fragment = new GetAllFragment();
        switchFragment(fragment);
    }



}
