package com.example.venson.soho.member_page;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.venson.soho.Common;
import com.example.venson.soho.HttpTask;
import com.example.venson.soho.R;
import com.example.venson.soho.obj_classes.Album;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by luping on 2018/5/13.
 */

public class UploadDialogFragment
        extends DialogFragment implements DialogInterface.OnClickListener {
    private Bitmap bitmap;
    private static final String USER_SERVLET = "/UserServlet";
    private static final String TAG = "UploadDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText etImgName = new EditText(getActivity());
        final EditText etImgDes = new EditText(getActivity());
        final ImageView imvResult = new ImageView(getActivity());
        Bundle bundle = getArguments();

        try {
            String uriStr = bundle.getString("uri");
            InputStream imageStream = getActivity().getContentResolver().openInputStream(Uri.parse(uriStr));
            bitmap = BitmapFactory.decodeStream(imageStream);
            imvResult.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        etImgName.setHint("相片名稱");
        etImgDes.setHint("描述");

        linearLayout.addView(imvResult);
        linearLayout.addView(etImgName);
        linearLayout.addView(etImgDes);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirm_upload)
                .setMessage(R.string.img_info)
                .setPositiveButton(R.string.text_Yes, this)
                .setNegativeButton(R.string.text_No, this)
                .setView(linearLayout)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (Common.networkConnected(getActivity())) {
                    String url = Common.URL + USER_SERVLET;
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                    JsonObject jsonObject = new JsonObject();
                    Album album = (Album) getActivity().getSupportFragmentManager().findFragmentByTag("MemberAlbumImgFragment").getArguments().getSerializable("album");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String img64 = Base64.encodeToString(b, Base64.DEFAULT);
                    jsonObject.addProperty("action", "uploadImg");
                    jsonObject.add("album",gson.toJsonTree(album,Album.class));
                    jsonObject.addProperty("img64", img64);
                    HttpTask uploadTask = new HttpTask(url, gson.toJson(jsonObject));

                    try {
                        String jsonIn = uploadTask.execute().get();
                        if (Integer.valueOf(jsonIn) == -1) {
                            Common.showToast(getActivity(), "上傳失敗");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }

                } else {
                    Common.showToast(getActivity(), R.string.msg_NoNetwork);
                }
                break;
            default:
                dialog.cancel();
                break;
        }
    }
}
