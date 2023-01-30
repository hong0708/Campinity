package com.ssafy.campinity.presentation.community

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemNoteListBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle

class CommunityNoteListAdapter(private val onNoteQuestionClicked: (noteQuestionId: String) -> Unit) :
    RecyclerView.Adapter<CommunityNoteListAdapter.CommunityNoteListViewHolder>() {

    private var noteList = listOf<NoteQuestionTitle>()
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
        viewHolder.onBind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size


    class CommunityNoteListViewHolder(val binding: ItemNoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NoteQuestionTitle) {
            binding.tvNoteTitle.text = data.content
        }
    }

    fun setCuration(noteQuestionTitle: List<NoteQuestionTitle>) {
        this.noteList = noteQuestionTitle
        notifyDataSetChanged()
    }
}
