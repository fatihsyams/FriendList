package com.fatihsyams.friendlist.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fatihsyams.friendlist.AddUser;
import com.fatihsyams.friendlist.MainActivity;
import com.fatihsyams.friendlist.R;
import com.fatihsyams.friendlist.model.Status;
import com.fatihsyams.friendlist.model.User;
import com.fatihsyams.friendlist.retrofit.ApiClient;
import com.fatihsyams.friendlist.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    List<User> listUser;
    Context context;
    ApiInterface apiInterface;

    public RecAdapter(List<User> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_contact, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.setItems(listUser.get(i));
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String[] item = {"Perbaharuhi", "Hapus"};
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Pilihan")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 1){
                                    deleteUser(listUser.get(i).getId());
                                }else if(which == 0){
                                    gotoUpdateUser(listUser.get(i)  );
                                }
                            }
                        })
                        .setNegativeButton("Batal", null)
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvEmail;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvEmail = itemView.findViewById(R.id.tv_email);
            tvNama = itemView.findViewById(R.id.tv_nama);

        }
        void setItems(User user){
            tvNama.setText(user.getNama());
            tvEmail.setText(user.getEmail());
        }
    }

    void deleteUser(String id){
        Call<Status> deleteUser = apiInterface.deleteUser(id);
        deleteUser.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                Toast.makeText(context, status.getMessage(), Toast.LENGTH_SHORT).show();
                MainActivity.mainActivity.getDataFromServer();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    void gotoUpdateUser(User user){
        Intent intent = new Intent(context, AddUser.class);
        intent.putExtra("action", 1);
        intent.putExtra("id", user.getId());
        intent.putExtra("nama", user.getNama());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("pass", user.getPass());
        context.startActivity(intent);
    }
}
