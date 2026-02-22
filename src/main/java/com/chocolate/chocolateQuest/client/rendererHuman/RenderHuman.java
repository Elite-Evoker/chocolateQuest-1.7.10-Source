package com.chocolate.chocolateQuest.client.rendererHuman;

import net.minecraft.entity.Entity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.passive.EntityHorse;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.init.Items;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.entity.RenderBiped;

public class RenderHuman extends RenderBiped
{
    float featherY;
    ItemStack item;
    protected ResourceLocation texture;
    
    public RenderHuman(final ModelBiped modelbase, final float f) {
        super(modelbase, f);
        this.featherY = -0.5f;
        this.item = new ItemStack(Items.feather);
        this.texture = new ResourceLocation("chocolatequest:textures/entity/biped/pirate.png");
    }
    
    public RenderHuman(final ModelBiped modelbase, final float f, final ResourceLocation r) {
        this(modelbase, f);
        this.texture = r;
    }
    
    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelHuman(1.0f, false);
        this.field_82425_h = new ModelHuman(0.5f, false);
    }
    
    protected void renderModel(final EntityLivingBase entityliving, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final EntityHumanBase e = (EntityHumanBase)entityliving;
        if (e.isSitting()) {
            this.setSitOffset();
        }
        super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
        this.renderLeftHandItem(entityliving, f1);
        if (!e.isInvisible()) {
            if (e.isCaptain()) {
                this.renderHelmetFeather(e);
            }
            if (e.shouldRenderCape()) {
                this.renderCape(e);
            }
        }
    }
    
    protected void renderLivingAt(final EntityLivingBase entityliving, final double d, final double d1, final double d2) {
        this.renderLivingLabel(entityliving, entityliving.getCommandSenderName(), d, d1, d2, 64);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        final EntityHumanBase human = (EntityHumanBase)entityliving;
        if (human.ridingEntity instanceof EntityHorse) {
            GL11.glTranslatef(0.0f, -0.5f, 0.0f);
        }
        if (human.isSpeaking()) {
            this.renderSpeech(human);
        }
    }
    
    protected void renderHelmetFeather(final EntityHumanBase e) {
        GL11.glPushMatrix();
        ((ModelBiped)this.mainModel).bipedHead.postRender(0.0625f);
        GL11.glTranslatef(-0.05f, this.featherY, 0.01f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 1.0f);
        GL11.glRotatef(125.0f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.renderManager.itemRenderer.renderItem((EntityLivingBase)e, this.item, 0);
        GL11.glPopMatrix();
    }
    
    protected void renderCape(final EntityHumanBase e) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5f, 0.0f, 0.2f);
        GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
        ((ModelBiped)this.mainModel).bipedBody.postRender(0.0625f);
        this.renderManager.renderEngine.bindTexture(BDHelper.getItemTexture());
        final int spriteIndex = e.getTeamID();
        final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
        final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
        final float i3 = 0.125f;
        final float i4 = 0.25f;
        final float f6 = 1.0f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)i1, (double)i3);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)i2, (double)i3);
        tessellator.addVertexWithUV(1.0, 1.2000000476837158, 0.0, (double)i2, (double)i4);
        tessellator.addVertexWithUV(0.0, 1.2000000476837158, 0.0, (double)i1, (double)i4);
        tessellator.draw();
        GL11.glPopMatrix();
    }
    
    protected void renderSpeech(final EntityHumanBase e) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, e.height * 1.6f, 0.0f);
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f - this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        this.renderManager.renderEngine.bindTexture(BDHelper.getItemTexture());
        final Tessellator tessellator = Tessellator.instance;
        final int spriteIndex = (e.ticksExisted / 160 + e.getEntityId()) % 16;
        final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
        final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
        final float i3 = 0.8125f;
        final float i4 = 0.875f;
        final float width;
        final float size = width = 0.6f;
        final float x = -0.5f;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, 0.0, 0.0, (double)i1, (double)i3);
        tessellator.addVertexWithUV((double)(x + width), 0.0, 0.0, (double)i2, (double)i3);
        tessellator.addVertexWithUV((double)(x + width), (double)size, 0.0, (double)i2, (double)i4);
        tessellator.addVertexWithUV((double)(x + 0.0f), (double)size, 0.0, (double)i1, (double)i4);
        tessellator.draw();
        GL11.glPopMatrix();
    }
    
    protected void setSitOffset() {
        GL11.glTranslatef(0.0f, 0.6f, 0.0f);
    }
    
    protected void renderLeftHandItem(final EntityLivingBase entityliving, final float f) {
        final ItemStack itemstack = ((EntityHumanBase)entityliving).getHeldItemLeft();
        if (itemstack != null) {
            GL11.glPushMatrix();
            ((ModelBiped)this.mainModel).bipedLeftArm.postRender(0.0625f);
            GL11.glTranslatef(0.0325f, 0.4375f, 0.0625f);
            final IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
            final boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (itemstack.getItem() == ChocolateQuest.shield) {
                final float var6 = 1.2f;
                GL11.glTranslatef(0.22f, 0.35f, 0.0f);
                GL11.glRotatef(169.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(22.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(8.0f, 0.0f, 1.0f, 0.0f);
            }
            if (itemstack.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockById(Item.getIdFromItem(itemstack.getItem())).getRenderType()))) {
                float var6 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var6 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-var6, -var6, var6);
            }
            else if (itemstack.getItem() == Items.bow) {
                final float var6 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (itemstack.getItem().isFull3D()) {
                final float var6 = 0.625f;
                if (itemstack.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                this.func_82422_c();
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (itemstack.getItem() instanceof ItemGolemWeapon) {
                this.doLeftItemRotation();
                this.doLeftHandRotationForGolemWeapon();
            }
            else {
                this.doLeftItemRotation();
            }
            this.renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
            if (itemstack.getItem().requiresMultipleRenderPasses()) {
                for (int x = 1; x < itemstack.getItem().getRenderPasses(itemstack.getMetadata()); ++x) {
                    this.renderManager.itemRenderer.renderItem(entityliving, itemstack, x);
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    public void doLeftItemRotation() {
        final float var6 = 0.375f;
        GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
        GL11.glScalef(var6, var6, var6);
        GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void doLeftHandRotationForGolemWeapon() {
        GL11.glTranslatef(-0.02f, 0.04f, -0.2f);
    }
    
    protected void rotateCorpse(final EntityLivingBase entityliving, final float f, final float f1, final float f2) {
        GL11.glRotatef(180.0f - f1, 0.0f, 1.0f, 0.0f);
        if (entityliving.deathTime > 0) {
            float f3 = (entityliving.deathTime + f2 - 1.0f) / 20.0f * 1.6f;
            f3 = MathHelper.sqrt_float(f3);
            if (f3 > 1.0f) {
                f3 = 1.0f;
            }
            GL11.glRotatef(f3 * this.getDeathMaxRotation(entityliving), 0.0f, 0.0f, 1.0f);
        }
    }
    
    public void renderCustomItem(final EntityLivingBase entityliving, final ItemStack itemstack, final int i) {
        RenderItemBase.doRenderItem(itemstack, i);
    }
    
    protected int getColorMultiplier(final EntityLivingBase entityliving, final float f, final float f1) {
        return 0;
    }
    
    protected void preRenderCallback(final EntityLivingBase entityliving, final float f) {
        final EntityHumanBase e = (EntityHumanBase)entityliving;
        final float s = e.getSizeModifier();
        GL11.glScalef(s, s, s);
    }
    
    protected void renderLivingLabel(final EntityLivingBase entityliving, final String s, final double d, final double d1, final double d2, final int i) {
        final EntityHumanBase human = (EntityHumanBase)entityliving;
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        if (entityliving.isOnSameTeam((EntityLivingBase)player)) {
            final FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            final float f1 = 1.6f;
            final float f2 = 0.01666667f * f1;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d + 0.0f, (float)d1 + entityliving.height + entityliving.height * 0.4f, (float)d2);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-f2, -f2, f2);
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            final Tessellator tessellator = Tessellator.instance;
            float width = 20.0f;
            final float height = 2.0f;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(1.0f, 0.0f, 0.0f, 1.0f);
            tessellator.addVertex((double)(-width - 1.0f), -1.0, 0.0);
            tessellator.addVertex((double)(-width - 1.0f), (double)height, 0.0);
            tessellator.addVertex((double)(width + 1.0f), (double)height, 0.0);
            tessellator.addVertex((double)(width + 1.0f), -1.0, 0.0);
            tessellator.draw();
            final float healthWidth = human.getHealth() * width * 2.0f / human.getMaxHealth();
            if (healthWidth > 0.0f) {
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(0.0f, 1.0f, 0.0f, 1.0f);
                tessellator.addVertex((double)(-width - 1.0f), -1.0, 0.0);
                tessellator.addVertex((double)(-width - 1.0f), (double)height, 0.0);
                tessellator.addVertex((double)(-width + healthWidth + 1.0f), (double)height, 0.0);
                tessellator.addVertex((double)(-width + healthWidth + 1.0f), -1.0, 0.0);
                tessellator.draw();
            }
            if (human.getOwner() == player) {
                GL11.glDepthMask(false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                byte byte0 = 0;
                byte0 = -10;
                tessellator.startDrawingQuads();
                width = (float)(fontrenderer.getStringWidth(s) / 2);
                tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                tessellator.addVertex((double)(-width - 1.0f), (double)(-1 + byte0), 0.0);
                tessellator.addVertex((double)(-width - 1.0f), (double)(8 + byte0), 0.0);
                tessellator.addVertex((double)(width + 1.0f), (double)(8 + byte0), 0.0);
                tessellator.addVertex((double)(width + 1.0f), (double)(-1 + byte0), 0.0);
                tessellator.draw();
                GL11.glEnable(3553);
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, (int)byte0, 553648127);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, (int)byte0, -1);
            }
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    protected ResourceLocation getEntityTexture(final Entity par1Entity) {
        return this.texture;
    }
}
