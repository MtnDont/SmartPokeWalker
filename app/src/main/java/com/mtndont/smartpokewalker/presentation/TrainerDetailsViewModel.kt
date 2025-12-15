package com.mtndont.smartpokewalker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtndont.smartpokewalker.ble.BLEManager
import com.mtndont.smartpokewalker.data.EvolutionDefinition
import com.mtndont.smartpokewalker.data.ItemDefinitions
import com.mtndont.smartpokewalker.data.ItemModel
import com.mtndont.smartpokewalker.data.MonsterBoxModel
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonsterDefinition
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.MonstersRepository
import com.mtndont.smartpokewalker.data.PartyMonsterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TrainerDetailsViewModel @Inject constructor(
    private val bleManager: BLEManager,
    private val monsterDataRepository: MonsterDataRepository,
    private val monstersRepository: MonstersRepository
) : ViewModel() {

    val trainerName: StateFlow<String> = monsterDataRepository.trainerName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialValue = "Red")

    val exploreSteps: StateFlow<Int> = monsterDataRepository.exploreSteps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialValue = 0)

    val mOverlayState: MutableStateFlow<AppOverlayState> = MutableStateFlow(AppOverlayState.WalkPager)
    val overlayState: StateFlow<AppOverlayState> = mOverlayState.asStateFlow()

    fun resetOverlay() {
        mOverlayState.value = AppOverlayState.WalkPager
    }

    fun explore() {
        viewModelScope.launch {
            mOverlayState.value = AppOverlayState.RouteExploration
            delay(10_000)
            resetOverlay()

            monsterDataRepository.setExploreSteps(0)

            monstersRepository.addItem(
                ItemModel.getRandomItem()
            )

            monstersRepository.createMonster(
                MonsterModel.getRandomInitialMonster()
            )
        }
    }

    fun getMonstersInBox(boxId: Int): Flow<List<MonsterModel>> {
        return monstersRepository.getMonstersInBox(boxId)
    }

    fun getMonstersInParty(): Flow<List<PartyMonsterModel>> {
        return monstersRepository.getPartyMonstersNameWithSlot()
    }

    fun getPartyListInstance(): List<MonsterModel> {
        return runBlocking {
            monstersRepository.getPartyStream().first()
        }
    }

    fun getMonsterFromId(monsterId: Long): Flow<MonsterModel> {
        return monstersRepository.getMonster(monsterId)
    }

    fun getMonsterInstanceFromId(monsterId: Long): MonsterModel {
        return runBlocking {
            monstersRepository.getMonster(monsterId).first()
        }
    }

    fun isMonsterExclusiveInParty(monsterId: Long): Flow<Boolean> {
        return monstersRepository.isMonsterExclusiveInParty(monsterId)
    }

    fun evolveMonsterFromDefinition(monster: MonsterModel, monsterDef: MonsterDefinition, evoDef: EvolutionDefinition?) {
        val newMonster = monster.evolveToDefinition(monsterDef)
        val usedItem = evoDef?.item?.let {
            ItemDefinitions.entries.firstOrNull { item ->
                item.nameId == it
            }
        }
        viewModelScope.launch {
            usedItem?.let {
                monstersRepository.subtractItem(it.id.toLong())
            }
            monstersRepository.upsertMonster(newMonster)
        }
    }

    fun evolveHiddenMonsterFromDefinition(monster: MonsterModel, monsterDef: MonsterDefinition, evoDef: EvolutionDefinition?) {
        val newMonster = monster.evolveToHiddenDefinition(monsterDef)
        val usedItem = evoDef?.item?.let {
            ItemDefinitions.entries.firstOrNull { item ->
                item.nameId == it
            }
        }
        viewModelScope.launch {
            usedItem?.let {
                monstersRepository.subtractItem(it.id.toLong())
            }
            monstersRepository.createMonster(newMonster)
        }
    }

    fun getAllItems(): Flow<List<ItemModel>> {
        return monstersRepository.getAllItems()
    }

    fun getItemsAvailable(): Flow<List<ItemModel>> {
        return monstersRepository.getItemsAvailable()
    }

    fun getItemsAvailableInstance(): List<ItemModel> {
        return runBlocking {
            monstersRepository.getItemsAvailable().first()
        }
    }

    fun getUnusedBoxSlots(boxId: Int): Flow<List<Int>> {
        return monstersRepository.getUsedBoxSlots(boxId).map {
            (0 until MonsterBoxModel.MAX_BOX_SIZE).filter { boxSlot ->
                boxSlot !in it
            }
        }
    }

    fun getFullBoxSlots(): Flow<List<Long>> {
        return monstersRepository.getAllBoxUsage()
    }

    fun moveMonsterToParty(monsterId: Long, partySlot: Int) {
        viewModelScope.launch {
            // If monsterId originally in Box
            //    move monster to partySlot if partySlot isn't taken
            //    swap monsters PartyModel and MonsterBoxModel
            // If monsterId originally in Party
            //    move monter to partySlot if partySlot isn't taken
            //    swap monsters PartyModel
            monstersRepository.moveMonsterToParty(monsterId, partySlot)
        }
    }

    fun moveMonsterToBox(monsterId: Long, boxId: Int) {
        viewModelScope.launch {
            monstersRepository.moveMonsterToBox(monsterId, boxId)
        }
    }

    fun releaseMonster(monsterId: Long) {
        viewModelScope.launch {
            monstersRepository.deleteMonster(monsterId)
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

sealed interface AppOverlayState {
    data object RouteExploration : AppOverlayState
    data object WalkPager : AppOverlayState
}