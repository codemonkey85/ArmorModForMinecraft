package com.codemonkey85.stardust.block;

import com.codemonkey85.stardust.StardustMod;
import com.codemonkey85.stardust.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, StardustMod.MOD_ID);

    public static final RegistryObject<Block> STARDUST_BLOCK =
            BLOCKS.register("stardust_block", () -> new StardustBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Item> STARDUST_BLOCK_ITEM =
            ModItems.ITEMS.register("stardust_block",
                    () -> new BlockItem(STARDUST_BLOCK.get(), new Item.Properties().fireResistant()));

    private ModBlocks() {}
}
