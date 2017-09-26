package com.valensas.littlehut.network

import com.google.gson.annotations.SerializedName

/**
 * Created by ogun on 25/09/2017.
 * Valensas 2017
 */
enum class BusStatus() {

    @SerializedName("waitingForBus")
    waitingForBus,

    @SerializedName("busHasArrived")
    busHasArrived,

    @SerializedName("busHasDeparted")
    busHasDeparted,

    @SerializedName("bonAppetit")
    bonAppetit,

    @SerializedName("busIsWaiting")
    busIsWaiting,

    @SerializedName("checkTomorrow")
    checkTomorrow,

    @SerializedName("busIsReturning")
    busIsReturning,

    @SerializedName("nonWorkingDay")
    nonWorkingDay,

    @SerializedName("checkLater")
    checkLater

}