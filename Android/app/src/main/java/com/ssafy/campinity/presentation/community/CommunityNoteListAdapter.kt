package com.ssafy.campinity.presentation.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemNoteListBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle

class CommunityNoteListAdapter(
    private val onNoteQuestionClicked: (noteQuestionId: String) -> Unit
) : RecyclerView.Adapter<CommunityNoteListAdapter.CommunityNoteListViewHolder>() {

    private var noteList = listOf<NoteQuestionTitle>()
    lateinit var binding: ItemNoteListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityNoteListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note_list,
            parent,
            false
        )
        return CommunityNoteListViewHolder(binding, onNoteQuestionClicked)
    }

    override fun onBindViewHolder(holder: CommunityNoteListViewHolder, position: Int) {
        val viewHolder: CommunityNoteListViewHolder = holder
        viewHolder.onBind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    class CommunityNoteListViewHolder(
        val binding: ItemNoteListBinding,
        private val onNoteQuestionClicked: (noteQuestionId: String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NoteQuestionTitle) {
            binding.apply {
                tvNoteTitle.text = data.content
                root.setOnClickListener {
                    onNoteQuestionClicked(data.questionId)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCuration(noteQuestionTitle: List<NoteQuestionTitle>) {
        this.noteList = noteQuestionTitle
        notifyDataSetChanged()
    }
}
