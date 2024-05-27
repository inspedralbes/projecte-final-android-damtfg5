package com.example.projectofinal;

import java.util.List;
import java.util.Map;

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
    Call<TeamData> createTeam(@Body TeamData teamData);

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

    @POST("/getSpaces")
    Call<List<Space>> getSpaces(@Body MunicipiRequest municipiRequest);

    @POST("/insertGameUserStats")
    Call<Void> insertGameUserStats(@Body GameUserStats userStats);
    @POST("/addGame")
    Call<Void> addGame(@Body GameData gameData);

    @POST("/getUserForAndroid")
    Call<GlobalDataUser> getUserForAndroid(@Body Map<String, Integer> userId);

    @POST("/getUser")
    Call<Usuario> getUser(@Body Map<String, Integer> userId);

    @POST("hoursPickedByDay")
    Call<List<MatchTime>> getHoursByDay(@Body DayLocationRequest request);

    @GET("/getMunicipis")
    Call<List<String>> getMunicipis();

    @POST("/updateUserForAndroid")
    Call<Void> updateUserForAndroid(@Body UpdateUserRequest updateUserRequest);
    @POST("updateUserPreferencesForAndroid")
    Call<Void> updateUserPreferencesForAndroid(@Body Map<String, Object> requestBody);
}
