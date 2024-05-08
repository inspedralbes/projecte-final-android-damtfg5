package com.example.projectofinal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/createTeam")
    Call<Void> createTeam(@Body TeamData teamData);

    @GET("/getUsers")
    Call<List<Usuario>> getUsers();

    @POST("/sendFriendRequest")
    Call<Void> sendFriendRequest(@Body FriendRequestBody friendRequestBody);

    @POST("authoritzationLogin")
    Call<LoginResponse> login(@Body LoginRequestBody loginRequestBody);

    @POST("/pendingFriendRequests")
    Call<List<Usuario>> getPendingFriendRequests(@Body UserIdRequest userIdRequest);

}
