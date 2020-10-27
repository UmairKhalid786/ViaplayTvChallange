package com.viaplay.android.tvsample.features.main.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.viaplay.android.tvsample.R

import dagger.hilt.android.AndroidEntryPoint

/**
 * Loads [MainFragment].
 */

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
