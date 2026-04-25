package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
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

    public static final RegistryObject<Item> STONE_BOW =
            ITEMS.register("stone_bow",
                    () -> new TieredBowItem(new Item.Properties().durability(500), 19, 0.5f, 5,
                            () -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.COBBLESTONE)));
    public static final RegistryObject<Item> IRON_BOW =
            ITEMS.register("iron_bow",
                    () -> new TieredBowItem(new Item.Properties().durability(700), 18, 1.0f, 14,
                            () -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT)));
    public static final RegistryObject<Item> GOLDEN_BOW =
            ITEMS.register("golden_bow",
                    () -> new TieredBowItem(new Item.Properties().durability(250), 17, 0.5f, 22,
                            () -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT)));
    public static final RegistryObject<Item> DIAMOND_BOW =
            ITEMS.register("diamond_bow",
                    () -> new TieredBowItem(new Item.Properties().durability(1100), 16, 1.5f, 10,
                            () -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.DIAMOND)));
    public static final RegistryObject<Item> NETHERITE_BOW =
            ITEMS.register("netherite_bow",
                    () -> new TieredBowItem(new Item.Properties().durability(1500).fireResistant(), 15, 2.0f, 15,
                            () -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.NETHERITE_INGOT)));
    public static final RegistryObject<Item> STARDUST_BOW =
            ITEMS.register("stardust_bow",
                    () -> new TieredBowItem(new Item.Properties().durability(2000).fireResistant(), 14, 2.5f, 18,
                            () -> net.minecraft.world.item.crafting.Ingredient.of(STARDUST_INGOT.get())));

    public static final RegistryObject<Item> STARDUST_SWORD =
            ITEMS.register("stardust_sword",
                    () -> new SwordItem(ModTiers.STARDUST, 3, -2.4F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> STARDUST_PICKAXE =
            ITEMS.register("stardust_pickaxe",
                    () -> new PickaxeItem(ModTiers.STARDUST, 1, -2.8F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> STARDUST_AXE =
            ITEMS.register("stardust_axe",
                    () -> new AxeItem(ModTiers.STARDUST, 5.0F, -3.0F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> STARDUST_SHOVEL =
            ITEMS.register("stardust_shovel",
                    () -> new ShovelItem(ModTiers.STARDUST, 1.5F, -3.0F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> STARDUST_HOE =
            ITEMS.register("stardust_hoe",
                    () -> new HoeItem(ModTiers.STARDUST, 0, 0.0F, new Item.Properties().fireResistant()));

    private ModItems() {}
}
