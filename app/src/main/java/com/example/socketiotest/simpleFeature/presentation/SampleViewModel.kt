package com.example.socketiotest.simpleFeature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socketiotest.simpleFeature.domain.GetInfoUseCase
import com.example.socketiotest.simpleFeature.domain.SendInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase,
    private val sendInfoUseCase: SendInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState

    init {
        viewModelScope.launch {

            getInfoUseCase.invoke()
                .onSuccess {
                    it.collect {
                        _uiState.value = it
                    }
                }
                .onFailure {
                    //do error
                }
        }
    }

    fun buttonClicked() {
        viewModelScope.launch {
            sendInfoUseCase.invoke("hi from client")
        }
    }
}