package com.fatihsyams.friendlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fatihsyams.friendlist.Adapter.RecAdapter;
import com.fatihsyams.friendlist.helper.MyPref;
import com.fatihsyams.friendlist.model.Status;
import com.fatihsyams.friendlist.model.User;
import com.fatihsyams.friendlist.retrofit.ApiClient;
import com.fatihsyams.friendlist.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecAdapter adapter;
    RecyclerView recMain;
    ApiInterface apiInterface;
    List<User> listUser;
    MyPref myPref;
    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();

    }

    public void getDataFromServer() {
        Call<List<User>> getAllUser = apiInterface.getAllUser();
        getAllUser.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listUser = response.body();
                setRecItems();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Gagal", "onFailure : " + t);
            }
        });
    }

    private void setRecItems() {
        adapter = new RecAdapter(listUser, this);
        recMain.setLayoutManager(new LinearLayoutManager(this));
        recMain.setAdapter(adapter);
    }

    private void initView() {
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        recMain = findViewById(R.id.rec_main);
        myPref = new MyPref(this);
        mainActivity = this;
    }

    public void gotoAddUser(View v){
        startActivity(new Intent(this, AddUser.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            AlertDialog.Builder alertLogout = new AlertDialog.Builder(this);

            alertLogout
                    .setTitle("Keluar")
                    .setMessage("Ingin Keluar ?")
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myPref.deleteUser();
                        }
                    })
                    .setNegativeButton("Batal", null)
                    .show();


        }
        return true;
    }

}
