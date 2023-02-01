package com.ssafy.campinity.presentation.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchListFragment : BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {

    private lateinit var campsiteList: List<CampsiteBriefInfo>

    override fun initView() {
        createDummy()
        initCampsiteList()
    }

    private fun initCampsiteList() {
        binding.rvCampsiteList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = SearchListAdapter(campsiteList,
            this@SearchListFragment::navigationToCampsiteDetailFragment,
                this@SearchListFragment::navigationToSearchPostboxFragment
                )
        }
    }

    private fun navigationToCampsiteDetailFragment(campsiteId: String) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment(
                campsiteId
            )
        )
    }

    private fun navigationToSearchPostboxFragment() {
        navigate(SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment())
    }

    private fun createDummy() {
        campsiteList = listOf(
            CampsiteBriefInfo(
                "uuid1", "캠핑장1", "경상북도 구미시", 5, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid2", "캠핑장2", "경상북도 구미시", 50, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid3", "캠핑장3", "경상북도 구미시", 500, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid4", "캠핑장4", "경상북도 구미시", 5000, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid11", "캠핑장1", "경상북도 구미시", 5, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid12", "캠핑장2", "경상북도 구미시", 50, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid13", "캠핑장3", "경상북도 구미시", 500, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            ), CampsiteBriefInfo(
                "uuid14", "캠핑장4", "경상북도 구미시", 5000, listOf(
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
                    "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
                    "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
                    "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
                    "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
                )
            )
        )
    }
}