package net.commie_meyl.tno_mod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class SoldierEntity extends SoldierBase {
    private static final DataParameter<String> TEXTURE = EntityDataManager.createKey(SoldierEntity.class, DataSerializers.STRING);
    protected static final String[] textures = {"textures/entity/wehrmacht.png", "textures/entity/wehrmacht1.png", "textures/entity/wehrmacht2.png"};

    public void setTexture(String texture) {
        this.dataManager.set(TEXTURE, texture);
    }

    public String texture() {
        return this.dataManager.get(TEXTURE);
    }

    public SoldierEntity(EntityType<? extends SoldierBase> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, ThreadLocalRandom.current().nextInt(25, 50 + 1))
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, .40D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 9.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D);
//                .createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);

    }

    protected void registerData() {
        super.registerData();
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
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(7, new SoldierBase.FireballAttackGoal(this, ThreadLocalRandom.current().nextInt(1, 4), 2));
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




    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setTexture(textures[ThreadLocalRandom.current().nextInt(0, textures.length)]);
        this.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100000, 1, true, false));
        this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100000, 1, true, false));
        return spawnDataIn;
    }



}
