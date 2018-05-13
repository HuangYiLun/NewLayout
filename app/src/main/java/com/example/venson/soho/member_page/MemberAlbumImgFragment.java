package com.example.venson.soho.member_page;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.GetImgTask;
import com.example.venson.soho.HttpTask;
import com.example.venson.soho.R;
import com.example.venson.soho.obj_classes.Album;
import com.example.venson.soho.obj_classes.AlbumImg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MemberAlbumImgFragment extends Fragment {
    private TextView tvAlbumNameTitle, tvUploadDate, tvEditDate;
    private Button btFilePick, btTakPhoto;
    private RecyclerView rvPicture;
    private static final String TAG = "MemperAlbumImgFragment";
    private static final String USER_SERVLET = "/UserServlet";
    private static final int REQ_TAKE_PICTURE = 0;
    private static final int REQ_PICK_IMAGE = 1;
    private static final int REQ_CROP_PICTURE = 2;
    private Uri contentUri, croppedImageUri;
    private byte[] image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_album_img, container, false);
        rvPicture = view.findViewById(R.id.rvPicture);
//        ImageView imvImg = view.findViewById(R.id.imvImg);
        List<AlbumImg> imgList = new ArrayList<>();
        rvPicture.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ImgAdapter adapter = new ImgAdapter(getActivity(), imgList);
        rvPicture.setAdapter(adapter);
        btFilePick = view.findViewById(R.id.btFilePick);
        btFilePick.setOnClickListener(new OnFilePickListener());
        btTakPhoto = view.findViewById(R.id.btTakePhoto);
        btTakPhoto.setOnClickListener(new OnTakePhotoListener());
        tvAlbumNameTitle = view.findViewById(R.id.tvAlbumNameTitle);
        tvUploadDate = view.findViewById(R.id.tvUploadDate);
        tvEditDate = view.findViewById(R.id.tvEditDate);
        //
        Bundle bundle = getArguments();
        if (bundle != null) {
            Album album = (Album) bundle.getSerializable("album");
            tvAlbumNameTitle.setText(String.valueOf(album.getAlbumName()));
            tvUploadDate.setText(String.valueOf(album.getCreateDate()));
            tvEditDate.setText(String.valueOf(album.getUpdateDate()));
            if (Common.networkConnected(getActivity())) {
                int albumId = album.getAlbumId();
                String url = Common.URL + USER_SERVLET;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getAlbumImgs");
                jsonObject.addProperty("albumId", albumId);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                String jsonOut = gson.toJson(jsonObject);
                HttpTask albumTask = new HttpTask(url, jsonOut);
                List<AlbumImg> imgs = null;
                try {
                    String jsonIn = albumTask.execute().get();
                    Log.d(TAG, jsonIn);
                    Type listType = new TypeToken<List<AlbumImg>>() {
                    }.getType();
                    imgs = gson.fromJson(jsonIn, listType);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if (imgs == null || imgs.isEmpty()) {
                    Common.showToast(getActivity(), "no img");
                } else {
                    adapter.setImgList(imgs);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Common.showToast(getActivity(), R.string.msg_NoNetwork);
            }
        }
        return view;
    }

    private class OnFilePickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_PICK_IMAGE);
//
        }
    }

        private class OnTakePhotoListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                File file = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                contentUri = FileProvider.getUriForFile(
                        getActivity(), getActivity().getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                if (isIntentAvailable(getActivity(), intent)) {
                    startActivityForResult(intent, REQ_TAKE_PICTURE);
                } else {
                    Common.showToast(getActivity(), R.string.no_camera_app);
                }
            }

    }
