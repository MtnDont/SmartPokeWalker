package com.mtndont.smartpokewalker.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Monster::class,
        Party::class,
        MonsterBox::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monstersDao(): MonstersDao
}