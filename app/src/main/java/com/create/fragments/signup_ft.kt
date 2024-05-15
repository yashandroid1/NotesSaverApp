package com.create.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.create.fragments.ViewModels.AuthViewModel
import com.create.fragments.databinding.FragmentSignupFtBinding
import com.create.fragments.models.RegisterRequest
import com.create.fragments.utils.NetworkResult
import com.create.fragments.utils.TOKEN_MANAGER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class signup_ft : Fragment() {

    private var _binding:FragmentSignupFtBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TOKEN_MANAGER

    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupFtBinding.inflate(inflater , container , false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigate(R.id.action_signup_ft_to_login_ft)
        }

        binding.loginTxtOpnr.setOnClickListener {
            findNavController().navigate(R.id.action_signup_ft_to_login_ft)
        }

        binding.signupBtn.setOnClickListener {
            count =1

            if (ValidUserInput().first){
                binding.progress.setVisibility(View.VISIBLE)
                authViewModel.signup(getUserRequest().name , getUserRequest().email , getUserRequest().password)


            }

            else{

                binding.sgnErrorTxt.text = ValidUserInput().second
                binding.errorFrameSg.setVisibility(View.VISIBLE)
                binding.errorFrameSg.setVisibility(View.VISIBLE)
            }
        }

        observer()

    }

    private fun observer(){
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            if (count == 1){
                count = 0
                when (it) {
                    is NetworkResult.Sucess -> {

                        Toast.makeText(requireContext(), "Signup Success", Toast.LENGTH_SHORT).show()
                        binding.progress.setVisibility(View.GONE)
                        tokenManager.saveEmail(it.data!!.user.Email)
                        findNavController().navigate(R.id.action_signup_ft_to_home_ft)
                    }

                    is NetworkResult.Error -> {

                        binding.progress.setVisibility(View.GONE)
                        binding.sgnErrorTxt.text = it.message
                        binding.errorFrameSg.setVisibility(View.VISIBLE)

                    }

                    is NetworkResult.Loading -> {

                        // binding.rgResponse.setVisibility(View.VISIBLE)

                    }
                }
            }

        })
    }

    private fun getUserRequest():RegisterRequest{
        var name = binding.sgName.text.toString()
        var email = binding.sgEmail.text.toString()
        var password = binding.sgPassword.text.toString()
        return RegisterRequest(name, email, password)
    }

    private fun ValidUserInput():Pair<Boolean , String>{
        return authViewModel.provideSignupDetails(getUserRequest().name , getUserRequest().email , getUserRequest().password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}