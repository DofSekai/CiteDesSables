package me.dofsekai.core;

import java.util.ArrayList;
import java.util.UUID;

public class Team {

    private final String name;
    private final UUID leaderUUID;
    private int money;
    private ArrayList<UUID> members = new ArrayList<>();
    private final int maxPlayer = 5;
    private static ArrayList<Team> teamsList = new ArrayList<>();

    public Team(String name, UUID leaderUUID, int money) {
        this.name = name;
        this.leaderUUID = leaderUUID;
        this.money = money;
        this.members.add(leaderUUID);
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

    public String getName() {
        return name;
    }

    public UUID getLeaderUUID() {
        return leaderUUID;
    }

    public int getMoney() {
        return money;
    }

    public void addMembers(UUID playerUUID) {
        if (this.members.size() < maxPlayer) this.members.add(playerUUID);
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
