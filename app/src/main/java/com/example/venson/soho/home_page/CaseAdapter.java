package com.example.venson.soho.home_page;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.HttpTask;
import com.example.venson.soho.R;
import com.example.venson.soho.obj_classes.Case;
import com.example.venson.soho.obj_classes.CaseTag;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luping on 2018/5/12.
 */

public class CaseAdapter extends MyAdapterManager<CaseAdapter.MyViewHolder, Case> {
    private static final String TAG="CaseAdapter";
    private LayoutInflater layoutInflater;
    //       private  List<Case> dataList;
    private ArrayList<CaseTag> caseTags;

    CaseAdapter(List<Case> dataList, Context context) {
        layoutInflater = LayoutInflater.from(context);
        super.dataList = dataList;
    }

    //        private List<Case> getItemList() {
//            return dataList;
//        }
    @Override
    void setItemList(List<Case> list) {
        super.dataList = list;
    }

    class MyViewHolder extends MyAdapterManager.PolyViewHolder {
        TextView tvCaseContent, tvCasePayMin, tvCasePayMax, tvCaseDate, tvCaseTag, tvCaseLocation, tvCaseNameId;

        MyViewHolder(View itemView) {
            super(itemView);
            tvCaseNameId = itemView.findViewById(R.id.case_name_id);
            tvCaseContent = itemView.findViewById(R.id.case_content);
            tvCasePayMin = itemView.findViewById(R.id.case_pay_min);
            tvCasePayMax = itemView.findViewById(R.id.case_pay_max);
            tvCaseDate = itemView.findViewById(R.id.case_date);
            tvCaseTag = itemView.findViewById(R.id.case_skill);
            tvCaseLocation = itemView.findViewById(R.id.case_location);
        }
    }

    @Override
    public CaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.case_item_cardview, parent, false);
        return new CaseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
        final Case c = dataList.get(position);
        final AppCompatActivity activity=(AppCompatActivity) myViewHolder.itemView.getContext();
        if (Common.networkConnected(activity)){
            String url = Common.URL + "/CaseServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "findTags");
            jsonObject.addProperty("caseId", c.getCaseId());
            Gson gson = new Gson();
            String jsonOut = gson.toJson(jsonObject);
            HttpTask caseTagTask = new HttpTask(url, jsonOut);
            try {
                String jsonIn = caseTagTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List<CaseTag>>() {
                }.getType();
                caseTags = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (caseTags == null || caseTags.isEmpty()) {
                Common.showToast(activity, R.string.msg_NoCases);
                myViewHolder.tvCaseTag.setText("");
            } else {
                String text = "";
                for (int i = 0; i < caseTags.size(); i++) {
                    text += caseTags.get(i).getName();
                }
                myViewHolder.tvCaseTag.setText(text + " ");
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
        Date date = c.getCaseRecruitStart();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        myViewHolder.tvCaseNameId.setText(c.getCaseName());
        myViewHolder.tvCaseContent.setText(c.getCaseDes());
        myViewHolder.tvCasePayMin.setText(String.valueOf(c.getCasePayMin()));
        myViewHolder.tvCasePayMax.setText(String.valueOf(c.getCasePayMax()));
        myViewHolder.tvCaseDate.setText(dateStr);
        myViewHolder.tvCaseLocation.setText(c.getCaseLocation());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CaseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", c);
                bundle.putSerializable("caseTags", caseTags);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    //on bind
    @Override
    public int getItemCount() {

        return dataList.size();
    }

}