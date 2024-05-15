package com.create.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.create.fragments.ViewModels.NotesViewModel
import com.create.fragments.adapter.NotesAdapter
import com.create.fragments.databinding.FragmentHomeFtBinding
import com.create.fragments.models.UserNote
import com.create.fragments.utils.Const.TAG
import com.create.fragments.utils.NetworkResult
import com.create.fragments.utils.TOKEN_MANAGER
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.app.Activity as Activity1
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR as SYSTEM_UI_FLAG_LIGHT_STATUS_BAR1


@AndroidEntryPoint
class home_ft : Fragment() {

    private var _binding: FragmentHomeFtBinding?=null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels <NotesViewModel>()
    private lateinit var notesAdapter:NotesAdapter

    @Inject
    lateinit var  tokenManager:TOKEN_MANAGER


    @SuppressLint("SuspiciousIndentation")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeFtBinding.inflate(inflater , container , false)

        notesAdapter = NotesAdapter(::onNoteClick)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.addBtn.setOnClickListener {

            findNavController().navigate(R.id.action_home_ft_to_message)
        }

        val token = tokenManager.getEmail()

        observer()

        if (token!=null){

            noteViewModel.getNote(token)

            notesAdapter = NotesAdapter(::onNoteClick)
            binding.rv.layoutManager = StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL)
            binding.rv.adapter = notesAdapter

        }



        else{ }

    }

    private fun observer(){

        noteViewModel.liveData.observe(viewLifecycleOwner , Observer {





            when(it){
                is NetworkResult.Sucess ->{
                    notesAdapter.saveData(it.data!!.UserNotes)
                    binding.firstTxt.setVisibility(View.GONE)
                    binding.progress.setVisibility(View.GONE)
                }

                is NetworkResult.Error ->{
                    binding.firstTxt.setVisibility(View.VISIBLE)
                    binding.progress.setVisibility(View.GONE)
                }

                else -> {}
            }

        })
    }

    private fun onNoteClick(noteResponse: UserNote){
        val bundle = bundleOf()
        bundle.putString("Note" , Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_home_ft_to_edit_ft , bundle)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}