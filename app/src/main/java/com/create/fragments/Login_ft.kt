package com.create.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.create.fragments.ViewModels.AuthViewModel
import com.create.fragments.databinding.FragmentLoginFtBinding
import com.create.fragments.models.UserRequest
import com.create.fragments.utils.Const.TAG
import com.create.fragments.utils.NetworkResult
import com.create.fragments.utils.TOKEN_MANAGER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Login_ft : Fragment() {

    private var _binding:FragmentLoginFtBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TOKEN_MANAGER

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentLoginFtBinding.inflate(inflater , container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.signupTxtOpnr.setOnClickListener {
            findNavController().navigate(R.id.action_login_ft_to_signup_ft)

        }




        binding.loginBtn.setOnClickListener {


                val validAtionResult = validUserInput()


                if (validAtionResult.first){

                    binding.progress.setVisibility(View.VISIBLE)

                    authViewModel.login(getUserRequset().email, getUserRequset().password)
                }
                else{

                    binding.progress.setVisibility(View.GONE)
                    binding.lgnErrorTxt.text = validAtionResult.second.toString()
                    binding.errorFrame.setVisibility(View.VISIBLE)

                }




        }

        observer()

    }

   private fun observer(){

       var count = 0
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            ++count
            Log.d(TAG , count.toString()+"plus")
            when (it) {
                is NetworkResult.Sucess -> {
                      binding.progress.setVisibility(View.GONE)
                    Toast.makeText(requireContext(), "Login Sucess", Toast.LENGTH_SHORT).show()
                      tokenManager.saveEmail(it.data!!.user.Email)
                   findNavController().navigate(R.id.action_login_ft_to_home_ft)

                }

                is NetworkResult.Error -> {
                    binding.progress.setVisibility(View.GONE)
                    binding.lgnErrorTxt.text = it.message
                    binding.errorFrame.setVisibility(View.VISIBLE)

                }

                is NetworkResult.Loading -> {

                 //binding.lgnErrorTxt.setVisibility(View.VISIBLE)

                }
               else -> {}
            }
        })
    }

    private fun getUserRequset():UserRequest{
        val email = binding.lgEmail.text.toString()
        val password = binding.lgPassword.text.toString()
        return UserRequest(email, password)
    }

    private fun validUserInput():Pair<Boolean , String>{

        val userRequest = getUserRequset()

        return authViewModel.provideDetailsLogin(userRequest.email   , userRequest.password)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}