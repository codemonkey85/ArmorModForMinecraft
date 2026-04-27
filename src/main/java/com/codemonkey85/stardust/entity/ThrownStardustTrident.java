package com.codemonkey85.stardust.entity;

import com.codemonkey85.stardust.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownStardustTrident extends AbstractArrow {
    public static final float BASE_THROW_DAMAGE = 12.0F;

    private static final EntityDataAccessor<Byte> ID_LOYALTY =
            SynchedEntityData.defineId(ThrownStardustTrident.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ID_FOIL =
            SynchedEntityData.defineId(ThrownStardustTrident.class, EntityDataSerializers.BOOLEAN);

    private ItemStack tridentItem = new ItemStack(ModItems.STARDUST_TRIDENT.get());
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;

    public ThrownStardustTrident(EntityType<? extends ThrownStardustTrident> type, Level level) {
        super(type, level);
    }

    public ThrownStardustTrident(Level level, LivingEntity shooter, ItemStack stack) {
        super(ModEntities.THROWN_STARDUST_TRIDENT.get(), shooter, level);
        this.tridentItem = stack.copy();
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
        this.entityData.set(ID_FOIL, stack.hasFoil());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_LOYALTY, (byte) 0);
        this.entityData.define(ID_FOIL, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (loyalty > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!this.isAcceptableReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 toOwner = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + toOwner.y * 0.015D * (double) loyalty, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }
                double speed = 0.05D * (double) loyalty;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(toOwner.normalize().scale(speed)));
                if (this.clientSideReturnTridentTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }
                ++this.clientSideReturnTridentTickCount;
            }
        }
        super.tick();
    }

    private boolean isAcceptableReturnOwner() {
        Entity owner = this.getOwner();
        if (owner != null && owner.isAlive()) {
            return !(owner instanceof ServerPlayer) || !owner.isSpectator();
        }
        return false;
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.tridentItem.copy();
    }

    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        float damage = BASE_THROW_DAMAGE;
        if (target instanceof LivingEntity living) {
            damage += EnchantmentHelper.getDamageBonus(this.tridentItem, living.getMobType());
        }
        Entity owner = this.getOwner();
        DamageSource damageSource = this.damageSources().trident(this, owner == null ? this : owner);
        this.dealtDamage = true;
        SoundEvent hitSound = SoundEvents.TRIDENT_HIT;
        if (target.hurt(damageSource, damage)) {
            if (target.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (target instanceof LivingEntity hitLiving) {
                if (owner instanceof LivingEntity ownerLiving) {
                    EnchantmentHelper.doPostHurtEffects(hitLiving, ownerLiving);
                    EnchantmentHelper.doPostDamageEffects(ownerLiving, hitLiving);
                }
                this.doPostHurtEffects(hitLiving);
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float volume = 1.0F;
        if (this.level() instanceof ServerLevel && this.level().isThundering() && this.isChanneling()) {
            BlockPos pos = target.blockPosition();
            if (this.level().canSeeSky(pos)) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level());
                if (bolt != null) {
                    bolt.moveTo(Vec3.atBottomCenterOf(pos));
                    bolt.setCause(owner instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                    this.level().addFreshEntity(bolt);
                    hitSound = SoundEvents.TRIDENT_THUNDER;
                    volume = 5.0F;
                }
            }
        }
        this.playSound(hitSound, volume, 1.0F);
    }

    public boolean isChanneling() {
        return EnchantmentHelper.hasChanneling(this.tridentItem);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Trident", 10)) {
            this.tridentItem = ItemStack.of(tag.getCompound("Trident"));
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.tridentItem));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Trident", this.tridentItem.save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void tickDespawn() {
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || loyalty <= 0) {
            super.tickDespawn();
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}
