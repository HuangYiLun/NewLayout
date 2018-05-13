package com.example.venson.soho.member_page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.HttpTask;
import com.example.venson.soho.R;
import com.example.venson.soho.obj_classes.Album;
import com.example.venson.soho.obj_classes.CaseTag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MemberAlbumFragment extends Fragment {
    private static final String TAG = "MemberAlbumFragment";
    private RecyclerView rvAlbum;
    //    private TextView tvTitle;
    private static final String USER_SERVLET = "/UserServlet";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_album, container, false);
        rvAlbum = view.findViewById(R.id.rvAlbum);
        rvAlbum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        AlbumAdapter albumAdapter = new AlbumAdapter(getContext(), new ArrayList<Album>());
        rvAlbum.setAdapter(albumAdapter);
//        tvTitle = view.findViewById(R.id.tvTool_bar_title);
//        tvTitle.setText(R.string.add_album_title);
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + USER_SERVLET;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAlbums");
            jsonObject.addProperty("userId", 1);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String jsonOut = gson.toJson(jsonObject);
            HttpTask albumTask = new HttpTask(url, jsonOut);
            List<Album> albumList = null;
            try {
                String jsonIn = albumTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List<Album>>() {
                }.getType();
                albumList = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (albumList == null || albumList.isEmpty()) {
                Common.showToast(getActivity(), "無相簿");
            } else {
                albumAdapter.setAlbumList(albumList);
                albumAdapter.notifyDataSetChanged();
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }

        return view;
    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Album> albumList;

        private void setAlbumList(List<Album> albumList) {
            this.albumList = albumList;
        }

        AlbumAdapter(Context context, List<Album> albumList) {
            layoutInflater = LayoutInflater.from(context);
            this.albumList = albumList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imvAlbumThumb;
            private TextView tvAlbumName, tvAlbumDes;

            public MyViewHolder(View itemView) {
                super(itemView);
                imvAlbumThumb = itemView.findViewById(R.id.imvAlbumThumb);
                tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
                tvAlbumDes = itemView.findViewById(R.id.tvAlbumDes);
            }
        }

        @Override
        public AlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.member_album_cardview, parent, false);
            return new AlbumAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Album album = albumList.get(position);
            holder.tvAlbumName.setText(String.valueOf(album.getAlbumName()));
            holder.tvAlbumDes.setText(String.valueOf(album.getAlbumDes()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new MemberAlbumImgFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("album",album);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment,"MemberAlbumImgFragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

        }


        @Override
        public int getItemCount() {
            return albumList.size();
        }


    }


}
