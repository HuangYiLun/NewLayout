package com.example.venson.soho.home_page;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by luping on 2018/5/12.
 */

//abstract Adapter Manager
public abstract class MyAdapterManager<P extends MyAdapterManager.PolyViewHolder, T> extends RecyclerView.Adapter<P> {
    protected List<T> dataList;
    //T defined for dateList's data Type
    //P defined for PolyViewHolder

    //change dataList
    abstract void setItemList(List<T> list);

    public abstract class PolyViewHolder extends RecyclerView.ViewHolder {
        public PolyViewHolder(View itemView) {
            super(itemView);
        }
    }
}