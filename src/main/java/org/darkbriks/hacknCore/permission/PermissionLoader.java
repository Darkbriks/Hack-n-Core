package org.darkbriks.hacknCore.permission;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.darkbriks.hacknCore.HacknCore;
import org.darkbriks.hacknCore.Logger;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class PermissionLoader
{
    public static void loadPermissions(HacknCore plugin)
    {
        assert plugin.PERMISSIONS_FILE != null;

        File file = plugin.getDataFolder().toPath().resolve(plugin.PERMISSIONS_FILE).toFile();
        if (!file.exists())
        {
            Logger.verbose(Logger.getMessage("hackncore.permission.permissionloader.loadpermissions.file.notfound", file.getPath()), true);
            return;
        }

        Yaml yaml = new Yaml();

        try (FileInputStream fis = new FileInputStream(file))
        {
            Map<String, Object> data = yaml.load(fis);
            if (data == null)
            {
                Logger.warn(Logger.getMessage("hackncore.permission.permissionloader.loadpermissions.file.empty", file.getPath()), true);
                return;
            }

            for (String key : data.keySet())
            {
                if (data.get(key) instanceof Map)
                {
                    String description = "No description provided.";
                    String defaultValue = "false";

                    if (data.get(key) instanceof Map value)
                    {
                        if (value.containsKey("description"))
                        {
                            description = (String) value.get("description");
                        }
                        if (value.containsKey("default"))
                        {
                            defaultValue = (String) value.get("default");
                        }
                    }

                    Bukkit.getPluginManager().addPermission(getPermission(defaultValue, key, description));
                }
            }
        }
        catch (Exception e)
        {
            Logger.error(Logger.getMessage("hackncore.permission.permissionloader.loadpermissions.error", e.getMessage()), true);
        }
    }

    private static @NotNull Permission getPermission(String defaultValue, String name, String description)
    {
        org.bukkit.permissions.PermissionDefault permDefault = switch (defaultValue.toLowerCase())
        {
            case "true" -> org.bukkit.permissions.PermissionDefault.TRUE;
            case "op" -> org.bukkit.permissions.PermissionDefault.OP;
            case "not_op" -> org.bukkit.permissions.PermissionDefault.NOT_OP;
            default -> org.bukkit.permissions.PermissionDefault.FALSE;
        };

        return new Permission(name, description, permDefault);
    }
}