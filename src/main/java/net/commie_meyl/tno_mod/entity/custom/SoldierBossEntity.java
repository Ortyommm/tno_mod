package net.commie_meyl.tno_mod.entity.custom;

import net.commie_meyl.tno_mod.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import org.graalvm.compiler.replacements.Log;

public class SoldierBossEntity extends VindicatorEntity {
    private static final DataParameter<Boolean> ATTACKING;
    public SoldierBossEntity(EntityType<? extends VindicatorEntity> type, World worldIn) {
        super(type, worldIn);
    }


    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 200)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, .5D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 30.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 70.0D)
                .createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);
//                .createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);

    }



    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
    }

//    @Override
//    protected boolean shouldBurnInDay() {
//        return false;
//    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
//        PlayerEntity.getPosX();
//        this.goalSelector.addGoal(6, new );
        this.goalSelector.addGoal(7, new SoldierBossEntity.FireballAttackGoal(this));
    }


    @Override
    protected int getExperiencePoints(PlayerEntity player)
    {
        return 3 + this.world.rand.nextInt(5);
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
//        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
        if (!super.attackEntityAsMob(entityIn)) {
            return false;
        } else {
//            if (entityIn instanceof LivingEntity) {
//                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200,3));
//                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 200));
//                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.NAUSEA, 200));
//            }
            return true;
        }
    }


    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        float f = difficultyIn.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(true);
//        if (spawnDataIn == null) {
//            spawnDataIn = new ZombieEntity.GroupData(func_241399_a_(worldIn.getRandom()), true);
//        }

//        if (spawnDataIn instanceof VindicatorEntity.GroupData) {
//            ZombieEntity.GroupData zombieentity$groupdata = (ZombieEntity.GroupData)spawnDataIn;

//            this.setBreakDoorsAItask(this.canBreakDoors());
            this.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100000, 1, true, true));
            this.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 100000, 2, true, true));
            this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100000, 2, true, true));
            this.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 100000, 10, true, true));
            this.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 100000, 2, true, true));
//            this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100000, 1, true, true));

//            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
//            this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
//            this.setEnchantmentBasedOnDifficulty(difficultyIn);
//        }


//        this.applyAttributeBonuses(f);
        return spawnDataIn;
    }

    public boolean isAttacking() {
        return (Boolean)this.dataManager.get(ATTACKING);
    }


    public void setAttacking(boolean p_175454_1_) {
        this.dataManager.set(ATTACKING, p_175454_1_);
    }


    static {
        ATTACKING = EntityDataManager.createKey(SoldierBossEntity.class, DataSerializers.BOOLEAN);
    }


//    static class GoToPlayerGoal extends MoveToBlockGoal {
//        private final SoldierBossEntity soldier;
//
//        public GoToPlayerGoal(SoldierBossEntity p_i45888_1_, double movementSpeed, int searchLength) {
//            super(p_i45888_1_, movementSpeed, searchLength);
//            this.soldier = p_i45888_1_;
//        }
//
//        public boolean shouldContinueExecuting() {
////            this.shouldMoveTo(this.soldier.world, Minecraft.getInstance().player.getPosition());
//            return true;
//        }
//
//        public boolean shouldExecute() {
//            return true;
//        }
//
//        public boolean shouldMove() {
//            return this.timeoutCounter % 160 == 0;
//        }
//
//        @Override
//        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
//            this.destinationBlock = blockPos;
//            //Minecraft.getInstance().player.getPosition()
////            System.out.println();
//            return true;
//        }
//
//        public void tick() {
//
//        }
//    }

    static class FireballAttackGoal extends Goal {
        private final SoldierBossEntity parentEntity;
        public int attackTimer;

        public FireballAttackGoal(SoldierBossEntity p_i45837_1_) {
            this.parentEntity = p_i45837_1_;
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
//            System.out.println(this);
            double lvt_2_1_ = 64.0D;
            if (target.getPosY() - this.parentEntity.getPosY() > 4 || target.getDistanceSq(this.parentEntity) < 4096.0D && target.getDistanceSq(this.parentEntity) > 256.0D/* && this.parentEntity.canEntityBeSeen(target)*/) {
                World world = this.parentEntity.world;
                ++this.attackTimer;
//                world.playSound((PlayerEntity)null, this.parentEntity.getPosition(), new SoundEvent("rocket"), "entity", 10, 2.0f);
//                if (this.attackTimer == 10 && !this.parentEntity.isSilent()) {
//                    world.playEvent((PlayerEntity)null, 1015, this.parentEntity.getPosition(), 0);
//                }

                if (this.attackTimer == 5) {
                    double lvt_5_1_ = 4.0D;
                    Vector3d lvt_7_1_ = this.parentEntity.getLook(1.0F);
                    double lvt_8_1_ = target.getPosX() - (this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D);
                    double lvt_10_1_ = target.getPosYHeight(0.5D) - (0.5D + this.parentEntity.getPosYHeight(0.5D));
                    double lvt_12_1_ = target.getPosZ() - (this.parentEntity.getPosZ() + lvt_7_1_.z * 4.0D);
//                    if (!this.parentEntity.isSilent()) {
//                        world.playEvent((PlayerEntity)null, 1016, this.parentEntity.getPosition(), 0);
//                    }
                    world.playSound(null, this.parentEntity.getPosition(), ModSoundEvents.ROCKET.get()
                            , SoundCategory.HOSTILE, 3, 1);

                    CustomFireballEntity fireball = new CustomFireballEntity(world, this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
                    fireball.explosionPower = 6;
                    fireball.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D, this.parentEntity.getPosYHeight(0.5D) + 0.5D, fireball.getPosZ() + lvt_7_1_.z * 4.0D);
                    world.addEntity(fireball);

                    this.attackTimer = -10;
                }
            } else if (this.attackTimer > 0) {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 2.5);
        }
    }
}
