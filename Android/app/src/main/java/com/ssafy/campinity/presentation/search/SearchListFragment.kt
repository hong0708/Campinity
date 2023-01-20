package com.ssafy.campinity.presentation.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchListFragment :
    BaseFragment<FragmentSearchListBinding>(R.layout.fragment_search_list) {
    private lateinit var campsiteList: ArrayList<CampsiteBriefInfo>
    override fun initView() {
        campsiteList = arrayListOf(
            CampsiteBriefInfo(
                "uuid1",
                "캠핑장1",
                "경상북도 구미시",
                5,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid2",
                "캠핑장2",
                "경상북도 구미시",
                50,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid3",
                "캠핑장3",
                "경상북도 구미시",
                500,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid4",
                "캠핑장4",
                "경상북도 구미시",
                5000,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid1",
                "캠핑장1",
                "경상북도 구미시",
                5,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid2",
                "캠핑장2",
                "경상북도 구미시",
                50,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid3",
                "캠핑장3",
                "경상북도 구미시",
                500,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            ),
            CampsiteBriefInfo(
                "uuid4",
                "캠핑장4",
                "경상북도 구미시",
                5000,
                arrayListOf(
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg",
                    "http://static.hubzum.zumst.com/hubzum/2018/02/06/09/962ec338ca3b4153b037168ec92756ac.jpg",
                    "https://t1.daumcdn.net/cfile/tistory/0138F14A517F77713A",
                    "https://i.ytimg.com/vi/5-mWvUR7_P0/maxresdefault.jpg"
                )
            )
        )

        binding.rvCampsiteList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = SearchListAdapter(campsiteList)
        }
    }
}