package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCampsiteReviewBinding
import com.ssafy.campinity.domain.entity.search.Review

class CampsiteReviewAdapter(
    private val context: Context,
    private var reviews: List<Review>,
    private val deleteReview: (String, Int) -> Unit
) : RecyclerView.Adapter<CampsiteReviewAdapter.ViewHolder>() {

    private lateinit var binding: ItemCampsiteReviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCampsiteReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])

        Glide.with(binding.ivProfile.context)
            .load("http://i8d101.p.ssafy.io:8003/images${reviews[position].profileImage}")
            .circleCrop().into(binding.ivProfile)
    }

    override fun getItemCount(): Int = reviews.size

    fun setData(sync: Int, reviews: List<Review>) {
        this.reviews = reviews
    }

    inner class ViewHolder(binding: ItemCampsiteReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Review) {
            binding.apply {
                tvUserName.text = item.authorName
                tvCreateTime.text = item.createAt

                if (item.rate >= 1) ivStar1.setBackgroundResource(R.drawable.ic_star_on)
                if (item.rate >= 2) ivStar2.setBackgroundResource(R.drawable.ic_star_on)
                if (item.rate >= 3) ivStar3.setBackgroundResource(R.drawable.ic_star_on)
                if (item.rate >= 4) ivStar4.setBackgroundResource(R.drawable.ic_star_on)
                if (item.rate >= 5) ivStar5.setBackgroundResource(R.drawable.ic_star_on)

                tvReviewContentEllipse.text = item.content
                tvReviewContent.text = item.content

                val viewTreeObserver = tvReviewContentEllipse.viewTreeObserver
                viewTreeObserver.addOnGlobalLayoutListener {
                    if (tvReviewContentEllipse.layout.getEllipsisCount(tvReviewContentEllipse.lineCount - 1) > 0) {
                        tvShowMore.setOnClickListener {
                            tvReviewContent.visibility = View.VISIBLE
                            tvReviewContentEllipse.visibility = View.GONE
                            tvShowMore.visibility = View.GONE
                        }
                    } else {
                        tvShowMore.visibility = View.INVISIBLE
                    }
                }
            }

            binding.ibDeleteReview.setOnClickListener {
                CampsiteReviewDeleteDialog(
                    context,
                    deleteReview,
                    item.reviewId,
                    adapterPosition
                ).show()
            }
        }
    }
}