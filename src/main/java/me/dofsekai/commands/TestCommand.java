package me.dofsekai.commands;

import me.dofsekai.core.Team;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("test");
        Team team = Team.getTeamByName("eee");
        OfflinePlayer dof = Bukkit.getOfflinePlayer("DofSekai");
        team.addMembers(dof);
        for (OfflinePlayer player : team.getMembers()) {
            if (player.isOnline()) Bukkit.getPlayer(player.getName()).sendMessage(dof.getName() + " a rejoint votre team");
        }
        return false;
    }
}
