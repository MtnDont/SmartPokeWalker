package com.mtndont.smartpokewalker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtndont.smartpokewalker.ble.BLEManager
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.MonstersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainerDetailsScreenViewModel @Inject constructor(
    private val bleManager: BLEManager,
    private val monsterDataRepository: MonsterDataRepository,
    private val monstersRepository: MonstersRepository
) : ViewModel() {

    val trainerName: StateFlow<String> = monsterDataRepository.trainerName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialValue = "Red")

    fun createNewMonster() {
        viewModelScope.launch {
            val randomMonster = MonsterModel.getRandomInitialMonster()
            monstersRepository.createMonster(
                dexId = randomMonster.dexId,
                name = randomMonster.name,
                experience = 0L,
                sex = randomMonster.sex,
                form = randomMonster.form
            )
        }
    }

    fun addMonstersToParty() {
        viewModelScope.launch {
            val monsterList = monstersRepository.getMonstersStream().first()
            monstersRepository.setPartyFromMonsters(monsterList)
        }
    }

    fun testBluetooth() {
        bleManager.startServer()
    }

    override fun onCleared() {
        super.onCleared()
        bleManager.stopServer()
    }
}