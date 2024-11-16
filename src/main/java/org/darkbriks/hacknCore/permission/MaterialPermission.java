package org.darkbriks.hacknCore.permission;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.darkbriks.hacknCore.HacknCore;
import org.darkbriks.hacknCore.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialPermission
{
    private static boolean initialized = false;
    private static final String NONE_PERMISSION_DISPLAY_NAME = "None";
    private static final Map<String, Permission> itemPermissions = new HashMap<>();
    private static final Map<Permission, String> permissionDisplayNames = new HashMap<>();

    public static void initialize(HacknCore plugin)
    {
        if (initialized) { return; }
        loadMaterialsPermissions(plugin);
        initialized = true;
    }

    private static void loadMaterialsPermissions(HacknCore plugin)
    {
        assert plugin.MATERIALS_PERMISSIONS_FILE != null;
        File file = plugin.getDataFolder().toPath().resolve(plugin.MATERIALS_PERMISSIONS_FILE).toFile();
        if (!file.exists())
        {
            Logger.verbose(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.file.notfound", file.getPath()), true);
            return;
        }

        Yaml yaml = new Yaml();

        try (FileInputStream fis = new FileInputStream(file))
        {
            Map<String, Object> data = yaml.load(fis);
            if (data == null)
            {
                Logger.warn(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.file.empty", file.getPath()), true);
                return;
            }

            for (String key : data.keySet())
            {
                if (data.get(key) instanceof Map)
                {
                    Permission permission = new Permission(key);
                    if (data.get(key) instanceof Map value)
                    {
                        if (value.containsKey("items") && value.get("items") instanceof List items)
                        {
                            for (Object item : items)
                            {
                                if (item instanceof String material) { itemPermissions.put(material, permission); }
                                else { Logger.warn(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.items.notstring", key), true); }
                            }
                        }
                        else { Logger.warn(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.items.notfound", permission), true); }

                        if (value.containsKey("display-name") && value.get("display-name") instanceof String displayName)
                        {
                            permissionDisplayNames.put(permission, displayName);
                        }
                        else
                        {
                            Logger.warn(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.displayname.notfound", permission), true);
                            permissionDisplayNames.put(permission, permission.getName());
                        }
                    }
                    else { Logger.error(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.value.notmap", key), true); }
                }
            }
        }
        catch (Exception e)
        {
            Logger.fatal(Logger.getMessage("hackncore.permission.materialpermission.loadmaterialspermissions.error", e.getMessage()), true);
        }
    }

    public static Permission getPermission(String material)
    {
        return itemPermissions.getOrDefault(material, null);
    }

    public static Permission getPermission(Material material)
    {
        return getPermission(material.toString());
    }

    public static Permission getPermission(ItemStack item)
    {
        return getPermission(item.getType().toString());
    }

    public static String getPermissionDisplayName(Permission permission)
    {
        return permissionDisplayNames.getOrDefault(permission, null);
    }

    public static String getPermissionDisplayName(String material)
    {
        Permission permission = getPermission(material);
        return permission == null ? NONE_PERMISSION_DISPLAY_NAME : getPermissionDisplayName(permission);
    }

    public static String getPermissionDisplayName(Material material)
    {
        return getPermissionDisplayName(material.toString());
    }

    public static String getPermissionDisplayName(ItemStack item)
    {
        return getPermissionDisplayName(item.getType().toString());
    }

    public static boolean hasPermission(Player player, String material)
    {
        Permission permission = getPermission(material);
        return permission == null || player.hasPermission(permission);
    }

    public static boolean hasPermission(Player player, Material material)
    {
        return !hasPermission(player, material.toString());
    }

    public static boolean hasPermission(Player player, ItemStack item)
    {
        return hasPermission(player, item.getType().toString());
    }

    public static List<String> getMaterials()
    {
        return List.copyOf(itemPermissions.keySet());
    }
}
