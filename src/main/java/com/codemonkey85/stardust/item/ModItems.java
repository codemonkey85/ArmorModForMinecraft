package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
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

    private ModItems() {}
}
