package com.valensas.littlehut.busattendance

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.valensas.littlehut.R
import com.valensas.littlehut.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_bus_attendance.*


/**
 * Created by ogun on 20/09/2017.
 * Valensas 2017
 */
class BusAttendanceFragment : BaseFragment<BusAttendanceContract.Presenter>(), BusAttendanceContract.View {

    private var listAdapter: BusAttendanceListAdapter? = null
    override lateinit var presenter: BusAttendanceContract.Presenter

    override fun getLayoutResource(): Int {
        return R.layout.fragment_bus_attendance
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        busAttendanceContainerLayout.setOnClickListener {
            presenter.onContainerClicked()
        }
        busAttendanceStatusChangeButton.setOnClickListener {
            presenter.onAttendanceStatusButtonClicked()
        }
        busAttendanceAttendButton.setOnClickListener {
            presenter.onAttendanceStatusActionClicked(true)
        }
        busAttendanceNotAttendButton.setOnClickListener {
            presenter.onAttendanceStatusActionClicked(false)
        }
    }

    override fun initAttendanceList(listViewModel: BusAttendanceListViewModel) {
        if (listAdapter == null) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            busAttendanceRecyclerView.layoutManager = layoutManager
            listAdapter = BusAttendanceListAdapter(listViewModel)
            busAttendanceRecyclerView.adapter = listAdapter
            busAttendanceStatusChangeButton.visibility = View.VISIBLE
        } else {
            listAdapter?.updateList(listViewModel)
        }
    }

    override fun showFABMenu() {
        busAttendanceAttendButton.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        busAttendanceNotAttendButton.animate().translationY(-resources.getDimension(R.dimen.standard_55))
    }

    override fun closeFABMenu() {
        busAttendanceAttendButton.animate().translationY(0.0f)
        busAttendanceNotAttendButton.animate().translationY(0.0f)
    }

    override fun changeFABIcon(iconResource: Int) {
        busAttendanceStatusChangeButton.setImageResource(iconResource)
    }

    companion object {
        fun newInstance() = BusAttendanceFragment()
    }
}

