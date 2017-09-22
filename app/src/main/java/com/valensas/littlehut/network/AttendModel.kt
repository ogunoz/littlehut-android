package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 22/09/2017.
 * Valensas 2017
 */
data class AttendModel(@SerializedName("isAttending")
                       val isAttending: Boolean)