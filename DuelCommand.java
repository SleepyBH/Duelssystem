package com.duelmaster.commands;

import com.duelmaster.DuelMaster;
import com.duelmaster.managers.DuelManager;
import org.bukkit.Bukkit;
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

public class DuelCommand implements CommandExecutor, TabCompleter {
    private final DuelMaster plugin;
    private final DuelManager duelManager;

    public DuelCommand(DuelMaster plugin) {
        this.plugin = plugin;
        this.duelManager = plugin.getDuelManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§eUsage: /duel <player|accept|deny|leave>");
            return true;
        }
        String sub = args[0].toLowerCase();
        switch (sub) {
            case "accept":
                duelManager.acceptDuel(player);
                break;
            case "deny":
                duelManager.denyDuel(player);
                break;
            case "leave":
                duelManager.leaveDuel(player);
                break;
            default:
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null || !target.isOnline()) {
                    player.sendMessage("§cPlayer not found or not online.");
                    return true;
                }
                duelManager.sendDuelRequest(player, target);
                player.sendMessage("§6You have challenged §e" + target.getName() + "§6 to a duel! Awaiting their response...\n§7(Tip: You can only have one active request at a time.)");
                target.sendMessage("§e⚔ §6" + player.getName() + " §ewants to duel you!\n§aType §l/duel accept§r§a to accept or §c/duel deny§a to decline.");
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("accept");
            options.add("deny");
            options.add("leave");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getName().equalsIgnoreCase(sender.getName())) {
                    options.add(p.getName());
                }
            }
            return options;
        }
        return Collections.emptyList();
    }
} 