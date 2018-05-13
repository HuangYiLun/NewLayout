package com.example.venson.soho.home_page;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.venson.soho.R;
import com.example.venson.soho.member_page.MemberFragment;
import com.example.venson.soho.obj_classes.User;

import java.util.List;

/**
 * Created by luping on 2018/5/12.
 */

public class MemberAdapter extends MyAdapterManager<MemberAdapter.MyViewHolder, User> {
    private LayoutInflater layoutInflater;

    public MemberAdapter(List<User> dataList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        super.dataList = dataList;
    }

    public class MyViewHolder extends MyAdapterManager.PolyViewHolder {
        TextView tvUserName, tvDes, tvSkill, tvTag;
        ImageView imvUserPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDes = itemView.findViewById(R.id.tvDes);
            tvSkill = itemView.findViewById(R.id.tvSkill);
            tvTag = itemView.findViewById(R.id.tvTag);
            imvUserPic = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    void setItemList(List<User> list) {
        super.dataList = list;
    }

    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.member_item_cardview, parent, false);
        return new MemberAdapter.MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
        final User u = dataList.get(position);
        myViewHolder.tvUserName.setText(String.valueOf(u.getUserName()));
        myViewHolder.tvDes.setText(String.valueOf(u.getUserSelfDes()));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment memberFragment = new MemberFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", u);
                memberFragment.setArguments(bundle);
                FragmentManager manager =((AppCompatActivity)myViewHolder.itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content, memberFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

    }


}