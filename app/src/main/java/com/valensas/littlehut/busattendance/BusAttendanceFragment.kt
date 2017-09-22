package com.valensas.littlehut.busattendance

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.valensas.littlehut.R
import com.valensas.littlehut.core.BaseFragment
import com.valensas.littlehut.network.BusAttendanceModel
import kotlinx.android.synthetic.main.fragment_bus_attendance.*


/**
 * Created by ogun on 20/09/2017.
 * Valensas 2017
 */
class BusAttendanceFragment : BaseFragment<BusAttendanceContract.Presenter>(), BusAttendanceContract.View {

    var listAdapter: BusAttendanceListAdapter? = null
    var isFabOpen = false
    override lateinit var presenter: BusAttendanceContract.Presenter

    override fun getLayoutResource(): Int {
        return R.layout.fragment_bus_attendance
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        busAttendanceContainerLayout.setOnClickListener { if (isFabOpen) closeFABMenu() }
        busAttendanceStatusChangeButton.setOnClickListener({
            if (isFabOpen) {
                closeFABMenu()
            } else {
                showFABMenu()
            }
        })
        busAttendanceAttendButton.setOnClickListener({
            presenter.onAttendanceStatusChangeClicked(true)
            closeFABMenu()
        })
        busAttendanceNotAttendButton.setOnClickListener({
            presenter.onAttendanceStatusChangeClicked(false)
            closeFABMenu()
        })
    }

    override fun initAttendanceList(list: BusAttendanceModel) {
        val viewModel = BusAttendanceListViewModel(list)
        if (listAdapter == null) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            busAttendanceRecyclerView.layoutManager = layoutManager
            listAdapter = BusAttendanceListAdapter(viewModel)
            busAttendanceRecyclerView.adapter = listAdapter
            busAttendanceStatusChangeButton.visibility = View.VISIBLE
        } else {
            listAdapter?.updateList(viewModel)
        }
    }

    fun showFABMenu() {
        isFabOpen = true;
        busAttendanceAttendButton.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        busAttendanceNotAttendButton.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
    }

    fun closeFABMenu() {
        isFabOpen = false;
        busAttendanceAttendButton.animate().translationY(0.0f);
        busAttendanceNotAttendButton.animate().translationY(0.0f);
    }

    override fun changeFABIcon(iconResource: Int) {
        busAttendanceStatusChangeButton.setImageResource(iconResource)
    }

    companion object {
        fun newInstance() = BusAttendanceFragment()
    }
}

