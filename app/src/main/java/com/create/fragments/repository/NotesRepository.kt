package com.create.fragments.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.create.fragments.api.NotesApi
import com.create.fragments.models.AddNotes
import com.create.fragments.models.GetNotes
import com.create.fragments.utils.NetworkResult
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesApi: NotesApi)  {

    private val _noteResponse = MutableLiveData<NetworkResult<GetNotes>>()
    val noteResponse:LiveData<NetworkResult<GetNotes>>
        get() = _noteResponse

    private val _editResponse = MutableLiveData<NetworkResult<AddNotes>>()
    val editResponse:LiveData<NetworkResult<AddNotes>>
        get() = _editResponse

    suspend fun getNote(email:String){
        _noteResponse.postValue(NetworkResult.Loading())
        val response = notesApi.getNotes(email)

        if (response.isSuccessful && response.body()!!.error!="400"){

         _noteResponse.postValue(NetworkResult.Sucess(response.body()!!))

       }
        else{
            _noteResponse.postValue(NetworkResult.Error("No Data"))
        }
    }

    suspend fun addNotes(email:String , title:String , description:String){
        val response = notesApi.addnote(email, title, description)

         if (response.isSuccessful && response.body()?.notes!=null){
             _editResponse.postValue(NetworkResult.Sucess(response.body()))

         }

        else{
            _editResponse.postValue(NetworkResult.Error("Not Autorised User"))
        }
    }

    suspend fun updateNote(email: String , id:Int , title: String , description: String){

        _editResponse.postValue(NetworkResult.Loading())
        val response = notesApi.noteUpdate(email , id , title , description)

        if (response.isSuccessful && response.body()!!.error=="200"){
            _editResponse.postValue(NetworkResult.Sucess(response.body()))
        }
        else{
            _editResponse.postValue(NetworkResult.Error(response.body()!!.message))
        }
    }
}