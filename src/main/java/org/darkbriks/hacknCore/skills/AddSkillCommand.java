package org.darkbriks.hacknCore.skills;

import dev.aurelium.auraskills.api.skill.Skills;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.darkbriks.hacknCore.HacknCore;
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

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage("Player " + args[0] + " not found.");
            return false;
        }
        User user = plugin.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        user.data().add(Node.builder("HacknCore.skill." + args[1].toLowerCase(Locale.ROOT)).build());
        plugin.getLuckPerms().getUserManager().saveUser(user);
        sender.sendMessage("Skill " + args[1] + " added to player " + args[0]);
        return true;
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
            // TODO: add support for custom skills
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
