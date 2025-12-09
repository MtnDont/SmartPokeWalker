package com.mtndont.smartpokewalker.data

import com.mtndont.smartpokewalker.R
import java.time.LocalTime
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

data class MonsterModel(
    val id: Long,
    val dexId: Int = 0,
    val name: String = "",
    val experience: Long = 0L,
    val sex: Int = -1,
    val form: Int = 0,
    val traded: Boolean = false
) {
    fun getDefinition(): MonsterDefinition {
        val dId = if (dexId < 1 || dexId > MonsterDefinitions.entries.size) 1 else dexId
        return MonsterDefinitions.entries[dId - 1]
    }

    fun getDefaultName(): String {
        return getDefinition().name
    }

    fun getFormResId(): Int {
        val definition = getDefinition()
        val resIds = definition.formResIds

        return try {
            if (definition.hasGenderDifferences) {
                resIds[sex - 1]
            }
            else {
                resIds[form]
            }
        } catch (_: Exception) {
            R.raw.a0
        }
    }

    fun getLevel(): Int {
        return min(
            abs(experience / 1000) + 1,
            100
        ).toInt()
    }

    /**
     * Verifies that the MonsterModel passses any of its defined EvolutionDefinitions
     */
    fun getAvailableEvolutions(party: List<MonsterModel>, includeHidden: Boolean = false): List<Pair<EvolutionMode, MonsterDefinition>> {
        val availableEvolutions = mutableListOf<Pair<EvolutionMode, MonsterDefinition>>()

        getDefinition().evolutions?.let { evolutionList ->
            val test = evolutionList.forEach { (dexId, evoDetailList) ->
                var evolutionMode = EvolutionMode.LEVEL_UP

                val idPasses = evoDetailList?.any { detail ->
                    evolutionMode = EvolutionMode.LEVEL_UP
                    val passedSex = detail.sex?.let {
                        it == sex
                    } ?: true

                    val passedMinLevel = detail.minLevel?.let {
                        getLevel() >= it
                    } ?: true

                    val passedTimeOfDay = detail.timeOfDay?.let {
                        val now = LocalTime.now()
                        // Night -> 20:00 to 03:59
                        // Day   -> 04:00 to 19:59
                        val night = now.isBefore(LocalTime.of(20, 0)).not()
                                || now.isBefore(LocalTime.of(4, 0))

                        when (it) {
                            "day" -> !night
                            "night" -> night
                            else -> !night
                        }
                    } ?: true

                    val passedRequiredMember = detail.requiredMember?.let {
                        party.any { partyMember ->
                            partyMember.dexId == it
                        }
                    } ?: true

                    val passedOtherAction = detail.otherAction?.let {
                        when (it) {
                            "trade" -> {
                                evolutionMode = EvolutionMode.TRADE
                                traded
                            }
                            "shed" -> {
                                evolutionMode = EvolutionMode.SHED
                                includeHidden && (party.size < PartyModel.MAX_PARTY_SIZE)
                            }
                            else -> true
                        }
                    } ?: true

                    passedSex && passedMinLevel && passedTimeOfDay && passedRequiredMember && passedOtherAction
                } ?: true

                if (idPasses) {
                    availableEvolutions.add(
                        Pair(evolutionMode, MonsterDefinitions.entries[dexId - 1])
                    )
                }
            }
        }

        return availableEvolutions.toList()
    }

    companion object {
        const val MAX_EXPERIENCE = 99000L

        fun getRandomInitialMonster(): MonsterModel {

            val randomDefinition = MonsterDefinitions.entries.filter {
                it.firstInEvolutionChain
            }.random()

            val sex = if (randomDefinition.genderless) {
                -1
            } else {
                Random.nextInt(1, 3)
            }

            val form = if (!randomDefinition.hasGenderDifferences || randomDefinition.genderless) {
                Random.nextInt(randomDefinition.formResIds.size)
            } else {
                0
            }

            return MonsterModel(
                id = 0,
                dexId = randomDefinition.id,
                name = randomDefinition.name,
                sex = sex,
                form = form
            )
        }

        fun getRandomMonster(dexId: Int? = null): MonsterModel {
            val definition = if (dexId != null && dexId > 0 && dexId <= MonsterDefinitions.entries.size) {
                MonsterDefinitions.entries[dexId - 1]
            } else {
                MonsterDefinitions.entries.random()
            }

            val sex = if (definition.genderless) {
                -1
            } else {
                Random.nextInt(1, 3)
            }

            val form = if (!definition.hasGenderDifferences || definition.genderless) {
                Random.nextInt(definition.formResIds.size)
            } else {
                0
            }

            return MonsterModel(
                id = 0,
                dexId = definition.id,
                name = definition.name,
                sex = sex,
                form = form
            )
        }
    }
}
