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
                    player.sendMessage("Ecrivez un autre nom de team");
                    return false;
                }
                Team team = new Team(args[2], player.getUniqueId(), 0);
                Bukkit.broadcastMessage("La team " + team.getName() + " a été créée.");
                team.setMoney(0);
                profile.setPlayerstate(PlayerState.NOTHING);
                return true;
            }

            if (args.length > 1 && args[0].equalsIgnoreCase("invite")) {
                Player playerInvite = Bukkit.getPlayer(args[1]);
                Player playerInvited = Bukkit.getPlayer(args[2]);
                Team team = Team.getTeamOf(playerInvite.getUniqueId());
                if (playerInvite == playerInvited) {
                    playerInvite.sendMessage("Pourquoi tu t'invites ?");
                    return false;
                }
                if (team.isMember(playerInvited.getUniqueId())) {
                    playerInvite.sendMessage("Invite pas un membre de ton équipe !!");
                    return false;
                }
                if (team.isInvited(playerInvited.getUniqueId())) {
                    playerInvite.sendMessage("Une fois pas deux !!");
                    return false;
                }
                Profile profilePlayerInvite = Profile.getProfileOfPlayer(playerInvite.getUniqueId());
                team.invite(playerInvite.getUniqueId(), playerInvited.getUniqueId());
                playerInvite.sendMessage("Vous avez invité " + playerInvited.getName());
                profilePlayerInvite.setPlayerstate(PlayerState.NOTHING);
                return true;
            }
        }
        return true;
    }
}
