package net.commie_meyl.tno_mod.entity.render;

import net.commie_meyl.tno_mod.TnoMod;
import net.commie_meyl.tno_mod.entity.custom.SoldierEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.ThreadLocalRandom;

public class SoldierRenderer extends MobRenderer<SoldierEntity, PlayerModel<SoldierEntity>>
{


    public SoldierRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PlayerModel<>(0.0f, false), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(SoldierEntity entity) {
        return new ResourceLocation(TnoMod.MOD_ID, entity.texture()/* textures[ThreadLocalRandom.current().nextInt(0, 2)]*/);
    }
}
