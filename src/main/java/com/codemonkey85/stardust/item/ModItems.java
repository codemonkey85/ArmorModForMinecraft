package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, StardustMod.MOD_ID);

    public static final RegistryObject<Item> STARDUST_INGOT =
            ITEMS.register("stardust_ingot", () -> new Item(new Item.Properties()));

    private ModItems() {}
}
