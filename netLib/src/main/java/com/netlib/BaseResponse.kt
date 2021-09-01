package com.netlib

open class BaseResponse {
    open var code: String? = null
    open var status: String? = null
    open var message: String? = null
    open var success: Boolean? = null

    override fun toString(): String {
        return "BaseResponse(code=$code, status=$status, message=$message, success=$success)"
    }
}
