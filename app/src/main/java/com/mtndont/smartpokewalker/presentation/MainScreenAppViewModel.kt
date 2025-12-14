package com.mtndont.smartpokewalker.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtndont.smartpokewalker.data.ItemModel
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.MonstersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenAppViewModel @Inject constructor(
    private val monsterDataRepository: MonsterDataRepository,
    private val monstersRepository: MonstersRepository
) : ViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    val totalWatts: StateFlow<Long> = monsterDataRepository.totalWatts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2_000), 0L)

    val partyList: StateFlow<List<MonsterModel>?> = monstersRepository.getPartyStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2_000), null)

    val itemList: StateFlow<List<ItemModel>?> = monstersRepository.getItemsAvailable()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2_000), null)

    val partyUiState: StateFlow<WalkUiState> = partyList
        .map { party ->
            when {
                party == null -> WalkUiState.Loading
                party.isEmpty() -> WalkUiState.StarterSelection(
                    starterMonsters = getRandomStarters()
                )
                else -> WalkUiState.Walking
            }
        }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2_000), WalkUiState.Loading)

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

    fun onEvent(event: WalkEvent) {
        when (event) {
            is WalkEvent.ConfirmStarter -> addMonster(event.monster)
        }
    }

    fun getRandomStarters(): List<MonsterModel> {
        return listOf(
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[0].id),
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[3].id),
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[6].id)
        )
    }

    fun addMonster(monster: MonsterModel) {
        viewModelScope.launch {
            monstersRepository.createMonster(monster)
        }
    }
}

sealed class UiState {
    object Startup : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
}

sealed interface WalkUiState {

    data object Loading : WalkUiState

    data class StarterSelection(
        val starterMonsters: List<MonsterModel>
    ) : WalkUiState

    data object Walking : WalkUiState
}

sealed interface WalkEvent {
    data class ConfirmStarter(val monster: MonsterModel) : WalkEvent
}