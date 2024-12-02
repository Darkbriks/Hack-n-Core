package org.darkbriks.hacknCore.skills;

import dev.aurelium.auraskills.api.event.skill.XpGainEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.darkbriks.hacknCore.Logger;

public class PlayerSkillEvents implements Listener
{
    @EventHandler
    public void onPlayerGainXP(XpGainEvent event)
    {
        if (!event.getPlayer().hasPermission("HacknCore.skill." + event.getSkill().name()))
        {
            event.setCancelled(true);
            event.setAmount(0);
            Logger.info("Player " + event.getPlayer().getName() + " does not have permission to gain XP for skill " + event.getSkill().name(), true);
            Logger.info("You need to unlock this skill before gaining XP", event.getPlayer(), true);
        }
    }
}