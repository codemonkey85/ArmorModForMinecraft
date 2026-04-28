package com.codemonkey85.stardust.event;

import com.codemonkey85.stardust.StardustMod;
import com.codemonkey85.stardust.item.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StardustMod.MOD_ID)
public final class PyropeSetBonusEvents {
    private static final int EFFECT_REFRESH_TICKS = 40;

    private PyropeSetBonusEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.side.isClient()) return;

        Player player = event.player;
        if (!isWearingFullPyropeSet(player)) return;

        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                EFFECT_REFRESH_TICKS, 0, false, false, false));
    }

    private static boolean isWearingFullPyropeSet(Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (!(stack.getItem() instanceof ArmorItem armor)) return false;
            if (armor.getMaterial() != ModArmorMaterials.PYROPE) return false;
        }
        return true;
    }
}
