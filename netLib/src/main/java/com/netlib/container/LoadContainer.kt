package com.netlib.container

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import com.netlib.R
import kotlinx.android.synthetic.main.error_page.view.*

/**
 * XML中的加载容器
 *      此类复写了BaseLoadContainer，实例化了几种错误页面
 *          在不同场景下只需要重新创建子类并实例化错误页面，即可使用
 *          【error_page.xml 基本符合错误页面使用规则，建议延续使用，替换展示内容】
 *
 */
class LoadContainer(context: Context, attrs: AttributeSet?) : BaseLoadContainer(context, attrs) {

    private val param = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

    override var loadingView: View = mInflater.inflate(R.layout.loading, this, false)
        set(value) {
            removeView(field)
            field = value
            addView(value)
            value.visibility = View.GONE
        }
    override var errorView: View = mInflater.inflate(R.layout.error_page, this, false)
        set(value) {
            removeView(field)
            value.layoutParams = param
            field = value
            addView(value)
            value.visibility = View.GONE
        }

    override fun showLoading() {
        super.showLoading()
        // 替换Loading样式
        // ...
    }

    override fun showEmpty() {
        super.showEmpty()
        errorView.error_img?.setImageResource(R.mipmap.error_empty)
        errorView.error_desc?.text = "当前页面为空"
        errorView.error_refresh?.visibility = View.GONE
    }

    override fun showError() {
        super.showError()
        errorView.error_img?.setImageResource(R.mipmap.error)
        errorView.error_desc?.text = "加载失败，请重试"
        errorView.error_refresh?.visibility = View.VISIBLE
        errorView.error_refresh?.setOnClickListener {

        }
    }

    override fun showNetError() {
        super.showNetError()
        errorView.error_img?.setImageResource(R.mipmap.error_net)
        errorView.error_desc?.text = "当前网络不可用"
        errorView.error_refresh?.visibility = View.VISIBLE
        errorView.error_refresh?.setOnClickListener {

        }
    }

    init {
        (loadingView as ContentLoadingProgressBar).show()
    }
}