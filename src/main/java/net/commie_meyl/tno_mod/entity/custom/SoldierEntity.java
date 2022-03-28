package net.commie_meyl.tno_mod.entity.custom;

import net.commie_meyl.tno_mod.entity.render.SoldierRenderer;
import net.commie_meyl.tno_mod.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import org.graalvm.compiler.replacements.Log;

public class SoldierEntity extends ZombieEntity {
    private static final DataParameter<Boolean> ATTACKING;
    private static final DataParameter<String> TEXTURE = EntityDataManager.createKey(SoldierEntity.class, DataSerializers.STRING);
    protected static final String[] textures = {"textures/entity/wehrmacht.png", "textures/entity/wehrmacht1.png", "textures/entity/wehrmacht2.png"};

    public void setTexture(String texture) {
        this.dataManager.set(TEXTURE, texture);
    }

    public String texture() {
        return this.dataManager.get(TEXTURE);
    }

    public SoldierEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, ThreadLocalRandom.current().nextInt(25, 50 + 1))
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, .39D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 9.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D)
                .createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
        this.dataManager.register(TEXTURE, textures[ThreadLocalRandom.current().nextInt(0, textures.length)]);
//        this.setTexture(textures[ThreadLocalRandom.current().nextInt(0, textures.length)]);

    }
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setTexture(compound.getString("TEXTURE"));
    }
        public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("TEXTURE", this.texture());
    }

        @Override
    public boolean isChild() {
        return false;
    }

    @Override
    protected boolean shouldDrown() {
        return false;
    }

    @Override
    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
//        PlayerEntity.getPosX();
        this.goalSelector.addGoal(7, new SoldierEntity.FireballAttackGoal(this));
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
    public void onKillEntity(ServerWorld world, LivingEntity killedEntity) {
      //not mutate village
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
        this.setTexture(textures[ThreadLocalRandom.current().nextInt(0, textures.length)]);
//        System.out.println(spawnDataIn);
        float f = difficultyIn.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(true);
        if (spawnDataIn == null) {
            spawnDataIn = new ZombieEntity.GroupData(func_241399_a_(worldIn.getRandom()), true);
        }
        if (spawnDataIn instanceof ZombieEntity.GroupData) {
            ZombieEntity.GroupData zombieentity$groupdata = (ZombieEntity.GroupData)spawnDataIn;

            this.setBreakDoorsAItask(this.canBreakDoors());
            this.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100000, 1, true, true));

//            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
//            this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
//            this.setEnchantmentBasedOnDifficulty(difficultyIn);
        }


        this.applyAttributeBonuses(f);
        return spawnDataIn;
    }

    public boolean isAttacking() {
        return (Boolean)this.dataManager.get(ATTACKING);
    }


    public void setAttacking(boolean p_175454_1_) {
        this.dataManager.set(ATTACKING, p_175454_1_);
    }


    static {
        ATTACKING = EntityDataManager.createKey(SoldierEntity.class, DataSerializers.BOOLEAN);
    }


//    static class GoToPlayerGoal extends MoveToBlockGoal {
//        private final SoldierEntity soldier;
//
//        public GoToPlayerGoal(SoldierEntity p_i45888_1_, double movementSpeed, int searchLength) {
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
        private final SoldierEntity parentEntity;
        public int attackTimer;

        public FireballAttackGoal(SoldierEntity p_i45837_1_) {
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
            if (target.getPosY() - this.parentEntity.getPosY() > 6 || target.getDistanceSq(this.parentEntity) < 4096.0D && target.getDistanceSq(this.parentEntity) > 256.0D/* && this.parentEntity.canEntityBeSeen(target)*/) {
                World world = this.parentEntity.world;
                ++this.attackTimer;
//                world.playSound((PlayerEntity)null, this.parentEntity.getPosition(), new SoundEvent("rocket"), "entity", 10, 2.0f);
//                if (this.attackTimer == 10 && !this.parentEntity.isSilent()) {
//                    world.playEvent((PlayerEntity)null, 1015, this.parentEntity.getPosition(), 0);
//                }

                if (this.attackTimer == 20) {
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
                    fireball.explosionPower = ThreadLocalRandom.current().nextInt(1, 4);
                    fireball.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D, this.parentEntity.getPosYHeight(0.5D) + 0.5D, fireball.getPosZ() + lvt_7_1_.z * 4.0D);
                    world.addEntity(fireball);

                    this.attackTimer = -40;
                }
            } else if (this.attackTimer > 0) {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }
}
