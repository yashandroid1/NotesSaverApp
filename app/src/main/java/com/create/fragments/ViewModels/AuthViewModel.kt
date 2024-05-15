package com.create.fragments.ViewModels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.create.fragments.models.UserResponse
import com.create.fragments.repository.UserRepository
import com.create.fragments.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponse


    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginUser(email, password)
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.signup(name, email, password)
        }

    }

    fun provideSignupDetails(name: String , email:String , password:String):Pair<Boolean , String>{

        var result = Pair(true , "")

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            result = Pair(false , "Provide Required Details")

        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            result = Pair(false , "InValid Email")

        }

        else if (password.length <=4){
            result = Pair(false , "Minimum 5 Characters")
        }

            return result
    }


    fun provideDetailsLogin(email: String, password: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            result = Pair(false , "Provide Required Details")

        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            result = Pair(false , "InValid Email")


        }

        else if (password.length <=4){
            result = Pair(false , "Minimum 5 Character")
        }

        return result

    }

}
