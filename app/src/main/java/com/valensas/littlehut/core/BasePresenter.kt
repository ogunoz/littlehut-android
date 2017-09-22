package com.valensas.littlehut.core

import com.valensas.littlehut.network.APIService
import com.valensas.littlehut.network.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created by ogun on 22/09/2017.
 * Valensas 2017
 */
abstract class BasePresenter() {

    protected var disposable: Disposable? = null

    protected val apiService by lazy {
        APIService.create()
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
            TODO("BaseFragmenta ilet ki Logine dönelim")
            //  startActivity(LoginActivity.newIntent(context))
            //  activity.overridePendingTransition(0, 0)
            //  activity.finish()
        }
    }

    abstract fun onViewAttached()

    fun onViewDetached() {
        disposable?.dispose()
    }
}