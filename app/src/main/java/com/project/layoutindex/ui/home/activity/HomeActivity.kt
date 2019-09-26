package com.project.layoutindex.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.layoutindex.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
    }
}
