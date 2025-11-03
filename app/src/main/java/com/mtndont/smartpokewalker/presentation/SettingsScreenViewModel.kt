package com.mtndont.smartpokewalker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonstersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val monsterDataRepository: MonsterDataRepository,
    private val monstersRepository: MonstersRepository
) : ViewModel() {
    fun createNewMonster() {
        viewModelScope.launch {
            monstersRepository.createMonster(
                dexId = Random.nextInt(1, 6),
                name = "test",
                experience = 0L,
                sex = Random.nextInt(1, 2),
                form = 0
            )
        }
    }

    fun addMonstersToParty() {
        viewModelScope.launch {
            val monsterList = monstersRepository.getMonstersStream().first()
            monstersRepository.setPartyFromMonsters(monsterList)
        }
    }
}