# DuelMaster

A modern 1v1 duel plugin for Spigot/Paper 1.21+ (Java 21, Maven).

Made by alexetrey (Dm for any issues or any help)

## Features
- /duel <player>: Send duel request
- /duel accept|deny|leave: Manage duels
- /dueladmin createarena <name>: Create arena
- /dueladmin setspawn <arena> <1|2>: Set arena spawnpoints
- Arenas saved in `arenas.yml`
- Requests timeout, cooldowns, anti-cheat, and more

## Requirements
- Java 21+
- PaperMC 1.21+ (or Spigot 1.21+)
- Maven


The plugin jar will be in `target/DuelMaster-1.0.0.jar`.

## Install
1. Place the jar in your server's `plugins/` folder.
2. Start the server. The plugin will generate config files.

## Arena Setup
1. `/dueladmin createarena <name>`
2. Stand at spawnpoint 1, run `/dueladmin setspawn <arena> 1`
3. Stand at spawnpoint 2, run `/dueladmin setspawn <arena> 2`

## Usage
- `/duel <player>` to challenge
- `/duel accept` or `/duel deny`
- `/duel leave` to forfeit

## Configuration
- `arenas.yml`: Arena locations
- `config.yml`: Messages, cooldowns, etc. (to be expanded)

## Notes
- Only one duel per player at a time
- Requests timeout in 30 seconds
- All player states are restored after duels

