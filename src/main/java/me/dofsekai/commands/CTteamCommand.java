package me.dofsekai.commands;

import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import me.dofsekai.menus.TeamMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CTteamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        if (sender.isOp()) {
            if (args.length > 1 && args[0].equalsIgnoreCase("kick")) {
                Team team = Team.getTeamOf(Bukkit.getOfflinePlayer(args[1]));
                if (team == null) return false;
                if (team.getLeader().equals(Bukkit.getOfflinePlayer(args[1]))) {
                    sender.sendMessage("pas possible c'est un leader");
                    return false;
                }
                team.kick(Bukkit.getOfflinePlayer(args[1]));
                sender.sendMessage(Bukkit.getOfflinePlayer(args[1]).getName() + " a été kick de sa team");
                return true;
            }
            if (args.length > 1 && args[0].equalsIgnoreCase("disband")) {
                Team team = Team.getTeamByName(args[1]);
                if (team == null) return false;
                Team.disband(team);
                sender.sendMessage("la team " + team.getName() + " a été disband");
                return true;
            }
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Profile.getProfileOfPlayer(player.getUniqueId()).getPlayerstate() == PlayerState.NOTHING) {
                player.openInventory(TeamMenu.General());
                return true;
            }
        }

        if (sender instanceof ConsoleCommandSender) {
            if (args.length > 1 && args[0].equalsIgnoreCase("create")) {
                Player player = Bukkit.getPlayer(args[1]);
                if (Team.getTeamByName(args[2]) != null) {
                    player.sendMessage("Cette team est déjà créée.");
                    player.sendMessage("Ecrivez un autre nom de team");
                    return false;
                }
                Team team = new Team(args[2], player, 0);
                Bukkit.broadcastMessage("La team " + team.getName() + " a été créée.");
                team.setMoney(0);
                Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.NOTHING);
                return true;
            }

            if (args.length > 1 && args[0].equalsIgnoreCase("invite")) {
                Player playerInvite = Bukkit.getPlayer(args[1]);
                Player playerInvited = Bukkit.getPlayer(args[2]);
                Team team = Team.getTeamOf(playerInvite);
                if (playerInvite == playerInvited) {
                    playerInvite.sendMessage("Pourquoi tu t'invites ?");
                    return false;
                }
                if (team.isMember(playerInvited)) {
                    playerInvite.sendMessage("Invite pas un membre de ton équipe !!");
                    return false;
                }
                if (team.isInvited(playerInvited.getUniqueId())) {
                    playerInvite.sendMessage("Une fois pas deux !!");
                    return false;
                }
                team.invite(playerInvited.getUniqueId());
                playerInvite.sendMessage("Vous avez invité " + playerInvited.getName());
                playerInvited.sendMessage(playerInvite.getName() + " vous a invité");
                Profile.getProfileOfPlayer(playerInvite.getUniqueId()).setPlayerstate(PlayerState.NOTHING);
                return true;
            }

            if (args.length > 1 && args[0].equalsIgnoreCase("join")) {
                System.out.println("ok");
                Team team = Team.getTeamByName(args[1]);
                Player playerInvite = Bukkit.getPlayer(args[2]);
                Player playerInvited = Bukkit.getPlayer(args[3]);
                if (team.getMembers().size() >= 2) {
                    playerInvite.sendMessage("Team complète");
                    return false;
                }
                playerInvited.sendMessage("Tu as rejoint la team.");
                for (OfflinePlayer p : team.getMembers()) {
                    if (p.isOnline()) Bukkit.getPlayer(p.getUniqueId()).sendMessage(playerInvited.getName() + " a rejoint la team");
                }
                team.addMembers(playerInvited);
                Profile.getProfileOfPlayer(playerInvited.getUniqueId()).setPlayerstate(PlayerState.NOTHING);
                return true;
            }
        }
        return true;
    }
}
