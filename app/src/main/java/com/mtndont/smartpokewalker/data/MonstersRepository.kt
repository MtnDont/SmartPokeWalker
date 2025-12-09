package com.mtndont.smartpokewalker.data

import kotlinx.coroutines.flow.Flow

interface MonstersRepository {

    fun getMonstersStream(): Flow<List<MonsterModel>>

    fun getPartyStream(): Flow<List<MonsterModel>>

    fun getPartyMonstersNameWithSlot(): Flow<List<PartyMonsterModel>>

    fun getMonstersInBox(boxId: Int): Flow<List<MonsterModel>>

    suspend fun createMonster(
        dexId: Int,
        name: String,
        experience: Long,
        sex: Int,
        form: Int
    ): Long

    suspend fun createMonster(monster: MonsterModel): Long

    suspend fun moveMonsterToParty(monsterId: Long, partySlot: Int): Long

    suspend fun moveMonsterToBox(monsterId: Long, boxId: Int): Long

    fun getMonster(id: Long): Flow<MonsterModel>

    fun getUsedBoxSlots(boxId: Int): Flow<List<Int>>

    suspend fun setPartyFromMonsters(monsters: List<MonsterModel>)

    suspend fun setPartyFromIDs(ids: List<Long>)

    suspend fun deleteMonster(id: Long): Int

    suspend fun addStepsToParty(steps: Long)

    fun isMonsterExclusiveInParty(monsterId: Long): Flow<Boolean>
}