package com.create.fragments.api

import com.create.fragments.models.AddNotes
import com.create.fragments.models.GetNotes
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NotesApi {

    @POST("notesapi/getnotes.php")
    @FormUrlEncoded
    suspend fun getNotes(@Field("email")email:String ):Response<GetNotes>

    @POST("notesapi/notesadd.php")
    @FormUrlEncoded
    suspend fun addnote(@Field("email")email:String , @Field("title")title:String , @Field("description")description:String):Response<AddNotes>

    @POST("notesapi/updatenote.php")
    @FormUrlEncoded
    suspend fun noteUpdate(@Field("email")email: String , @Field("id")id:Int , @Field("title")title: String , @Field("description")description: String):Response<AddNotes>
}