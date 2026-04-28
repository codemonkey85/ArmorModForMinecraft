package com.codemonkey85.stardust.item;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    STARDUST(StardustMod.MOD_ID + ":stardust", 50,
            Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
                m.put(ArmorItem.Type.HELMET, 13);
                m.put(ArmorItem.Type.CHESTPLATE, 16);
                m.put(ArmorItem.Type.LEGGINGS, 15);
                m.put(ArmorItem.Type.BOOTS, 13);
            }),
            Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
                m.put(ArmorItem.Type.HELMET, 4);
                m.put(ArmorItem.Type.CHESTPLATE, 9);
                m.put(ArmorItem.Type.LEGGINGS, 7);
                m.put(ArmorItem.Type.BOOTS, 4);
            }),
            18, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0f, 0.15f,
            () -> Ingredient.of(ModItems.STARDUST_INGOT.get())),
    PYROPE(StardustMod.MOD_ID + ":pyrope", 25,
            Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
                m.put(ArmorItem.Type.HELMET, 11);
                m.put(ArmorItem.Type.CHESTPLATE, 16);
                m.put(ArmorItem.Type.LEGGINGS, 15);
                m.put(ArmorItem.Type.BOOTS, 13);
            }),
            Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
                m.put(ArmorItem.Type.HELMET, 2);
                m.put(ArmorItem.Type.CHESTPLATE, 6);
                m.put(ArmorItem.Type.LEGGINGS, 5);
                m.put(ArmorItem.Type.BOOTS, 2);
            }),
            12, SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f,
            () -> Ingredient.of(ModItems.PYROPE_INGOT.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> durabilityMap;
    private final EnumMap<ArmorItem.Type, Integer> defenseMap;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier,
                      EnumMap<ArmorItem.Type, Integer> durabilityMap,
                      EnumMap<ArmorItem.Type, Integer> defenseMap,
                      int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance,
                      Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.durabilityMap = durabilityMap;
        this.defenseMap = defenseMap;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override public int getDurabilityForType(ArmorItem.Type type) { return durabilityMap.get(type) * durabilityMultiplier; }
    @Override public int getDefenseForType(ArmorItem.Type type) { return defenseMap.get(type); }
    @Override public int getEnchantmentValue() { return enchantmentValue; }
    @Override public SoundEvent getEquipSound() { return equipSound; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
    @Override public String getName() { return name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockbackResistance; }
}
