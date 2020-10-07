package com.viaplay.android.tvsample.features.main.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viaplay.android.tvsample.features.main.domain.Content
import com.viaplay.android.tvsample.features.main.domain.GetContentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject constructor(
        private val getContentUseCase: GetContentUseCase
) : ViewModel() {

    private val _content = MutableLiveData<Content>()
    val content: LiveData<Content> = _content


    fun startDataLoad() {
        viewModelScope.launch {
            val content = withContext(Dispatchers.IO){
                getContentUseCase.invoke()
            }

            _content.value = content
        }
    }
}