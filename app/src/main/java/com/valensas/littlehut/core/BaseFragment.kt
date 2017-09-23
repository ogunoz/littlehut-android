package com.valensas.littlehut.core

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valensas.littlehut.login.LoginActivity

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
abstract class BaseFragment<in V : BaseView, T : BasePresenter<V>>
    : Fragment(), BaseView {

    protected abstract var presenter: T

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun turnToLogin() {
        startActivity(LoginActivity.newIntent(context))
        activity.overridePendingTransition(0, 0)
        activity.finish()
    }

    @LayoutRes
    protected abstract fun getLayoutResource(): Int
}