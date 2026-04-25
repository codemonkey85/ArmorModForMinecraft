package com.codemonkey85.stardust.event;

import com.codemonkey85.stardust.StardustMod;
import com.codemonkey85.stardust.item.TieredBowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StardustMod.MOD_ID)
public final class TieredBowEvents {
    private static final int VANILLA_FULL_DRAW_TICKS = 20;

    private TieredBowEvents() {}

    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        ItemStack bow = event.getBow();
        if (bow.getItem() instanceof TieredBowItem tieredBow) {
            float scale = (float) VANILLA_FULL_DRAW_TICKS / (float) tieredBow.getChargeTicks();
            event.setCharge((int) Math.ceil(event.getCharge() * scale));
        }
    }
}
