package org.darkbriks.hacknCore.skills;

import dev.aurelium.auraskills.api.skill.Skills;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.darkbriks.hacknCore.HacknCore;
import org.darkbriks.hacknCore.playerData.PlayerData;
import org.darkbriks.hacknCore.playerData.PlayerDataManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AddSkillCommand implements CommandExecutor, TabExecutor
{
    // Commande qui ajoute un skill (AuraSkills) à un joueur
    // Commande: /add-skill <player> <skill>
    // Permission: paperPlugin.skill.add
    // Arguments: <player> le joueur à qui ajouter le skill, <skill> le nom du skill à ajouter
    // Exemple: /add-skill Notch mining
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        HacknCore plugin = (HacknCore) Bukkit.getPluginManager().getPlugin("HacknCore");
        if (plugin == null || plugin.getAuraSkills() == null)
        {
            sender.sendMessage("AuraSkills not detected, cannot use this command.");
            return false;
        }
        if (args.length != 2)
        {
            sender.sendMessage("Usage: /add-skill <player> <skill>");
            return false;
        }
        if (!sender.hasPermission("paperPlugin.skill.add"))
        {
            sender.sendMessage("You do not have permission to use this command.");
            return false;
        }

        try
        {
            PlayerData playerData = PlayerDataManager.getPlayerData(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());
            if (playerData == null) { sender.sendMessage("An error occurred while loading player data, if this persists, please contact an administrator."); return false; }

            Skills skill = Skills.valueOf(args[1].toUpperCase(Locale.ROOT));
            playerData.unlockSkill(skill.name());
            sender.sendMessage("Skill " + skill.name() + " added to player " + args[0]);
            return true;
        }
        catch (NullPointerException e)
        {
            sender.sendMessage("Player not found: " + args[0] + ". Please make sure the player is online.");
            return false;
        }
        catch (IllegalArgumentException e)
        {
            sender.sendMessage("Invalid skill name: " + args[1]);
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (strings.length == 1)
        {
            List<String> players = new ArrayList<>();
            for (Player player : Objects.requireNonNull(commandSender.getServer()).getOnlinePlayers())
            {
                players.add(player.getName());
            }
            return players;
        }
        else if (strings.length == 2)
        {
            List<String> skills = new ArrayList<>();
            for (Skills skill : Skills.values())
            {
                skills.add(skill.name());
            }
            return skills;
        }
        return null;
    }
}
