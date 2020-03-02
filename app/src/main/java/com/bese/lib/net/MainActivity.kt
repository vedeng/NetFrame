package com.bese.lib.net

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bese.lib.net.request.AppBaseUrlRequest
import com.bese.lib.net.response.AppBaseUrlResponse
import com.blankj.utilcode.util.ToastUtils
import com.netlib.BaseCallback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceBaseUrl()
    }


    private fun replaceBaseUrl() {
        AppBaseUrlRequest().request(AppBaseUrlRequest.Param("1"), object : BaseCallback<AppBaseUrlResponse>() {
            override fun onSuccess(response: AppBaseUrlResponse?) {
                response?.data?.appUrl?.run {
                    ToastUtils.showShort(this)
                }
            }

        })
    }

}
