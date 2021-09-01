package com.netlib

open class BaseResponse {
    open var code: String? = null
    open var status: String? = null
    open var message: String? = null
    open var success: Boolean? = null

    constructor() { }

    constructor(code: String?, status: String?, message: String?, success: Boolean?) {
        this.code = code
        this.status = status
        this.message = message
        this.success = success
    }

    override fun toString(): String {
        return "BaseResponse(code=$code, status=$status, message=$message, success=$success)"
    }
}
