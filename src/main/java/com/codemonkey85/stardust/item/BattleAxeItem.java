package com.codemonkey85.stardust.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class BattleAxeItem extends AxeItem {
    private static final float DAMAGE_BONUS = 2.0f;
    private static final float SPEED_PENALTY = 0.2f;

    public BattleAxeItem(Tier tier, float attackDamageBonus, float attackSpeed, Item.Properties properties) {
        super(tier, attackDamageBonus + DAMAGE_BONUS, attackSpeed - SPEED_PENALTY, properties);
    }
}
