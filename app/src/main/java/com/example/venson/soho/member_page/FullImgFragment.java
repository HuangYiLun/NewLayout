package com.example.venson.soho.member_page;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.GetImgTask;
import com.example.venson.soho.R;
import com.example.venson.soho.obj_classes.AlbumImg;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * Created by luping on 2018/5/11.
 */

public class FullImgFragment extends Fragment {

    private ImageView imvImgFull;
    private TextView tvImgName, tvImgUploadDate, tvImgEditDate, tvImgDes;
    private ImageButton imbEdit, imbDelete;
    private static final String TAG = "FullImgFragment";
    private static final String USER_SERVLET = "/UserServlet";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.img_full_fragment, container, false);
        imvImgFull = view.findViewById(R.id.imvImgFull);
        tvImgName = view.findViewById(R.id.tvImgName);
        tvImgUploadDate = view.findViewById(R.id.tvImgUploadDate);
        tvImgEditDate = view.findViewById(R.id.tvImgEditDate);
        tvImgDes = view.findViewById(R.id.tvImgDes);
        imbEdit = view.findViewById(R.id.imbEdit);
        imbDelete = view.findViewById(R.id.imbDelete);
        imbEdit.setOnClickListener(new OnEditListener());
        imbDelete.setOnClickListener(new OnDeleteListener());
        Bundle bundle = getArguments();
        if (bundle != null) {
            AlbumImg albumImg = (AlbumImg) bundle.get("img");
            Date upload = albumImg.getImgCreateDate();
            Date edit = albumImg.getImgLastEditDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tvImgName.setText(String.valueOf(albumImg.getImgName()));
            tvImgUploadDate.setText(simpleDateFormat.format(upload));
            tvImgEditDate.setText(simpleDateFormat.format(edit));
            tvImgDes.setText(String.valueOf(albumImg.getImgDes()));
            if (Common.networkConnected(getActivity())) {
                String path = albumImg.getImgPath();
                String url = Common.URL + USER_SERVLET;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getFullImage");
                jsonObject.addProperty("path", path);
                GetImgTask getImgTask = new GetImgTask(url, path, 1000, imvImgFull);
                Bitmap bitmap = null;
                try {
                    bitmap = getImgTask.execute().get();
                    Log.d(TAG, bitmap.toString());

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if (bitmap == null) {
                    Common.showToast(getActivity(), "找不到此圖片");
                } else {
                    imvImgFull.setImageBitmap(bitmap);
                }
            } else {
                Common.showToast(getActivity(), R.string.msg_NoNetwork);
            }
        }
        return view;
    }

    class OnDeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new DeleteDialog().show(getActivity().getSupportFragmentManager(), "exit");
        }
    }

    class OnEditListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new EditDialog().show(getActivity().getSupportFragmentManager(), "exit");
        }
    }

    private abstract static class DialogManager
            extends DialogFragment implements DialogInterface.OnClickListener {
    }

    public static class EditDialog extends DialogManager {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final EditText etName = new EditText(getContext());
            final EditText etDes = new EditText(getContext());
            etName.setHint("圖片名稱");
            etDes.setHint("簡介");
            linearLayout.addView(etName);
            linearLayout.addView(etDes);
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.edit_title)
                    .setView(linearLayout)
                    .setMessage(R.string.edit_msg)
                    .setPositiveButton(R.string.text_Yes, this)
                    .setNegativeButton(R.string.text_No, this)
                    .create();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    }

    public static class DeleteDialog extends DialogManager {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.confirm_delete_title)
                    .setMessage(R.string.confirm_delete_msg).setPositiveButton(R.string.text_Yes, this)
                    .setNegativeButton(R.string.text_No, this)
                    .create();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    dialogInterface.cancel();
                    if (Common.networkConnected(getActivity())) {

                    }
                    break;
                default:
                    dialogInterface.cancel();
                    break;
            }
        }
    }
//    public static class AlertDialogFragment
//            extends DialogFragment implements DialogInterface.OnClickListener {
//        @NonNull
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//            return new AlertDialog.Builder(getActivity())
//                    .setTitle(R.string.text_Exit)
//                    .setIcon(R.drawable.ic_alert)
//                    .setMessage(R.string.msg_WantToExit)
//                    .setPositiveButton(R.string.text_Yes, this)
//                    .setNegativeButton(R.string.text_No, this)
//                    .create();
//        }
//
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            switch (which) {
//                case DialogInterface.BUTTON_POSITIVE:
//                    getActivity().finish();
//                    break;
//                default:
//                    dialog.cancel();
//                    break;
//            }
//        }
//    }
}


