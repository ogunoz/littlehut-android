package com.valensas.littlehut.core

import com.valensas.littlehut.network.APIService
import com.valensas.littlehut.network.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var view: V? = null

    protected var disposable: Disposable? = null

    protected val apiService by lazy {
        APIService.get()
    }

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    protected fun <T : BaseResponse> sendRequest(respObserv: Observable<T>) {
        sendRequest(respObserv) { response: T -> onResponse(response) }
    }


    protected fun <T : BaseResponse> sendRequest(respObserv: Observable<T>, responseHandler: (response: T) -> Unit) {
        sendRequest(respObserv, responseHandler) { error: Throwable -> onError(error) }
    }

    protected fun <T : BaseResponse> sendRequest(respObserv: Observable<T>,
                                                 responseHandler: (response: T) -> Unit,
                                                 errorHandler: (error: Throwable) -> Unit) {
        disposable = respObserv
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> responseHandler(result) },
                        { error -> errorHandler(error) }
                )
    }

    protected fun onResponse(response: BaseResponse) {

    }

    protected fun onError(error: Throwable) {
        if (error is HttpException && error.code() == 401) {
            view?.turnToLogin()
        }
    }
}