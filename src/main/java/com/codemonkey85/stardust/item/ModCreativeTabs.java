package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StardustMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> STARDUST_TAB = TABS.register("stardust_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + StardustMod.MOD_ID + ".stardust_tab"))
                    .icon(() -> new ItemStack(ModItems.STARDUST_INGOT.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.STARDUST_INGOT.get());
                        output.accept(ModItems.STARDUST_HELMET.get());
                        output.accept(ModItems.STARDUST_CHESTPLATE.get());
                        output.accept(ModItems.STARDUST_LEGGINGS.get());
                        output.accept(ModItems.STARDUST_BOOTS.get());
                        output.accept(ModItems.WOODEN_BATTLE_AXE.get());
                        output.accept(ModItems.STONE_BATTLE_AXE.get());
                        output.accept(ModItems.IRON_BATTLE_AXE.get());
                        output.accept(ModItems.GOLDEN_BATTLE_AXE.get());
                        output.accept(ModItems.DIAMOND_BATTLE_AXE.get());
                        output.accept(ModItems.NETHERITE_BATTLE_AXE.get());
                        output.accept(ModItems.STARDUST_BATTLE_AXE.get());
                        output.accept(ModItems.STONE_BOW.get());
                        output.accept(ModItems.IRON_BOW.get());
                        output.accept(ModItems.GOLDEN_BOW.get());
                        output.accept(ModItems.DIAMOND_BOW.get());
                        output.accept(ModItems.NETHERITE_BOW.get());
                        output.accept(ModItems.STARDUST_BOW.get());
                        output.accept(ModItems.STARDUST_SWORD.get());
                        output.accept(ModItems.STARDUST_PICKAXE.get());
                        output.accept(ModItems.STARDUST_AXE.get());
                        output.accept(ModItems.STARDUST_SHOVEL.get());
                        output.accept(ModItems.STARDUST_HOE.get());
                    })
                    .build());

    private ModCreativeTabs() {}
}
