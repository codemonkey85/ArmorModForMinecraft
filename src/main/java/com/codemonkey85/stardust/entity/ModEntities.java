package com.codemonkey85.stardust.entity;

import com.codemonkey85.stardust.StardustMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StardustMod.MOD_ID);

    public static final RegistryObject<EntityType<ThrownStardustTrident>> THROWN_STARDUST_TRIDENT =
            ENTITIES.register("thrown_stardust_trident", () ->
                    EntityType.Builder.<ThrownStardustTrident>of(ThrownStardustTrident::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("thrown_stardust_trident"));

    private ModEntities() {}
}
