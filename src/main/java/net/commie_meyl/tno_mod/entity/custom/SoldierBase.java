package net.commie_meyl.tno_mod.entity.custom;

import net.commie_meyl.tno_mod.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import javax.annotation.Nullable;

public class SoldierBase extends VindicatorEntity {
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(SoldierBase.class, DataSerializers.BOOLEAN);
    public SoldierBase(EntityType<? extends VindicatorEntity> type, World worldIn) {
        super(type, worldIn);
    }


    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
    }

    //TODO extends monster entity and revenge

    //Vindicator overriding start

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setCustomName(@Nullable ITextComponent name) {}

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {}

    //Vindicator end

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, () -> true));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true, true));
        //Are already in vindicator class
       /* this.goalSelector.addGoal(0, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new MeleeAttackGoal(this, 1, false));*/

    }


    @Override
    protected int getExperiencePoints(PlayerEntity player)
    {
        return 3 + this.world.rand.nextInt(30);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_PILLAGER_AMBIENT;
    }


    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn)
    {
        this.playSound(SoundEvents.ENTITY_HOGLIN_STEP, 0.20F, 0.5F);
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!super.attackEntityAsMob(entityIn)) {
            return false;
        } else {
            return true;
        }
    }


    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        this.setCanPickUpLoot(true);
        return spawnDataIn;
    }


    public void setAttacking(boolean p_175454_1_) {
        this.dataManager.set(ATTACKING, p_175454_1_);
    }


    static class FireballAttackGoal extends Goal {
        private final SoldierBase parentEntity;
        private final int explosionPower;
        private final double attackSlownessCoefficient;
        public int attackTimer;

        public FireballAttackGoal(SoldierBase p_i45837_1_, int explosionPower, double attackSlownessCoefficient) {
            this.parentEntity = p_i45837_1_;
            this.explosionPower = explosionPower;
            this.attackSlownessCoefficient = attackSlownessCoefficient;
        }

        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }

        public void startExecuting() {
            this.attackTimer = 0;
        }

        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }

        public void tick() {
            LivingEntity target = this.parentEntity.getAttackTarget();
            double lvt_2_1_ = 64.0D;
            if (target.getPosY() - this.parentEntity.getPosY() > 4 || target.getDistanceSq(this.parentEntity) < 4096.0D && target.getDistanceSq(this.parentEntity) > 256.0D/* && this.parentEntity.canEntityBeSeen(target)*/) {
                World world = this.parentEntity.world;
                ++this.attackTimer;
                if (this.attackTimer == 20 * this.attackSlownessCoefficient) {
                    double lvt_5_1_ = 4.0D;
                    Vector3d lvt_7_1_ = this.parentEntity.getLook(1.0F);
                    double lvt_8_1_ = target.getPosX() - (this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D);
                    double lvt_10_1_ = target.getPosYHeight(0.5D) - (0.5D + this.parentEntity.getPosYHeight(0.5D));
                    double lvt_12_1_ = target.getPosZ() - (this.parentEntity.getPosZ() + lvt_7_1_.z * 4.0D);
                    world.playSound(null, this.parentEntity.getPosition(), ModSoundEvents.ROCKET.get()
                            , SoundCategory.HOSTILE, 3, 1);

                    CustomFireballEntity fireball = new CustomFireballEntity(world, this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
                    fireball.explosionPower = explosionPower;
                    fireball.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D, this.parentEntity.getPosYHeight(0.5D) + 0.5D, fireball.getPosZ() + lvt_7_1_.z * 4.0D);
                    world.addEntity(fireball);

                    this.attackTimer = (int) Math.round(-40 * this.attackSlownessCoefficient);
                }
            } else if (this.attackTimer > 0) {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10 * this.attackSlownessCoefficient);
        }
    }
}
