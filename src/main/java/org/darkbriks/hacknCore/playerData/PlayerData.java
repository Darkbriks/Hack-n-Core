package org.darkbriks.hacknCore.playerData;

import dev.aurelium.auraskills.api.skill.Skills;
import org.darkbriks.hacknCore.HacknCore;
import org.darkbriks.hacknCore.Logger;
import org.darkbriks.hacknCore.utils.YAML;

import java.io.File;
import java.util.*;

public record PlayerData(UUID playerUUID, Set<String> unlockedSkills)
{
    public PlayerData(UUID playerUUID) { this(playerUUID, new HashSet<>()); }

    public boolean hasSkill(String skillName) { return unlockedSkills.contains(skillName); }
    public boolean hasSkill(Skills skill) { return hasSkill(skill.name()); }

    public void unlockSkill(String skillName) { unlockedSkills.add(skillName); }
    public void unlockSkill(Skills skill) { unlockSkill(skill.name()); }

    public void lockSkill(String skillName) { unlockedSkills.remove(skillName); }
    public void lockSkill(Skills skill) { lockSkill(skill.name()); }

    public void save(HacknCore plugin, boolean async)
    {
        assert plugin.PLAYER_DATA_FOLDER != null;
        File file = plugin.getDataFolder().toPath().resolve(plugin.PLAYER_DATA_FOLDER).resolve(playerUUID.toString() + ".yml").toFile();

        Logger.verbose("Saving player data: " + file.getPath(), true);

        YAML.saveRecord(this, file, async);
    }
}