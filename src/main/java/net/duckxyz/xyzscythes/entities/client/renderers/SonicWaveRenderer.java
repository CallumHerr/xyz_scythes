package net.duckxyz.xyzscythes.entities.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.duckxyz.xyzscythes.entities.projectiles.SonicBlast;

public class SonicWaveRenderer extends EntityRenderer<SonicBlast> {

    @Override
    public void render(SonicBlast pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SonicBlast pEntity) {
        return null;
    }

    public SonicWaveRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
