package com.fatihsyams.friendlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fatihsyams.friendlist.helper.MyPref;
import com.fatihsyams.friendlist.model.User;
import com.fatihsyams.friendlist.retrofit.ApiClient;
import com.fatihsyams.friendlist.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText edtEmail, edtPass;
    MaterialButton btnClick;
    MyPref myPref;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        if (!(myPref.getUserid() == null)){
            gotoMain();
        }

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();

                if (email.isEmpty()){
                    edtEmail.setError("Email tidak boleh kosong !");
                }else if (pass.isEmpty()){
                    edtPass.setError("Pass Harus di isi ");
                }else {
                    Call<User> login = apiInterface.loginUser(email, pass);
                    login.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            myPref.saveUser(user);
                            gotoMain();
                            finish();

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    void gotoMain(){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void initView() {
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        myPref = new MyPref(this);
        edtEmail = findViewById(R.id.edt_email_login);
        edtPass = findViewById(R.id.edt_pass_login);
        btnClick = findViewById(R.id.btn_login);
    }


}
