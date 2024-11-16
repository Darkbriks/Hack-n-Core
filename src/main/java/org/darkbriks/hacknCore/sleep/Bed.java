package org.darkbriks.hacknCore.sleep;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.darkbriks.hacknCore.HacknCore;
import org.darkbriks.hacknCore.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bed implements Listener
{
    private final HacknCore plugin;
    private final int HEARTS_PARTICLE_PER_SECOND = 2;

    private final Map<Player, Integer> healingPlayers = new HashMap<>();

    public Bed(HacknCore plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event)
    {
        Player player = event.getPlayer();
        if (!player.hasPermission("paperPlugin.sleep")) { return; }

        if (event.getBedEnterResult().toString().equals("OK") && !healingPlayers.containsKey(player))
        {
            healingPlayers.put(player, getPlayerHealingAmount(player));
            startHealingTask(player);
            Logger.debug("Player " + player.getName() + " is in bed", true);
        }

        // Si on est en journée, le joueur peut s'asseoir sur le lit sans passer la nuit
        if (event.getBedEnterResult().toString().equals("NOT_POSSIBLE_NOW") && !healingPlayers.containsKey(player))
        {
            // Création d'un ArmorStand invisible pour simuler le fait que le joueur s'assoit sur le lit
            player.getWorld().spawn(event.getBed().getLocation().add(0.5, 0.6, 0.5), ArmorStand.class, armorStand ->
            {
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setSmall(true);
                armorStand.setMarker(true);
                armorStand.setInvulnerable(true);
                armorStand.setCustomNameVisible(false);

                @NotNull MetadataValue metadataValue = new FixedMetadataValue(plugin, true);
                armorStand.setMetadata("bed", metadataValue);

                armorStand.addPassenger(player);
                healingPlayers.put(player, getPlayerHealingAmount(player));
                startHealingTask(player);
                Logger.debug("Player " + player.getName() + " is sitting on bed", true);
            });
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event)
    {
        Player player = event.getPlayer();
        if (healingPlayers.containsKey(player))
        {
            healingPlayers.remove(player);
            Logger.debug("Player " + player.getName() + " left bed", true);
        }
    }

    @EventHandler
    public void onPlayerDismount(PlayerToggleSneakEvent event)
    {
        Player player = event.getPlayer();
        if (player.getVehicle() instanceof ArmorStand armorStand)
        {
            if (armorStand.hasMetadata("bed"))
            {
                armorStand.removePassenger(player);
                healingPlayers.remove(player);
                armorStand.remove();
                Logger.debug("Player " + player.getName() + " left bed (ArmorStand)", true);
            }
        }
    }

    private int getPlayerHealingAmount(Player player)
    {
        for (int i = 5; i >= 0; i--)
        {
            if (player.hasPermission("HacknCore.sleep." + i)) { return i; }
        }
        return 0;
    }

    private void startHealingTask(Player player)
    {
        new BukkitRunnable()
        {
            private float lifeAccumulator = 0;
            private float heartAccumulator = 0;
            private final int healingRate = healingPlayers.get(player);

            @Override
            public void run()
            {
                if (!healingPlayers.containsKey(player)) { cancel(); return; }

                if (player.getHealth() < Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue())
                {
                    lifeAccumulator += healingRate / 20.0f;
                    if (lifeAccumulator >= 1)
                    {
                        player.setHealth(Math.min(player.getHealth() + 1, Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()));
                        lifeAccumulator -= 1;
                    }
                }

                heartAccumulator += HEARTS_PARTICLE_PER_SECOND / 20.0f;
                if (heartAccumulator >= 1)
                {
                    player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 0.5, 0), 1);
                    heartAccumulator -= 1;
                }

                updateActionBar(player, player.getHealth(), Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue(), healingRate);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void updateActionBar(Player player, double currentHealth, double maxHealth, int healingRate)
    {
        player.sendActionBar("§c❤ " + (int) currentHealth + "§r / §c❤ " + (int) maxHealth + "§r - §a+" + healingRate + "❤/s");
    }
}