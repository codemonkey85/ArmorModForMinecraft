package com.codemonkey85.stardust.event;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StardustMod.MOD_ID)
public final class MultishotBowEvents {
    private static final float[] EXTRA_ANGLES = {-10.0f, 10.0f};

    private MultishotBowEvents() {}

    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        ItemStack bow = event.getBow();
        if (!(bow.getItem() instanceof BowItem bowItem)) return;

        int multishot = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, bow);
        if (multishot <= 0) return;

        Level level = event.getLevel();
        if (level.isClientSide) return;

        Player player = event.getEntity();
        float power = BowItem.getPowerForTime(event.getCharge());
        if (power < 0.1f) return;

        boolean infinity = player.getAbilities().instabuild
                || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0;
        ItemStack arrowStack = player.getProjectile(bow);
        if (arrowStack.isEmpty()) {
            if (!infinity) return;
            arrowStack = new ItemStack(Items.ARROW);
        }

        ArrowItem arrowItem = arrowStack.getItem() instanceof ArrowItem ai ? ai : (ArrowItem) Items.ARROW;
        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow);
        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow);
        boolean flame = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow) > 0;

        Vec3 look = player.getLookAngle();
        for (float angle : EXTRA_ANGLES) {
            AbstractArrow arrow = arrowItem.createArrow(level, arrowStack, player);
            arrow = bowItem.customArrow(arrow);

            if (powerLevel > 0) arrow.setBaseDamage(arrow.getBaseDamage() + (double) powerLevel * 0.5D + 0.5D);
            if (punchLevel > 0) arrow.setKnockback(punchLevel);
            if (flame) arrow.setSecondsOnFire(100);

            double rad = Math.toRadians(angle);
            double cos = Math.cos(rad);
            double sin = Math.sin(rad);
            double dx = look.x * cos - look.z * sin;
            double dz = look.x * sin + look.z * cos;
            arrow.setPos(player.getX(), player.getEyeY() - 0.10000000149011612D, player.getZ());
            arrow.shoot(dx, look.y, dz, power * 3.0F, 1.0F);

            if (power == 1.0F) arrow.setCritArrow(true);
            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

            level.addFreshEntity(arrow);
        }
    }
}
