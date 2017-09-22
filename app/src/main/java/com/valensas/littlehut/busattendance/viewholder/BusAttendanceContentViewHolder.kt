package com.valensas.littlehut.busattendance.viewholder

import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.valensas.littlehut.R
import com.valensas.littlehut.busattendance.item.BusAttendanceContentViewModel
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by ogun on 21/09/2017.
 * Valensas 2017
 */
class BusAttendanceContentViewHolder(parent: ViewGroup) : BusAttendanceViewHolder(parent, R.layout.item_bus_attendance_list) {

    var itemNameView: TextView
    var itemMailView: TextView
    var itemImageView: CircleImageView

    init {
        itemNameView = itemView.findViewById(R.id.busAttendanceItemName)
        itemMailView = itemView.findViewById(R.id.busAttendanceItemMail)
        itemImageView = itemView.findViewById(R.id.busAttendanceItemImage)
    }

    fun onBindData(viewModel: BusAttendanceContentViewModel) {
        itemNameView.text = viewModel.name
        itemMailView.text = viewModel.email
        if (!TextUtils.isEmpty(viewModel.profileUrl)) {
            Picasso.with(itemView.context)
                    .load(viewModel.profileUrl)
                    .placeholder(R.color.gray)
                    .error(R.color.gray)
                    .into(itemImageView)
        } else {
            itemImageView.setImageResource(R.color.gray)
        }
    }
}