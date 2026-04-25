package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, StardustMod.MOD_ID);

    public static final RegistryObject<Item> STARDUST_INGOT =
            ITEMS.register("stardust_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STARDUST_HELMET =
            ITEMS.register("stardust_helmet",
                    () -> new ArmorItem(ModArmorMaterials.STARDUST, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> STARDUST_CHESTPLATE =
            ITEMS.register("stardust_chestplate",
                    () -> new ArmorItem(ModArmorMaterials.STARDUST, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> STARDUST_LEGGINGS =
            ITEMS.register("stardust_leggings",
                    () -> new ArmorItem(ModArmorMaterials.STARDUST, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> STARDUST_BOOTS =
            ITEMS.register("stardust_boots",
                    () -> new ArmorItem(ModArmorMaterials.STARDUST, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_BATTLE_AXE =
            ITEMS.register("wooden_battle_axe",
                    () -> new BattleAxeItem(Tiers.WOOD, 6.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_BATTLE_AXE =
            ITEMS.register("stone_battle_axe",
                    () -> new BattleAxeItem(Tiers.STONE, 7.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> IRON_BATTLE_AXE =
            ITEMS.register("iron_battle_axe",
                    () -> new BattleAxeItem(Tiers.IRON, 6.0F, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BATTLE_AXE =
            ITEMS.register("golden_battle_axe",
                    () -> new BattleAxeItem(Tiers.GOLD, 6.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE =
            ITEMS.register("diamond_battle_axe",
                    () -> new BattleAxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE =
            ITEMS.register("netherite_battle_axe",
                    () -> new BattleAxeItem(Tiers.NETHERITE, 5.0F, -3.0F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> STARDUST_BATTLE_AXE =
            ITEMS.register("stardust_battle_axe",
                    () -> new BattleAxeItem(ModTiers.STARDUST, 5.0F, -3.0F, new Item.Properties().fireResistant()));

    private ModItems() {}
}
