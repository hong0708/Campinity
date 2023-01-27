package com.ssafy.campinity.presentation.community

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemNoteListBinding

class CommunityNoteListAdapter(private val datum: ArrayList<String>) :
    RecyclerView.Adapter<CommunityNoteListAdapter.CommunityNoteListViewHolder>() {

    lateinit var binding: ItemNoteListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityNoteListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note_list,
            parent,
            false
        )
        Log.d("왜?", "onCreateViewHolder: ")
        return CommunityNoteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityNoteListViewHolder, position: Int) {
        val viewHolder: CommunityNoteListViewHolder = holder
        viewHolder.onBind(datum[position])
    }

    override fun getItemCount(): Int = datum.size


    inner class CommunityNoteListViewHolder(val binding: ItemNoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(datum: String) {
            binding.tvNoteTitle.text = datum
            Log.d("왜?", "onBind: $datum")
        }
    }
}
