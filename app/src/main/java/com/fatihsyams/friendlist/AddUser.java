package com.fatihsyams.friendlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fatihsyams.friendlist.model.Status;
import com.fatihsyams.friendlist.retrofit.ApiClient;
import com.fatihsyams.friendlist.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

    EditText edtNama, edtEmail, edtPassword, edtRePassword;
    Button btnSave;

    String id;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initView();
        getDataFromAdapter();

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (btnSave.getText().toString().equals("Simpan")) {
                    addUser();
                } else {
                    updateUser();
                }
            }
        });
    }

    public void addUser() {
        String nama = edtNama.getText().toString();
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();
        String repass = edtRePassword.getText().toString();

        if (repass.equals(pass)) {
            Call<Status> addData = apiInterface.addUser(nama, email, pass);
            addData.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    Status status = response.body();
                    Toast.makeText(AddUser.this, status.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    Toast.makeText(AddUser.this, "Error  :" + t, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            edtRePassword.setError("Sandi Berbeda");
        }
    }

    private void getDataFromAdapter() {
        int action = getIntent().getIntExtra("action", 0);
        id = getIntent().getStringExtra("id");
        String nama = getIntent().getStringExtra("nama");
        String email = getIntent().getStringExtra("email");
        String pass = getIntent().getStringExtra("pass");

        if (action == 1) {
            edtNama.setText(nama);
            edtEmail.setText(email);
            edtPassword.setText(pass);
            edtRePassword.setText(pass);

            btnSave.setText("Update");
        }
    }

    private void initView() {

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        edtNama = findViewById(R.id.edt_nama);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_pass);
        edtRePassword = findViewById(R.id.edt_repass);
        btnSave = findViewById(R.id.btn_simpan);
    }

    void updateUser() {

        String nama = edtNama.getText().toString();
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();
        String repass = edtRePassword.getText().toString();

        Call<Status> updateUser = apiInterface.updateUser(id, nama, email, pass);
        updateUser.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                Toast.makeText(AddUser.this, status.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }
}