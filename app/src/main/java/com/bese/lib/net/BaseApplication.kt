package com.bese.lib.net

import android.app.Application
import com.netlib.APICreator

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initNetLib()

    }

    private fun initNetLib() {

        // 全部配置初始化
        // APICreator.init("http://127.0.0.1/", 20, 20, 20, false, true)

        // 部分数据初始化
        // APICreator.init("http://127.0.0.1/", enableHttps = false)

        // Simple Init
        APICreator.init(NetTool.BaseUrl)

    }

}