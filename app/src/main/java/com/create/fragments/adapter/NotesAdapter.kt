package com.create.fragments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.create.fragments.databinding.ItemDesignBinding
import com.create.fragments.models.UserNote

class NotesAdapter(private val onNoteClick: (UserNote) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var datalist:List<UserNote> = emptyList()

    inner class NotesViewHolder(private var binding: ItemDesignBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(model:UserNote){
            binding.apply {
                titleTxt.text = model.Title
                descriptionTxt.text = model.Description
                binding.root.setOnClickListener {
                    onNoteClick(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemDesignBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return NotesViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun saveData(list: List<UserNote>){
        this.datalist = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return datalist.size

    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = getItemId(position)
        item?.let {
            holder.bind(model = datalist[position])
        }

        }
    }