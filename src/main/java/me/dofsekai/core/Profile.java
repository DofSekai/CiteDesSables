package me.dofsekai.core;

import java.util.ArrayList;
import java.util.UUID;

public class Profile {

    private final String name;
    private final UUID uuid;
    private Team team;
    private PlayerState playerstate;
    private static ArrayList<Profile> profiles = new ArrayList<>();

    public Profile(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        team = null;
        playerstate = PlayerState.NOTHING;
        profiles.add(this);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public PlayerState getPlayerstate() {
        return playerstate;
    }

    public void setPlayerstate(PlayerState playerstate) {
        this.playerstate = playerstate;
    }

    public static boolean hasProfile(UUID playerUUID) {
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getUuid() == playerUUID) return true;
        }
        return false;
    }

    public static Profile getProfileOfPlayer(UUID playerUUID) {
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getUuid() == playerUUID) return profiles.get(i);
        }
        return null;
    }
}
