# SmartPokewalker
An expanded PokeWalker for use on WearOS.

## Build Preparation & Requirements
Graphics are not included with this project. A ROM of ***your own*** must be supplied. For testing purposes I utilize a backup of my copy of SoulSilver.

1. Place your ROM file at the `root` of the project and name it `rom.nds`

2. The script that processes the ROM requires **Python 3.7+** and the following dependencies installed through pip
```bash
pip install ndspy pillow
```
The images get extracted prior to the `merge` gradle task so that they can be included in the build's Resource references.

## Planned Features
* Trade over BLE