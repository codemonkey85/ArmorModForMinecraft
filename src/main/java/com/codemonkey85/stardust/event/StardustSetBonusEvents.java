package com.codemonkey85.stardust.event;

import com.codemonkey85.stardust.StardustMod;
import com.codemonkey85.stardust.item.ModArmorMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StardustMod.MOD_ID)
public final class StardustSetBonusEvents {
    // Tracks flight we granted, so we only revoke our own grant and don't fight
    // other mods or creative-mode flight.
    private static final String FLY_GRANT_KEY = "StardustSetBonusFly";
    private static final int EFFECT_REFRESH_TICKS = 40;

    private StardustSetBonusEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.side.isClient()) return;

        Player player = event.player;
        boolean fullSet = isWearingFullStardustSet(player);

        if (fullSet) {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,
                    EFFECT_REFRESH_TICKS, 0, false, false, false));
        }

        updateFlight(player, fullSet);
    }

    private static boolean isWearingFullStardustSet(Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (!(stack.getItem() instanceof ArmorItem armor)) return false;
            if (armor.getMaterial() != ModArmorMaterials.STARDUST) return false;
        }
        return true;
    }

    private static void updateFlight(Player player, boolean fullSet) {
        if (player.getAbilities().instabuild || player.isSpectator()) return;

        CompoundTag data = player.getPersistentData();
        boolean weGranted = data.getBoolean(FLY_GRANT_KEY);

        if (fullSet) {
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                data.putBoolean(FLY_GRANT_KEY, true);
                player.onUpdateAbilities();
            }
        } else if (weGranted) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            data.putBoolean(FLY_GRANT_KEY, false);
            player.onUpdateAbilities();
        }
    }
}
