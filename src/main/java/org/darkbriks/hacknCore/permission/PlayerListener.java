package org.darkbriks.hacknCore.permission;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.darkbriks.hacknCore.Logger;

import java.util.Objects;

public class PlayerListener implements Listener
{
    //private final HacknCore plugin;

    //public PlayerListener(HacknCore plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Material item = player.getInventory().getItemInMainHand().getType();

        if (MaterialPermission.hasPermission(player, item))
        {
            event.setCancelled(true);
            exchangeForbiddenItemWithValidItem(player, player.getInventory().getHeldItemSlot());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Material block = event.getBlock().getType();
        Material item = player.getInventory().getItemInMainHand().getType();

        if (MaterialPermission.hasPermission(player, item))
        {
            event.setCancelled(true);
            exchangeForbiddenItemWithValidItem(player, player.getInventory().getHeldItemSlot());

        }
        else if (MaterialPermission.hasPermission(player, block))
        {
            Logger.info(Logger.getMessage("hackncore.playerlistener.onblockbreak.cancel.player", block, MaterialPermission.getPermission(block).getName()), player, true);
            Logger.info(Logger.getMessage("hackncore.playerlistener.onblockbreak.cancel.server", player.getName(), block, MaterialPermission.getPermission(block).getName()), true);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player player)
        {
            Material item = player.getInventory().getItemInMainHand().getType();

            if (MaterialPermission.hasPermission(player, item))
            {
                event.setCancelled(true);
                exchangeForbiddenItemWithValidItem(player, player.getInventory().getHeldItemSlot());
            }
        }
    }

    @EventHandler
    public void onPlayerConsumeItem(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        Material item = event.getItem().getType();

        if (MaterialPermission.hasPermission(player, item))
        {
            event.setCancelled(true);
            exchangeForbiddenItemWithValidItem(player, player.getInventory().getHeldItemSlot());
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event)
    {
        if (event.getView().getPlayer() instanceof Player player)
        {
            try
            {
                Material item = Objects.requireNonNull(event.getRecipe()).getResult().getType();

                if (MaterialPermission.hasPermission(player, item))
                {
                    Logger.info(Logger.getMessage("hackncore.playerlistener.onprepareitemcraft.cancel.player", item, MaterialPermission.getPermission(item).getName()), player, true);
                    Logger.info(Logger.getMessage("hackncore.playerlistener.onprepareitemcraft.cancel.server", player.getName(), item, MaterialPermission.getPermission(item).getName()), true);
                    event.getInventory().setResult(null);
                }
            }
            catch (Exception ignored) {}
        }
    }

    // Ne devrait jamais annuler l'événement, mais est présent par précaution au cas où le joueur trouverait un moyen de contourner onPrepareItemCraft
    @EventHandler
    public void onCraftItem(CraftItemEvent event)
    {
        if (event.getView().getPlayer() instanceof Player player)
        {
            Material item = event.getRecipe().getResult().getType();

            if (MaterialPermission.hasPermission(player, item))
            {
                Logger.info(Logger.getMessage("hackncore.playerlistener.oncraftitem.cancel.player", item, MaterialPermission.getPermission(item).getName()), player, true);
                Logger.info(Logger.getMessage("hackncore.playerlistener.oncraftitem.cancel.server", player.getName(), item, MaterialPermission.getPermission(item).getName()), true);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event)
    {
        try
        {
            Player player = event.getPlayer();
            Material item = Objects.requireNonNull(player.getInventory().getItem(event.getNewSlot())).getType();

            if (MaterialPermission.hasPermission(player, item))
            {
                exchangeForbiddenItemWithValidItem(player, event.getNewSlot());
            }
        }
        catch (Exception ignored) {}
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getWhoClicked() instanceof Player player)
        {
            if (event.getAction() == InventoryAction.PLACE_ALL)
            {
                if (event.getSlotType() == InventoryType.SlotType.ARMOR)
                {
                    ItemStack cursor = event.getCursor();

                    if (!MaterialPermission.hasPermission(player, cursor))
                    {
                        Logger.info(Logger.getMessage("hackncore.playerlistener.oninventoryclick.armorequip.cancel.player", cursor.getType(), MaterialPermission.getPermission(cursor.getType()).getName()), player, true);
                        Logger.info(Logger.getMessage("hackncore.playerlistener.oninventoryclick.armorequip.cancel.server", player.getName(), cursor.getType(), MaterialPermission.getPermission(cursor.getType()).getName()), true);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private void exchangeForbiddenItemWithValidItem(Player player, int slot)
    {
        ItemStack itemInHand = player.getInventory().getItem(slot);
        if (itemInHand == null) { return; }

        for (int i = 0; i < player.getInventory().getSize(); i++)
        {
            // Skip the current slot and the main hand
            if (i == slot || i == player.getInventory().getHeldItemSlot()) { continue; }

            ItemStack potentialItem = player.getInventory().getItem(i);
            if (potentialItem != null)
            {
                if (MaterialPermission.hasPermission(player, potentialItem))
                {
                    swapItem(slot, i, player);
                    Logger.info(Logger.getMessage("hackncore.playerlistener.exchangeforbiddenitemwithvaliditem.exchangewithnonempty.player", itemInHand.getType(), potentialItem.getType()), player, true);
                    Logger.info(Logger.getMessage("hackncore.playerlistener.exchangeforbiddenitemwithvaliditem.exchangewithnonempty.server", player.getName(), itemInHand.getType(), potentialItem.getType()), true);
                    return;
                }
            }
            else
            {
                swapItem(slot, i, player);
                Logger.info(Logger.getMessage("hackncore.playerlistener.exchangeforbiddenitemwithvaliditem.exchangewithempty.player", itemInHand.getType()), player, true);
                Logger.info(Logger.getMessage("hackncore.playerlistener.exchangeforbiddenitemwithvaliditem.exchangewithempty.server", player.getName(), itemInHand.getType()), true);
                return;
            }
        }
        // If no valid item was found, drop the forbidden item
        player.getWorld().dropItem(player.getLocation(), itemInHand);
        player.getInventory().setItem(slot, null);
        Logger.info(Logger.getMessage("hackncore.playerlistener.exchangeforbiddenitemwithvaliditem.drop.player", itemInHand.getType()), player, true);
        Logger.info(Logger.getMessage("hackncore.playerlistener.exchangeforbiddenitemwithvaliditem.drop.server", player.getName(), itemInHand.getType()), true);
    }

    private void swapItem(int slot1, int slot2, Player player)
    {
        ItemStack item1 = player.getInventory().getItem(slot1);
        ItemStack item2 = player.getInventory().getItem(slot2);
        player.getInventory().setItem(slot1, item2);
        player.getInventory().setItem(slot2, item1);
    }
}
