package com.valensas.littlehut.network.sse

import com.google.gson.Gson
import com.tylerjroach.eventsource.EventSourceHandler
import com.tylerjroach.eventsource.MessageEvent

/**
 * Created by ogun on 24/09/2017.
 * Valensas 2017
 */
open class SSEHandler : EventSourceHandler {
    protected val gson = Gson()

    override fun onConnect() {
        println()
    }

    override fun onComment(comment: String?) {
        println()
    }

    override fun onMessage(event: String?, message: MessageEvent?) {
        println()
    }

    override fun onClosed(willReconnect: Boolean) {
        println()
    }

    override fun onError(t: Throwable?) {
        println()
    }
}