package com.mtndont.smartpokewalker.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMonstersRepository @Inject constructor(
    private val dataSource: MonstersDao
) : MonstersRepository {
    override fun getMonstersStream(): Flow<List<MonsterModel>> {
        return dataSource.getMonstersStream().map { monster ->
            withContext(Dispatchers.Default) {
                monster.toExternal()
            }
        }
    }

    override fun getPartyStream(): Flow<List<MonsterModel>> {
        return dataSource.getPartyMonsters().map { monster ->
            withContext(Dispatchers.Default) {
                monster.toExternal()
            }
        }
    }

    override fun getPartyMonstersNameWithSlot(): Flow<List<PartyMonsterModel>> {
        return dataSource.getPartyMonstersNameWithSlot().map { partyMonsters ->
            withContext(Dispatchers.Default) {
                partyMonsters.toExternal()
            }
        }
    }

    override fun getMonstersInBox(boxId: Int): Flow<List<MonsterModel>> {
        return dataSource.getMonstersInBox(boxId).map { monster ->
            withContext(Dispatchers.Default) {
                monster.toExternal()
            }
        }
    }

    override suspend fun upsertMonster(monster: MonsterModel): Long {
        return dataSource.upsert(
            monster.toLocal()
        )
    }

    override suspend fun createMonster(
        dexId: Int,
        name: String,
        experience: Long,
        sex: Int,
        form: Int
    ): Long {
        val monster = MonsterModel(
            id = 0,
            dexId = dexId,
            name = name,
            experience = experience,
            sex = sex,
            form = form
        )
        return dataSource.upsert(monster.toLocal())
    }

    override suspend fun createMonster(monster: MonsterModel): Long {
        return dataSource.createMonster(monster.toLocal())
    }

    override suspend fun moveMonsterToParty(monsterId: Long, partySlot: Int): Long {
        return dataSource.moveMonsterToParty(monsterId, partySlot)
    }

    override suspend fun moveMonsterToBox(monsterId: Long, boxId: Int): Long {
        return dataSource.moveMonsterToBox(monsterId, boxId)
    }

    override fun getMonster(id: Long): Flow<MonsterModel> {
        return dataSource.findById(id).map { monster ->
            monster.toExternal()
        }
    }

    override fun getUsedBoxSlots(boxId: Int): Flow<List<Int>> {
        return dataSource.getUsedBoxSlots(boxId)
    }

    override fun getAllBoxUsage(): Flow<List<Long>> {
        return dataSource.getAllBoxUsage()
    }

    override suspend fun setPartyFromMonsters(monsters: List<MonsterModel>) {
        val idList = monsters.map { monster ->
            monster.id
        }
        return dataSource.setParty(idList)
    }

    override suspend fun setPartyFromIDs(ids: List<Long>) {
        return dataSource.setParty(ids)
    }

    override suspend fun deleteMonster(id: Long): Int {
        return dataSource.deleteById(id)
    }

    override fun getAllItems(): Flow<List<ItemModel>> {
        return dataSource.getAllItems().map {
            withContext(Dispatchers.Default) {
                it.toExternal()
            }
        }
    }

    override fun getItemsAvailable(): Flow<List<ItemModel>> {
        return dataSource.getItemsAvailable().map {
            withContext(Dispatchers.Default) {
                it.toExternal()
            }
        }
    }

    override suspend fun addItem(item: ItemDefinition): Long {
        return dataSource.addItem(item)
    }

    override suspend fun addStepsToParty(steps: Long) {
        dataSource.addStepsToParty(steps)
    }

    override fun isMonsterExclusiveInParty(monsterId: Long): Flow<Boolean> {
        return dataSource.isMonsterExclusiveInParty(monsterId)
    }
}