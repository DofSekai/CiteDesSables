package me.dofsekai;

import me.dofsekai.commands.CTteamCommand;
import me.dofsekai.listeners.PlayerActionServeur;
import me.dofsekai.listeners.PlayerInteract;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("cteam").setExecutor(new CTteamCommand());
        getServer().getPluginManager().registerEvents(new PlayerActionServeur(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
