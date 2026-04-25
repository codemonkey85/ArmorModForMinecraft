package com.codemonkey85.stardust.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class TieredBowItem extends BowItem {
    private final int chargeTicks;
    private final float damageBonus;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    public TieredBowItem(Properties properties, int chargeTicks, float damageBonus,
                         int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        super(properties);
        this.chargeTicks = chargeTicks;
        this.damageBonus = damageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    public int getChargeTicks() {
        return chargeTicks;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repair) {
        return repairIngredient.get().test(repair) || super.isValidRepairItem(stack, repair);
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        if (damageBonus > 0.0f) {
            arrow.setBaseDamage(arrow.getBaseDamage() + damageBonus);
        }
        return arrow;
    }
}
