package me.dofsekai.core;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Team {

    private final String name;
    private static final int maxCharactersName = 15;
    private OfflinePlayer leader;
    private int money;
    private final ArrayList<OfflinePlayer> members = new ArrayList<>();
    private static final ArrayList<Team> teamsList = new ArrayList<>();
    private final ArrayList<UUID> invited;

    public Team(String name, OfflinePlayer leader, int money) {
        this.name = name;
        this.money = money;
        this.leader = leader;
        this.addMembers(leader);
        this.invited = new ArrayList<>();
        teamsList.add(this);
    }

    public static Team getTeamOf(OfflinePlayer player) {
        for (Team team : Team.teamsList) {
            if (team.isMember(player)) return team;
        }
        return null;
    }

    public boolean isMember(OfflinePlayer member) {
        return (this.members.contains(member));
    }

    public static Team getTeamByName(String name) {
        for (Team team : Team.teamsList) {
            if (name.equalsIgnoreCase(team.getName())) return team;
        }
        return null;
    }

    public static boolean hasSpecialCharacters(String name) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        return m.find();
    }

    public String getName() {
        return name;
    }

    public OfflinePlayer getLeader() {
        return leader;
    }

    public int getMoney() {
        return money;
    }

    public static int getMaxCharactersName() { return maxCharactersName; }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean addMembers(OfflinePlayer player) {
        if (this.members.size() >= 2) return false;
        this.members.add(player);
        return true;
    }

    public boolean isInvited(UUID playerUUID) {
        return this.invited.contains(playerUUID);
    }

    public void invite(UUID playerInvitedUUID) {
        this.invited.add(playerInvitedUUID);
    }

    //Déconnexion du joueur
    public void removeInvite(UUID playerUUID) {
        if (this.invited.contains(playerUUID)) return;
        this.invited.remove(playerUUID);
    }
    
    public static ArrayList<Team> getAllTeams() {
        return teamsList;
    }

    public ArrayList<UUID> getInvited() { return this.invited; }

    public void kick(OfflinePlayer player) { if (this.members.contains(player)) this.members.remove(player); }

    public static void disband(Team team) {
        teamsList.remove(team);
    }

    public ArrayList<OfflinePlayer> getMembers() { return members; }
}
