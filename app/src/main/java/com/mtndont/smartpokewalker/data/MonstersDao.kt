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
    suspend fun findById(id: Long): Monster?

    @Upsert
    suspend fun upsert(monster: Monster): Long

    @Query("DELETE FROM monsters WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Transaction
    @Query("""
        SELECT m.*
        FROM party AS p
        INNER JOIN monsters AS m ON p.monsterId = m.id
        ORDER BY p.slot ASC
        LIMIT ${PartyModel.MAX_PARTY_SIZE}
    """)
    fun getPartyMonsters(): Flow<List<Monster>>

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
    suspend fun insertPartyMember(party: Party)
}