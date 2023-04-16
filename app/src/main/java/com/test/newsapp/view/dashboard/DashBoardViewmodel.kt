package com.test.newsapp.view.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.newsapp.data.model.HeadlinesResponseModel
import com.test.newsapp.data.repository.NewsAppRepository
import com.test.newsapp.network.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewmodel @Inject constructor(private val repo: NewsAppRepository) : ViewModel() {

    val headlineStateFlow: MutableStateFlow<ApiState<HeadlinesResponseModel>> =
        MutableStateFlow(ApiState.Empty)

    val _headlineStateFlow: StateFlow<ApiState<HeadlinesResponseModel>> = headlineStateFlow

    fun getHeadlines() {
        viewModelScope.launch {
            repo.getHeadlines().onEach {
                headlineStateFlow.value = it
            }
        }
    }

}