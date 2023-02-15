package com.ssafy.campinity.common.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.PageIndicatorBinding

class PageIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: PageIndicatorBinding
    private lateinit var getNextPage: (Int) -> Unit
    private var textSize = 0
    private var baseTextColor = 0
    private var selectedFont = 0
    private var totalPages = 10
    private var startPage = 1
    private var endPage = 5
    private var currentPage = 1

    init {
        setView()
        setupAttributes(attrs)
        setupListeners()
    }

    private fun setView() {
        binding = PageIndicatorBinding.inflate(LayoutInflater.from(context), this, true)
        setPage()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageIndicator)

        textSize = typedArray.getDimensionPixelSize(R.styleable.PageIndicator_textSize, 14)
        baseTextColor =
            typedArray.getResourceId(R.styleable.PageIndicator_baseTextColor, R.color.grey)
        selectedFont =
            typedArray.getResourceId(R.styleable.PageIndicator_selectedFont, R.font.roboto_bold)
        typedArray.recycle()

        highlightPageNumber(1)
    }

    private fun setupListeners() {
        binding.btnArrowLeft.setOnClickListener {
            if (currentPage > startPage) {
                currentPage -= 1
                highlightPageNumber(currentPage)
                getNextPage(currentPage)
            } else if (currentPage > 1) {
                currentPage -= 1
                endPage = currentPage
                startPage -= 5
                setPage()
                highlightPageNumber(currentPage)
                getNextPage(currentPage)
            } else {
                context.showToastMessage("마지막 페이지입니다.")
            }
        }

        binding.btnArrowRight.setOnClickListener {
            if (currentPage < endPage) {
                currentPage += 1
                highlightPageNumber(currentPage)
                getNextPage(currentPage)
            } else if (currentPage < totalPages) {
                currentPage += 1
                startPage = currentPage
                endPage = if (endPage + 5 > totalPages) totalPages else endPage + 5
                setPage()
                highlightPageNumber(currentPage)
                getNextPage(currentPage)
            }
        }

        for (page in 0..endPage - startPage) {
            val pageId =
                resources.getIdentifier("tv_page_${page + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)

            pageTextView.setOnClickListener {
                currentPage = startPage + page
                highlightPageNumber(currentPage)
                getNextPage(currentPage)
            }
        }
    }

    private fun highlightPageNumber(selectedNumber: Int) {
        for (page in 0..endPage - startPage) {
            val pageId =
                resources.getIdentifier("tv_page_${page + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)

            if (startPage + page == selectedNumber) {
                pageTextView.typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
                pageTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                pageTextView.typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                pageTextView.setTextColor(baseTextColor)
            }
        }
    }

    private fun setPage() {
        for (index in 0..endPage - startPage) {
            val pageId =
                resources.getIdentifier("tv_page_${index + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)
            pageTextView.text = resources.getString(R.string.page_number, startPage + index)
            pageTextView.visibility = View.VISIBLE
        }

        for (index in endPage - startPage + 1 until 5) {
            val pageId =
                resources.getIdentifier("tv_page_${index + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)
            pageTextView.visibility = View.GONE
        }
    }

    fun setStartPage(startPage: Int) {
        this.startPage = startPage
        setPage()
    }

    fun setTotalPage(totalPage: Int) {
        this.totalPages = totalPage
    }

    fun setGetNextPage(getNextPage: (Int) -> Unit) {
        this.getNextPage = getNextPage
    }

    fun initPageIndicator(totalPage: Int) {
        this.startPage = 1
        this.endPage = if (totalPage > 5) 5 else totalPage
        this.currentPage = 1
        this.totalPages = totalPage
        setPage()
        highlightPageNumber(currentPage)
    }
}