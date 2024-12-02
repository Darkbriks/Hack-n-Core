package org.darkbriks.hacknCore.permission.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.darkbriks.hacknCore.Logger;
import org.darkbriks.hacknCore.permission.MaterialPermission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetItemPermissionCommand implements CommandExecutor, TabExecutor
{
    // Commande qui renvoie le grade requis pour utiliser un item donné
    // Commande: /get-item-grade <item>
    // Permission: HacknCore.permission.see
    // Arguments: <item> - l'item dont on veut voir le grade requis
    // Exemple: /get-item-grade DIAMOND_SWORD
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if (args.length != 1) { return false; }
        if (!sender.hasPermission("HacknCore.permission.see")) { return false; }
        if (Material.getMaterial(args[0]) == null) { return false; }
        //sender.sendMessage("Pour utiliser " + args[0] + ", le grade requis est: " + MaterialPermission.getPermissionDisplayName(args[0]));
        sender.sendMessage(Logger.getMessage("hackncore.permission.commands.getitempermissioncommand.oncommand.message", args[0], MaterialPermission.getPermissionDisplayName(args[0])));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (strings.length == 1)
        {
            String input = strings[0].toUpperCase(Locale.ROOT);
            List<String> suggestions = new ArrayList<>();

            for (Material material : Material.values())
            {
                if (material.name().startsWith(input))
                {
                    suggestions.add(material.name().toUpperCase(Locale.ROOT));
                }
            }

            return suggestions;
        }
        return null;
    }
}
