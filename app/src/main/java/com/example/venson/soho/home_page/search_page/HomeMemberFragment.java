package com.example.venson.soho.home_page.search_page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.HttpTask;
import com.example.venson.soho.R;
import com.example.venson.soho.MemberClass;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by venson on 2018/4/28.
 */

public class HomeMemberFragment extends Fragment {
    private static final String TAG = "getAllMemeberFragment";
    private HttpTask memberGetAllTask;
    private RecyclerView rvMemeber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_member_fragment, container, false);
        findView(view);
        rvMemeber.setLayoutManager(new LinearLayoutManager(getActivity()));
        showMember();
        return view;
    }

    private void showMember() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/MemberServlet";
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
            List<MemberClass> users = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            String jsonOut = jsonObject.toString();
            memberGetAllTask = new HttpTask(url, jsonOut);
            try {
                String jsonIn = memberGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List<MemberClass>>() {
                }.getType();
                users = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (users == null || users.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoMember);
            } else {
                rvMemeber.setAdapter(new getAllMemberAdapter(users, getContext()));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    private void findView(View view) {
        rvMemeber = view.findViewById(R.id.rvMember);
    }

    private class getAllMemberAdapter extends RecyclerView.Adapter<getAllMemberAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List<MemberClass> users;
        public getAllMemberAdapter(List<MemberClass> users, Context context) {
            this.users = users;
            layoutInflater = LayoutInflater.from(context);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView member_tvUser,member_tvDescripition,member_tvSkill;
            public MyViewHolder(View itemView) {
                super(itemView);
                member_tvUser = itemView.findViewById(R.id.tvUserName);
                member_tvDescripition = itemView.findViewById(R.id.tvDes);
                member_tvSkill = itemView.findViewById(R.id.tvSkill);
            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.member_item_cardview, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            MemberClass user = users.get(position);
            myViewHolder.member_tvUser.setText(user.getName());
            myViewHolder.member_tvDescripition.setText(user.getDescription());
            myViewHolder.member_tvSkill.setText(user.getSkill());
        }


        @Override
        public int getItemCount() {
            return users.size();
        }


    }





} // end
