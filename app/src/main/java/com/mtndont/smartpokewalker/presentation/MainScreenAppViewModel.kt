package com.mtndont.smartpokewalker.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.MonstersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenAppViewModel @Inject constructor(
    private val monsterDataRepository: MonsterDataRepository,
    private val monstersRepository: MonstersRepository
) : ViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    val partyUiState: StateFlow<WalkUiState> = combine(
        monstersRepository.getPartyStream(),
        monsterDataRepository.totalWatts
    ) { partyList, totalWatts ->
        when(partyList.size) {
            0 -> WalkUiState.StarterSelection(
                starterMonsters = getRandomStarters()
            )
            else -> WalkUiState.Walking(
                totalWatts = totalWatts,
                party = partyList
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2_000), WalkUiState.Loading)

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

    data class Walking(
        val totalWatts: Long,
        val party: List<MonsterModel>
    ) : WalkUiState
}

sealed interface WalkEvent {
    data class ConfirmStarter(val monster: MonsterModel) : WalkEvent
}