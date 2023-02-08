package com.ssafy.campinity.presentation.community.campsite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCampsiteTitleBinding
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo

class CommunityCampsiteTitleListAdapter(
    private val onCampsiteTitleClicked: (
        campsiteBriefInfo: CampsiteBriefInfo
    ) -> Unit
) : RecyclerView.Adapter<CommunityCampsiteTitleListAdapter.CommunityCampsiteTitleListViewHolder>() {

    private var campsiteList = listOf<CampsiteBriefInfo>()
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
        return CommunityCampsiteTitleListViewHolder(binding, onCampsiteTitleClicked)
    }

    override fun onBindViewHolder(holder: CommunityCampsiteTitleListViewHolder, position: Int) {
        val viewHolder: CommunityCampsiteTitleListViewHolder = holder
        viewHolder.onBind(campsiteList[position])
    }

    override fun getItemCount(): Int = campsiteList.size

    class CommunityCampsiteTitleListViewHolder(
        val binding: ItemCampsiteTitleBinding,
        private val onCampsiteTitleClicked: (
            campsiteBriefInfo: CampsiteBriefInfo
        ) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CampsiteBriefInfo) {
            binding.apply {
                binding.campsiteName = data
                root.setOnClickListener {
                    onCampsiteTitleClicked(
                        data
                    )
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCampsiteBriefInfo(campsiteBriefInfo: List<CampsiteBriefInfo>) {
        this.campsiteList = campsiteBriefInfo
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}