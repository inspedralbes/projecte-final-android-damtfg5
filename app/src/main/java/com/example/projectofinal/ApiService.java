package com.example.projectofinal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("authoritzationLogin")
    Call<LoginResponse> login(@Body LoginRequestBody loginRequestBody);

    @POST("/registerUser")
    Call<Void> registerUser(@Body RegisterRequest registerRequest);

    @POST("/createTeam")
    Call<Void> createTeam(@Body TeamData teamData);

    @GET("/getUsers")
    Call<List<Usuario>> getUsers();

    @POST("/sendFriendRequest")
    Call<Void> sendFriendRequest(@Body FriendRequestBody friendRequestBody);

    @POST("/pendingFriendRequests")
    Call<List<Usuario>> getPendingFriendRequests(@Body UserIdRequest userIdRequest);

    @POST("/responseFriendRequest")
    Call<Void> responseFriendRequest(@Body ResponseFriendRequest responseFriendRequest);

    @POST("/getTeam")
    Call<List<TeamData>> getTeam(@Body UserIdRequest userId);

}
