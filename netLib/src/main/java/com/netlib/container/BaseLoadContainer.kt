package com.netlib.container

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

/**
 * 加载容器 可选择显示 加载view 内容view 错误view
 */
abstract class BaseLoadContainer(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    val mInflater: LayoutInflater = LayoutInflater.from(context)
    var onLoadListener: OnLoadListener? = null

    var contentView: View? = null
    abstract var loadingView: View
    abstract var errorView: View
    var showing: View? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        // contentView
        check(childCount == 1) { "LoadContainer有且只能有一个ContentView" }
        contentView = getChildAt(0)
        showing = contentView
        // errorView
        errorView.visibility = View.GONE
        addView(errorView)
        // loadingView
        loadingView.visibility = View.GONE
        addView(loadingView)
    }

    private fun show(toShow: View?) {
        if (showing != toShow) {
            showing?.visibility = View.GONE
            toShow?.visibility = View.VISIBLE
            showing = toShow
        }
    }

    open fun showLoading() {
        show(loadingView)
    }

    fun showContent() {
        show(contentView)
    }

    open fun showEmpty() {
        show(errorView)
    }

    open fun showError() {
        errorView
        show(errorView)
    }

    open fun showNetError() {
        show(errorView)
    }

    interface OnLoadListener {
        fun onLoad()
    }

    fun load() {
        onLoadListener?.onLoad()
    }
}
