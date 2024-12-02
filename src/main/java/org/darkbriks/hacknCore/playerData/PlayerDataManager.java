package org.darkbriks.hacknCore.playerData;

import dev.aurelium.auraskills.api.skill.Skills;
import org.bukkit.Bukkit;
import org.darkbriks.hacknCore.HacknCore;
import org.darkbriks.hacknCore.utils.YAML;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

public class PlayerDataManager
{
    private static HacknCore plugin;
    private static boolean isInitialized = false;
    private static final Map<UUID, PlayerData> loadedPlayers = new HashMap<>();

    public static void init(HacknCore plugin)
    {
        if (!isInitialized)
        {
            PlayerDataManager.plugin = plugin;
            isInitialized = true;
        }
    }

    private static void add(UUID uuid, Set<String> unlockedSkills) { loadedPlayers.put(uuid, new PlayerData(uuid, unlockedSkills)); }
    private static void add(PlayerData playerData) { loadedPlayers.put(playerData.playerUUID(), playerData); }
    private static void remove(UUID uuid) { loadedPlayers.remove(uuid); }


    public static boolean isPlayerLoaded(UUID uuid) { return loadedPlayers.containsKey(uuid); }
    public static boolean isPlayerLoaded(String playerName)
    {
        try { return isPlayerLoaded(Objects.requireNonNull(Bukkit.getPlayer(playerName)).getUniqueId()); }
        catch (NullPointerException e) { return false; }
    }


    public static @Nullable PlayerData getPlayerData(UUID uuid) { if (isPlayerLoaded(uuid)) { return loadedPlayers.get(uuid); } else { return load(uuid); } }
    public static @Nullable PlayerData getPlayerData(String playerName)
    {
        try { return getPlayerData(Objects.requireNonNull(Bukkit.getPlayer(playerName)).getUniqueId()); }
        catch (NullPointerException e) { return null; }
    }


    public static boolean hasSkill(UUID uuid, String skillName) { if (isPlayerLoaded(uuid)) { return loadedPlayers.get(uuid).hasSkill(skillName); } else { return false; } }
    public static boolean hasSkill(UUID uuid, Skills skill) { return hasSkill(uuid, skill.name()); }


    public static void save(UUID uuid, boolean async) { if (isPlayerLoaded(uuid)) { loadedPlayers.get(uuid).save(plugin, async); } }
    public static void save(UUID uuid) { save(uuid, false); }


    public static @Nullable PlayerData load(UUID uuid)
    {
        if (isPlayerLoaded(uuid)) { return getPlayerData(uuid); }

        assert plugin.PLAYER_DATA_FOLDER != null;
        File file = plugin.getDataFolder().toPath().resolve(plugin.PLAYER_DATA_FOLDER).resolve(uuid.toString() + ".yml").toFile();

        PlayerData playerData = YAML.loadRecord(PlayerData.class, file);
        if (playerData == null) { playerData = new PlayerData(uuid); }
        add(playerData);

        return playerData;
    }

    public static void saveAll(boolean async)
    {
        for (UUID uuid : loadedPlayers.keySet()) { save(uuid, async); }
    }

    public static void unloadPlayer(UUID uuid) { save(uuid); remove(uuid); }
    public static void unloadPlayer(String playerName)
    {
        try { unloadPlayer(Objects.requireNonNull(Bukkit.getPlayer(playerName)).getUniqueId()); }
        catch (NullPointerException ignored) { }
    }
}
