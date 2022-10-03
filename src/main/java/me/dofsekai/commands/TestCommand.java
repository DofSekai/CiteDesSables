package me.dofsekai.commands;

import me.dofsekai.core.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            if (args.length > 1 && args[0].equalsIgnoreCase("kick")) {
                Team team = Team.getTeamOf(Bukkit.getPlayer(args[1]).getUniqueId());
                if (team.isLeader(((Player) sender).getUniqueId())) {
                    sender.sendMessage("pas possible c'est un leader");
                    return false;
                }
                team.kick(Bukkit.getPlayer(args[1]).getUniqueId());
                sender.sendMessage(Bukkit.getPlayer(args[1]).getName() + "a été kick de sa team");
                return true;
            }
            if (args.length > 1 && args[0].equalsIgnoreCase("disband")) {
                Team team = Team.getTeamByName(args[1]);
                Team.disband(team);
                sender.sendMessage("la team " + team.getName() + " a été disband");
                return true;
            }
        } else {
            sender.sendMessage("impossible");
        }
        System.out.println("nombre de teams : " + Team.getAllTeams().size());
        return false;
    }
}
