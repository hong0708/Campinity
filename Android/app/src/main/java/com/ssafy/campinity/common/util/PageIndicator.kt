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
    private var textSize = 0
    private var baseTextColor = 0
    private var selectedFont = 0
    private var totalPages = 5
    private var startPage = 1
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
            }
        }

        binding.btnArrowRight.setOnClickListener {
            if (currentPage < totalPages) {
                currentPage += 1
                highlightPageNumber(currentPage)
            }
        }

        for (page in 0 until totalPages) {
            val pageId =
                resources.getIdentifier("tv_page_${page + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)

            pageTextView.setOnClickListener {
                currentPage = startPage + page
                highlightPageNumber(currentPage)
            }
        }
    }

    private fun highlightPageNumber(selectedNumber: Int) {
        for (page in 0 until totalPages) {
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
        for (index in 0 until totalPages) {
            val pageId =
                resources.getIdentifier("tv_page_${index + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)
            pageTextView.text = resources.getString(R.string.page_number, startPage + index)
        }

        for (index in totalPages until 5) {
            val pageId =
                resources.getIdentifier("tv_page_${index + 1}", "id", context.packageName)
            val pageTextView = findViewById<TextView>(pageId)
            pageTextView.visibility = View.GONE
        }
    }

    fun setStartPage(startPage: Int) {
        this.startPage = startPage
    }

    fun setTotalPage(totalPage: Int) {
        this.totalPages = totalPage
    }
}