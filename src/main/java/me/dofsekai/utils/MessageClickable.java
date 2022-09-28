package me.dofsekai.utils;

import me.dofsekai.core.Team;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.util.UUID;

public class MessageClickable {

    public static void inviteMessage(Team team, UUID playerInviteUUID, UUID playerInvitedUUID) {
        Bukkit.getPlayer(playerInvitedUUID).sendMessage(Bukkit.getPlayer(team.getLeaderUUID()) + " vous a invité à rejoindre sa team " + team.getName());
        TextComponent msg = new TextComponent("[Rejoindre]");
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Rejoindre la team").create()));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cteam join " + team.getName() + " " + Bukkit.getPlayer(playerInvitedUUID).getName()));
        Bukkit.getPlayer(playerInvitedUUID).spigot().sendMessage(msg);
    }
}
