package com.mtndont.smartpokewalker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MonstersDao {

    @Query("SELECT * FROM monsters")
    fun getMonstersStream(): Flow<List<Monster>>

    @Query("SELECT * FROM monsters WHERE id = :id")
    fun findById(id: Long): Flow<Monster>

    @Upsert
    suspend fun upsert(monster: Monster): Long

    @Query("DELETE FROM monsters WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("DELETE FROM box WHERE monsterId = :monsterId")
    suspend fun deleteMonsterBoxByMonsterId(monsterId: Long)

    @Query("DELETE FROM party WHERE monsterId = :monsterId")
    suspend fun deletePartyByMonsterId(monsterId: Long)

    @Query("""
        SELECT m.*
        FROM party AS p
        INNER JOIN monsters AS m ON p.monsterId = m.id
        ORDER BY p.slot ASC
        LIMIT ${PartyModel.MAX_PARTY_SIZE}
    """)
    fun getPartyMonsters(): Flow<List<Monster>>

    @Query("""
        SELECT p.slot, m.*
        FROM party AS p
        JOIN monsters AS m ON p.monsterId = m.id
        ORDER BY p.slot ASC
        LIMIT ${PartyModel.MAX_PARTY_SIZE}
    """)
    fun getPartyMonstersNameWithSlot(): Flow<List<PartyMonster>>

    @Query("""
        SELECT m.*
        FROM monsters AS m
        JOIN box AS b ON b.monsterId = m.id
        WHERE b.boxId = :boxId
        ORDER BY b.boxSlot ASC
        LIMIT ${MonsterBoxModel.MAX_BOX_SIZE}
    """)
    fun getMonstersInBox(boxId: Int): Flow<List<Monster>>

    @Transaction
    suspend fun createMonster(monster: Monster): Long {
        val partyCount = countPartyMembers()
        val monsterId = upsert(monster)
        if (partyCount < PartyModel.MAX_PARTY_SIZE) {
            val usedSlots = getUsedPartySlots()
            val nextSlot = (1..PartyModel.MAX_PARTY_SIZE).firstOrNull {
                it !in usedSlots
            }

            nextSlot?.let {
                insertPartyMember(
                    Party(
                        slot = it,
                        monsterId = monsterId
                    )
                )
            }
        } else {
            val boxUsage = getBoxUsage()

            val targetBox = boxUsage.firstOrNull { it.usedSlots < MonsterBoxModel.MAX_BOX_SIZE }

            val boxId: Int
            val nextSlot: Int
            if (targetBox != null) {
                boxId = targetBox.boxId
                // First unused slot in box
                val usedSlots = getUsedBoxSlotsAsync(boxId)
                nextSlot = (0 until MonsterBoxModel.MAX_BOX_SIZE).firstOrNull {
                    it !in usedSlots
                } ?: 0

                //nextSlot = (getMaxBoxSlot(boxId) ?: -1) + 1
            } else {
                boxId = if (boxUsage.isEmpty()) 0 else (boxUsage.maxOf { it.boxId } + 1)
                nextSlot = 0
            }

            insertBoxMonster(
                MonsterBox(
                    boxId = boxId,
                    boxSlot = nextSlot,
                    monsterId = monsterId
                )
            )
        }

        return monsterId
    }

    @Transaction
    suspend fun moveMonsterToParty(monsterId: Long, partySlot: Int): Long {
        val partyEntry = getPartyByMonsterId(monsterId)
        val boxEntry = getMonsterBoxByMonsterId(monsterId)
        val occupant = getPartyBySlot(partySlot)

        // If monsterId originally in Box
        //    move monster to partySlot if partySlot isn't taken
        //    swap monsters PartyModel and MonsterBoxModel
        // If monsterId originally in Party
        //    move monter to partySlot if partySlot isn't taken
        //    swap monsters PartyModel
        if (boxEntry != null) {
            deleteMonsterBoxByMonsterId(monsterId)
            occupant?.let { otherPartyMember ->
                insertBoxMonster(
                    MonsterBox(
                        boxId = boxEntry.boxId,
                        boxSlot = boxEntry.boxSlot,
                        monsterId = otherPartyMember.monsterId
                    )
                )
            }
            return insertPartyMember(
                Party(
                    slot = partySlot,
                    monsterId = monsterId
                )
            )
        }
        else if (partyEntry != null) {
            deletePartyByMonsterId(monsterId)
            occupant?.let { otherPartyMember ->
                insertPartyMember(
                    Party(
                        slot = partyEntry.slot,
                        monsterId = otherPartyMember.monsterId
                    )
                )
            }
            return insertPartyMember(
                Party(
                    slot = partySlot,
                    monsterId = monsterId
                )
            )
        }

        return -1
    }

    // Error on boxId full
    @Transaction
    suspend fun moveMonsterToBox(monsterId: Long, boxId: Int): Long {
        // If monsterId originally in Box
        //    move monster to boxId if boxId isn't full
        // If monsterId originally in Party
        //    move monter to boxId if boxId isn't full
        //    delete monster PartyModel

        val usedSlots = getUsedBoxSlotsAsync(boxId)
        val nextSlot = (0 until MonsterBoxModel.MAX_BOX_SIZE).firstOrNull {
            it !in usedSlots
        } ?: -1

        if (nextSlot == -1) {
            throw Exception("MonsterBox ($boxId) full")
        }

        deleteMonsterBoxByMonsterId(monsterId)
        deletePartyByMonsterId(monsterId)

        return insertBoxMonster(
            MonsterBox(
                boxId = boxId,
                boxSlot = nextSlot,
                monsterId = monsterId
            )
        )
    }

    @Transaction
    suspend fun setParty(newPartyIds: List<Long>) {
        clearParty()
        newPartyIds.take(6).forEachIndexed { index, monsterId ->
            insertPartyMember(
                Party(
                    slot = index,
                    monsterId = monsterId
                )
            )
        }
    }

    @Query("DELETE FROM party")
    suspend fun clearParty()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartyMember(party: Party): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoxMonster(monsterBox: MonsterBox): Long

    @Query("SELECT * FROM party WHERE slot = :partySlot")
    suspend fun getPartyBySlot(partySlot: Int): Party?

    @Query("SELECT * FROM party WHERE monsterId = :monsterId")
    suspend fun getPartyByMonsterId(monsterId: Long): Party?

    @Query("SELECT DISTINCT slot FROM party")
    suspend fun getUsedPartySlots(): List<Int>

    @Query("SELECT * FROM box WHERE monsterId = :monsterId")
    suspend fun getMonsterBoxByMonsterId(monsterId: Long): MonsterBox?

    @Query("SELECT boxId, COUNT(*) AS usedSlots FROM box GROUP BY boxId ORDER BY boxId ASC")
    suspend fun getBoxUsage(): List<BoxUsage>

    @Query("""
        SELECT boxId
        FROM box
        GROUP BY boxId
        HAVING COUNT(boxSlot) >= ${MonsterBoxModel.MAX_BOX_SIZE}
    """)
    fun getAllBoxUsage(): Flow<List<Long>>

    @Query("SELECT MAX(boxSlot) FROM box WHERE boxId = :boxId")
    suspend fun getMaxBoxSlot(boxId: Int): Int?

    @Query("SELECT boxSlot FROM box WHERE boxId = :boxId")
    suspend fun getUsedBoxSlotsAsync(boxId: Int): List<Int>

    @Query("SELECT boxSlot FROM box WHERE boxId = :boxId")
    fun getUsedBoxSlots(boxId: Int): Flow<List<Int>>

    @Query("SELECT COUNT(*) FROM party")
    suspend fun countPartyMembers(): Int

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Item?

    @Upsert
    suspend fun upsertItem(item: Item): Long

    @Query("SELECT DISTINCT id, itemCount FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE itemCount > 0")
    fun getItemsAvailable(): Flow<List<Item>>

    @Transaction
    suspend fun addItem(item: ItemDefinition): Long {
        val dbItem = getItemById(item.id)

        return upsertItem(
            Item(
                id = item.id,
                itemCount = (dbItem?.itemCount ?: 0) + 1
            )
        )
    }

    @Query("UPDATE items SET itemCount = itemCount - 1 WHERE id = :itemId")
    suspend fun subtractItem(itemId: Long)

    @Query("""
        UPDATE monsters
        SET experience = experience + :steps
        WHERE id IN (SELECT monsterId FROM party)
            AND experience < ${MonsterModel.MAX_EXPERIENCE}
    """)
    suspend fun addStepsToParty(steps: Long)

    @Query("""
        SELECT 1
        FROM party
        WHERE monsterId = :monsterId
            AND (SELECT COUNT(*) FROM party) = 1
    """)
    fun isMonsterExclusiveInParty(monsterId: Long): Flow<Boolean>
}

data class BoxUsage(
    val boxId: Int,
    val usedSlots: Int
)