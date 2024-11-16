package org.darkbriks.hacknCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.darkbriks.hacknCore.permission.MaterialPermission;
import org.darkbriks.hacknCore.permission.PlayerListener;
import org.darkbriks.hacknCore.permission.commands.GetItemPermissionCommand;
import org.darkbriks.hacknCore.sleep.Bed;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public final class HacknCore extends JavaPlugin
{
    public final String PLUGIN_NAME = "HacknCore";
    public final String PLUGIN_VERSION = "0.1.0";

    public final boolean DEBUG = getConfig().getBoolean("debug");
    public final boolean VERBOSE = getConfig().getBoolean("verbose");
    public final String MATERIALS_PERMISSIONS_FILE = getConfig().getString("materials-permissions-file");

    @Deprecated
    @Override
    public java.util.logging.@NotNull Logger getLogger() { return super.getLogger(); }

    @Override
    public void onEnable()
    {
        try
        {
            saveHacknCoreConfig();

            //logger = new Logger(this);
            Logger.init(this);
            Logger.info(Logger.getMessage("hackncore.hackncore.onenable.enable.start", PLUGIN_NAME, PLUGIN_VERSION, DEBUG, VERBOSE), true);

            initializePermissions();
            registerCrafts();
            registerCommands();
            registerEvents();

            Logger.info(Logger.getMessage("hackncore.hackncore.onenable.enable.success", PLUGIN_NAME), true);
        }
        catch (Exception e)
        {
            Logger.fatal(Logger.getMessage("hackncore.hackncore.onenable.enable.error", PLUGIN_NAME, e.getMessage()), true);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable()
    {
        Logger.info(Logger.getMessage("hackncore.hackncore.ondisable.disable.start", PLUGIN_NAME), true);
        Logger.info(Logger.getMessage("hackncore.hackncore.ondisable.disable.success", PLUGIN_NAME), true);
    }

    private void saveHacknCoreConfig()
    {
        saveDefaultConfig();
        saveResource("materials-permissions.yml", false);
        saveResource("languages/en_US.properties", false);
        saveResource("languages/fr_FR.properties", false);
    }

    private void initializePermissions()
    {
        try
        {
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.initializepermissions.start"), true);
            MaterialPermission.initialize(this);
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.initializepermissions.success"), true);
        }
        catch (Exception e)
        {
            Logger.fatal(Logger.getMessage("hackncore.hackncore.onenable.initializepermissions.error", e.getMessage()), true);
        }
    }

    private void registerCrafts()
    {
        try
        {
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.registeringcrafts.start"), true);
            CustomCrafts.registerCrafts(this);
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.registeringcrafts.success"), true);
        }
        catch (Exception e)
        {
            Logger.fatal(Logger.getMessage("hackncore.hackncore.onenable.registeringcrafts.error", e.getMessage()), true);
        }
    }

    private void registerCommands()
    {
        try
        {
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.registeringcommands.start"), true);
            Objects.requireNonNull(this.getCommand("get-item-permission")).setExecutor(new GetItemPermissionCommand());
            Objects.requireNonNull(this.getCommand("get-item-permission")).setTabCompleter(new GetItemPermissionCommand());
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.registeringcommands.success"), true);
        }
        catch (NullPointerException e)
        {
            Logger.fatal(Logger.getMessage("hackncore.hackncore.onenable.registeringcommands.error", e.getMessage()), true);
        }
    }

    private void registerEvents()
    {
        try
        {
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.registeringevents.start"), true);
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Bukkit.getPluginManager().registerEvents(new Bed(this), this);
            Logger.verbose(Logger.getMessage("hackncore.hackncore.onenable.registeringevents.success"), true);
        }
        catch (Exception e)
        {
            Logger.fatal(Logger.getMessage("hackncore.hackncore.onenable.registeringevents.error", e.getMessage()), true);
        }
    }
}
