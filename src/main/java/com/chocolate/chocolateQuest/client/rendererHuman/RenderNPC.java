package com.chocolate.chocolateQuest.client.rendererHuman;

import net.minecraft.client.model.ModelBase;
import com.chocolate.chocolateQuest.client.TextureExternal;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.client.model.ModelGolemSmall;
import com.chocolate.chocolateQuest.client.model.ModelMonkey;
import com.chocolate.chocolateQuest.client.model.ModelMinotaur;
import com.chocolate.chocolateQuest.client.model.ModelNaga;
import com.chocolate.chocolateQuest.client.model.ModelSpecter;
import com.chocolate.chocolateQuest.client.model.ModelHumanSkeleton;
import net.minecraft.client.model.ModelBiped;
import com.chocolate.chocolateQuest.client.model.ModelHuman;

public class RenderNPC extends RenderHuman
{
    static ModelHuman base;
    ModelHuman skeleton;
    ModelHuman specter;
    ModelHuman triton;
    ModelHuman minotaur;
    ModelHuman monkey;
    ModelHuman golem;
    
    public RenderNPC() {
        super(RenderNPC.base, 0.5f);
        this.skeleton = new ModelHumanSkeleton();
        this.specter = new ModelSpecter();
        this.triton = new ModelNaga();
        this.minotaur = new ModelMinotaur();
        this.monkey = new ModelMonkey();
        this.golem = new ModelGolemSmall();
    }
    
    @Override
    protected void renderModel(final EntityLivingBase entityliving, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final EntityHumanNPC npc = (EntityHumanNPC)entityliving;
        final float red = BDHelper.getColorRed(npc.color);
        final float green = BDHelper.getColorGreen(npc.color);
        final float blue = BDHelper.getColorBlue(npc.color);
        GL11.glColor4f(red, green, blue, 1.0f);
        switch (npc.modelType) {
            case 3: {
                this.mainModel = (ModelBase)this.triton;
                break;
            }
            case 4: {
                this.mainModel = (ModelBase)this.minotaur;
                break;
            }
            case 6: {
                this.mainModel = (ModelBase)this.specter;
                break;
            }
            case 5: {
                this.mainModel = (ModelBase)this.skeleton;
                break;
            }
            case 7: {
                this.mainModel = (ModelBase)this.monkey;
                break;
            }
            case 8: {
                this.mainModel = (ModelBase)this.golem;
                break;
            }
            default: {
                this.mainModel = (ModelBase)RenderNPC.base;
                break;
            }
        }
        final ModelBiped modelBiped = (ModelBiped)this.mainModel;
        this.modelBipedMain = modelBiped;
        this.renderPassModel = (ModelBase)modelBiped;
        super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
    }
    
    public void doRender(final Entity entity, final double x, final double y, final double z, final float ry, final float rp) {
        final EntityHumanNPC npc = (EntityHumanNPC)entity;
        super.doRender(entity, x, y, z, ry, rp);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityliving, final float f) {
        super.preRenderCallback(entityliving, f);
        final EntityHumanNPC e = (EntityHumanNPC)entityliving;
        if (e.modelType == 1) {
            GL11.glScalef(0.7f, 0.7f, 0.7f);
        }
        GL11.glScalef(e.size + 0.5f, e.size + 0.5f, e.size + 0.5f);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entityliving, final int par2, final float par3) {
        final EntityHumanNPC e = (EntityHumanNPC)entityliving;
        if (e.modelType == 3) {
            return (par2 != 2 && par2 != 3) ? super.shouldRenderPass(entityliving, par2, par3) : 0;
        }
        return super.shouldRenderPass(entityliving, par2, par3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity e) {
        final EntityHumanNPC npc = (EntityHumanNPC)e;
        if (npc.hasPlayerTexture) {
            if (npc.textureLocationPlayer == null) {
                ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
                final String name = npc.texture;
                resourcelocation = AbstractClientPlayer.getLocationSkin(name);
                AbstractClientPlayer.getDownloadImageSkin(resourcelocation, name);
                npc.textureLocationPlayer = resourcelocation;
            }
            return npc.textureLocationPlayer;
        }
        if (npc.texture.startsWith("@")) {
            return new ResourceLocation("cql:" + npc.texture.substring(1));
        }
        return new ResourceLocation("chocolateQuest:textures/entity/biped/" + npc.texture);
    }
    
    protected void bindTexture(final ResourceLocation rl) {
        if (rl.getResourceDomain().equals("cql")) {
            TextureExternal.bindTexture(rl.getResourcePath());
        }
        else {
            super.bindTexture(rl);
        }
    }
    
    static {
        RenderNPC.base = new ModelHuman();
    }
}
