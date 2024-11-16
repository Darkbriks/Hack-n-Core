package org.darkbriks.hacknCore;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

public class Logger
{
    static private Logger instance = null;

    static private HacknCore plugin;
    static private MiniMessage miniMessage;
    static private LegacyComponentSerializer legacyComponentSerializer;
    static private ResourceBundle messages;

    static private String LEGACY_PREFIX;
    static private String PREFIX;

    private Logger(HacknCore hacknCore)
    {
        plugin = hacknCore;
        miniMessage = MiniMessage.miniMessage();
        legacyComponentSerializer = LegacyComponentSerializer.legacySection();
        if (plugin.getConfig().getBoolean("logger.use-prefix"))
        {
            LEGACY_PREFIX = plugin.getConfig().getString("logger.legacy-prefix");
            PREFIX = plugin.getConfig().getString("logger.prefix");
        }
        else
        {
            LEGACY_PREFIX = "";
            PREFIX = "";
        }

        Locale locale = Locale.getDefault();
        messages = ResourceBundle.getBundle("languages/" + locale.getLanguage() + "_" + locale.getCountry());
    }

    @NotNull
    static public Logger getInstance(HacknCore plugin)
    {
        if (instance == null) { instance = new Logger(plugin); }
        return instance;
    }

    static public Logger getInstance()
    {
        return instance;
    }

    static public String getMessage(String key, Object... args)
    {
        try { return MessageFormat.format(messages.getString(key), args); }
        catch (Exception e) { return "Message not found for key: " + key; }
    }

    static public void info(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + message)); }
    }

    static public void info(String message, Player player, boolean legacy)
    {
        info(message, (Audience) player, legacy);
    }

    static public void info(String message, boolean legacy)
    {
        info(message, plugin.getServer().getConsoleSender(), legacy);
    }

    static public void warn(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + "§e" + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + "<yellow>" + message)); }
    }

    static public void warn(String message, Player player, boolean legacy)
    {
        warn(message, (Audience) player, legacy);
    }

    static public void warn(String message, boolean legacy)
    {
        warn(message, plugin.getServer().getConsoleSender(), legacy);
    }

    static public void error(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + "§c" + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + "<red>" + message)); }
    }

    static public void error(String message, Player player, boolean legacy)
    {
        error(message, (Audience) player, legacy);
    }

    static public void error(String message, boolean legacy)
    {
        error(message, plugin.getServer().getConsoleSender(), legacy);
    }


    static public void fatal(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + "§4§l" + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + "<dark_red><bold>" + message)); }
    }

    static public void fatal(String message, Player player, boolean legacy)
    {
        fatal(message, (Audience) player, legacy);
    }

    static public void fatal(String message, boolean legacy)
    {
        fatal(message, plugin.getServer().getConsoleSender(), legacy);
    }

    static public void success(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + "§a" + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + "<green>" + message)); }
    }

    static public void success(String message, Player player, boolean legacy)
    {
        success(message, (Audience) player, legacy);
    }

    static public void success(String message, boolean legacy)
    {
        success(message, plugin.getServer().getConsoleSender(), legacy);
    }

    static public void debug(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + "§9" + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + "<blue>" + message)); }
    }

    static public void debug(String message, Player player, boolean legacy)
    {
        debug(message, (Audience) player, legacy);
    }

    static public void debug(String message, boolean legacy)
    {
        debug(message, plugin.getServer().getConsoleSender(), legacy);
    }

    static public void verbose(String message, Audience audience, boolean legacy)
    {
        if (legacy) { audience.sendMessage(legacyComponentSerializer.deserialize(LEGACY_PREFIX + "§3" + message)); }
        else { audience.sendMessage(miniMessage.deserialize(PREFIX + "<dark_aqua>" + message)); }
    }

    static public void verbose(String message, Player player, boolean legacy)
    {
        verbose(message, (Audience) player, legacy);
    }

    static public void verbose(String message, boolean legacy)
    {
        verbose(message, plugin.getServer().getConsoleSender(), legacy);
    }

    static public void test()
    {
        info("Legacy Logger testing started", true);
        info("Info test", true);
        warn("Warn test", true);
        error("Error test", true);
        fatal("Fatal test", true);
        success("Success test", true);
        debug("Debug test", true);
        verbose("Verbose test", true);
        info("Legacy Logger testing ended", true);
        info("Modern Logger testing started", false);
        info("Info test", false);
        warn("Warn test", false);
        error("Error test", false);
        fatal("Fatal test", false);
        success("Success test", false);
        debug("Debug test", false);
        verbose("Verbose test", false);
        info("Modern Logger testing ended", false);
    }

    static public void test(Player player)
    {
        info("Legacy Logger testing started", player, true);
        info("Info test", player, true);
        warn("Warn test", player, true);
        error("Error test", player, true);
        fatal("Fatal test", player, true);
        success("Success test", player, true);
        debug("Debug test", player, true);
        verbose("Verbose test", player, true);
        info("Legacy Logger testing ended", player, true);
        info("Modern Logger testing started", player, false);
        info("Info test", player, false);
        warn("Warn test", player, false);
        error("Error test", player, false);
        fatal("Fatal test", player, false);
        success("Success test", player, false);
        debug("Debug test", player, false);
        verbose("Verbose test", player, false);
        info("Modern Logger testing ended", player, false);
    }
}
