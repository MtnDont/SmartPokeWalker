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
        return dataSource.upsert(monster.toLocal())
    }

    override suspend fun createStarterAndParty(monster: MonsterModel): Long {
        return dataSource.createStarterAndParty(monster.toLocal())
    }

    override suspend fun getMonster(id: Long): MonsterModel? {
        return dataSource.findById(id)?.toExternal()
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

    override suspend fun updateMonster(
        id: Long,
        name: String,
        experience: Long
    ) {
        val monster = getMonster(id)?.copy(
            name = name,
            experience = experience
        ) ?: throw Exception("Monster (id $id) not found")

        dataSource.upsert(monster.toLocal())
    }

    override suspend fun deleteMonster(id: Long): Int {
        return dataSource.deleteById(id)
    }

    override suspend fun addStepsToParty(steps: Long) {
        dataSource.addStepsToParty(steps)
    }
}