package com.valensas.littlehut.core

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by ogun on 23/09/2017.
 * Valensas 2017
 */
abstract class BaseFragment<T : BasePresenter> : BaseView<T>, Fragment() {

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewAttached()
    }

    override fun onDetach() {
        presenter.onViewDetached()
        super.onDetach()
    }

    @LayoutRes
    protected abstract fun getLayoutResource(): Int
}