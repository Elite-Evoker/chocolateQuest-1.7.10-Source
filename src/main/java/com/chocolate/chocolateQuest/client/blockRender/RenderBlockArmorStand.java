package com.chocolate.chocolateQuest.client.blockRender;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import net.minecraft.tileentity.TileEntity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.client.RenderBanner;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderBlockArmorStand extends TileEntitySpecialRenderer
{
    RenderBanner render;
    EntityHumanBase entity;
    
    public RenderBlockArmorStand() {
        this.render = new RenderBanner(0.0f);
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float f) {
        final BlockArmorStandTileEntity te = (BlockArmorStandTileEntity)tileentity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5f, (float)y, (float)z + 0.5f);
        GL11.glRotatef((float)(-te.rotation), 0.0f, 1.0f, 0.0f);
        if (te.cargoItems != null) {
            if (this.entity == null) {
                (this.entity = new EntityHumanBase((World)Minecraft.getMinecraft().theWorld)).setCurrentItemOrArmor(0, null);
            }
            else if (this.entity.worldObj != Minecraft.getMinecraft().theWorld) {
                (this.entity = new EntityHumanBase((World)Minecraft.getMinecraft().theWorld)).setCurrentItemOrArmor(0, null);
            }
            for (int i = 0; i < 4; ++i) {
                this.entity.setCurrentItemOrArmor(i + 1, te.cargoItems[i]);
            }
            this.entity.setCurrentItemOrArmor(0, te.cargoItems[4]);
            RenderManager.instance.renderEntityWithPosYaw((Entity)this.entity, 0.0, 0.0, 0.0, 0.0f, 0.0f);
        }
        GL11.glPopMatrix();
    }
    
    public void drawBox(final double x0, final double x1, final double y0, final double y1, final double z0, final double z1, final double tx0, final double tx1, final double ty0, final double ty1) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
        tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y0, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z0, tx1, ty0);
        tessellator.addVertexWithUV(x0, y1, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z0, tx0, ty1);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x0, y1, z1, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z1, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y1, z0, tx0, ty1);
        tessellator.draw();
    }
}
