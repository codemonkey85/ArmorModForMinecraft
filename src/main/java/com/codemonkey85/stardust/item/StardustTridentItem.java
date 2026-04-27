package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.entity.ThrownStardustTrident;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StardustTridentItem extends TridentItem {
    private static final double ATTACK_DAMAGE = 10.0D;
    private static final double ATTACK_SPEED = -2.9D;

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public StardustTridentItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", ATTACK_SPEED, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) {
            return;
        }
        int useDuration = this.getUseDuration(stack) - timeLeft;
        if (useDuration < 10) {
            return;
        }
        int riptide = EnchantmentHelper.getRiptide(stack);
        if (riptide > 0 && !player.isInWaterOrRain()) {
            return;
        }

        if (!level.isClientSide) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(entity.getUsedItemHand()));
            if (riptide == 0) {
                ThrownStardustTrident trident = new ThrownStardustTrident(level, player, stack);
                trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float) riptide * 0.5F, 1.0F);
                if (player.getAbilities().instabuild) {
                    trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                level.addFreshEntity(trident);
                level.playSound(null, trident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(stack);
                }
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (riptide > 0) {
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            float dx = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
            float dy = -Mth.sin(pitch * ((float) Math.PI / 180F));
            float dz = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
            float magnitude = Mth.sqrt(dx * dx + dy * dy + dz * dz);
            float push = 3.0F * ((1.0F + (float) riptide) / 4.0F);
            dx *= push / magnitude;
            dy *= push / magnitude;
            dz *= push / magnitude;
            player.push(dx, dy, dz);
            player.startAutoSpinAttack(20);
            if (player.onGround()) {
                player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999F, 0.0D));
            }
            var sound = riptide >= 3 ? SoundEvents.TRIDENT_RIPTIDE_3
                    : riptide >= 2 ? SoundEvents.TRIDENT_RIPTIDE_2
                    : SoundEvents.TRIDENT_RIPTIDE_1;
            level.playSound(null, player, sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
