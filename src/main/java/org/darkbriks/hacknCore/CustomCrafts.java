package org.darkbriks.hacknCore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomCrafts
{
    public static void registerCrafts(HacknCore plugin)
    {
        // 9 rotten flesh -> 1 leather
        Logger.verbose("Registering recipe for ROTTON_FLESH to LEATHER", true);
        ItemStack leather = new ItemStack(Material.LEATHER, 1);
        NamespacedKey leatherKey = new NamespacedKey(plugin, "rotten_flesh_to_leather");
        ShapedRecipe leatherRecipe = new ShapedRecipe(leatherKey, leather);
        leatherRecipe.shape("ZZZ", "ZZZ", "ZZZ");
        leatherRecipe.setIngredient('Z', Material.ROTTEN_FLESH);
        Bukkit.addRecipe(leatherRecipe);

        // 1 withe wool -> 3 string
        Logger.verbose("Registering recipe for WHITE_WOOL to STRING", true);
        ItemStack string = new ItemStack(Material.STRING, 3);
        NamespacedKey stringKey = new NamespacedKey(plugin, "wool_to_string");
        ShapedRecipe stringRecipe = new ShapedRecipe(stringKey, string);
        stringRecipe.shape("Z  ", "   ", "   ");
        stringRecipe.setIngredient('Z', Material.WHITE_WOOL);
        Bukkit.addRecipe(stringRecipe);

        // 1 colored wool -> 1 dye (color of dye is the same as wool)
        for (Material wool : Material.values())
        {
            if (wool.name().endsWith("_WOOL") && !wool.name().equals("WHITE_WOOL") && !wool.name().equals("LEGACY_WOOL"))
            {
                Material dye = Material.valueOf(wool.name().replace("WOOL", "DYE"));
                Logger.verbose("Registering recipe for " + wool.name() + " to " + dye.name(), true);
                ItemStack dyeStack = new ItemStack(dye, 1);
                NamespacedKey dyeKey = new NamespacedKey(plugin, wool.name().toLowerCase() + "_to_dye");
                ShapedRecipe dyeRecipe = new ShapedRecipe(dyeKey, dyeStack);
                dyeRecipe.shape("Z  ", "   ", "   ");
                dyeRecipe.setIngredient('Z', wool);
                Bukkit.addRecipe(dyeRecipe);
            }
        }

        // 1 cobbelsone + 8 wheat seeds -> 1 mossy cobblestone
        Logger.verbose("Registering recipe for COBBLESTONE to MOSSY_COBBLESTONE", true);
        ItemStack mossyCobblestone = new ItemStack(Material.MOSSY_COBBLESTONE, 1);
        NamespacedKey mossyCobblestoneKey = new NamespacedKey(plugin, "cobblestone_to_mossy_cobblestone");
        ShapedRecipe mossyCobblestoneRecipe = new ShapedRecipe(mossyCobblestoneKey, mossyCobblestone);
        mossyCobblestoneRecipe.shape("ZZZ", "ZAZ", "ZZZ");
        mossyCobblestoneRecipe.setIngredient('Z', Material.WHEAT_SEEDS);
        mossyCobblestoneRecipe.setIngredient('A', Material.COBBLESTONE);
        Bukkit.addRecipe(mossyCobblestoneRecipe);

        // 1 stonebrick + 8 wheat seeds -> 1 mossy stonebrick
        Logger.verbose("Registering recipe for STONE_BRICKS to MOSSY_STONE_BRICKS", true);
        ItemStack mossyStonebrick = new ItemStack(Material.MOSSY_STONE_BRICKS, 1);
        NamespacedKey mossyStonebrickKey = new NamespacedKey(plugin, "stonebrick_to_mossy_stonebrick");
        ShapedRecipe mossyStonebrickRecipe = new ShapedRecipe(mossyStonebrickKey, mossyStonebrick);
        mossyStonebrickRecipe.shape("ZZZ", "ZAZ", "ZZZ");
        mossyStonebrickRecipe.setIngredient('Z', Material.WHEAT_SEEDS);
        mossyStonebrickRecipe.setIngredient('A', Material.STONE_BRICKS);
        Bukkit.addRecipe(mossyStonebrickRecipe);

        // 1 dirt + 8 short grass -> 1 grass block
        Logger.verbose("Registering recipe for DIRT to GRASS_BLOCK with SHORT_GRASS", true);
        ItemStack grassBlock1 = new ItemStack(Material.GRASS_BLOCK, 1);
        NamespacedKey grassBlock1Key = new NamespacedKey(plugin, "dirt_to_grass_block_with_short_grass");
        ShapedRecipe grassBlock1Recipe = new ShapedRecipe(grassBlock1Key, grassBlock1);
        grassBlock1Recipe.shape("ZZZ", "ZAZ", "ZZZ");
        grassBlock1Recipe.setIngredient('Z', Material.SHORT_GRASS);
        grassBlock1Recipe.setIngredient('A', Material.DIRT);
        Bukkit.addRecipe(grassBlock1Recipe);

        // 1 dirt + 8 tall grass -> 1 grass block
        Logger.verbose("Registering recipe for DIRT to GRASS_BLOCK with TALL_GRASS", true);
        ItemStack grassBlock2 = new ItemStack(Material.GRASS_BLOCK, 1);
        NamespacedKey grassBlock2Key = new NamespacedKey(plugin, "dirt_to_grass_block_with_tall_grass");
        ShapedRecipe grassBlock2Recipe = new ShapedRecipe(grassBlock2Key, grassBlock2);
        grassBlock2Recipe.shape("ZZZ", "ZAZ", "ZZZ");
        grassBlock2Recipe.setIngredient('Z', Material.TALL_GRASS);
        grassBlock2Recipe.setIngredient('A', Material.DIRT);
        Bukkit.addRecipe(grassBlock2Recipe);

        // 1 dirt + 8 fern -> 1 grass block
        Logger.verbose("Registering recipe for DIRT to GRASS_BLOCK with FERN", true);
        ItemStack grassBlock3 = new ItemStack(Material.GRASS_BLOCK, 1);
        NamespacedKey grassBlock3Key = new NamespacedKey(plugin, "dirt_to_grass_block_with_fern");
        ShapedRecipe grassBlock3Recipe = new ShapedRecipe(grassBlock3Key, grassBlock3);
        grassBlock3Recipe.shape("ZZZ", "ZAZ", "ZZZ");
        grassBlock3Recipe.setIngredient('Z', Material.FERN);
        grassBlock3Recipe.setIngredient('A', Material.DIRT);
        Bukkit.addRecipe(grassBlock3Recipe);

        // 1 dirt + 8 large fern -> 1 grass block
        Logger.verbose("Registering recipe for DIRT to GRASS_BLOCK with LARGE_FERN", true);
        ItemStack grassBlock4 = new ItemStack(Material.GRASS_BLOCK, 1);
        NamespacedKey grassBlock4Key = new NamespacedKey(plugin, "dirt_to_grass_block_with_large_fern");
        ShapedRecipe grassBlock4Recipe = new ShapedRecipe(grassBlock4Key, grassBlock4);
        grassBlock4Recipe.shape("ZZZ", "ZAZ", "ZZZ");
        grassBlock4Recipe.setIngredient('Z', Material.LARGE_FERN);
        grassBlock4Recipe.setIngredient('A', Material.DIRT);
        Bukkit.addRecipe(grassBlock4Recipe);

        // 9 bloc de feuille d'un type -> 1 pousse de ce type
        for (Material leaf : Material.values())
        {
            // TODO: add azalea leaves
            if (leaf.name().endsWith("_LEAVES") && !leaf.name().contains("AZALEA"))
            {
                String leafName = leaf.name().replace("LEAVES", "SAPLING");
                if (leafName.equals("MANGROVE_SAPLING")) { leafName = "MANGROVE_PROPAGULE"; }
                Logger.verbose("Registering recipe for " + leaf.name() + " to " + leafName, true);
                Material sapling = Material.valueOf(leafName);
                ItemStack saplingStack = new ItemStack(sapling, 1);
                NamespacedKey saplingKey = new NamespacedKey(plugin, leaf.name().toLowerCase() + "_to_sapling");
                ShapedRecipe saplingRecipe = new ShapedRecipe(saplingKey, saplingStack);
                saplingRecipe.shape("ZZZ", "ZZZ", "ZZZ");
                saplingRecipe.setIngredient('Z', leaf);
                Bukkit.addRecipe(saplingRecipe);
            }
        }

        // 9 string -> 1 cobweb
        Logger.verbose("Registering recipe for STRING to COBWEB", true);
        ItemStack cobweb = new ItemStack(Material.COBWEB, 1);
        NamespacedKey cobwebKey = new NamespacedKey(plugin, "string_to_cobweb");
        ShapedRecipe cobwebRecipe = new ShapedRecipe(cobwebKey, cobweb);
        cobwebRecipe.shape("ZZZ", "ZZZ", "ZZZ");
        cobwebRecipe.setIngredient('Z', Material.STRING);
        Bukkit.addRecipe(cobwebRecipe);

        // 4 vines + 1 sea pickle -> 4 glow lichen
        Logger.verbose("Registering recipe for VINES and SEA_PICKLE to GLOW_LICHEN", true);
        ItemStack glowLichen = new ItemStack(Material.GLOW_LICHEN, 4);
        NamespacedKey glowLichenKey = new NamespacedKey(plugin, "vines_and_sea_pickle_to_glow_lichen");
        ShapedRecipe glowLichenRecipe = new ShapedRecipe(glowLichenKey, glowLichen);
        glowLichenRecipe.shape("Z Z", " A ", "Z Z");
        glowLichenRecipe.setIngredient('Z', Material.VINE);
        glowLichenRecipe.setIngredient('A', Material.SEA_PICKLE);
        Bukkit.addRecipe(glowLichenRecipe);

        // 1 iron ingot -> 9 iron nuggets
        Logger.verbose("Registering recipe for IRON_INGOT to IRON_NUGGETS", true);
        ItemStack ironNuggets = new ItemStack(Material.IRON_NUGGET, 9);
        NamespacedKey ironNuggetsKey = new NamespacedKey(plugin, "iron_ingot_to_iron_nuggets");
        ShapedRecipe ironNuggetsRecipe = new ShapedRecipe(ironNuggetsKey, ironNuggets);
        ironNuggetsRecipe.shape("Z  ", "   ", "   ");
        ironNuggetsRecipe.setIngredient('Z', Material.IRON_INGOT);
        Bukkit.addRecipe(ironNuggetsRecipe);

        // 5 iron nuggets -> 1 chainmail helmet
        Logger.verbose("Registering recipe for IRON_NUGGETS to CHAINMAIL_HELMET", true);
        ItemStack chainmailHelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        NamespacedKey chainmailHelmetKey = new NamespacedKey(plugin, "iron_nuggets_to_chainmail_helmet");
        ShapedRecipe chainmailHelmetRecipe = new ShapedRecipe(chainmailHelmetKey, chainmailHelmet);
        chainmailHelmetRecipe.shape("ZZZ", "Z Z", "   ");
        chainmailHelmetRecipe.setIngredient('Z', Material.IRON_NUGGET);
        Bukkit.addRecipe(chainmailHelmetRecipe);
        
        // 4 iron nuggets -> 1 chainmail boots
        Logger.verbose("Registering recipe for IRON_NUGGETS to CHAINMAIL_BOOTS", true);
        ItemStack chainmailBoots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        NamespacedKey chainmailBootsKey = new NamespacedKey(plugin, "iron_nuggets_to_chainmail_boots");
        ShapedRecipe chainmailBootsRecipe = new ShapedRecipe(chainmailBootsKey, chainmailBoots);
        chainmailBootsRecipe.shape("   ", "Z Z", "Z Z");
        chainmailBootsRecipe.setIngredient('Z', Material.IRON_NUGGET);
        Bukkit.addRecipe(chainmailBootsRecipe);
        
        // 7 iron nuggets -> 1 chainmail leggings
        Logger.verbose("Registering recipe for IRON_NUGGETS to CHAINMAIL_LEGGINGS", true);
        ItemStack chainmailLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        NamespacedKey chainmailLeggingsKey = new NamespacedKey(plugin, "iron_nuggets_to_chainmail_leggings");
        ShapedRecipe chainmailLeggingsRecipe = new ShapedRecipe(chainmailLeggingsKey, chainmailLeggings);
        chainmailLeggingsRecipe.shape("ZZZ", "Z Z", "Z Z");
        chainmailLeggingsRecipe.setIngredient('Z', Material.IRON_NUGGET);
        Bukkit.addRecipe(chainmailLeggingsRecipe);
        
        // 8 iron nuggets -> 1 chainmail chestplate
        Logger.verbose("Registering recipe for IRON_NUGGETS to CHAINMAIL_CHESTPLATE", true);
        ItemStack chainmailChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        NamespacedKey chainmailChestplateKey = new NamespacedKey(plugin, "iron_nuggets_to_chainmail_chestplate");
        ShapedRecipe chainmailChestplateRecipe = new ShapedRecipe(chainmailChestplateKey, chainmailChestplate);
        chainmailChestplateRecipe.shape("Z Z", "ZZZ", "ZZZ");
        chainmailChestplateRecipe.setIngredient('Z', Material.IRON_NUGGET);
        Bukkit.addRecipe(chainmailChestplateRecipe);
    }
}
