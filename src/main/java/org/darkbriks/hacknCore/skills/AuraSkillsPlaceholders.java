package org.darkbriks.hacknCore.skills;

import dev.aurelium.auraskills.api.skill.Skills;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.darkbriks.hacknCore.Logger;
import org.jetbrains.annotations.NotNull;

public class AuraSkillsPlaceholders extends PlaceholderExpansion
{

    @Override
    public @NotNull String getIdentifier() { return "HacknCore placeholders for AuraSkills"; }

    @Override
    public @NotNull String getAuthor() { return "Darkbriks"; }

    @Override
    public @NotNull String getVersion() { return "0.2.0-dev"; }

    @Override
    public String onPlaceholderRequest(Player player, String identifier)
    {
        // %HacknCore_auraskills_has_skill_<skill>%
        Logger.verbose("Requesting placeholder: " + identifier, true);
        if (identifier.startsWith("auraskills_has_skill_"))
        {
            String skillName = identifier.substring(21);
            if (player != null)
            {
                return player.hasPermission("HacknCore.skill." + skillName) ? "true" : "false";
            }
            return "false";
        }

        return null;
    }
}