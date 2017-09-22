package com.valensas.littlehut.busattendance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.valensas.littlehut.R
import com.valensas.littlehut.util.replaceFragmentInActivity

/**
 * Created by ogun on 20/09/2017.
 * Valensas 2017
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as BusAttendanceFragment? ?:
                BusAttendanceFragment.newInstance().also {
                    replaceFragmentInActivity(it, R.id.contentFrame)
                }
        BusAttendancePresenter(fragment)
    }


    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }
}