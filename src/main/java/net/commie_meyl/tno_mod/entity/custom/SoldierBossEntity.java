package net.commie_meyl.tno_mod.entity.custom;

import net.commie_meyl.tno_mod.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.world.*;
import net.minecraft.world.server.ServerBossInfo;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import org.graalvm.compiler.replacements.Log;

public class SoldierBossEntity extends SoldierBase {
    private final ServerBossInfo bossInfo = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public SoldierBossEntity(EntityType<? extends SoldierBase> type, World worldIn) {
        super(type, worldIn);
    }


    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 200)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, .5D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 30.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 70.0D);
    }

    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    protected void updateAITasks(){
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(7, new SoldierBase.FireballAttackGoal(this, 6, .3));

    }


    @Override
    protected int getExperiencePoints(PlayerEntity player)
    {
        return 200;
    }


    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100000, 1, true, false));
        this.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 100000, 2, true, false));
        this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100000, 2, true, false));
        this.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 100000, 10, true, false));
        this.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 100000, 2, true, false));
        return spawnDataIn;
    }
}
