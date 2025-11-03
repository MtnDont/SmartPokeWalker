package com.mtndont.smartpokewalker

import android.app.Application
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    val monsterDataRepository by lazy { MonsterDataRepository(this) }
}