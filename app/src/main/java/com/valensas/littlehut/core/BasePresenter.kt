package com.valensas.littlehut.core

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}