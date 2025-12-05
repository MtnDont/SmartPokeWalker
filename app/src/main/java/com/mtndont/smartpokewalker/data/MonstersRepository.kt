package com.mtndont.smartpokewalker.data

import kotlinx.coroutines.flow.Flow

interface MonstersRepository {

    fun getMonstersStream(): Flow<List<MonsterModel>>

    fun getPartyStream(): Flow<List<MonsterModel>>

    suspend fun createMonster(
        dexId: Int,
        name: String,
        experience: Long,
        sex: Int,
        form: Int
    ): Long

    suspend fun createMonster(monster: MonsterModel): Long

    suspend fun createStarterAndParty(monster: MonsterModel): Long

    suspend fun getMonster(id: Long): MonsterModel?

    suspend fun setPartyFromMonsters(monsters: List<MonsterModel>)

    suspend fun setPartyFromIDs(ids: List<Long>)

    suspend fun updateMonster(id: Long, name: String, experience: Long)

    suspend fun deleteMonster(id: Long): Int

    suspend fun addStepsToParty(steps: Long)
}