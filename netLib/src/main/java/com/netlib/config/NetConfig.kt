package com.netlib.config

object NetConfig {

    var BASE_URL = "http://127.0.0.1/"
    var CONNECT_TIMEOUT = 10
    var REQUEST_TIMEOUT = 10
    var RESPONSE_TIMEOUT = 10

    /** 是否开启HTTPS */
    var ENABLE_HTTPS = true
    /** 是否启用网络日志输出。如开启。可在Logcat面板中查看网络数据返回 */
    var ENABLE_NET_LOG_ECHO = true

    var mHeaderParamsMap: HashMap<String, String?>? = null
    var USER_AGENT_VALUE = "android"
}