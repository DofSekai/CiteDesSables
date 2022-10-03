package me.dofsekai.core;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Team {

    private final String name;
    private static final int maxCharactersName = 15;
    private final UUID leaderUUID;
    private int money;
    private final ArrayList<UUID> members = new ArrayList<>();
    private static final ArrayList<Team> teamsList = new ArrayList<>();
    private final ArrayList<UUID> invited;

    public Team(String name, UUID leaderUUID, int money) {
        this.name = name;
        this.leaderUUID = leaderUUID;
        this.money = money;
        this.addMembers(leaderUUID);
        this.invited = new ArrayList<>();
        teamsList.add(this);
    }

    public static Team getTeamOf(UUID uuidPlayer) {
        for (Team team : Team.teamsList) {
            if (team.isMember(uuidPlayer)) return team;
        }
        return null;
    }

    public boolean isMember(UUID uuidMember) {
        return (this.members.contains(uuidMember));
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

    public UUID getLeaderUUID() {
        return leaderUUID;
    }

    public int getMoney() {
        return money;
    }

    public static int getMaxCharactersName() { return maxCharactersName; }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean addMembers(UUID playerUUID) {
        if (this.members.size() > 2) return false;
        this.members.add(playerUUID);
        return true;
    }

    public boolean isInvited(UUID playerUUID) {
        return this.invited.contains(playerUUID);
    }

    public void invite(UUID playerInvitedUUID) {
        this.invited.add(playerInvitedUUID);
        Bukkit.getPlayer(this.leaderUUID).sendMessage(Bukkit.getPlayer(this.leaderUUID).getName() + " vous a invité dans la team " + this.name);
    }

    public boolean isLeader(UUID playerUUID) {
        return this.leaderUUID.equals(playerUUID);
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

    public void kick(UUID playerUUID) {
        if (this.members.contains(playerUUID)) this.members.remove(playerUUID);
    }

    public static void disband(Team team) {
        teamsList.remove(team);
    }

    public ArrayList<UUID> getMembers() { return members; }
}
