package com.codemonkey85.stardust.mixin;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ArrowDamageEnchantment;
import net.minecraft.world.item.enchantment.ArrowFireEnchantment;
import net.minecraft.world.item.enchantment.ArrowInfiniteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    private void stardust$extendCompatibility(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment self = (Enchantment) (Object) this;
        Item item = stack.getItem();

        if (self instanceof MultiShotEnchantment && item instanceof BowItem) {
            cir.setReturnValue(true);
            return;
        }

        if (item instanceof CrossbowItem
                && (self instanceof ArrowDamageEnchantment
                    || self instanceof ArrowFireEnchantment
                    || self instanceof ArrowInfiniteEnchantment)) {
            cir.setReturnValue(true);
        }
    }
}
