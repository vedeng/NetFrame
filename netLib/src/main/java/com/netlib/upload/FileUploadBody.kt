package com.netlib.upload

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * 带进度回调的文件上传RequestBody
 *
 * @param requestBody 实际的待包装请求体
 * @param file 原始文件对象
 * @param keyCode 请求身份识别标示（更新进度时用来识别请求）
 * @param callback 进度回调
 */
class FileUploadBody(private val requestBody: RequestBody, private val file: File, private val callback: ProcessCallback?, private val keyCode: String?) : RequestBody() {

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 10240
        private var DOWNLOAD_PROCESS = 0L
    }

    /**
     * 重写调用实际的响应体的contentType
     */
    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    /**
     * 重写调用实际的响应体的contentLength
     */
    @Throws(IOException::class)
    override fun contentLength(): Long {
        return requestBody.contentLength()
    }

    /**
     * 重写进行写入
     */
    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fis = FileInputStream(file)
        var uploaded: Long = 0
        fis.use { ins ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (ins.read(buffer).also { read = it } != -1) { // update progress on UI thread
                handler.post(ProgressUpdater(uploaded, contentLength()))
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
            }
        }
    }

    private inner class ProgressUpdater(private val mUploaded: Long, private val mTotal: Long) : Runnable {
        override fun run() {
            // 如果total值不正常，进度始终展示 0
            val process = if (mTotal == 0L) 0L else (mUploaded * 100 / mTotal)
            Log.d("pic translate ->: ", "current: $mUploaded , total: $mTotal .")
            if (process > DOWNLOAD_PROCESS) {
                callback?.onProgress(process.toInt(), keyCode ?: "--")
            }
            DOWNLOAD_PROCESS = process
        }
    }

}