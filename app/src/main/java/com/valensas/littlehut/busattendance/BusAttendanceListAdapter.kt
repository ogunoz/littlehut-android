package com.valensas.littlehut.busattendance

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.valensas.littlehut.busattendance.item.BusAttendanceContentViewModel
import com.valensas.littlehut.busattendance.item.BusAttendanceHeaderViewModel
import com.valensas.littlehut.busattendance.viewholder.BusAttendanceContentViewHolder
import com.valensas.littlehut.busattendance.viewholder.BusAttendanceHeaderViewHolder
import com.valensas.littlehut.busattendance.viewholder.BusAttendanceViewHolder

/**
 * Created by ogun on 21/09/2017.
 * Valensas 2017
 */
class BusAttendanceListAdapter(val busAttendanceListViewModel: BusAttendanceListViewModel)
    : RecyclerView.Adapter<BusAttendanceViewHolder>() {

    private val HEADER_TYPE = 0
    private val CONTENT_TYPE = 1

    override fun onBindViewHolder(holder: BusAttendanceViewHolder, position: Int) {
        if (holder is BusAttendanceHeaderViewHolder) {
            holder.onBindData(busAttendanceListViewModel.busList[position] as BusAttendanceHeaderViewModel)
        } else {
            (holder as BusAttendanceContentViewHolder).onBindData(busAttendanceListViewModel.busList[position]
                    as BusAttendanceContentViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusAttendanceViewHolder {
        val viewHolder: BusAttendanceViewHolder
        when (viewType) {
            HEADER_TYPE -> {
                viewHolder = BusAttendanceHeaderViewHolder(parent)
            }
            else -> {
                viewHolder = BusAttendanceContentViewHolder(parent)
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return busAttendanceListViewModel.busList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (busAttendanceListViewModel.busList[position] is BusAttendanceContentViewModel) {
            return CONTENT_TYPE
        } else {
            return HEADER_TYPE
        }
    }

    fun updateList(viewModel: BusAttendanceListViewModel) {
        busAttendanceListViewModel.busList.clear()
        busAttendanceListViewModel.busList.addAll(viewModel.busList)
        notifyDataSetChanged()
    }

}