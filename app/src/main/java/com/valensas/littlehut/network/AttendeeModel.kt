package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 19/09/2017.
 * Valensas 2017
 */
data class AttendeeModel(@SerializedName("name")
                         val name: String,
                         @SerializedName("email")
                         val email: String)