package com.mtndont.smartpokewalker.presentation

import androidx.lifecycle.ViewModel
import com.mtndont.smartpokewalker.data.MonsterDataRepository
import com.mtndont.smartpokewalker.data.MonsterModel
import javax.inject.Inject

class RouteExplorationViewModel @Inject constructor(
    private val monsterDataRepository: MonsterDataRepository
) : ViewModel() {
    fun randomMonster(): MonsterModel {
        return MonsterModel.getRandomMonster()
    }
}