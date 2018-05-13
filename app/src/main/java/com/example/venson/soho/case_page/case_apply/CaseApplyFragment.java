package com.example.venson.soho.case_page.case_apply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.venson.soho.R;

/**
 * Created by ricky on 2018/4/30.
 */

public class CaseApplyFragment extends Fragment {
    private CaseData caseData;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            caseData = (CaseData) getArguments().getSerializable("caseData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.caseapply_fragment, container, false);
        if (getArguments() != null) {
            caseData = (CaseData) getArguments().getSerializable("caseinfo");
        }
//        TextView tvCaseName = view.findViewById(R.id.tvCaseName);
//        tvCaseName.setText(caseData.getCasename());
//
//        TextView tvCaseNeed = view.findViewById(R.id.tvCaseNeed);
//        tvCaseNeed.setText(caseData.getCaseneed());
        return view;
    }
}