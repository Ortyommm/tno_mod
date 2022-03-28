package net.commie_meyl.tno_mod.events;

import net.commie_meyl.tno_mod.TnoMod;
import net.commie_meyl.tno_mod.entity.ModEntityTypes;
import net.commie_meyl.tno_mod.entity.custom.SoldierBossEntity;
import net.commie_meyl.tno_mod.entity.custom.SoldierEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TnoMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.SOLDIER.get(), SoldierEntity.setCustomAttributes().create());
        event.put(ModEntityTypes.SOLDIER_BOSS.get(), SoldierBossEntity.setCustomAttributes().create());
    }

//    @SubscribeEvent
//    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
//        ModSpawnEggItem.initSpawnEggs();
//    }
}
