package com.codemonkey85.stardust.block;

import com.codemonkey85.stardust.StardustMod;
import com.codemonkey85.stardust.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
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

    public static final RegistryObject<Block> PYROPE_ORE =
            BLOCKS.register("pyrope_ore", () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .strength(3.0F, 3.0F)
                            .sound(SoundType.STONE)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(2, 5)));

    public static final RegistryObject<Block> DEEPSLATE_PYROPE_ORE =
            BLOCKS.register("deepslate_pyrope_ore", () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DEEPSLATE)
                            .strength(4.5F, 3.0F)
                            .sound(SoundType.DEEPSLATE)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(2, 5)));

    public static final RegistryObject<Block> PYROPE_BLOCK =
            BLOCKS.register("pyrope_block", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_RED)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Item> PYROPE_ORE_ITEM =
            ModItems.ITEMS.register("pyrope_ore",
                    () -> new BlockItem(PYROPE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_PYROPE_ORE_ITEM =
            ModItems.ITEMS.register("deepslate_pyrope_ore",
                    () -> new BlockItem(DEEPSLATE_PYROPE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PYROPE_BLOCK_ITEM =
            ModItems.ITEMS.register("pyrope_block",
                    () -> new BlockItem(PYROPE_BLOCK.get(), new Item.Properties()));

    private ModBlocks() {}
}
