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

    @POST("/getUsers")
    Call<List<Usuario>> getUsers(@Body UserIdRequest userIdRequest);

    @POST("/sendFriendRequest")
    Call<Void> sendFriendRequest(@Body FriendRequestBody friendRequestBody);

    @POST("/pendingFriendRequests")
    Call<List<Usuario>> getPendingFriendRequests(@Body UserIdRequest userIdRequest);

    @POST("/responseFriendRequest")
    Call<Void> responseFriendRequest(@Body ResponseFriendRequest responseFriendRequest);

    @POST("/getTeam")
    Call<List<TeamData>> getTeam(@Body UserIdRequest userId);

    @POST("/inviteUserToTeam")
    Call<Void> inviteUserToTeam(@Body InviteBody inviteBody);

    @POST("/pendingTeamInvitations")
    Call<List<TeamData>> getPendingTeamInvitations(@Body UserIdRequest userIdRequest);

    @POST("/responseTeamInvitation")
    Call<Void> responseTeamInvitation(@Body ResponseFriendRequest responseFriendRequest);

    @POST("/teamUsers")
    Call<List<Usuario>> getTeamUsers(@Body TeamIdRequest idTeam);

}
