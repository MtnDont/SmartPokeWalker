package com.mtndont.smartpokewalker.data

import com.mtndont.smartpokewalker.R

object MonsterDefinitions {
    val entries = listOf(
        MonsterDefinition(
            id = 1,
            name = "Bulbasaur",
            genderRate = 1,
            formResIds = listOf(R.raw.a0),
            evolutions = mapOf(
                2 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 2,
            name = "Ivysaur",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a1),
            evolutions = mapOf(
                3 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 3,
            name = "Venusaur",
            genderRate = 1,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a2,R.raw.a493),
        ),
        MonsterDefinition(
            id = 4,
            name = "Charmander",
            genderRate = 1,
            formResIds = listOf(R.raw.a3),
            evolutions = mapOf(
                5 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 5,
            name = "Charmeleon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a4),
            evolutions = mapOf(
                6 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 6,
            name = "Charizard",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a5),
        ),
        MonsterDefinition(
            id = 7,
            name = "Squirtle",
            genderRate = 1,
            formResIds = listOf(R.raw.a6),
            evolutions = mapOf(
                8 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 8,
            name = "Wartortle",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a7),
            evolutions = mapOf(
                9 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 9,
            name = "Blastoise",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a8),
        ),
        MonsterDefinition(
            id = 10,
            name = "Caterpie",
            genderRate = 4,
            formResIds = listOf(R.raw.a9),
            evolutions = mapOf(
                11 to listOf(
                    EvolutionDefinition(minLevel = 7)
                )
            )
        ),
        MonsterDefinition(
            id = 11,
            name = "Metapod",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a10),
            evolutions = mapOf(
                12 to listOf(
                    EvolutionDefinition(minLevel = 10)
                )
            )
        ),
        MonsterDefinition(
            id = 12,
            name = "Butterfree",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a11,R.raw.a494),
        ),
        MonsterDefinition(
            id = 13,
            name = "Weedle",
            genderRate = 4,
            formResIds = listOf(R.raw.a12),
            evolutions = mapOf(
                14 to listOf(
                    EvolutionDefinition(minLevel = 7)
                )
            )
        ),
        MonsterDefinition(
            id = 14,
            name = "Kakuna",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a13),
            evolutions = mapOf(
                15 to listOf(
                    EvolutionDefinition(minLevel = 10)
                )
            )
        ),
        MonsterDefinition(
            id = 15,
            name = "Beedrill",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a14),
        ),
        MonsterDefinition(
            id = 16,
            name = "Pidgey",
            genderRate = 4,
            formResIds = listOf(R.raw.a15),
            evolutions = mapOf(
                17 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 17,
            name = "Pidgeotto",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a16),
            evolutions = mapOf(
                18 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 18,
            name = "Pidgeot",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a17),
        ),
        MonsterDefinition(
            id = 19,
            name = "Rattata",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a18,R.raw.a495),
            evolutions = mapOf(
                20 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 20,
            name = "Raticate",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a19,R.raw.a496),
        ),
        MonsterDefinition(
            id = 21,
            name = "Spearow",
            genderRate = 4,
            formResIds = listOf(R.raw.a20),
            evolutions = mapOf(
                22 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 22,
            name = "Fearow",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a21),
        ),
        MonsterDefinition(
            id = 23,
            name = "Ekans",
            genderRate = 4,
            formResIds = listOf(R.raw.a22),
            evolutions = mapOf(
                24 to listOf(
                    EvolutionDefinition(minLevel = 22)
                )
            )
        ),
        MonsterDefinition(
            id = 24,
            name = "Arbok",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a23),
        ),
        MonsterDefinition(
            id = 25,
            name = "Pikachu",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a24,R.raw.a497),
            evolutions = mapOf(
                26 to listOf(
                    EvolutionDefinition(item = "thunder-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 26,
            name = "Raichu",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a25,R.raw.a498),
        ),
        MonsterDefinition(
            id = 27,
            name = "Sandshrew",
            genderRate = 4,
            formResIds = listOf(R.raw.a26),
            evolutions = mapOf(
                28 to listOf(
                    EvolutionDefinition(minLevel = 22)
                )
            )
        ),
        MonsterDefinition(
            id = 28,
            name = "Sandslash",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a27),
        ),
        MonsterDefinition(
            id = 29,
            name = "Nidoran-f",
            genderRate = 8,
            formResIds = listOf(R.raw.a28),
            evolutions = mapOf(
                30 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 30,
            name = "Nidorina",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a29),
            evolutions = mapOf(
                31 to listOf(
                    EvolutionDefinition(item = "moon-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 31,
            name = "Nidoqueen",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a30),
        ),
        MonsterDefinition(
            id = 32,
            name = "Nidoran-m",
            genderRate = 0,
            formResIds = listOf(R.raw.a31),
            evolutions = mapOf(
                33 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 33,
            name = "Nidorino",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a32),
            evolutions = mapOf(
                34 to listOf(
                    EvolutionDefinition(item = "moon-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 34,
            name = "Nidoking",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a33),
        ),
        MonsterDefinition(
            id = 35,
            name = "Clefairy",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a34),
            evolutions = mapOf(
                36 to listOf(
                    EvolutionDefinition(item = "moon-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 36,
            name = "Clefable",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a35),
        ),
        MonsterDefinition(
            id = 37,
            name = "Vulpix",
            genderRate = 6,
            formResIds = listOf(R.raw.a36),
            evolutions = mapOf(
                38 to listOf(
                    EvolutionDefinition(item = "fire-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 38,
            name = "Ninetales",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a37),
        ),
        MonsterDefinition(
            id = 39,
            name = "Jigglypuff",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a38),
            evolutions = mapOf(
                40 to listOf(
                    EvolutionDefinition(item = "moon-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 40,
            name = "Wigglytuff",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a39),
        ),
        MonsterDefinition(
            id = 41,
            name = "Zubat",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a40,R.raw.a499),
            evolutions = mapOf(
                42 to listOf(
                    EvolutionDefinition(minLevel = 22)
                )
            )
        ),
        MonsterDefinition(
            id = 42,
            name = "Golbat",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a41,R.raw.a500),
            evolutions = mapOf(
                169 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 43,
            name = "Oddish",
            genderRate = 4,
            formResIds = listOf(R.raw.a42),
            evolutions = mapOf(
                44 to listOf(
                    EvolutionDefinition(minLevel = 21)
                )
            )
        ),
        MonsterDefinition(
            id = 44,
            name = "Gloom",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a43,R.raw.a501),
            evolutions = mapOf(
                45 to listOf(
                    EvolutionDefinition(item = "leaf-stone")
                ),
                182 to listOf(
                    EvolutionDefinition(item = "sun-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 45,
            name = "Vileplume",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a44,R.raw.a502),
        ),
        MonsterDefinition(
            id = 46,
            name = "Paras",
            genderRate = 4,
            formResIds = listOf(R.raw.a45),
            evolutions = mapOf(
                47 to listOf(
                    EvolutionDefinition(minLevel = 24)
                )
            )
        ),
        MonsterDefinition(
            id = 47,
            name = "Parasect",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a46),
        ),
        MonsterDefinition(
            id = 48,
            name = "Venonat",
            genderRate = 4,
            formResIds = listOf(R.raw.a47),
            evolutions = mapOf(
                49 to listOf(
                    EvolutionDefinition(minLevel = 31)
                )
            )
        ),
        MonsterDefinition(
            id = 49,
            name = "Venomoth",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a48),
        ),
        MonsterDefinition(
            id = 50,
            name = "Diglett",
            genderRate = 4,
            formResIds = listOf(R.raw.a49),
            evolutions = mapOf(
                51 to listOf(
                    EvolutionDefinition(minLevel = 26)
                )
            )
        ),
        MonsterDefinition(
            id = 51,
            name = "Dugtrio",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a50),
        ),
        MonsterDefinition(
            id = 52,
            name = "Meowth",
            genderRate = 4,
            formResIds = listOf(R.raw.a51),
            evolutions = mapOf(
                53 to listOf(
                    EvolutionDefinition(minLevel = 28)
                )
            )
        ),
        MonsterDefinition(
            id = 53,
            name = "Persian",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a52),
        ),
        MonsterDefinition(
            id = 54,
            name = "Psyduck",
            genderRate = 4,
            formResIds = listOf(R.raw.a53),
            evolutions = mapOf(
                55 to listOf(
                    EvolutionDefinition(minLevel = 33)
                )
            )
        ),
        MonsterDefinition(
            id = 55,
            name = "Golduck",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a54),
        ),
        MonsterDefinition(
            id = 56,
            name = "Mankey",
            genderRate = 4,
            formResIds = listOf(R.raw.a55),
            evolutions = mapOf(
                57 to listOf(
                    EvolutionDefinition(minLevel = 28)
                )
            )
        ),
        MonsterDefinition(
            id = 57,
            name = "Primeape",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a56),
        ),
        MonsterDefinition(
            id = 58,
            name = "Growlithe",
            genderRate = 2,
            formResIds = listOf(R.raw.a57),
            evolutions = mapOf(
                59 to listOf(
                    EvolutionDefinition(item = "fire-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 59,
            name = "Arcanine",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a58),
        ),
        MonsterDefinition(
            id = 60,
            name = "Poliwag",
            genderRate = 4,
            formResIds = listOf(R.raw.a59),
            evolutions = mapOf(
                61 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 61,
            name = "Poliwhirl",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a60),
            evolutions = mapOf(
                62 to listOf(
                    EvolutionDefinition(item = "water-stone")
                ),
                186 to listOf(
                    EvolutionDefinition(item = "kings-rock")
                )
            )
        ),
        MonsterDefinition(
            id = 62,
            name = "Poliwrath",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a61),
        ),
        MonsterDefinition(
            id = 63,
            name = "Abra",
            genderRate = 2,
            formResIds = listOf(R.raw.a62),
            evolutions = mapOf(
                64 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 64,
            name = "Kadabra",
            genderRate = 2,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a63,R.raw.a503),
            evolutions = mapOf(
                65 to listOf(
                    EvolutionDefinition(otherAction = "trade")
                )
            )
        ),
        MonsterDefinition(
            id = 65,
            name = "Alakazam",
            genderRate = 2,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a64,R.raw.a504),
        ),
        MonsterDefinition(
            id = 66,
            name = "Machop",
            genderRate = 2,
            formResIds = listOf(R.raw.a65),
            evolutions = mapOf(
                67 to listOf(
                    EvolutionDefinition(minLevel = 28)
                )
            )
        ),
        MonsterDefinition(
            id = 67,
            name = "Machoke",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a66),
            evolutions = mapOf(
                68 to listOf(
                    EvolutionDefinition(otherAction = "trade")
                )
            )
        ),
        MonsterDefinition(
            id = 68,
            name = "Machamp",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a67),
        ),
        MonsterDefinition(
            id = 69,
            name = "Bellsprout",
            genderRate = 4,
            formResIds = listOf(R.raw.a68),
            evolutions = mapOf(
                70 to listOf(
                    EvolutionDefinition(minLevel = 21)
                )
            )
        ),
        MonsterDefinition(
            id = 70,
            name = "Weepinbell",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a69),
            evolutions = mapOf(
                71 to listOf(
                    EvolutionDefinition(item = "leaf-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 71,
            name = "Victreebel",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a70),
        ),
        MonsterDefinition(
            id = 72,
            name = "Tentacool",
            genderRate = 4,
            formResIds = listOf(R.raw.a71),
            evolutions = mapOf(
                73 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 73,
            name = "Tentacruel",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a72),
        ),
        MonsterDefinition(
            id = 74,
            name = "Geodude",
            genderRate = 4,
            formResIds = listOf(R.raw.a73),
            evolutions = mapOf(
                75 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 75,
            name = "Graveler",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a74),
            evolutions = mapOf(
                76 to listOf(
                    EvolutionDefinition(otherAction = "trade")
                )
            )
        ),
        MonsterDefinition(
            id = 76,
            name = "Golem",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a75),
        ),
        MonsterDefinition(
            id = 77,
            name = "Ponyta",
            genderRate = 4,
            formResIds = listOf(R.raw.a76),
            evolutions = mapOf(
                78 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 78,
            name = "Rapidash",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a77),
        ),
        MonsterDefinition(
            id = 79,
            name = "Slowpoke",
            genderRate = 4,
            formResIds = listOf(R.raw.a78),
            evolutions = mapOf(
                80 to listOf(
                    EvolutionDefinition(minLevel = 37)
                ),
                199 to listOf(
                    EvolutionDefinition(item = "kings-rock")
                )
            )
        ),
        MonsterDefinition(
            id = 80,
            name = "Slowbro",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a79),
        ),
        MonsterDefinition(
            id = 81,
            name = "Magnemite",
            formResIds = listOf(R.raw.a80),
            evolutions = mapOf(
                82 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 82,
            name = "Magneton",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a81),
            evolutions = mapOf(
                462 to listOf(
                    EvolutionDefinition(item = "thunder-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 83,
            name = "Farfetch'd",
            genderRate = 4,
            formResIds = listOf(R.raw.a82),
        ),
        MonsterDefinition(
            id = 84,
            name = "Doduo",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a83,R.raw.a505),
            evolutions = mapOf(
                85 to listOf(
                    EvolutionDefinition(minLevel = 31)
                )
            )
        ),
        MonsterDefinition(
            id = 85,
            name = "Dodrio",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a84,R.raw.a506),
        ),
        MonsterDefinition(
            id = 86,
            name = "Seel",
            genderRate = 4,
            formResIds = listOf(R.raw.a85),
            evolutions = mapOf(
                87 to listOf(
                    EvolutionDefinition(minLevel = 34)
                )
            )
        ),
        MonsterDefinition(
            id = 87,
            name = "Dewgong",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a86),
        ),
        MonsterDefinition(
            id = 88,
            name = "Grimer",
            genderRate = 4,
            formResIds = listOf(R.raw.a87),
            evolutions = mapOf(
                89 to listOf(
                    EvolutionDefinition(minLevel = 38)
                )
            )
        ),
        MonsterDefinition(
            id = 89,
            name = "Muk",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a88),
        ),
        MonsterDefinition(
            id = 90,
            name = "Shellder",
            genderRate = 4,
            formResIds = listOf(R.raw.a89),
            evolutions = mapOf(
                91 to listOf(
                    EvolutionDefinition(item = "water-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 91,
            name = "Cloyster",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a90),
        ),
        MonsterDefinition(
            id = 92,
            name = "Gastly",
            genderRate = 4,
            formResIds = listOf(R.raw.a91),
            evolutions = mapOf(
                93 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 93,
            name = "Haunter",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a92),
            evolutions = mapOf(
                94 to listOf(
                    EvolutionDefinition(otherAction = "trade")
                )
            )
        ),
        MonsterDefinition(
            id = 94,
            name = "Gengar",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a93),
        ),
        MonsterDefinition(
            id = 95,
            name = "Onix",
            genderRate = 4,
            formResIds = listOf(R.raw.a94),
            evolutions = mapOf(
                208 to listOf(
                    EvolutionDefinition(item = "metal-coat")
                )
            )
        ),
        MonsterDefinition(
            id = 96,
            name = "Drowzee",
            genderRate = 4,
            formResIds = listOf(R.raw.a95),
            evolutions = mapOf(
                97 to listOf(
                    EvolutionDefinition(minLevel = 26)
                )
            )
        ),
        MonsterDefinition(
            id = 97,
            name = "Hypno",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a96,R.raw.a507),
        ),
        MonsterDefinition(
            id = 98,
            name = "Krabby",
            genderRate = 4,
            formResIds = listOf(R.raw.a97),
            evolutions = mapOf(
                99 to listOf(
                    EvolutionDefinition(minLevel = 28)
                )
            )
        ),
        MonsterDefinition(
            id = 99,
            name = "Kingler",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a98),
        ),
        MonsterDefinition(
            id = 100,
            name = "Voltorb",
            formResIds = listOf(R.raw.a99),
            evolutions = mapOf(
                101 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 101,
            name = "Electrode",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a100),
        ),
        MonsterDefinition(
            id = 102,
            name = "Exeggcute",
            genderRate = 4,
            formResIds = listOf(R.raw.a101),
            evolutions = mapOf(
                103 to listOf(
                    EvolutionDefinition(item = "leaf-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 103,
            name = "Exeggutor",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a102),
        ),
        MonsterDefinition(
            id = 104,
            name = "Cubone",
            genderRate = 4,
            formResIds = listOf(R.raw.a103),
            evolutions = mapOf(
                105 to listOf(
                    EvolutionDefinition(minLevel = 28)
                )
            )
        ),
        MonsterDefinition(
            id = 105,
            name = "Marowak",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a104),
        ),
        MonsterDefinition(
            id = 106,
            name = "Hitmonlee",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a105),
        ),
        MonsterDefinition(
            id = 107,
            name = "Hitmonchan",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a106),
        ),
        MonsterDefinition(
            id = 108,
            name = "Lickitung",
            genderRate = 4,
            formResIds = listOf(R.raw.a107),
            evolutions = mapOf(
                463 to null
            )
        ),
        MonsterDefinition(
            id = 109,
            name = "Koffing",
            genderRate = 4,
            formResIds = listOf(R.raw.a108),
            evolutions = mapOf(
                110 to listOf(
                    EvolutionDefinition(minLevel = 35)
                )
            )
        ),
        MonsterDefinition(
            id = 110,
            name = "Weezing",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a109),
        ),
        MonsterDefinition(
            id = 111,
            name = "Rhyhorn",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a110,R.raw.a508),
            evolutions = mapOf(
                112 to listOf(
                    EvolutionDefinition(minLevel = 42)
                )
            )
        ),
        MonsterDefinition(
            id = 112,
            name = "Rhydon",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a111,R.raw.a509),
            evolutions = mapOf(
                464 to listOf(
                    EvolutionDefinition(item = "protector")
                )
            )
        ),
        MonsterDefinition(
            id = 113,
            name = "Chansey",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a112),
            evolutions = mapOf(
                242 to listOf(
                    EvolutionDefinition(minLevel = 4)
                )
            )
        ),
        MonsterDefinition(
            id = 114,
            name = "Tangela",
            genderRate = 4,
            formResIds = listOf(R.raw.a113),
            evolutions = mapOf(
                465 to null
            )
        ),
        MonsterDefinition(
            id = 115,
            name = "Kangaskhan",
            genderRate = 8,
            formResIds = listOf(R.raw.a114),
        ),
        MonsterDefinition(
            id = 116,
            name = "Horsea",
            genderRate = 4,
            formResIds = listOf(R.raw.a115),
            evolutions = mapOf(
                117 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 117,
            name = "Seadra",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a116),
            evolutions = mapOf(
                230 to listOf(
                    EvolutionDefinition(item = "dragon-scale")
                )
            )
        ),
        MonsterDefinition(
            id = 118,
            name = "Goldeen",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a117,R.raw.a510),
            evolutions = mapOf(
                119 to listOf(
                    EvolutionDefinition(minLevel = 33)
                )
            )
        ),
        MonsterDefinition(
            id = 119,
            name = "Seaking",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a118,R.raw.a511),
        ),
        MonsterDefinition(
            id = 120,
            name = "Staryu",
            formResIds = listOf(R.raw.a119),
            evolutions = mapOf(
                121 to listOf(
                    EvolutionDefinition(item = "water-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 121,
            name = "Starmie",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a120),
        ),
        MonsterDefinition(
            id = 122,
            name = "Mr-mime",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a121),
        ),
        MonsterDefinition(
            id = 123,
            name = "Scyther",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a122,R.raw.a512),
            evolutions = mapOf(
                212 to listOf(
                    EvolutionDefinition(item = "metal-coat")
                )
            )
        ),
        MonsterDefinition(
            id = 124,
            name = "Jynx",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a123),
        ),
        MonsterDefinition(
            id = 125,
            name = "Electabuzz",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a124),
            evolutions = mapOf(
                466 to listOf(
                    EvolutionDefinition(item = "electirizer")
                )
            )
        ),
        MonsterDefinition(
            id = 126,
            name = "Magmar",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a125),
            evolutions = mapOf(
                467 to listOf(
                    EvolutionDefinition(item = "magmarizer")
                )
            )
        ),
        MonsterDefinition(
            id = 127,
            name = "Pinsir",
            genderRate = 4,
            formResIds = listOf(R.raw.a126),
        ),
        MonsterDefinition(
            id = 128,
            name = "Tauros",
            genderRate = 0,
            formResIds = listOf(R.raw.a127),
        ),
        MonsterDefinition(
            id = 129,
            name = "Magikarp",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a128,R.raw.a513),
            evolutions = mapOf(
                130 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 130,
            name = "Gyarados",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a129,R.raw.a514),
        ),
        MonsterDefinition(
            id = 131,
            name = "Lapras",
            genderRate = 4,
            formResIds = listOf(R.raw.a130),
        ),
        MonsterDefinition(
            id = 132,
            name = "Ditto",
            formResIds = listOf(R.raw.a131),
        ),
        MonsterDefinition(
            id = 133,
            name = "Eevee",
            genderRate = 1,
            formResIds = listOf(R.raw.a132),
            evolutions = mapOf(
                134 to listOf(
                    EvolutionDefinition(item = "water-stone")
                ),
                135 to listOf(
                    EvolutionDefinition(item = "thunder-stone")
                ),
                136 to listOf(
                    EvolutionDefinition(item = "fire-stone")
                ),
                196 to listOf(
                    EvolutionDefinition(minLevel = 18, timeOfDay = "day")
                ),
                197 to listOf(
                    EvolutionDefinition(minLevel = 18, timeOfDay = "night")
                ),
                470 to listOf(
                    EvolutionDefinition(item = "leaf-stone")
                ),
                471 to listOf(
                    EvolutionDefinition(item = "ice-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 134,
            name = "Vaporeon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a133),
        ),
        MonsterDefinition(
            id = 135,
            name = "Jolteon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a134),
        ),
        MonsterDefinition(
            id = 136,
            name = "Flareon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a135),
        ),
        MonsterDefinition(
            id = 137,
            name = "Porygon",
            formResIds = listOf(R.raw.a136),
            evolutions = mapOf(
                233 to listOf(
                    EvolutionDefinition(item = "up-grade")
                )
            )
        ),
        MonsterDefinition(
            id = 138,
            name = "Omanyte",
            genderRate = 1,
            formResIds = listOf(R.raw.a137),
            evolutions = mapOf(
                139 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 139,
            name = "Omastar",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a138),
        ),
        MonsterDefinition(
            id = 140,
            name = "Kabuto",
            genderRate = 1,
            formResIds = listOf(R.raw.a139),
            evolutions = mapOf(
                141 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 141,
            name = "Kabutops",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a140),
        ),
        MonsterDefinition(
            id = 142,
            name = "Aerodactyl",
            genderRate = 1,
            formResIds = listOf(R.raw.a141),
        ),
        MonsterDefinition(
            id = 143,
            name = "Snorlax",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a142),
        ),
        MonsterDefinition(
            id = 144,
            name = "Articuno",
            formResIds = listOf(R.raw.a143),
        ),
        MonsterDefinition(
            id = 145,
            name = "Zapdos",
            formResIds = listOf(R.raw.a144),
        ),
        MonsterDefinition(
            id = 146,
            name = "Moltres",
            formResIds = listOf(R.raw.a145),
        ),
        MonsterDefinition(
            id = 147,
            name = "Dratini",
            genderRate = 4,
            formResIds = listOf(R.raw.a146),
            evolutions = mapOf(
                148 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 148,
            name = "Dragonair",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a147),
            evolutions = mapOf(
                149 to listOf(
                    EvolutionDefinition(minLevel = 55)
                )
            )
        ),
        MonsterDefinition(
            id = 149,
            name = "Dragonite",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a148),
        ),
        MonsterDefinition(
            id = 150,
            name = "Mewtwo",
            formResIds = listOf(R.raw.a149),
        ),
        MonsterDefinition(
            id = 151,
            name = "Mew",
            formResIds = listOf(R.raw.a150),
        ),
        MonsterDefinition(
            id = 152,
            name = "Chikorita",
            genderRate = 1,
            formResIds = listOf(R.raw.a151),
            evolutions = mapOf(
                153 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 153,
            name = "Bayleef",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a152),
            evolutions = mapOf(
                154 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 154,
            name = "Meganium",
            genderRate = 1,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a153,R.raw.a515),
        ),
        MonsterDefinition(
            id = 155,
            name = "Cyndaquil",
            genderRate = 1,
            formResIds = listOf(R.raw.a154),
            evolutions = mapOf(
                156 to listOf(
                    EvolutionDefinition(minLevel = 14)
                )
            )
        ),
        MonsterDefinition(
            id = 156,
            name = "Quilava",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a155),
            evolutions = mapOf(
                157 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 157,
            name = "Typhlosion",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a156),
        ),
        MonsterDefinition(
            id = 158,
            name = "Totodile",
            genderRate = 1,
            formResIds = listOf(R.raw.a157),
            evolutions = mapOf(
                159 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 159,
            name = "Croconaw",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a158),
            evolutions = mapOf(
                160 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 160,
            name = "Feraligatr",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a159),
        ),
        MonsterDefinition(
            id = 161,
            name = "Sentret",
            genderRate = 4,
            formResIds = listOf(R.raw.a160),
            evolutions = mapOf(
                162 to listOf(
                    EvolutionDefinition(minLevel = 15)
                )
            )
        ),
        MonsterDefinition(
            id = 162,
            name = "Furret",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a161),
        ),
        MonsterDefinition(
            id = 163,
            name = "Hoothoot",
            genderRate = 4,
            formResIds = listOf(R.raw.a162),
            evolutions = mapOf(
                164 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 164,
            name = "Noctowl",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a163),
        ),
        MonsterDefinition(
            id = 165,
            name = "Ledyba",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a164,R.raw.a516),
            evolutions = mapOf(
                166 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 166,
            name = "Ledian",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a165,R.raw.a517),
        ),
        MonsterDefinition(
            id = 167,
            name = "Spinarak",
            genderRate = 4,
            formResIds = listOf(R.raw.a166),
            evolutions = mapOf(
                168 to listOf(
                    EvolutionDefinition(minLevel = 22)
                )
            )
        ),
        MonsterDefinition(
            id = 168,
            name = "Ariados",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a167),
        ),
        MonsterDefinition(
            id = 169,
            name = "Crobat",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a168),
        ),
        MonsterDefinition(
            id = 170,
            name = "Chinchou",
            genderRate = 4,
            formResIds = listOf(R.raw.a169),
            evolutions = mapOf(
                171 to listOf(
                    EvolutionDefinition(minLevel = 27)
                )
            )
        ),
        MonsterDefinition(
            id = 171,
            name = "Lanturn",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a170),
        ),
        MonsterDefinition(
            id = 172,
            name = "Pichu",
            genderRate = 4,
            formResIds = listOf(R.raw.a662,R.raw.a663),
            evolutions = mapOf(
                25 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 173,
            name = "Cleffa",
            genderRate = 6,
            formResIds = listOf(R.raw.a172),
            evolutions = mapOf(
                35 to listOf(
                    EvolutionDefinition(minLevel = 4)
                )
            )
        ),
        MonsterDefinition(
            id = 174,
            name = "Igglybuff",
            genderRate = 6,
            formResIds = listOf(R.raw.a173),
            evolutions = mapOf(
                39 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 175,
            name = "Togepi",
            genderRate = 1,
            formResIds = listOf(R.raw.a174),
            evolutions = mapOf(
                176 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 176,
            name = "Togetic",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a175),
            evolutions = mapOf(
                468 to listOf(
                    EvolutionDefinition(item = "shiny-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 177,
            name = "Natu",
            genderRate = 4,
            formResIds = listOf(R.raw.a176),
            evolutions = mapOf(
                178 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 178,
            name = "Xatu",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a177,R.raw.a518),
        ),
        MonsterDefinition(
            id = 179,
            name = "Mareep",
            genderRate = 4,
            formResIds = listOf(R.raw.a178),
            evolutions = mapOf(
                180 to listOf(
                    EvolutionDefinition(minLevel = 15)
                )
            )
        ),
        MonsterDefinition(
            id = 180,
            name = "Flaaffy",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a179),
            evolutions = mapOf(
                181 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 181,
            name = "Ampharos",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a180),
        ),
        MonsterDefinition(
            id = 182,
            name = "Bellossom",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a181),
        ),
        MonsterDefinition(
            id = 183,
            name = "Marill",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a182),
            evolutions = mapOf(
                184 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 184,
            name = "Azumarill",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a183),
        ),
        MonsterDefinition(
            id = 185,
            name = "Sudowoodo",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a184,R.raw.a519),
        ),
        MonsterDefinition(
            id = 186,
            name = "Politoed",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a185,R.raw.a520),
        ),
        MonsterDefinition(
            id = 187,
            name = "Hoppip",
            genderRate = 4,
            formResIds = listOf(R.raw.a186),
            evolutions = mapOf(
                188 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 188,
            name = "Skiploom",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a187),
            evolutions = mapOf(
                189 to listOf(
                    EvolutionDefinition(minLevel = 27)
                )
            )
        ),
        MonsterDefinition(
            id = 189,
            name = "Jumpluff",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a188),
        ),
        MonsterDefinition(
            id = 190,
            name = "Aipom",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a189,R.raw.a521),
            evolutions = mapOf(
                424 to null
            )
        ),
        MonsterDefinition(
            id = 191,
            name = "Sunkern",
            genderRate = 4,
            formResIds = listOf(R.raw.a190),
            evolutions = mapOf(
                192 to listOf(
                    EvolutionDefinition(item = "sun-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 192,
            name = "Sunflora",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a191),
        ),
        MonsterDefinition(
            id = 193,
            name = "Yanma",
            genderRate = 4,
            formResIds = listOf(R.raw.a192),
            evolutions = mapOf(
                469 to null
            )
        ),
        MonsterDefinition(
            id = 194,
            name = "Wooper",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a193,R.raw.a522),
            evolutions = mapOf(
                195 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 195,
            name = "Quagsire",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a194,R.raw.a523),
        ),
        MonsterDefinition(
            id = 196,
            name = "Espeon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a195),
        ),
        MonsterDefinition(
            id = 197,
            name = "Umbreon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a196),
        ),
        MonsterDefinition(
            id = 198,
            name = "Murkrow",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a197,R.raw.a524),
            evolutions = mapOf(
                430 to listOf(
                    EvolutionDefinition(item = "dusk-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 199,
            name = "Slowking",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a198),
        ),
        MonsterDefinition(
            id = 200,
            name = "Misdreavus",
            genderRate = 4,
            formResIds = listOf(R.raw.a199),
            evolutions = mapOf(
                429 to listOf(
                    EvolutionDefinition(item = "dusk-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 201,
            name = "Unown",
            formResIds = listOf(R.raw.a590,R.raw.a591,R.raw.a592,R.raw.a593,R.raw.a594,R.raw.a595,R.raw.a596,R.raw.a597,R.raw.a598,R.raw.a599,R.raw.a600,R.raw.a601,R.raw.a602,R.raw.a603,R.raw.a604,R.raw.a605,R.raw.a606,R.raw.a607,R.raw.a608,R.raw.a609,R.raw.a610,R.raw.a611,R.raw.a612,R.raw.a613,R.raw.a614,R.raw.a615,R.raw.a616,R.raw.a617),
        ),
        MonsterDefinition(
            id = 202,
            name = "Wobbuffet",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a201,R.raw.a525),
        ),
        MonsterDefinition(
            id = 203,
            name = "Girafarig",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a202,R.raw.a526),
        ),
        MonsterDefinition(
            id = 204,
            name = "Pineco",
            genderRate = 4,
            formResIds = listOf(R.raw.a203),
            evolutions = mapOf(
                205 to listOf(
                    EvolutionDefinition(minLevel = 31)
                )
            )
        ),
        MonsterDefinition(
            id = 205,
            name = "Forretress",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a204),
        ),
        MonsterDefinition(
            id = 206,
            name = "Dunsparce",
            genderRate = 4,
            formResIds = listOf(R.raw.a205),
        ),
        MonsterDefinition(
            id = 207,
            name = "Gligar",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a206,R.raw.a527),
            evolutions = mapOf(
                472 to listOf(
                    EvolutionDefinition(item = "razor-fang", timeOfDay = "night")
                )
            )
        ),
        MonsterDefinition(
            id = 208,
            name = "Steelix",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a207,R.raw.a528),
        ),
        MonsterDefinition(
            id = 209,
            name = "Snubbull",
            genderRate = 6,
            formResIds = listOf(R.raw.a208),
            evolutions = mapOf(
                210 to listOf(
                    EvolutionDefinition(minLevel = 23)
                )
            )
        ),
        MonsterDefinition(
            id = 210,
            name = "Granbull",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a209),
        ),
        MonsterDefinition(
            id = 211,
            name = "Qwilfish",
            genderRate = 4,
            formResIds = listOf(R.raw.a210),
        ),
        MonsterDefinition(
            id = 212,
            name = "Scizor",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a211,R.raw.a529),
        ),
        MonsterDefinition(
            id = 213,
            name = "Shuckle",
            genderRate = 4,
            formResIds = listOf(R.raw.a212),
        ),
        MonsterDefinition(
            id = 214,
            name = "Heracross",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a213,R.raw.a530),
        ),
        MonsterDefinition(
            id = 215,
            name = "Sneasel",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a214,R.raw.a531),
            evolutions = mapOf(
                461 to listOf(
                    EvolutionDefinition(item = "razor-claw", timeOfDay = "night")
                )
            )
        ),
        MonsterDefinition(
            id = 216,
            name = "Teddiursa",
            genderRate = 4,
            formResIds = listOf(R.raw.a215),
            evolutions = mapOf(
                217 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 217,
            name = "Ursaring",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a216,R.raw.a532),
        ),
        MonsterDefinition(
            id = 218,
            name = "Slugma",
            genderRate = 4,
            formResIds = listOf(R.raw.a217),
            evolutions = mapOf(
                219 to listOf(
                    EvolutionDefinition(minLevel = 38)
                )
            )
        ),
        MonsterDefinition(
            id = 219,
            name = "Magcargo",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a218),
        ),
        MonsterDefinition(
            id = 220,
            name = "Swinub",
            genderRate = 4,
            formResIds = listOf(R.raw.a219),
            evolutions = mapOf(
                221 to listOf(
                    EvolutionDefinition(minLevel = 33)
                )
            )
        ),
        MonsterDefinition(
            id = 221,
            name = "Piloswine",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a220,R.raw.a533),
            evolutions = mapOf(
                473 to null
            )
        ),
        MonsterDefinition(
            id = 222,
            name = "Corsola",
            genderRate = 6,
            formResIds = listOf(R.raw.a221),
        ),
        MonsterDefinition(
            id = 223,
            name = "Remoraid",
            genderRate = 4,
            formResIds = listOf(R.raw.a222),
            evolutions = mapOf(
                224 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 224,
            name = "Octillery",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a223,R.raw.a534),
        ),
        MonsterDefinition(
            id = 225,
            name = "Delibird",
            genderRate = 4,
            formResIds = listOf(R.raw.a224),
        ),
        MonsterDefinition(
            id = 226,
            name = "Mantine",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a225),
        ),
        MonsterDefinition(
            id = 227,
            name = "Skarmory",
            genderRate = 4,
            formResIds = listOf(R.raw.a226),
        ),
        MonsterDefinition(
            id = 228,
            name = "Houndour",
            genderRate = 4,
            formResIds = listOf(R.raw.a227),
            evolutions = mapOf(
                229 to listOf(
                    EvolutionDefinition(minLevel = 24)
                )
            )
        ),
        MonsterDefinition(
            id = 229,
            name = "Houndoom",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a228,R.raw.a535),
        ),
        MonsterDefinition(
            id = 230,
            name = "Kingdra",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a229),
        ),
        MonsterDefinition(
            id = 231,
            name = "Phanpy",
            genderRate = 4,
            formResIds = listOf(R.raw.a230),
            evolutions = mapOf(
                232 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 232,
            name = "Donphan",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a231,R.raw.a536),
        ),
        MonsterDefinition(
            id = 233,
            name = "Porygon2",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a232),
            evolutions = mapOf(
                474 to listOf(
                    EvolutionDefinition(item = "dubious-disc")
                )
            )
        ),
        MonsterDefinition(
            id = 234,
            name = "Stantler",
            genderRate = 4,
            formResIds = listOf(R.raw.a233),
        ),
        MonsterDefinition(
            id = 235,
            name = "Smeargle",
            genderRate = 4,
            formResIds = listOf(R.raw.a234),
        ),
        MonsterDefinition(
            id = 236,
            name = "Tyrogue",
            genderRate = 0,
            formResIds = listOf(R.raw.a235),
            evolutions = mapOf(
                106 to listOf(
                    EvolutionDefinition(minLevel = 20)
                ),
                107 to listOf(
                    EvolutionDefinition(minLevel = 20)
                ),
                237 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 237,
            name = "Hitmontop",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a236),
        ),
        MonsterDefinition(
            id = 238,
            name = "Smoochum",
            genderRate = 8,
            formResIds = listOf(R.raw.a237),
            evolutions = mapOf(
                124 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 239,
            name = "Elekid",
            genderRate = 2,
            formResIds = listOf(R.raw.a238),
            evolutions = mapOf(
                125 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 240,
            name = "Magby",
            genderRate = 2,
            formResIds = listOf(R.raw.a239),
            evolutions = mapOf(
                126 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 241,
            name = "Miltank",
            genderRate = 8,
            formResIds = listOf(R.raw.a240),
        ),
        MonsterDefinition(
            id = 242,
            name = "Blissey",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a241),
        ),
        MonsterDefinition(
            id = 243,
            name = "Raikou",
            formResIds = listOf(R.raw.a242),
        ),
        MonsterDefinition(
            id = 244,
            name = "Entei",
            formResIds = listOf(R.raw.a243),
        ),
        MonsterDefinition(
            id = 245,
            name = "Suicune",
            formResIds = listOf(R.raw.a244),
        ),
        MonsterDefinition(
            id = 246,
            name = "Larvitar",
            genderRate = 4,
            formResIds = listOf(R.raw.a245),
            evolutions = mapOf(
                247 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 247,
            name = "Pupitar",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a246),
            evolutions = mapOf(
                248 to listOf(
                    EvolutionDefinition(minLevel = 55)
                )
            )
        ),
        MonsterDefinition(
            id = 248,
            name = "Tyranitar",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a247),
        ),
        MonsterDefinition(
            id = 249,
            name = "Lugia",
            formResIds = listOf(R.raw.a248),
        ),
        MonsterDefinition(
            id = 250,
            name = "Ho-oh",
            formResIds = listOf(R.raw.a249),
        ),
        MonsterDefinition(
            id = 251,
            name = "Celebi",
            formResIds = listOf(R.raw.a250),
        ),
        MonsterDefinition(
            id = 252,
            name = "Treecko",
            genderRate = 1,
            formResIds = listOf(R.raw.a251),
            evolutions = mapOf(
                253 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 253,
            name = "Grovyle",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a252),
            evolutions = mapOf(
                254 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 254,
            name = "Sceptile",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a253),
        ),
        MonsterDefinition(
            id = 255,
            name = "Torchic",
            genderRate = 1,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a254,R.raw.a537),
            evolutions = mapOf(
                256 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 256,
            name = "Combusken",
            genderRate = 1,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a255,R.raw.a538),
            evolutions = mapOf(
                257 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 257,
            name = "Blaziken",
            genderRate = 1,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a256,R.raw.a539),
        ),
        MonsterDefinition(
            id = 258,
            name = "Mudkip",
            genderRate = 1,
            formResIds = listOf(R.raw.a257),
            evolutions = mapOf(
                259 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 259,
            name = "Marshtomp",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a258),
            evolutions = mapOf(
                260 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 260,
            name = "Swampert",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a259),
        ),
        MonsterDefinition(
            id = 261,
            name = "Poochyena",
            genderRate = 4,
            formResIds = listOf(R.raw.a260),
            evolutions = mapOf(
                262 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 262,
            name = "Mightyena",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a261),
        ),
        MonsterDefinition(
            id = 263,
            name = "Zigzagoon",
            genderRate = 4,
            formResIds = listOf(R.raw.a262),
            evolutions = mapOf(
                264 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 264,
            name = "Linoone",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a263),
        ),
        MonsterDefinition(
            id = 265,
            name = "Wurmple",
            genderRate = 4,
            formResIds = listOf(R.raw.a264),
            evolutions = mapOf(
                266 to listOf(
                    EvolutionDefinition(minLevel = 7)
                ),
                268 to listOf(
                    EvolutionDefinition(minLevel = 7)
                )
            )
        ),
        MonsterDefinition(
            id = 266,
            name = "Silcoon",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a265),
            evolutions = mapOf(
                267 to listOf(
                    EvolutionDefinition(minLevel = 10)
                )
            )
        ),
        MonsterDefinition(
            id = 267,
            name = "Beautifly",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a266,R.raw.a540),
        ),
        MonsterDefinition(
            id = 268,
            name = "Cascoon",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a267),
            evolutions = mapOf(
                269 to listOf(
                    EvolutionDefinition(minLevel = 10)
                )
            )
        ),
        MonsterDefinition(
            id = 269,
            name = "Dustox",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a268,R.raw.a541),
        ),
        MonsterDefinition(
            id = 270,
            name = "Lotad",
            genderRate = 4,
            formResIds = listOf(R.raw.a269),
            evolutions = mapOf(
                271 to listOf(
                    EvolutionDefinition(minLevel = 14)
                )
            )
        ),
        MonsterDefinition(
            id = 271,
            name = "Lombre",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a270),
            evolutions = mapOf(
                272 to listOf(
                    EvolutionDefinition(item = "water-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 272,
            name = "Ludicolo",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a271,R.raw.a542),
        ),
        MonsterDefinition(
            id = 273,
            name = "Seedot",
            genderRate = 4,
            formResIds = listOf(R.raw.a272),
            evolutions = mapOf(
                274 to listOf(
                    EvolutionDefinition(minLevel = 14)
                )
            )
        ),
        MonsterDefinition(
            id = 274,
            name = "Nuzleaf",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a273,R.raw.a543),
            evolutions = mapOf(
                275 to listOf(
                    EvolutionDefinition(item = "leaf-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 275,
            name = "Shiftry",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a274,R.raw.a544),
        ),
        MonsterDefinition(
            id = 276,
            name = "Taillow",
            genderRate = 4,
            formResIds = listOf(R.raw.a275),
            evolutions = mapOf(
                277 to listOf(
                    EvolutionDefinition(minLevel = 22)
                )
            )
        ),
        MonsterDefinition(
            id = 277,
            name = "Swellow",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a276),
        ),
        MonsterDefinition(
            id = 278,
            name = "Wingull",
            genderRate = 4,
            formResIds = listOf(R.raw.a277),
            evolutions = mapOf(
                279 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 279,
            name = "Pelipper",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a278),
        ),
        MonsterDefinition(
            id = 280,
            name = "Ralts",
            genderRate = 4,
            formResIds = listOf(R.raw.a279),
            evolutions = mapOf(
                281 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 281,
            name = "Kirlia",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a280),
            evolutions = mapOf(
                282 to listOf(
                    EvolutionDefinition(minLevel = 30)
                ),
                475 to listOf(
                    EvolutionDefinition(sex = 2, item = "dawn-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 282,
            name = "Gardevoir",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a281),
        ),
        MonsterDefinition(
            id = 283,
            name = "Surskit",
            genderRate = 4,
            formResIds = listOf(R.raw.a282),
            evolutions = mapOf(
                284 to listOf(
                    EvolutionDefinition(minLevel = 22)
                )
            )
        ),
        MonsterDefinition(
            id = 284,
            name = "Masquerain",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a283),
        ),
        MonsterDefinition(
            id = 285,
            name = "Shroomish",
            genderRate = 4,
            formResIds = listOf(R.raw.a284),
            evolutions = mapOf(
                286 to listOf(
                    EvolutionDefinition(minLevel = 23)
                )
            )
        ),
        MonsterDefinition(
            id = 286,
            name = "Breloom",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a285),
        ),
        MonsterDefinition(
            id = 287,
            name = "Slakoth",
            genderRate = 4,
            formResIds = listOf(R.raw.a286),
            evolutions = mapOf(
                288 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 288,
            name = "Vigoroth",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a287),
            evolutions = mapOf(
                289 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 289,
            name = "Slaking",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a288),
        ),
        MonsterDefinition(
            id = 290,
            name = "Nincada",
            genderRate = 4,
            formResIds = listOf(R.raw.a289),
            evolutions = mapOf(
                291 to listOf(
                    EvolutionDefinition(minLevel = 20)
                ),
                292 to listOf(
                    EvolutionDefinition(otherAction = "shed")
                )
            )
        ),
        MonsterDefinition(
            id = 291,
            name = "Ninjask",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a290),
        ),
        MonsterDefinition(
            id = 292,
            name = "Shedinja",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a291),
        ),
        MonsterDefinition(
            id = 293,
            name = "Whismur",
            genderRate = 4,
            formResIds = listOf(R.raw.a292),
            evolutions = mapOf(
                294 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 294,
            name = "Loudred",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a293),
            evolutions = mapOf(
                295 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 295,
            name = "Exploud",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a294),
        ),
        MonsterDefinition(
            id = 296,
            name = "Makuhita",
            genderRate = 2,
            formResIds = listOf(R.raw.a295),
            evolutions = mapOf(
                297 to listOf(
                    EvolutionDefinition(minLevel = 24)
                )
            )
        ),
        MonsterDefinition(
            id = 297,
            name = "Hariyama",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a296),
        ),
        MonsterDefinition(
            id = 298,
            name = "Azurill",
            genderRate = 6,
            formResIds = listOf(R.raw.a297),
            evolutions = mapOf(
                183 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 299,
            name = "Nosepass",
            genderRate = 4,
            formResIds = listOf(R.raw.a298),
            evolutions = mapOf(
                476 to null
            )
        ),
        MonsterDefinition(
            id = 300,
            name = "Skitty",
            genderRate = 6,
            formResIds = listOf(R.raw.a299),
            evolutions = mapOf(
                301 to listOf(
                    EvolutionDefinition(item = "moon-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 301,
            name = "Delcatty",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a300),
        ),
        MonsterDefinition(
            id = 302,
            name = "Sableye",
            genderRate = 4,
            formResIds = listOf(R.raw.a301),
        ),
        MonsterDefinition(
            id = 303,
            name = "Mawile",
            genderRate = 4,
            formResIds = listOf(R.raw.a302),
        ),
        MonsterDefinition(
            id = 304,
            name = "Aron",
            genderRate = 4,
            formResIds = listOf(R.raw.a303),
            evolutions = mapOf(
                305 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 305,
            name = "Lairon",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a304),
            evolutions = mapOf(
                306 to listOf(
                    EvolutionDefinition(minLevel = 42)
                )
            )
        ),
        MonsterDefinition(
            id = 306,
            name = "Aggron",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a305),
        ),
        MonsterDefinition(
            id = 307,
            name = "Meditite",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a306,R.raw.a545),
            evolutions = mapOf(
                308 to listOf(
                    EvolutionDefinition(minLevel = 37)
                )
            )
        ),
        MonsterDefinition(
            id = 308,
            name = "Medicham",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a307,R.raw.a546),
        ),
        MonsterDefinition(
            id = 309,
            name = "Electrike",
            genderRate = 4,
            formResIds = listOf(R.raw.a308),
            evolutions = mapOf(
                310 to listOf(
                    EvolutionDefinition(minLevel = 26)
                )
            )
        ),
        MonsterDefinition(
            id = 310,
            name = "Manectric",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a309),
        ),
        MonsterDefinition(
            id = 311,
            name = "Plusle",
            genderRate = 4,
            formResIds = listOf(R.raw.a310),
        ),
        MonsterDefinition(
            id = 312,
            name = "Minun",
            genderRate = 4,
            formResIds = listOf(R.raw.a311),
        ),
        MonsterDefinition(
            id = 313,
            name = "Volbeat",
            genderRate = 0,
            formResIds = listOf(R.raw.a312),
        ),
        MonsterDefinition(
            id = 314,
            name = "Illumise",
            genderRate = 8,
            formResIds = listOf(R.raw.a313),
        ),
        MonsterDefinition(
            id = 315,
            name = "Roselia",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a314,R.raw.a547),
            evolutions = mapOf(
                407 to listOf(
                    EvolutionDefinition(item = "shiny-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 316,
            name = "Gulpin",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a315,R.raw.a548),
            evolutions = mapOf(
                317 to listOf(
                    EvolutionDefinition(minLevel = 26)
                )
            )
        ),
        MonsterDefinition(
            id = 317,
            name = "Swalot",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a316,R.raw.a549),
        ),
        MonsterDefinition(
            id = 318,
            name = "Carvanha",
            genderRate = 4,
            formResIds = listOf(R.raw.a317),
            evolutions = mapOf(
                319 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 319,
            name = "Sharpedo",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a318),
        ),
        MonsterDefinition(
            id = 320,
            name = "Wailmer",
            genderRate = 4,
            formResIds = listOf(R.raw.a319),
            evolutions = mapOf(
                321 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 321,
            name = "Wailord",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a320),
        ),
        MonsterDefinition(
            id = 322,
            name = "Numel",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a321,R.raw.a550),
            evolutions = mapOf(
                323 to listOf(
                    EvolutionDefinition(minLevel = 33)
                )
            )
        ),
        MonsterDefinition(
            id = 323,
            name = "Camerupt",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a322,R.raw.a551),
        ),
        MonsterDefinition(
            id = 324,
            name = "Torkoal",
            genderRate = 4,
            formResIds = listOf(R.raw.a323),
        ),
        MonsterDefinition(
            id = 325,
            name = "Spoink",
            genderRate = 4,
            formResIds = listOf(R.raw.a324),
            evolutions = mapOf(
                326 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 326,
            name = "Grumpig",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a325),
        ),
        MonsterDefinition(
            id = 327,
            name = "Spinda",
            genderRate = 4,
            formResIds = listOf(R.raw.a326),
        ),
        MonsterDefinition(
            id = 328,
            name = "Trapinch",
            genderRate = 4,
            formResIds = listOf(R.raw.a327),
            evolutions = mapOf(
                329 to listOf(
                    EvolutionDefinition(minLevel = 35)
                )
            )
        ),
        MonsterDefinition(
            id = 329,
            name = "Vibrava",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a328),
            evolutions = mapOf(
                330 to listOf(
                    EvolutionDefinition(minLevel = 45)
                )
            )
        ),
        MonsterDefinition(
            id = 330,
            name = "Flygon",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a329),
        ),
        MonsterDefinition(
            id = 331,
            name = "Cacnea",
            genderRate = 4,
            formResIds = listOf(R.raw.a330),
            evolutions = mapOf(
                332 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 332,
            name = "Cacturne",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a331,R.raw.a552),
        ),
        MonsterDefinition(
            id = 333,
            name = "Swablu",
            genderRate = 4,
            formResIds = listOf(R.raw.a332),
            evolutions = mapOf(
                334 to listOf(
                    EvolutionDefinition(minLevel = 35)
                )
            )
        ),
        MonsterDefinition(
            id = 334,
            name = "Altaria",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a333),
        ),
        MonsterDefinition(
            id = 335,
            name = "Zangoose",
            genderRate = 4,
            formResIds = listOf(R.raw.a334),
        ),
        MonsterDefinition(
            id = 336,
            name = "Seviper",
            genderRate = 4,
            formResIds = listOf(R.raw.a335),
        ),
        MonsterDefinition(
            id = 337,
            name = "Lunatone",
            formResIds = listOf(R.raw.a336),
        ),
        MonsterDefinition(
            id = 338,
            name = "Solrock",
            formResIds = listOf(R.raw.a337),
        ),
        MonsterDefinition(
            id = 339,
            name = "Barboach",
            genderRate = 4,
            formResIds = listOf(R.raw.a338),
            evolutions = mapOf(
                340 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 340,
            name = "Whiscash",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a339),
        ),
        MonsterDefinition(
            id = 341,
            name = "Corphish",
            genderRate = 4,
            formResIds = listOf(R.raw.a340),
            evolutions = mapOf(
                342 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 342,
            name = "Crawdaunt",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a341),
        ),
        MonsterDefinition(
            id = 343,
            name = "Baltoy",
            formResIds = listOf(R.raw.a342),
            evolutions = mapOf(
                344 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 344,
            name = "Claydol",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a343),
        ),
        MonsterDefinition(
            id = 345,
            name = "Lileep",
            genderRate = 1,
            formResIds = listOf(R.raw.a344),
            evolutions = mapOf(
                346 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 346,
            name = "Cradily",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a345),
        ),
        MonsterDefinition(
            id = 347,
            name = "Anorith",
            genderRate = 1,
            formResIds = listOf(R.raw.a346),
            evolutions = mapOf(
                348 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 348,
            name = "Armaldo",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a347),
        ),
        MonsterDefinition(
            id = 349,
            name = "Feebas",
            genderRate = 4,
            formResIds = listOf(R.raw.a348),
            evolutions = mapOf(
                350 to listOf(
                    EvolutionDefinition(item = "prism-scale")
                )
            )
        ),
        MonsterDefinition(
            id = 350,
            name = "Milotic",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a349,R.raw.a553),
        ),
        MonsterDefinition(
            id = 351,
            name = "Castform",
            genderRate = 4,
            formResIds = listOf(R.raw.a618,R.raw.a619,R.raw.a620,R.raw.a621),
        ),
        MonsterDefinition(
            id = 352,
            name = "Kecleon",
            genderRate = 4,
            formResIds = listOf(R.raw.a351),
        ),
        MonsterDefinition(
            id = 353,
            name = "Shuppet",
            genderRate = 4,
            formResIds = listOf(R.raw.a352),
            evolutions = mapOf(
                354 to listOf(
                    EvolutionDefinition(minLevel = 37)
                )
            )
        ),
        MonsterDefinition(
            id = 354,
            name = "Banette",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a353),
        ),
        MonsterDefinition(
            id = 355,
            name = "Duskull",
            genderRate = 4,
            formResIds = listOf(R.raw.a354),
            evolutions = mapOf(
                356 to listOf(
                    EvolutionDefinition(minLevel = 37)
                )
            )
        ),
        MonsterDefinition(
            id = 356,
            name = "Dusclops",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a355),
            evolutions = mapOf(
                477 to listOf(
                    EvolutionDefinition(item = "reaper-cloth")
                )
            )
        ),
        MonsterDefinition(
            id = 357,
            name = "Tropius",
            genderRate = 4,
            formResIds = listOf(R.raw.a356),
        ),
        MonsterDefinition(
            id = 358,
            name = "Chimecho",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a357),
        ),
        MonsterDefinition(
            id = 359,
            name = "Absol",
            genderRate = 4,
            formResIds = listOf(R.raw.a358),
        ),
        MonsterDefinition(
            id = 360,
            name = "Wynaut",
            genderRate = 4,
            formResIds = listOf(R.raw.a359),
            evolutions = mapOf(
                202 to listOf(
                    EvolutionDefinition(minLevel = 15)
                )
            )
        ),
        MonsterDefinition(
            id = 361,
            name = "Snorunt",
            genderRate = 4,
            formResIds = listOf(R.raw.a360),
            evolutions = mapOf(
                362 to listOf(
                    EvolutionDefinition(minLevel = 42)
                ),
                478 to listOf(
                    EvolutionDefinition(sex = 1, item = "dawn-stone")
                )
            )
        ),
        MonsterDefinition(
            id = 362,
            name = "Glalie",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a361),
        ),
        MonsterDefinition(
            id = 363,
            name = "Spheal",
            genderRate = 4,
            formResIds = listOf(R.raw.a362),
            evolutions = mapOf(
                364 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 364,
            name = "Sealeo",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a363),
            evolutions = mapOf(
                365 to listOf(
                    EvolutionDefinition(minLevel = 44)
                )
            )
        ),
        MonsterDefinition(
            id = 365,
            name = "Walrein",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a364),
        ),
        MonsterDefinition(
            id = 366,
            name = "Clamperl",
            genderRate = 4,
            formResIds = listOf(R.raw.a365),
            evolutions = mapOf(
                367 to listOf(
                    EvolutionDefinition(item = "deep-sea-tooth")
                ),
                368 to listOf(
                    EvolutionDefinition(item = "deep-sea-scale")
                )
            )
        ),
        MonsterDefinition(
            id = 367,
            name = "Huntail",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a366),
        ),
        MonsterDefinition(
            id = 368,
            name = "Gorebyss",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a367),
        ),
        MonsterDefinition(
            id = 369,
            name = "Relicanth",
            genderRate = 1,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a368,R.raw.a554),
        ),
        MonsterDefinition(
            id = 370,
            name = "Luvdisc",
            genderRate = 6,
            formResIds = listOf(R.raw.a369),
        ),
        MonsterDefinition(
            id = 371,
            name = "Bagon",
            genderRate = 4,
            formResIds = listOf(R.raw.a370),
            evolutions = mapOf(
                372 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 372,
            name = "Shelgon",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a371),
            evolutions = mapOf(
                373 to listOf(
                    EvolutionDefinition(minLevel = 50)
                )
            )
        ),
        MonsterDefinition(
            id = 373,
            name = "Salamence",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a372),
        ),
        MonsterDefinition(
            id = 374,
            name = "Beldum",
            formResIds = listOf(R.raw.a373),
            evolutions = mapOf(
                375 to listOf(
                    EvolutionDefinition(minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 375,
            name = "Metang",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a374),
            evolutions = mapOf(
                376 to listOf(
                    EvolutionDefinition(minLevel = 45)
                )
            )
        ),
        MonsterDefinition(
            id = 376,
            name = "Metagross",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a375),
        ),
        MonsterDefinition(
            id = 377,
            name = "Regirock",
            formResIds = listOf(R.raw.a376),
        ),
        MonsterDefinition(
            id = 378,
            name = "Regice",
            formResIds = listOf(R.raw.a377),
        ),
        MonsterDefinition(
            id = 379,
            name = "Registeel",
            formResIds = listOf(R.raw.a378),
        ),
        MonsterDefinition(
            id = 380,
            name = "Latias",
            genderRate = 8,
            formResIds = listOf(R.raw.a379),
        ),
        MonsterDefinition(
            id = 381,
            name = "Latios",
            genderRate = 0,
            formResIds = listOf(R.raw.a380),
        ),
        MonsterDefinition(
            id = 382,
            name = "Kyogre",
            formResIds = listOf(R.raw.a381),
        ),
        MonsterDefinition(
            id = 383,
            name = "Groudon",
            formResIds = listOf(R.raw.a382),
        ),
        MonsterDefinition(
            id = 384,
            name = "Rayquaza",
            formResIds = listOf(R.raw.a383),
        ),
        MonsterDefinition(
            id = 385,
            name = "Jirachi",
            formResIds = listOf(R.raw.a384),
        ),
        MonsterDefinition(
            id = 386,
            name = "Deoxys",
            formResIds = listOf(R.raw.a586,R.raw.a587,R.raw.a588,R.raw.a589),
        ),
        MonsterDefinition(
            id = 387,
            name = "Turtwig",
            genderRate = 1,
            formResIds = listOf(R.raw.a386),
            evolutions = mapOf(
                388 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 388,
            name = "Grotle",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a387),
            evolutions = mapOf(
                389 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 389,
            name = "Torterra",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a388),
        ),
        MonsterDefinition(
            id = 390,
            name = "Chimchar",
            genderRate = 1,
            formResIds = listOf(R.raw.a389),
            evolutions = mapOf(
                391 to listOf(
                    EvolutionDefinition(minLevel = 14)
                )
            )
        ),
        MonsterDefinition(
            id = 391,
            name = "Monferno",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a390),
            evolutions = mapOf(
                392 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 392,
            name = "Infernape",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a391),
        ),
        MonsterDefinition(
            id = 393,
            name = "Piplup",
            genderRate = 1,
            formResIds = listOf(R.raw.a392),
            evolutions = mapOf(
                394 to listOf(
                    EvolutionDefinition(minLevel = 16)
                )
            )
        ),
        MonsterDefinition(
            id = 394,
            name = "Prinplup",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a393),
            evolutions = mapOf(
                395 to listOf(
                    EvolutionDefinition(minLevel = 36)
                )
            )
        ),
        MonsterDefinition(
            id = 395,
            name = "Empoleon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a394),
        ),
        MonsterDefinition(
            id = 396,
            name = "Starly",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a395,R.raw.a560),
            evolutions = mapOf(
                397 to listOf(
                    EvolutionDefinition(minLevel = 14)
                )
            )
        ),
        MonsterDefinition(
            id = 397,
            name = "Staravia",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a396,R.raw.a563),
            evolutions = mapOf(
                398 to listOf(
                    EvolutionDefinition(minLevel = 34)
                )
            )
        ),
        MonsterDefinition(
            id = 398,
            name = "Staraptor",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a397,R.raw.a564),
        ),
        MonsterDefinition(
            id = 399,
            name = "Bidoof",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a398,R.raw.a567),
            evolutions = mapOf(
                400 to listOf(
                    EvolutionDefinition(minLevel = 15)
                )
            )
        ),
        MonsterDefinition(
            id = 400,
            name = "Bibarel",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a399,R.raw.a568),
        ),
        MonsterDefinition(
            id = 401,
            name = "Kricketot",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a400,R.raw.a580),
            evolutions = mapOf(
                402 to listOf(
                    EvolutionDefinition(minLevel = 10)
                )
            )
        ),
        MonsterDefinition(
            id = 402,
            name = "Kricketune",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a401,R.raw.a581),
        ),
        MonsterDefinition(
            id = 403,
            name = "Shinx",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a402,R.raw.a574),
            evolutions = mapOf(
                404 to listOf(
                    EvolutionDefinition(minLevel = 15)
                )
            )
        ),
        MonsterDefinition(
            id = 404,
            name = "Luxio",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a403,R.raw.a575),
            evolutions = mapOf(
                405 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 405,
            name = "Luxray",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a404,R.raw.a576),
        ),
        MonsterDefinition(
            id = 406,
            name = "Budew",
            genderRate = 4,
            formResIds = listOf(R.raw.a405),
            evolutions = mapOf(
                315 to listOf(
                    EvolutionDefinition(minLevel = 18, timeOfDay = "day")
                )
            )
        ),
        MonsterDefinition(
            id = 407,
            name = "Roserade",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a406,R.raw.a557),
        ),
        MonsterDefinition(
            id = 408,
            name = "Cranidos",
            genderRate = 1,
            formResIds = listOf(R.raw.a407),
            evolutions = mapOf(
                409 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 409,
            name = "Rampardos",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a408),
        ),
        MonsterDefinition(
            id = 410,
            name = "Shieldon",
            genderRate = 1,
            formResIds = listOf(R.raw.a409),
            evolutions = mapOf(
                411 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 411,
            name = "Bastiodon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a410),
        ),
        MonsterDefinition(
            id = 412,
            name = "Burmy",
            genderRate = 4,
            formResIds = listOf(R.raw.a622,R.raw.a623,R.raw.a624),
            evolutions = mapOf(
                413 to listOf(
                    EvolutionDefinition(sex = 1, minLevel = 20)
                ),
                414 to listOf(
                    EvolutionDefinition(sex = 2, minLevel = 20)
                )
            )
        ),
        MonsterDefinition(
            id = 413,
            name = "Wormadam",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a412,R.raw.a625,R.raw.a626,R.raw.a627),
        ),
        MonsterDefinition(
            id = 414,
            name = "Mothim",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a413),
        ),
        MonsterDefinition(
            id = 415,
            name = "Combee",
            genderRate = 1,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a414,R.raw.a573),
            evolutions = mapOf(
                416 to listOf(
                    EvolutionDefinition(sex = 1, minLevel = 21)
                )
            )
        ),
        MonsterDefinition(
            id = 416,
            name = "Vespiquen",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a415),
        ),
        MonsterDefinition(
            id = 417,
            name = "Pachirisu",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a416,R.raw.a579),
        ),
        MonsterDefinition(
            id = 418,
            name = "Buizel",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a417,R.raw.a556),
            evolutions = mapOf(
                419 to listOf(
                    EvolutionDefinition(minLevel = 26)
                )
            )
        ),
        MonsterDefinition(
            id = 419,
            name = "Floatzel",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a418,R.raw.a561),
        ),
        MonsterDefinition(
            id = 420,
            name = "Cherubi",
            genderRate = 4,
            formResIds = listOf(R.raw.a419),
            evolutions = mapOf(
                421 to listOf(
                    EvolutionDefinition(minLevel = 25)
                )
            )
        ),
        MonsterDefinition(
            id = 421,
            name = "Cherrim",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a632,R.raw.a633),
        ),
        MonsterDefinition(
            id = 422,
            name = "Shellos",
            genderRate = 4,
            formResIds = listOf(R.raw.a628,R.raw.a629),
            evolutions = mapOf(
                423 to listOf(
                    EvolutionDefinition(minLevel = 30)
                )
            )
        ),
        MonsterDefinition(
            id = 423,
            name = "Gastrodon",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a630,R.raw.a631),
        ),
        MonsterDefinition(
            id = 424,
            name = "Ambipom",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a423,R.raw.a585),
        ),
        MonsterDefinition(
            id = 425,
            name = "Drifloon",
            genderRate = 4,
            formResIds = listOf(R.raw.a424),
            evolutions = mapOf(
                426 to listOf(
                    EvolutionDefinition(minLevel = 28)
                )
            )
        ),
        MonsterDefinition(
            id = 426,
            name = "Drifblim",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a425),
        ),
        MonsterDefinition(
            id = 427,
            name = "Buneary",
            genderRate = 4,
            formResIds = listOf(R.raw.a426),
            evolutions = mapOf(
                428 to listOf(
                    EvolutionDefinition(minLevel = 32)
                )
            )
        ),
        MonsterDefinition(
            id = 428,
            name = "Lopunny",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a427),
        ),
        MonsterDefinition(
            id = 429,
            name = "Mismagius",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a428),
        ),
        MonsterDefinition(
            id = 430,
            name = "Honchkrow",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a429),
        ),
        MonsterDefinition(
            id = 431,
            name = "Glameow",
            genderRate = 6,
            formResIds = listOf(R.raw.a430),
            evolutions = mapOf(
                432 to listOf(
                    EvolutionDefinition(minLevel = 38)
                )
            )
        ),
        MonsterDefinition(
            id = 432,
            name = "Purugly",
            genderRate = 6,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a431),
        ),
        MonsterDefinition(
            id = 433,
            name = "Chingling",
            genderRate = 4,
            formResIds = listOf(R.raw.a432),
            evolutions = mapOf(
                358 to listOf(
                    EvolutionDefinition(minLevel = 30, timeOfDay = "night")
                )
            )
        ),
        MonsterDefinition(
            id = 434,
            name = "Stunky",
            genderRate = 4,
            formResIds = listOf(R.raw.a433),
            evolutions = mapOf(
                435 to listOf(
                    EvolutionDefinition(minLevel = 34)
                )
            )
        ),
        MonsterDefinition(
            id = 435,
            name = "Skuntank",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a434),
        ),
        MonsterDefinition(
            id = 436,
            name = "Bronzor",
            formResIds = listOf(R.raw.a435),
            evolutions = mapOf(
                437 to listOf(
                    EvolutionDefinition(minLevel = 33)
                )
            )
        ),
        MonsterDefinition(
            id = 437,
            name = "Bronzong",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a436),
        ),
        MonsterDefinition(
            id = 438,
            name = "Bonsly",
            genderRate = 4,
            formResIds = listOf(R.raw.a437),
            evolutions = mapOf(
                185 to null
            )
        ),
        MonsterDefinition(
            id = 439,
            name = "Mime-jr",
            genderRate = 4,
            formResIds = listOf(R.raw.a438),
            evolutions = mapOf(
                122 to null
            )
        ),
        MonsterDefinition(
            id = 440,
            name = "Happiny",
            genderRate = 8,
            formResIds = listOf(R.raw.a439),
            evolutions = mapOf(
                113 to listOf(
                    EvolutionDefinition(item = "oval-stone", timeOfDay = "day")
                )
            )
        ),
        MonsterDefinition(
            id = 441,
            name = "Chatot",
            genderRate = 4,
            formResIds = listOf(R.raw.a440),
        ),
        MonsterDefinition(
            id = 442,
            name = "Spiritomb",
            genderRate = 4,
            formResIds = listOf(R.raw.a441),
        ),
        MonsterDefinition(
            id = 443,
            name = "Gible",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a442,R.raw.a582),
            evolutions = mapOf(
                444 to listOf(
                    EvolutionDefinition(minLevel = 24)
                )
            )
        ),
        MonsterDefinition(
            id = 444,
            name = "Gabite",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a443,R.raw.a583),
            evolutions = mapOf(
                445 to listOf(
                    EvolutionDefinition(minLevel = 48)
                )
            )
        ),
        MonsterDefinition(
            id = 445,
            name = "Garchomp",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a444,R.raw.a584),
        ),
        MonsterDefinition(
            id = 446,
            name = "Munchlax",
            genderRate = 1,
            formResIds = listOf(R.raw.a445),
            evolutions = mapOf(
                143 to listOf(
                    EvolutionDefinition(minLevel = 18)
                )
            )
        ),
        MonsterDefinition(
            id = 447,
            name = "Riolu",
            genderRate = 1,
            formResIds = listOf(R.raw.a446),
            evolutions = mapOf(
                448 to listOf(
                    EvolutionDefinition(minLevel = 18, timeOfDay = "day")
                )
            )
        ),
        MonsterDefinition(
            id = 448,
            name = "Lucario",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a447),
        ),
        MonsterDefinition(
            id = 449,
            name = "Hippopotas",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a448,R.raw.a569),
            evolutions = mapOf(
                450 to listOf(
                    EvolutionDefinition(minLevel = 34)
                )
            )
        ),
        MonsterDefinition(
            id = 450,
            name = "Hippowdon",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a449,R.raw.a570),
        ),
        MonsterDefinition(
            id = 451,
            name = "Skorupi",
            genderRate = 4,
            formResIds = listOf(R.raw.a450),
            evolutions = mapOf(
                452 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 452,
            name = "Drapion",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a451),
        ),
        MonsterDefinition(
            id = 453,
            name = "Croagunk",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a452,R.raw.a565),
            evolutions = mapOf(
                454 to listOf(
                    EvolutionDefinition(minLevel = 37)
                )
            )
        ),
        MonsterDefinition(
            id = 454,
            name = "Toxicroak",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a453,R.raw.a566),
        ),
        MonsterDefinition(
            id = 455,
            name = "Carnivine",
            genderRate = 4,
            formResIds = listOf(R.raw.a454),
        ),
        MonsterDefinition(
            id = 456,
            name = "Finneon",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a455,R.raw.a571),
            evolutions = mapOf(
                457 to listOf(
                    EvolutionDefinition(minLevel = 31)
                )
            )
        ),
        MonsterDefinition(
            id = 457,
            name = "Lumineon",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a456,R.raw.a572),
        ),
        MonsterDefinition(
            id = 458,
            name = "Mantyke",
            genderRate = 4,
            formResIds = listOf(R.raw.a457),
            evolutions = mapOf(
                226 to listOf(
                    EvolutionDefinition(requiredMember = 223)
                )
            )
        ),
        MonsterDefinition(
            id = 459,
            name = "Snover",
            genderRate = 4,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a458,R.raw.a577),
            evolutions = mapOf(
                460 to listOf(
                    EvolutionDefinition(minLevel = 40)
                )
            )
        ),
        MonsterDefinition(
            id = 460,
            name = "Abomasnow",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a459,R.raw.a578),
        ),
        MonsterDefinition(
            id = 461,
            name = "Weavile",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a460,R.raw.a555),
        ),
        MonsterDefinition(
            id = 462,
            name = "Magnezone",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a461),
        ),
        MonsterDefinition(
            id = 463,
            name = "Lickilicky",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a462),
        ),
        MonsterDefinition(
            id = 464,
            name = "Rhyperior",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a463,R.raw.a562),
        ),
        MonsterDefinition(
            id = 465,
            name = "Tangrowth",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a464,R.raw.a558),
        ),
        MonsterDefinition(
            id = 466,
            name = "Electivire",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a465),
        ),
        MonsterDefinition(
            id = 467,
            name = "Magmortar",
            genderRate = 2,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a466),
        ),
        MonsterDefinition(
            id = 468,
            name = "Togekiss",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a467),
        ),
        MonsterDefinition(
            id = 469,
            name = "Yanmega",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a468),
        ),
        MonsterDefinition(
            id = 470,
            name = "Leafeon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a469),
        ),
        MonsterDefinition(
            id = 471,
            name = "Glaceon",
            genderRate = 1,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a470),
        ),
        MonsterDefinition(
            id = 472,
            name = "Gliscor",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a471),
        ),
        MonsterDefinition(
            id = 473,
            name = "Mamoswine",
            genderRate = 4,
            firstInEvolutionChain = false,
            hasGenderDifferences = true,
            formResIds = listOf(R.raw.a472,R.raw.a559),
        ),
        MonsterDefinition(
            id = 474,
            name = "Porygon-z",
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a473),
        ),
        MonsterDefinition(
            id = 475,
            name = "Gallade",
            genderRate = 0,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a474),
        ),
        MonsterDefinition(
            id = 476,
            name = "Probopass",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a475),
        ),
        MonsterDefinition(
            id = 477,
            name = "Dusknoir",
            genderRate = 4,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a476),
        ),
        MonsterDefinition(
            id = 478,
            name = "Froslass",
            genderRate = 8,
            firstInEvolutionChain = false,
            formResIds = listOf(R.raw.a477),
        ),
        MonsterDefinition(
            id = 479,
            name = "Rotom",
            formResIds = listOf(R.raw.a654,R.raw.a655,R.raw.a656,R.raw.a657,R.raw.a658,R.raw.a659),
        ),
        MonsterDefinition(
            id = 480,
            name = "Uxie",
            formResIds = listOf(R.raw.a479),
        ),
        MonsterDefinition(
            id = 481,
            name = "Mesprit",
            formResIds = listOf(R.raw.a480),
        ),
        MonsterDefinition(
            id = 482,
            name = "Azelf",
            formResIds = listOf(R.raw.a481),
        ),
        MonsterDefinition(
            id = 483,
            name = "Dialga",
            formResIds = listOf(R.raw.a482),
        ),
        MonsterDefinition(
            id = 484,
            name = "Palkia",
            formResIds = listOf(R.raw.a483),
        ),
        MonsterDefinition(
            id = 485,
            name = "Heatran",
            genderRate = 4,
            formResIds = listOf(R.raw.a484),
        ),
        MonsterDefinition(
            id = 486,
            name = "Regigigas",
            formResIds = listOf(R.raw.a485),
        ),
        MonsterDefinition(
            id = 487,
            name = "Giratina",
            formResIds = listOf(R.raw.a660,R.raw.a661),
        ),
        MonsterDefinition(
            id = 488,
            name = "Cresselia",
            genderRate = 8,
            formResIds = listOf(R.raw.a487),
        ),
        MonsterDefinition(
            id = 489,
            name = "Phione",
            formResIds = listOf(R.raw.a488),
        ),
        MonsterDefinition(
            id = 490,
            name = "Manaphy",
            formResIds = listOf(R.raw.a489),
        ),
        MonsterDefinition(
            id = 491,
            name = "Darkrai",
            formResIds = listOf(R.raw.a490),
        ),
        MonsterDefinition(
            id = 492,
            name = "Shaymin",
            formResIds = listOf(R.raw.a652,R.raw.a653),
        ),
        MonsterDefinition(
            id = 493,
            name = "Arceus",
            formResIds = listOf(R.raw.a634,R.raw.a635,R.raw.a636,R.raw.a637,R.raw.a638,R.raw.a639,R.raw.a640,R.raw.a641,R.raw.a642,R.raw.a644,R.raw.a645,R.raw.a646,R.raw.a647,R.raw.a648,R.raw.a649,R.raw.a650,R.raw.a651),
        )
    )
}