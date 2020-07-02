package com.bese.lib.net

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bese.lib.net.request.CheckUpdateRequest
import com.bese.lib.net.response.CheckUpdateResponse
import com.netlib.CallBackWithLC
import com.netlib.container.BaseLoadContainer
import kotlinx.android.synthetic.main.activity_request_lc.*

class NetRequestWithLCActivity : AppCompatActivity() {

    private val okUrl = NetTool.BaseUrl + "app/update"
    private val errorUrl = "$okUrl-1"
    private var mUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_lc)

        explain?.setOnClickListener {
            Toast.makeText(
                this, "网络请求在一个LoadContainer容器内，\n 请求时展示Loading页面（非全屏Dialog），" +
                        "\n 请求成功时展示正常内容，\n 请求失败时展示对应的错误页面", Toast.LENGTH_LONG
            ).show()
        }

        send?.setOnClickListener {
            mUrl = okUrl
            load_container?.run {
                showLoading()
                load()
            }
        }

        send_error?.setOnClickListener {
            mUrl = errorUrl
            load_container?.run {
                showLoading()
                load()
            }
        }

        send_no_net?.setOnClickListener {
            mUrl = "http://127.0.0.0/"
            load_container?.run {
                showLoading()
                load()
            }

        }

        load_container?.onLoadListener = object : BaseLoadContainer.OnLoadListener {
            override fun onLoad() {
                requestCheckUpdate()
            }
        }

    }

    private fun requestCheckUpdate() {
        CheckUpdateRequest(mUrl).request(CheckUpdateRequest.Param("1.0"),
            object : CallBackWithLC<CheckUpdateResponse>(load_container, false) {
                override fun onSuccess(response: CheckUpdateResponse?) {
                    super.onSuccess(response)
                    response?.data?.latestVersion?.run {
                        version_name?.text = "这里是容器的内容，\n 服务端最新版本是：\n $this"
                    }
                }

                override fun onLoadEnd(isSuccess: Boolean?) {
                    super.onLoadEnd(isSuccess)
                    // 其他网络结束统一处理，不管成功还是失败
                }
            })
    }

}
