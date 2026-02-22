package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBiped;

public class ModelArmor extends ModelBiped
{
    public static final int HELMET = 0;
    public static final int PLATE = 1;
    static ResourceLocation layer2;
    static ResourceLocation layer1;
    public int type;
    ItemStack cachedItem;
    int renderPass;
    boolean isSitting;
    float movement;
    
    public ModelArmor(final int type) {
        this(1.0f, type);
    }
    
    public ModelArmor(final float f, final int type) {
        super(f);
        this.type = 0;
        this.cachedItem = null;
        this.renderPass = 0;
        this.isSitting = false;
        this.movement = 0.0f;
        this.type = type;
    }
    
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.type == 0) {
            this.bipedHead.render(par7);
            this.bipedHeadwear.render(par7);
        }
        else if (this.type == 1) {
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            if (par1Entity instanceof EntityLivingBase) {
                this.renderCape(((EntityLivingBase)par1Entity).getEquipmentInSlot(3));
                this.renderFront(((EntityLivingBase)par1Entity).getEquipmentInSlot(3));
            }
        }
        else if (this.type == 2) {
            this.bipedBody.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
        }
        else if (this.type == 3) {
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
        }
    }
    
    public void renderArmor(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        if (this.type == 0) {
            this.bipedHead.render(par7);
            this.bipedHeadwear.render(par7);
        }
        else if (this.type == 1) {
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
        }
        else if (this.type == 2) {
            this.bipedBody.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
        }
        else if (this.type == 3) {
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
        }
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setArmorColor(final ItemStack is) {
        final int color = is.getItem().getColorFromItemStack(is, 0);
        GL11.glColor4f(BDHelper.getColorRed(color), BDHelper.getColorGreen(color), BDHelper.getColorBlue(color), 1.0f);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity e) {
        this.isSneak = e.isSneaking();
        this.aimedBow = false;
        this.heldItemRight = 0;
        this.isSitting = false;
        this.movement = f * f1 * 0.0f;
        if (e instanceof EntityPlayer && ((EntityPlayer)e).getItemInUseCount() > 0) {
            final EnumAction enumaction = ((EntityPlayer)e).getItemInUse().getItemUseAction();
            if (enumaction == EnumAction.block) {
                this.heldItemRight = 3;
            }
            else if (enumaction == EnumAction.bow) {
                this.aimedBow = true;
            }
        }
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
        if (e instanceof EntityHumanBase) {
            this.setHumanRotationAngles(f, f1, f2, f3, f4, f5, (EntityHumanBase)e);
        }
    }
    
    public void setHumanRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final EntityHumanBase e) {
        if (e.isSitting()) {
            this.isSitting = true;
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX -= 0.62831855f;
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= 0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.5707964f;
            this.bipedLeftLeg.rotateAngleX = -1.5707964f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        else {
            if (e.isSneaking()) {
                this.bipedBody.rotateAngleX = 0.5f;
                final ModelRenderer bipedRightLeg = this.bipedRightLeg;
                bipedRightLeg.rotateAngleX -= 0.0f;
                final ModelRenderer bipedLeftLeg = this.bipedLeftLeg;
                bipedLeftLeg.rotateAngleX -= 0.0f;
                final ModelRenderer bipedRightArm2 = this.bipedRightArm;
                bipedRightArm2.rotateAngleX += 0.4f;
                final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
                bipedLeftArm2.rotateAngleX += 0.4f;
                this.bipedRightLeg.rotationPointZ = 4.0f;
                this.bipedLeftLeg.rotationPointZ = 4.0f;
                this.bipedRightLeg.rotationPointY = 9.0f;
                this.bipedLeftLeg.rotationPointY = 9.0f;
                this.bipedHead.rotationPointY = 1.0f;
            }
            if (e.isTwoHanded()) {
                this.setTwoHandedAngles(f2);
            }
            if (e.isAiming()) {
                final float rotX = this.bipedRightArm.rotateAngleX;
                final float rotY = this.bipedRightArm.rotateAngleY;
                if (e.rightHand.isTwoHanded()) {
                    if (e.rightHand.isAiming()) {
                        this.setAimingAngles(f2);
                    }
                }
                else if (e.rightHand.isAiming()) {
                    this.setAimingAnglesRight(f2);
                }
                if (e.leftHand.isAiming()) {
                    this.setAimingAnglesLeft(f2);
                }
                if (e.rightHand.isAiming() && this.swingProgress > 0.0f) {
                    final ModelRenderer bipedRightArm3 = this.bipedRightArm;
                    bipedRightArm3.rotateAngleY += rotY;
                    final ModelRenderer bipedRightArm4 = this.bipedRightArm;
                    bipedRightArm4.rotateAngleX += rotX + 0.3f;
                }
            }
            if (e.leftHandSwing > 0) {
                final int attackAnim = e.leftHandSwing;
                final int maxAttackAnimTime = 10;
                if (e.haveShied()) {
                    this.setShiedRotation(f2);
                    final float animProgress = (attackAnim + (attackAnim - 1) * f5) / maxAttackAnimTime * 4.82f;
                    final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
                    bipedLeftArm3.rotateAngleY += MathHelper.cos(animProgress) * 1.8f - 0.8f;
                    final ModelRenderer bipedLeftArm4 = this.bipedLeftArm;
                    bipedLeftArm4.rotateAngleX -= MathHelper.cos(animProgress) * 0.6f;
                }
                else {
                    final float animProgress = (float)((attackAnim + (attackAnim - 1) * f5) / maxAttackAnimTime * 3.141592653589793);
                    final ModelRenderer bipedLeftArm5 = this.bipedLeftArm;
                    bipedLeftArm5.rotateAngleY += MathHelper.cos(animProgress) * 0.5f;
                    final ModelRenderer bipedLeftArm6 = this.bipedLeftArm;
                    bipedLeftArm6.rotateAngleX -= MathHelper.sin(animProgress) * 1.2f;
                }
            }
            else if (e.isDefending()) {
                this.setShiedRotation(f2);
            }
        }
    }
    
    public void setTwoHandedAngles(final float time) {
        float f7 = 0.0f;
        final float rotationYaw = 0.0f;
        final float swing = MathHelper.sin(this.swingProgress * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = -0.3f;
        this.bipedRightArm.rotateAngleY = rotationYaw - 0.6f;
        this.bipedLeftArm.rotateAngleY = rotationYaw + 0.6f;
        this.bipedRightArm.rotateAngleX = -0.8f + swing;
        this.bipedLeftArm.rotateAngleX = -0.8f + swing;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleX += MathHelper.sin(time * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        float f8 = 1.0f - this.swingProgress;
        f8 *= f8;
        f8 *= f8;
        f8 = 1.0f - f8;
        f7 = MathHelper.sin(f8 * 3.1415927f);
        final float f9 = MathHelper.sin(this.swingProgress * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
        this.bipedRightArm.rotateAngleX -= (float)(f7 * 1.2 + f9);
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
        this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
        this.bipedLeftArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - (f7 * 1.2 + f9));
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
        this.bipedLeftArm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
    }
    
    public void setAimingAngles(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - f7 * 0.6f) + this.bipedHead.rotateAngleY;
        this.bipedLeftArm.rotateAngleY = 0.1f - f7 * 0.6f + this.bipedHead.rotateAngleY + 0.4f;
        this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += MathHelper.sin(time * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
    }
    
    public void setAimingAnglesRight(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        this.bipedRightArm.rotateAngleY = -0.060000002f + this.bipedHead.rotateAngleY;
        this.bipedRightArm.rotateAngleZ = MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
    }
    
    public void setAimingAnglesLeft(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
        this.bipedLeftArm.rotateAngleY = 0.1f - f7 * 0.6f + this.bipedHead.rotateAngleY;
        this.bipedLeftArm.rotateAngleZ = -MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
    }
    
    public void setShiedRotation(final float time) {
        final float f7 = 0.0f;
        final float f8 = 0.0f;
        this.bipedLeftArm.rotateAngleZ = -0.7f;
        this.bipedLeftArm.rotateAngleY = 1.2f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= f7 * 1.2f - f8 * 0.4f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(time * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(time * 0.067f) * 0.05f;
    }
    
    public ModelArmor reset() {
        this.cachedItem = null;
        this.renderPass = 0;
        return this;
    }
    
    protected void renderCape(final ItemStack is) {
        if (is.getItem() instanceof ItemArmorBase && ((ItemArmorBase)is.getItem()).hasCape(is)) {
            final int spriteIndex = ((ItemArmorBase)is.getItem()).getCape(is);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5f, 0.0f, 0.22f);
            GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
            if (this.renderPass <= 1) {
                Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
            }
            final float tx0 = (spriteIndex % 16 * 16 + 0) / 256.0f;
            final float tx2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
            final float ty0 = (32 + spriteIndex / 16 * 32) / 256.0f;
            final float tyc = (48 + spriteIndex / 16 * 32) / 256.0f;
            final float ty2 = (64 + spriteIndex / 16 * 32) / 256.0f;
            final float txc = (spriteIndex % 16 * 16 + 8) / 256.0f;
            final double capeSize = 0.75;
            final double x0 = 0.0;
            final double xc = 0.5;
            final double x2 = 1.0;
            final double zDesp = -0.12;
            double sneakDesp = 0.0;
            double sneakDespHands = 0.0;
            double yBase = 0.0;
            if (this.isSneak) {
                sneakDesp = 0.3;
                sneakDespHands = 0.14;
                yBase = -0.1;
            }
            final double shoulderZr = Math.sin(this.bipedRightArm.rotateAngleX) * 0.05;
            double shoulderYr = -Math.sin(this.bipedRightArm.rotateAngleX) * 0.2;
            final double shoulderZl = Math.sin(this.bipedLeftArm.rotateAngleX) * 0.05;
            double shoulderYl = -Math.sin(this.bipedLeftArm.rotateAngleX) * 0.2;
            final double handDesp = 0.5;
            final double rightHandZ = Math.max(0.0, Math.sin(this.bipedRightArm.rotateAngleX)) * 0.5;
            final double leftHandZ = Math.max(0.0, Math.sin(this.bipedLeftArm.rotateAngleX)) * 0.5;
            double handZr = rightHandZ;
            double handZl = leftHandZ;
            double handZmid = Math.max(handZr, handZl);
            double handYl = (-1.0 + Math.cos(this.bipedLeftLeg.rotateAngleX)) * 0.5 + 0.75;
            double handYr = (-1.0 + Math.cos(this.bipedRightLeg.rotateAngleX)) * 0.5 + 0.75;
            double handYmid = Math.max(handYl, handYr);
            double footZl = -0.24 + sneakDesp + Math.max(0.0, Math.sin(this.bipedLeftLeg.rotateAngleX));
            double footZr = -0.24 + sneakDesp + Math.max(0.0, Math.sin(this.bipedRightLeg.rotateAngleX));
            double footZMid = Math.max(footZr, footZl);
            double footYl = -1.0 + Math.cos(this.bipedLeftLeg.rotateAngleX) + 0.75;
            double footYr = -1.0 + Math.cos(this.bipedRightLeg.rotateAngleX) + 0.75;
            double footYMid = 0.75 + Math.max(footYr, footYl);
            handZr += -0.12 + sneakDespHands;
            handZl += -0.12 + sneakDespHands;
            handZmid += -0.12 + sneakDespHands;
            footYl += 0.75;
            footYr += 0.75;
            shoulderYl += yBase;
            shoulderYr += yBase;
            handYr += yBase;
            handYl += yBase;
            handYmid += yBase;
            footYl += yBase;
            footYr += yBase;
            footYMid += yBase;
            if (this.isSitting || this.isRiding) {
                final double sitOffset = 0.63;
                handYr += sitOffset;
                handYl += sitOffset;
                handYmid += sitOffset;
                final double sittingYOffset = 0.55;
                footZl = 0.75;
                footZr = 0.75;
                footZMid = 0.75;
                footYl += sittingYOffset;
                footYr += sittingYOffset;
                footYMid += sittingYOffset;
            }
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(4);
            tessellator.addVertexWithUV(1.0, shoulderYl, shoulderZl, (double)tx2, (double)ty0);
            tessellator.addVertexWithUV(0.0, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(1.0, shoulderYl, shoulderZl, (double)tx2, (double)ty0);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(1.0, handYl, handZl, (double)tx2, (double)tyc);
            tessellator.addVertexWithUV(0.0, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
            tessellator.addVertexWithUV(0.0, handYr, handZr, (double)tx0, (double)tyc);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(1.0, handYl, handZl, (double)tx2, (double)tyc);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.addVertexWithUV(1.0, footYl, footZl, (double)tx2, (double)ty2);
            tessellator.addVertexWithUV(1.0, handYl, handZl, (double)tx2, (double)tyc);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.addVertexWithUV(0.0, handYr, handZr, (double)tx0, (double)tyc);
            tessellator.addVertexWithUV(0.0, footYr, footZr, (double)tx0, (double)ty2);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.0, handYr, handZr, (double)tx0, (double)tyc);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.draw();
            GL11.glPopMatrix();
        }
    }
    
    protected void renderFront(final ItemStack is) {
        if (is.getItem() instanceof ItemArmorBase && ((ItemArmorBase)is.getItem()).hasApron(is)) {
            final int spriteIndex = ((ItemArmorBase)is.getItem()).getApron(is);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5f, 0.0f, -0.21f);
            GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
            if (this.renderPass <= 1) {
                Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
            }
            final float tx0 = (spriteIndex % 16 * 16 + 0) / 256.0f;
            final float tx2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
            final float ty0 = (32 + spriteIndex / 16 * 32) / 256.0f;
            final float tyc = (48 + spriteIndex / 16 * 32) / 256.0f;
            final float ty2 = (64 + spriteIndex / 16 * 32) / 256.0f;
            final float txc = (spriteIndex % 16 * 16 + 8) / 256.0f;
            final double capeSize = 0.55;
            final double x0 = 0.25;
            final double xc = 0.5;
            final double x2 = 0.75;
            final double zDesp = -0.12;
            final double sneakDesp = 0.0;
            final double sneakDespHands = 0.0;
            double yBase = 0.1;
            if (this.isSneak) {
                yBase = 0.2;
            }
            final double shoulderZr = 0.0;
            double shoulderYr = 0.0;
            final double shoulderZl = 0.0;
            double shoulderYl = 0.0;
            final double rightHandZ = 0.0;
            final double leftHandZ = 0.0;
            double handZr = -0.12;
            double handZl = -0.12;
            final double handZmid = -0.12;
            double handYl = 0.55;
            double handYr = 0.55;
            double handYmid = 0.55;
            final double footZl = -0.24 + Math.min(0.0, Math.sin(this.bipedLeftLeg.rotateAngleX)) * 0.5;
            final double footZr = -0.24 + Math.min(0.0, Math.sin(this.bipedRightLeg.rotateAngleX)) * 0.5;
            final double footZMid = Math.min(footZr, footZl);
            double footYl = Math.min(0.0, Math.sin(this.bipedLeftLeg.rotateAngleX)) * 0.5 + 1.1;
            double footYr = Math.min(0.0, Math.sin(this.bipedRightLeg.rotateAngleX)) * 0.5 + 1.1;
            double footYMid = Math.min(footYr, footYl);
            shoulderYl += yBase;
            shoulderYr += yBase;
            handYr += yBase;
            handYl += yBase;
            handYmid += yBase;
            footYl += yBase;
            footYr += yBase;
            footYMid += yBase;
            if (this.isSitting || this.isRiding) {
                final double sitOffset = -0.22;
                handZr += sitOffset;
                handZl += sitOffset;
                handYmid += sitOffset;
                footYr = (footYMid = (footYl = 0.5));
            }
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(4);
            tessellator.addVertexWithUV(0.75, shoulderYl, shoulderZl, (double)tx2, (double)ty0);
            tessellator.addVertexWithUV(0.25, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.75, shoulderYl, shoulderZl, (double)tx2, (double)ty0);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.75, handYl, handZl, (double)tx2, (double)tyc);
            tessellator.addVertexWithUV(0.25, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
            tessellator.addVertexWithUV(0.25, handYr, handZr, (double)tx0, (double)tyc);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.75, handYl, handZl, (double)tx2, (double)tyc);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.addVertexWithUV(0.75, footYl, footZl, (double)tx2, (double)ty2);
            tessellator.addVertexWithUV(0.75, handYl, handZl, (double)tx2, (double)tyc);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.addVertexWithUV(0.25, handYr, handZr, (double)tx0, (double)tyc);
            tessellator.addVertexWithUV(0.25, footYr, footZr, (double)tx0, (double)ty2);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.addVertexWithUV(0.5, handYmid, handZmid, (double)txc, (double)tyc);
            tessellator.addVertexWithUV(0.25, handYr, handZr, (double)tx0, (double)tyc);
            tessellator.addVertexWithUV(0.5, footYMid, footZMid, (double)txc, (double)ty2);
            tessellator.draw();
            GL11.glPopMatrix();
        }
    }
    
    static {
        ModelArmor.layer2 = new ResourceLocation("textures/models/armor/leather_layer_2.png");
        ModelArmor.layer1 = new ResourceLocation("textures/models/armor/leather_layer_1.png");
    }
}
