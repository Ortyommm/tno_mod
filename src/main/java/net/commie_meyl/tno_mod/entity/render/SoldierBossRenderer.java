package net.commie_meyl.tno_mod.entity.render;

import net.commie_meyl.tno_mod.TnoMod;
import net.commie_meyl.tno_mod.entity.custom.SoldierBossEntity;
import net.commie_meyl.tno_mod.entity.custom.SoldierEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class SoldierBossRenderer extends MobRenderer<SoldierBossEntity, PlayerModel<SoldierBossEntity>>
{
    protected static ResourceLocation TEXTURE =
            new ResourceLocation(TnoMod.MOD_ID, "textures/entity/wehrmacht3.png");
    public SoldierBossRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PlayerModel<>(0.0f, false), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(SoldierBossEntity entity) {
        return TEXTURE;
    }
}
