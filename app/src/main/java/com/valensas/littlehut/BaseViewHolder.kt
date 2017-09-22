package com.valensas.littlehut

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by ogun on 21/09/2017.
 * Valensas 2017
 */
open class BaseViewHolder : RecyclerView.ViewHolder {

    constructor(itemView: View?) : super(itemView)

    constructor(parent: ViewGroup, @LayoutRes layoutResId: Int) :
            this(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)) {

    }
}