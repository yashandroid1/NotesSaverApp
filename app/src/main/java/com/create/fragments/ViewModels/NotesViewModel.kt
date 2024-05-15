package com.create.fragments.ViewModels

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.create.fragments.models.AddNotes
import com.create.fragments.models.GetNotes
import com.create.fragments.repository.NotesRepository
import com.create.fragments.utils.Const.TAG
import com.create.fragments.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(private val noteRepository: NotesRepository ) : ViewModel() {


    val liveData:LiveData<NetworkResult<GetNotes>>
        get() = noteRepository.noteResponse

    val ed_livedata:LiveData<NetworkResult<AddNotes>>
     get() = noteRepository.editResponse

var count = 0

    fun getNote(email: String){
        viewModelScope.launch {
            noteRepository.getNote(email)

        }
    }

    fun addNotes(email:String , title:String , description:String){
        viewModelScope.launch {
            noteRepository.addNotes(email, title, description)
        }
    }

    fun updateNote(email: String , id:Int , title: String , description: String){
        viewModelScope.launch {
            noteRepository.updateNote(email, id, title, description)
        }
    }


}