//    String name = etName.getText().toString().trim();
//                if (name.length() <= 0) {
//        Common.showToast(getActivity(), R.string.msg_NameIsInvalid);
//        return;
//    }
//    String phoneNo = etPhone.getText().toString().trim();
//    String address = etAddress.getText().toString().trim();
//    List<Address> addressList;
//    double latitude = 0.0;
//    double longitude = 0.0;
//                try {
//        addressList = new Geocoder(getActivity()).getFromLocationName(address, 1);
//        latitude = addressList.get(0).getLatitude();
//        longitude = addressList.get(0).getLongitude();
//    } catch (IOException e) {
//        Log.e(TAG, e.toString());
//    }
//                if (image == null) {
//        Common.showToast(getActivity(), R.string.msg_NoImage);
//        return;
//    }
//
//                if (Common.networkConnected(getActivity())) {
//        String url = Common.URL + "/SpotServlet";
//        Spot spot = new Spot(0, name, phoneNo, address, latitude, longitude);
//        String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("action", "spotInsert");
//        jsonObject.addProperty("spot", new Gson().toJson(spot));
//        jsonObject.addProperty("imageBase64", imageBase64);
//        int count = 0;
//        try {
//            String result = new MyTask(url, jsonObject.toString()).execute().get();
//            count = Integer.valueOf(result);
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//        if (count == 0) {
//            Common.showToast(getActivity(), R.string.msg_InsertFail);
//        } else {
//            Common.showToast(getActivity(), R.string.msg_InsertSuccess);
//        }
//    } else {
//        Common.showToast(getActivity(), R.string.msg_NoNetwork);
//    }
//    /* 回前一個Fragment */
//    getFragmentManager().popBackStack()

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List<AlbumImg> imgList;

        public void setImgList(List<AlbumImg> imgList) {
            this.imgList = imgList;
        }

        ImgAdapter(Context context, List<AlbumImg> imgList) {
            layoutInflater = LayoutInflater.from(context);
            this.imgList = imgList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imvImg;

            public MyViewHolder(View itemView) {
                super(itemView);
                imvImg = itemView.findViewById(R.id.imvImg);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.member_album_img_cardview, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final AlbumImg albumImg = imgList.get(position);
            String path = albumImg.getImgPath();
            if (Common.networkConnected(getActivity())) {
                String url = Common.URL + USER_SERVLET;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getImage");
                jsonObject.addProperty("path", path);
                GetImgTask getImgTask = new GetImgTask(url, path, 120, holder.imvImg);
                Bitmap bitmap = null;
                try {
                    bitmap = getImgTask.execute().get();
                    Log.d(TAG, bitmap.toString());

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if (bitmap == null) {
                    holder.imvImg.setImageResource(R.drawable.gallery);
                } else {
                    holder.imvImg.setImageBitmap(bitmap);
                }
            } else {
                Common.showToast(getActivity(), R.string.msg_NoNetwork);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new FullImgFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("img", albumImg);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });


        }
        @Override
        public int getItemCount() {
            return imgList.size();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle bundle=new Bundle();
        UploadDialogFragment uploadDialogFragment =new UploadDialogFragment();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_TAKE_PICTURE:
                    crop(contentUri);
                    bundle.putString("uri",contentUri.toString());
                    uploadDialogFragment.setArguments(bundle);
                    uploadDialogFragment.show(getActivity().getSupportFragmentManager(), "exit");
                    break;
                case REQ_PICK_IMAGE:
                    Uri uri = intent.getData();
                    crop(uri);
                    bundle.putString("uri",uri.toString());
                    uploadDialogFragment.setArguments(bundle);
                    uploadDialogFragment.show(getActivity().getSupportFragmentManager(), "exit");
                    break;
                case REQ_CROP_PICTURE:
                    Log.d(TAG, "REQ_CROP_PICTURE: " + croppedImageUri.toString());
                    try {
                        Bitmap picture = BitmapFactory.decodeStream(
                                getActivity().getContentResolver().openInputStream(croppedImageUri));
//                        ivSpot.setImageBitmap(picture);
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        picture.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        image = out.toByteArray();
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
            }
        }
    }
    private void crop(Uri sourceImageUri) {
        File file = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        file = new File(file, "picture_cropped.jpg");
        croppedImageUri = Uri.fromFile(file);
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // the recipient of this Intent can read soruceImageUri's data
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // set image source Uri and type
            cropIntent.setDataAndType(sourceImageUri, "image/*");
            // send crop message
            cropIntent.putExtra("crop", "true");
            // aspect ratio of the cropped area, 0 means user define
            cropIntent.putExtra("aspectX", 0); // this sets the max width
            cropIntent.putExtra("aspectY", 0); // this sets the max height
            // output with and height, 0 keeps original size
            cropIntent.putExtra("outputX", 0);
            cropIntent.putExtra("outputY", 0);
            // whether keep original aspect ratio
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedImageUri);
            // whether return data by the intent
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQ_CROP_PICTURE);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Common.showToast(getActivity(), "This device doesn't support the crop action!");
        }
    }


}

