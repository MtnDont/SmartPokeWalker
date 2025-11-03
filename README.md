# SmartPokewalker
An expanded PokeWalker for use on WearOS.

## Build Preparation
Graphics are not included with this project. A ROM of *your own* must be supplied. For testing purposes I utilize a backup of my copy of SoulSilver.

Place the ROM at the `root` of the project, named `rom.nds`

The images get extracted prior to the `merge` gradle task so that they can be included in the build's Resource references.

## Planned Features
* Trade over BLE