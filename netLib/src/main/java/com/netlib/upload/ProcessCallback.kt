package com.netlib.upload

interface ProcessCallback {
    /**
     * 通知当前的下载进度
     * @param progress
     */
    fun onProgress(progress: Int, flag: String = "--") { }

    /**
     * 通知下载成功
     */
    fun onSuccess() { }

    /**
     * 通知下载失败
     */
    fun onFailed() { }

    /**
     * 通知下载暂停
     */
    fun onPaused() { }

    /**
     * 通知下载取消事件
     */
    fun onCanceled() { }

}