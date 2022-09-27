package me.dofsekai.commands;

import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import me.dofsekai.menus.TeamMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CTteamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Profile.getProfileOfPlayer(player.getUniqueId()).getPlayerstate() == PlayerState.NOTHING) player.openInventory(TeamMenu.General());
            return true;
        }

        if (sender instanceof ConsoleCommandSender) {
            if (args.length > 1 && args[0].equalsIgnoreCase("create")) {
                Player player = Bukkit.getPlayer(args[1]);
                Profile profile = Profile.getProfileOfPlayer(player.getUniqueId());
                if (Team.getTeamByName(args[2]) != null) {
                    player.sendMessage("Cette team est déjà créée.");
                    return false;
                }
                Team team = new Team(args[2], player.getUniqueId(), 0);
                Bukkit.broadcastMessage("La team " + team.getName() + " a été créée.");
                profile.setPlayerstate(PlayerState.NOTHING);
                return true;
            }
        }
        return false;
    }
}
