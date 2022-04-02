package net.commie_meyl.tno_mod.entity;

import net.commie_meyl.tno_mod.entity.custom.SoldierBossEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.commie_meyl.tno_mod.TnoMod;
import net.commie_meyl.tno_mod.entity.custom.SoldierEntity;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, TnoMod.MOD_ID);

    public static final RegistryObject<EntityType<SoldierEntity>> SOLDIER =
            ENTITY_TYPES.register("soldier",
                    () -> EntityType.Builder.create(SoldierEntity::new,
                                    EntityClassification.MONSTER).size(1f, 1.9f)
                            .build(new ResourceLocation(TnoMod.MOD_ID, "soldier").toString()));

    public static final RegistryObject<EntityType<SoldierBossEntity>> SOLDIER_BOSS =
            ENTITY_TYPES.register("soldier_boss",
                    () -> EntityType.Builder.create(SoldierBossEntity::new,
                                    EntityClassification.MONSTER).size(1f, 1.9f)
                            .build(new ResourceLocation(TnoMod.MOD_ID, "soldier_boss").toString()));





    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
