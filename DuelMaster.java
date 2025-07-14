package com.duelmaster;

import org.bukkit.plugin.java.JavaPlugin;
import com.duelmaster.managers.ArenaManager;
import com.duelmaster.managers.DuelManager;
import com.duelmaster.commands.DuelCommand;
import com.duelmaster.commands.DuelAdminCommand;
import com.duelmaster.listeners.DuelListener;

public class DuelMaster extends JavaPlugin {
    private static DuelMaster instance;
    private ArenaManager arenaManager;
    private DuelManager duelManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.arenaManager = new ArenaManager(this);
        this.duelManager = new DuelManager(this);
        DuelCommand duelCommand = new DuelCommand(this);
        getCommand("duel").setExecutor(duelCommand);
        getCommand("duel").setTabCompleter(duelCommand);
        DuelAdminCommand adminCommand = new DuelAdminCommand(this);
        getCommand("dueladmin").setExecutor(adminCommand);
        getCommand("dueladmin").setTabCompleter(adminCommand);
        getServer().getPluginManager().registerEvents(new DuelListener(this), this);
        getLogger().info("DuelMaster enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DuelMaster disabled.");
    }

    public static DuelMaster getInstance() {
        return instance;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }
} 