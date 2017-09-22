package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 19/09/2017.
 * Valensas 2017
 */
class BaseResponse<T> {
    @SerializedName("status")
    var status: Boolean? = null
    @SerializedName("result")
    var result: T? = null
}