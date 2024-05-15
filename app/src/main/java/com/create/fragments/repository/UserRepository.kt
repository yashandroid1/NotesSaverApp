package com.create.fragments.repository

import com.create.fragments.api.UserApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.create.fragments.models.UserResponse
import com.create.fragments.utils.NetworkResult
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponse = MutableLiveData<NetworkResult<UserResponse>>()
     val userResponse : LiveData<NetworkResult<UserResponse>>
        get() = _userResponse

    suspend fun loginUser(email:String , password:String){

        _userResponse.postValue(NetworkResult.Loading())

        val response = userApi.login(email , password)
        if (response.isSuccessful && response.body()?.error.toString()== "200"){

           _userResponse.postValue(NetworkResult.Sucess(response.body()!!))

        }

        else if (response.body()!!.error!!.contains("inValid id")){

            _userResponse.postValue(NetworkResult.Error("User Not exist"))

        }

        else{

            _userResponse.postValue(NetworkResult.Error("Wrong Password"))

        }
    }

    suspend fun signup(name:String , email: String , password: String){
        val response = userApi.signup(name , email , password)

        _userResponse.postValue(NetworkResult.Loading())

        if (response.isSuccessful && response.body()!!.user!=null){
            _userResponse.postValue(NetworkResult.Sucess(response.body()!!))
        }

        else{
            _userResponse.postValue(NetworkResult.Error("Already Exist"))
        }

        }
    }