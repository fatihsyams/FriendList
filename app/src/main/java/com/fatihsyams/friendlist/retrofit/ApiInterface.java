package com.fatihsyams.friendlist.retrofit;

import com.fatihsyams.friendlist.model.Status;
import com.fatihsyams.friendlist.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("rpld/get-all-user.php")
    Call<List<User>> getAllUser();

    @FormUrlEncoded
    @POST("rpld/insert-user.php")
    Call<Status> addUser (@Field("nama") String nama, @Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("rpld/login.php")
    Call<User> loginUser (@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("rpld/delete-user.php")
    Call<Status> deleteUser (@Field("id") String id);

    @FormUrlEncoded
    @POST("rpld/update-user.php")
    Call<Status> updateUser (@Field("id") String id, @Field("nama") String nama, @Field("email") String email, @Field("pass") String pass);

}
