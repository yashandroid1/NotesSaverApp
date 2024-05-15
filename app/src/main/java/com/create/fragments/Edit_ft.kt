package com.create.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.create.fragments.ViewModels.NotesViewModel
import com.create.fragments.databinding.FragmentEditFtBinding
import com.create.fragments.models.UserNote
import com.create.fragments.utils.NetworkResult
import com.create.fragments.utils.TOKEN_MANAGER
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Edit_ft : Fragment() {
    private var _binding:FragmentEditFtBinding?=null
    private val binding get() = _binding!!
    private val noteEditViewmodel by viewModels<NotesViewModel>()
    private var notes:UserNote?=null
    private var noteId:Int?=null


    @Inject
    lateinit var tokenManager: TOKEN_MANAGER

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditFtBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observe()
        setInilize()

        binding.saveBtn.setOnClickListener {

            var title =binding.titleEd.text.toString()
            var description = binding.descriptionEd.text.toString()
            var email = tokenManager.getEmail().toString()

           if (notes==null) {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    noteEditViewmodel.addNotes(email, title, description)
                    noteEditViewmodel.count = 1

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Write notes first for save",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            else{
               noteEditViewmodel.count = 1
               noteEditViewmodel.updateNote(email , noteId!! , title ,description )
            }

        }


    }

    private fun setInilize() {
        val jsonNote = arguments?.getString("Note")
        if (jsonNote!=null){
            notes = Gson().fromJson(jsonNote , UserNote::class.java)
            notes?.let {
                binding.titleEd.setText(it.Title)
                binding.descriptionEd.setText(it.Description)
                noteId = it.ID
            }
        }
    }

    private fun observe(){

        noteEditViewmodel.ed_livedata.observe(viewLifecycleOwner  , Observer {
            var count = noteEditViewmodel.count

           if (count == 1) {
               count = 0
               when(it){
                   is NetworkResult.Sucess ->{
                       if (notes==null){
                           Toast.makeText(requireContext(), "Saved Notes", Toast.LENGTH_SHORT).show()
                       }

                       else{
                           Toast.makeText(requireContext(), "Notes Updated", Toast.LENGTH_SHORT).show()
                       }

                       findNavController().popBackStack()
                   }

                   is NetworkResult.Error ->{}

                   is NetworkResult.Loading -> {}
               }
           }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}