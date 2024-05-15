package com.create.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.create.fragments.databinding.FragmentSplashFtBinding
import com.create.fragments.utils.Const
import com.create.fragments.utils.TOKEN_MANAGER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Splash_ft : Fragment() {
    private var _binding:FragmentSplashFtBinding ?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TOKEN_MANAGER


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashFtBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = tokenManager.getEmail()



        Handler(Looper.getMainLooper()).postDelayed({

           if (email!=null){

               findNavController().navigate(R.id.action_splash_ft_to_home_ft)

           }
            else{

               findNavController().navigate(R.id.action_splash_ft_to_login_ft)

            }

        },1111)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}