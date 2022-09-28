package me.dofsekai.core;

import me.dofsekai.utils.MessageClickable;

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

    public void addMembers(UUID playerUUID) {
        if (this.members.size() < 2) this.members.add(playerUUID);
    }

    public boolean isInvited(UUID playerUUID) {
        return this.invited.contains(playerUUID);
    }

    public void invite(UUID playerInviteUUID, UUID playerInvitedUUID) {
        if (!this.invited.contains(playerInvitedUUID)) this.invited.add(playerInvitedUUID);
        MessageClickable.inviteMessage(this, playerInviteUUID, playerInvitedUUID);
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
}
