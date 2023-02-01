package com.ssafy.campinity.presentation.community.campsite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCampsiteTitleBinding
import com.ssafy.campinity.domain.entity.community.CampsiteTitle

class CommunityCampsiteTitleListAdapter(
    private val campsiteClicked: () -> Unit
) :
    RecyclerView.Adapter<CommunityCampsiteTitleListAdapter.CommunityCampsiteTitleListViewHolder>() {

    private var campsiteList = listOf<CampsiteTitle>()
    lateinit var binding: ItemCampsiteTitleBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityCampsiteTitleListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_campsite_title,
            parent,
            false
        )
        return CommunityCampsiteTitleListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityCampsiteTitleListViewHolder, position: Int) {
        val viewHolder: CommunityCampsiteTitleListViewHolder = holder
        viewHolder.onBind(campsiteList[position])
    }

    override fun getItemCount(): Int = campsiteList.size

    class CommunityCampsiteTitleListViewHolder(
        val binding: ItemCampsiteTitleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CampsiteTitle) {
            binding.apply {
                tvCampsiteTitle.text = data.title
                tvCampsiteAddress.text = data.address
            }
        }
    }

    fun setAnswer(campsiteTitle: List<CampsiteTitle>) {
        this.campsiteList = campsiteTitle
        notifyDataSetChanged()
    }
}