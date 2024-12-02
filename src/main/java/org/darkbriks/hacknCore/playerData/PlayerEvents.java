package org.darkbriks.hacknCore.playerData;

import dev.aurelium.auraskills.api.event.skill.XpGainEvent;
import dev.aurelium.auraskills.api.skill.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.darkbriks.hacknCore.Logger;

import java.util.UUID;

public class PlayerEvents implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Logger.info("Player join event", true);

        PlayerData playerData = PlayerDataManager.getPlayerData(playerUUID);
        if (playerData == null)
        {
            Logger.error("Error loading player data for " + player.getName(), true);
        }
        else
        {
            Logger.info("Player data loaded for " + player.getName(), true);

            // Afficher les compétences débloquées
            Logger.info("Unlocked skills: " + playerData.unlockedSkills(), player, true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Logger.info("Player quit event", true);
        PlayerDataManager.unloadPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        Logger.info("Player kick event", true);
        PlayerDataManager.unloadPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerGainXP(XpGainEvent event)
    {
        Skills skill = (Skills) event.getSkill();
        if (skill != null && !PlayerDataManager.hasSkill(event.getPlayer().getUniqueId(), skill))
        {
            event.setCancelled(true);
            event.setAmount(0);
            Logger.info("Player " + event.getPlayer().getName() + " does not have skill " + skill.name(), true);
            Logger.info("You need to unlock this skill before gaining XP", event.getPlayer(), true);
        }
    }

    // Quand le joueur ouvre le menu des compétences
    /*@EventHandler
    public void onPlayerOpenSkillsMenu(PlayerSkillsMenuOpenEvent event)
    {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerDataManager.getPlayerData(player.getUniqueId());
        if (playerData == null)
        {
            Logger.error("Error loading player data for " + player.getName(), true);
        }
        else
        {
            Logger.info("Player data loaded for " + player.getName(), true);

            // Afficher les compétences débloquées
            Logger.info("Unlocked skills: " + playerData.unlockedSkills(), player, true);
        }
    }*/
}