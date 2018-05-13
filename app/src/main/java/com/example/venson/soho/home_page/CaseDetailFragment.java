package com.example.venson.soho.home_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.venson.soho.R;
import com.example.venson.soho.obj_classes.Case;
import com.example.venson.soho.obj_classes.CaseTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CaseDetailFragment extends Fragment {
    private TextView detail_tvCase, detail_tvLocation, detail_tvConten,
            detail_tvExpire, detail_tvRelease, detail_tvSkill, detail_tvUser,
            detail_tvWrokDay, detail_tvPay_min, detail_tvPay_max, detail_tvCompany;
    private TextView tvToolbar_title;
    private Button detail_btApply;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_case_detail_fragment, container, false);
        findView(view);
        tvToolbar_title.setText(R.string.case_detail);
        Case c = (Case) getArguments().getSerializable("c");
        ArrayList<CaseTag> caseTags = (ArrayList<CaseTag>) getArguments().getSerializable("caseTags");
        if (c != null && caseTags != null) {
            detail_tvUser.setText(String.valueOf(c.getCaseOwnerId()));
            detail_tvCase.setText(c.getCaseName());
            detail_tvLocation.setText(c.getCaseLocation());
            detail_tvConten.setText(c.getCaseDes());
            String text = "";
            for (int i = 0; i < caseTags.size(); i++) {
                text += caseTags.get(i).getName();
            }
            detail_tvSkill.setText(text);
            detail_tvPay_min.setText(String.valueOf(c.getCasePayMin()));
            detail_tvPay_max.setText(String.valueOf(c.getCasePayMax()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date expire = c.getCaseRecruitEnd();
            Date release = c.getCaseRecruitStart();
            String expireStr = dateFormat.format(expire);
            String releaseStr = dateFormat.format(release);
            detail_tvExpire.setText(expireStr);
            detail_tvRelease.setText(releaseStr);
        }

        return view;
    }


    private void findView(View view) {
        detail_tvCase = view.findViewById(R.id.case_detail_tvCase);
        detail_tvLocation = view.findViewById(R.id.case_detail_tvLocation);
        detail_tvCompany = view.findViewById(R.id.case_detail_tvCompany);
        detail_tvConten = view.findViewById(R.id.case_detail_tvConten);
        detail_tvExpire = view.findViewById(R.id.case_detail_tvExpire);
        detail_tvRelease = view.findViewById(R.id.case_detail_tvRelease);
        detail_tvPay_min = view.findViewById(R.id.case_detail_tvPay_min);
        detail_tvPay_max = view.findViewById(R.id.case_detail_tvPay_max);
        detail_tvSkill = view.findViewById(R.id.case_detail_tvSkill);
        detail_tvUser = view.findViewById(R.id.case_detail_tvUser);
        detail_btApply = view.findViewById(R.id.case_detail_btApply);
        detail_tvWrokDay = view.findViewById(R.id.case_detail_tvWrokDay);
        tvToolbar_title = view.findViewById(R.id.tvTool_bar_title);

    }
}
