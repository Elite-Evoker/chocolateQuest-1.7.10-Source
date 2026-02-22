package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.client.entity.AbstractClientPlayer;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderBiped;

public class RenderBaiter extends RenderBiped
{
    ResourceLocation texture;
    
    public RenderBaiter(final ModelBiped par1ModelBiped, final float par2) {
        super(par1ModelBiped, par2);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/biped/pirateBoss.png");
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final EntityBaiter e = (EntityBaiter)entity;
        if (e.getThrower() instanceof AbstractClientPlayer) {
            final AbstractClientPlayer player = (AbstractClientPlayer)e.getThrower();
            return player.getLocationSkin();
        }
        if (e.getThrower() instanceof EntityHumanNPC) {
            final AbstractClientPlayer player = (AbstractClientPlayer)e.getThrower();
            return player.getLocationSkin();
        }
        return this.texture;
    }
}
