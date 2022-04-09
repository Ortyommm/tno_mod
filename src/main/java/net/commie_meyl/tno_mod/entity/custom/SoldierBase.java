package net.commie_meyl.tno_mod.entity.custom;

import net.commie_meyl.tno_mod.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
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
//        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true, true));
        this.targetSelector.addGoal(6, new MoveToPlayerGoal(this));
        this.goalSelector.removeGoal( new RandomWalkingGoal(this, 0.6D));
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

    static class MoveToPlayerGoal extends Goal {
        private final SoldierBase parentEntity;
        private int destroyBlockTimer = 0;
        private int placeBlockTimer = 0;
        private double yDifferenceTrigger = 1;

        public MoveToPlayerGoal(SoldierBase parentEntity) {
            this.parentEntity = parentEntity;
        }

        @Override
        public boolean shouldExecute() {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            return (player.getDistanceSq(this.parentEntity) < 2048.0D || Math.abs(player.getPosY() - this.parentEntity.getPosY()) > yDifferenceTrigger) && this.parentEntity.getAttackTarget() == null;
        }

        private boolean canBlockBeDestroyed(BlockState block){
            if(block.getMaterial().isLiquid()) {
                return false;
            }

            return !block.isAir() && !BlockTags.WITHER_IMMUNE.contains(block.getBlock());
        }

        private void destroyBlockIfCanBeDestroyed(World world, BlockPos blockPos){
            BlockState block = world.getBlockState(blockPos);

            if(this.canBlockBeDestroyed(block)) {
                world.destroyBlock(blockPos, false, this.parentEntity);
            }
        }

        private void tryPlaceBlock(World world, BlockPos blockPos, boolean shouldJump) {
            boolean canBlockBePlaced = BlockPos.getAllInBox(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1)).anyMatch((nearBlockPos) -> {
                BlockState block = world.getBlockState(nearBlockPos);
                return !block.isAir() && !block.getMaterial().isLiquid();
            });
            if(!canBlockBePlaced) {
                return;
            }
if(shouldJump){
    this.parentEntity.jump();
}
            world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
        }

        public void tick(){
            ++this.destroyBlockTimer;
            ++this.placeBlockTimer;
            World world = this.parentEntity.world;
            ClientPlayerEntity player = Minecraft.getInstance().player;
            double yPosDifference = player.getPosY() - this.parentEntity.getPosY();
            double yPos = yPosDifference >= 0 ? this.parentEntity.getPosY() : this.parentEntity.getPosY() - 1;
            this.parentEntity.getLookController().setLookPosition(player.getPosX(), player.getPosYEye(), player.getPosZ());

            if(Math.abs(yPosDifference) > yDifferenceTrigger ) {
                if(this.placeBlockTimer >= 10) {
                    double yModifier = yPosDifference > 0 ? 2 : -1;
//                    this.parentEntity.getNavigator().tryMoveToXYZ(this.parentEntity.getPosX(), this.parentEntity.getPosY() + 1, this.parentEntity.getPosZ(), 1);
                    BlockPos blockOverPos = new BlockPos(this.parentEntity.getPosX(), this.parentEntity.getPosY()+yModifier, this.parentEntity.getPosZ());
                    BlockPos blockOver2Pos = new BlockPos(this.parentEntity.getPosX(), this.parentEntity.getPosY()+yModifier, this.parentEntity.getPosZ()+1);
                    this.destroyBlockIfCanBeDestroyed(world, blockOverPos);
                    this.destroyBlockIfCanBeDestroyed(world, blockOver2Pos);

                    BlockPos blockPos = new BlockPos(this.parentEntity.getPosX(), this.parentEntity.getPosY(), this.parentEntity.getPosZ());
                    this.tryPlaceBlock(world, blockPos, true);
                    this.placeBlockTimer = 0;
                }
                    return;
            }
            this.parentEntity.getNavigator().tryMoveToXYZ(player.getPosX(), player.getPosY(), player.getPosZ(), 1);
            BlockPos blockUnderSoldierPos = new BlockPos(this.parentEntity.getPosX(), this.parentEntity.getPosY()-1, this.parentEntity.getPosZ());
            BlockState blockUnderSoldier = world.getBlockState(blockUnderSoldierPos);
            if(blockUnderSoldier.isAir() || blockUnderSoldier.getBlock() != Blocks.DIRT) {
                this.tryPlaceBlock(world, blockUnderSoldierPos, false);
            }


            BlockPos block1Pos = new BlockPos(this.parentEntity.getPosX()+1, yPos, this.parentEntity.getPosZ());
            BlockPos block2Pos = new BlockPos(this.parentEntity.getPosX()+1, yPos+1, this.parentEntity.getPosZ());
            BlockState block1 = world.getBlockState(block1Pos);
            BlockState block2 = world.getBlockState(block1Pos);
//            System.out.println(world.getBlockState(block1Pos).canEntityDestroy(world, block2Pos, this.parentEntity));
            if(this.destroyBlockTimer >= 100) {
                    System.out.println(this.destroyBlockTimer);
                boolean shouldDestroy1 = this.canBlockBeDestroyed(block1);
                boolean shouldDestroy2 = this.canBlockBeDestroyed(block2);
                if(shouldDestroy2 || shouldDestroy1) {
                    this.destroyBlockTimer = 0;
                if(shouldDestroy1) {
                    world.destroyBlock(block1Pos, false, this.parentEntity);
                }
                if(shouldDestroy2) {
                    world.destroyBlock(block2Pos, false, this.parentEntity);
                }
                }

            }


//            world.destroyBlock(block2Pos, true, this.parentEntity);

//            System.out.println(world.getBlockState(block2).getBlock().addHitEffects(, world));
        }
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

        private void attackTarget(LivingEntity target) {
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
                    this.attackTarget(target);
                } else if (this.attackTimer > 0) {
                    --this.attackTimer;
                }
                this.parentEntity.setAttacking(this.attackTimer > 10 * this.attackSlownessCoefficient);
        }
    }
}
