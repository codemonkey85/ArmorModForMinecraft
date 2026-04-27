package com.codemonkey85.stardust.block;

import com.codemonkey85.stardust.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class StardustBlock extends Block {
    public StardustBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        ItemStack tool = player.getMainHandItem();
        return tool.is(ModItems.STARDUST_PICKAXE.get());
    }
}
