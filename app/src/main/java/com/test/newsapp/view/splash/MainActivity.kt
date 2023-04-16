package com.test.newsapp.view.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.test.newsapp.R
import com.test.newsapp.network.ApiState
import com.test.newsapp.utils.openActivity
import com.test.newsapp.view.dashboard.DashBoardViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<DashBoardViewmodel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.getHeadlines()
        observer()
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel._headlineStateFlow.collect {

                when (it) {
                    is ApiState.Error -> {
                        Log.d("Status", "Error")
                    }
                    ApiState.Loading -> {
                        Log.d("Status", "Loading")

                    }
                    is ApiState.Success -> {
                        Log.d("Status", it.data.articles.toString())

                    }
                    else -> {}
                }
            }
        }
    }


}