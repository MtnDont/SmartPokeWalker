package com.mtndont.smartpokewalker.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenAppViewModel @Inject constructor(
    private val monsterDataRepository: MonsterDataRepository
) : ViewModel() {

    val currentSteps = monsterDataRepository.currentSteps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0L)

    val totalWatts = monsterDataRepository.totalWatts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0L)

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    init {
        viewModelScope.launch {
            val supported = true
            uiState.value = if (supported) {
                UiState.Supported
            } else {
                UiState.NotSupported
            }
        }
    }
}

sealed class UiState {
    object Startup : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
}