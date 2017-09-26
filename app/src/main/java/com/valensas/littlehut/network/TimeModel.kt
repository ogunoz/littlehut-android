package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 27/09/2017.
 * Valensas 2017
 */
data class TimeModel(@SerializedName("hours")
                     val hours: Int,
                     @SerializedName("minutes")
                     val minutes: Int) : BaseResponse()