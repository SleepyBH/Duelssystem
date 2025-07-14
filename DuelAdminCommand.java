package com.duelmaster.commands;

import com.duelmaster.DuelMaster;
import com.duelmaster.managers.ArenaManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DuelAdminCommand implements CommandExecutor, TabCompleter {
    private final DuelMaster plugin;
    private final ArenaManager arenaManager;

    public DuelAdminCommand(DuelMaster plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("duelmaster.admin")) {
            player.sendMessage("§cNo permission.");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("§eUsage: /dueladmin <createarena|setspawn>");
            return true;
        }
        String sub = args[0].toLowerCase();
        switch (sub) {
            case "createarena":
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /dueladmin createarena <name>");
                    return true;
                }
                String name = args[1];
                arenaManager.createArena(name);
                player.sendMessage("§aArena '" + name + "' created.");
                break;
            case "setspawn":
                if (args.length < 3) {
                    player.sendMessage("§cUsage: /dueladmin setspawn <arena> <1|2>");
                    return true;
                }
                String arenaName = args[1];
                int spawn;
                try {
                    spawn = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cSpawn must be 1 or 2.");
                    return true;
                }
                if (spawn != 1 && spawn != 2) {
                    player.sendMessage("§cSpawn must be 1 or 2.");
                    return true;
                }
                Location loc = player.getLocation();
                arenaManager.setArenaSpawn(arenaName, spawn, loc);
                player.sendMessage("§aSet spawn " + spawn + " for arena '" + arenaName + "'.");
                break;
            default:
                player.sendMessage("§eUsage: /dueladmin <createarena|setspawn>");
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("createarena");
            options.add("setspawn");
            return options;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("setspawn")) {
            return new ArrayList<>(arenaManager.getArenas().stream().map(a -> a.getName()).toList());
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("setspawn")) {
            List<String> spawns = new ArrayList<>();
            spawns.add("1");
            spawns.add("2");
            return spawns;
        }
        return Collections.emptyList();
    }
} 