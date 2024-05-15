package com.create.fragments.api


import com.create.fragments.models.UserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface UserApi {


    @FormUrlEncoded
    @POST("notesapi/signin.php")
    suspend fun login(@Field("email") email : String,@Field("password") password : String) : Response<UserResponse>

    @FormUrlEncoded
    @POST("notesapi/signup.php")
    suspend fun signup (@Field("name") name:String ,@Field("email") email : String,@Field("password") password : String):Response<UserResponse>
}