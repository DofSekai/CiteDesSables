package me.dofsekai;

import me.dofsekai.commands.CTteamCommand;
import me.dofsekai.commands.TestCommand;
import me.dofsekai.listeners.PlayerActionServeur;
import me.dofsekai.listeners.PlayerInteract;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin Cite on enable");
        getCommand("test").setExecutor(new TestCommand());
        getCommand("cteam").setExecutor(new CTteamCommand());
        getServer().getPluginManager().registerEvents(new PlayerActionServeur(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
