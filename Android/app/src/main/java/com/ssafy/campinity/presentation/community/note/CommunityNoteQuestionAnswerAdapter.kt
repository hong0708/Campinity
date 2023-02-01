package com.ssafy.campinity.presentation.community.note

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemNoteAnswerListBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer

class CommunityNoteQuestionAnswerAdapter :
    RecyclerView.Adapter<CommunityNoteQuestionAnswerAdapter.CommunityNoteQuestionAnswerViewHolder>() {

    private var answerList = listOf<NoteQuestionAnswer>()
    lateinit var binding: ItemNoteAnswerListBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityNoteQuestionAnswerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note_answer_list,
            parent,
            false
        )
        return CommunityNoteQuestionAnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityNoteQuestionAnswerViewHolder, position: Int) {
        val viewHolder: CommunityNoteQuestionAnswerViewHolder = holder
        viewHolder.onBind(answerList[position])
    }

    override fun getItemCount(): Int = answerList.size

    class CommunityNoteQuestionAnswerViewHolder(
        val binding: ItemNoteAnswerListBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NoteQuestionAnswer) {
            binding.tvNoteTitle.text = data.content
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAnswer(noteQuestionAnswer: List<NoteQuestionAnswer>) {
        this.answerList = noteQuestionAnswer
        notifyDataSetChanged()
    }
}