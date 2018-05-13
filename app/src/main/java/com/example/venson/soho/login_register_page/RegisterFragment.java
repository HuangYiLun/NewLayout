package com.example.venson.soho.login_register_page;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.venson.soho.Common;
import com.example.venson.soho.R;
import com.example.venson.soho.home_page.HomeFragment;

public class RegisterFragment extends Fragment {
    private final static String TAG = "RegistFragment";
    private EditText etEmail, etPassword, etConfirmPassword, etName;
    private Button btRegister;
    private boolean userExit = false;
    boolean isInputValid = true;
    private RadioGroup rgSex;
    private int gender =1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_layout, container, false);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassWord);
        etName = view.findViewById(R.id.etName);
        btRegister = view.findViewById(R.id.btRegister);
        rgSex = view.findViewById(R.id.rgSex);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rbMen){
                    gender = 1;
                    Log.d(TAG,"gender=1");
                }else if (checkedId == R.id.rbWomen){
                    gender = 0;
                    Log.d(TAG,"gender=0");
                }

            }
        });



        //////註冊按鈕
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String name = etName.getText().toString().trim();


                if (userExit) {
                    isInputValid = false;
                }
                if (email.isEmpty()) {

                    isInputValid = false;
                }
                if (password.isEmpty()) {

                    isInputValid = false;
                }
                if (!confirmPassword.equals(password)) {

                    isInputValid = false;
                }
                if (name.isEmpty()) {

                    isInputValid = false;
                }


                if (isInputValid) {
                    String url = Common.URL + "UserServlet";
//                    User user = new User(email, password, name,gender);
                    int count = 0;
                    try {
//                        count = new UserRegisterTask().execute(url, "insert", user).get();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (count == 0) {
                        Common.showToast(getActivity(), "fail create");
                    } else {
                        SharedPreferences preferences = getActivity().getSharedPreferences(
                                Common.PREF_FILE, Context.MODE_PRIVATE);
                        preferences.edit().putBoolean("login", true)
                                .putString("email", email)
                                .putString("password", password).apply();
                        Fragment fragment = new HomeFragment();
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content, fragment).commit();
                    }
                }
            }
        });


        return view;
    }


}
