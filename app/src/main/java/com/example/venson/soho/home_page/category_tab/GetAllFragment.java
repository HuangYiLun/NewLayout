package com.example.venson.soho.home_page.category_tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.venson.soho.HttpTask;
import com.example.venson.soho.R;
import com.example.venson.soho.MemberClass;


public class GetAllFragment extends Fragment {
    private static final String TAG = "getAllCaseFragment";
    private HttpTask caseGetAllTask;
    private RecyclerView rvGetAll;
    private MemberClass user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_getall_fragment, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {

    }



} // end
