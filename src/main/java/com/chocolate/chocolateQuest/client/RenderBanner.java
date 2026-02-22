package com.chocolate.chocolateQuest.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import net.minecraft.item.ItemStack;
import com.chocolate.chocolateQuest.ChocolateQuest;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.texture.TextureManager;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class RenderBanner extends Render
{
    private float field_40269_a;
    
    public RenderBanner(final float f) {
        this.field_40269_a = f;
    }
    
    public void doRender(final Entity entity, final double x, final double y, final double z, final float f, final float f1) {
        final EntityCursor e = (EntityCursor)entity;
        if (e.type == 0) {
            int flag = 0;
            if (e.item != null) {
                flag = e.item.getMetadata() % 16;
            }
            this.renderBanner(x, y, z, entity.rotationYaw, flag, this.renderManager.renderEngine);
        }
        else {
            float width = 1.0f;
            if (e.followEntity != null) {
                width = e.followEntity.width;
            }
            this.renderSelector(x, y, z, width, this.renderManager.renderEngine, e.color);
        }
    }
    
    public void renderBanner(final double x, final double y, final double z, final float rotation, final int spriteIndex, final TextureManager renderEngine) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(-rotation, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, 0.0f, 0.0f);
        final float f2 = 1.0f;
        GL11.glScalef(f2, f2 * 2.0f, f2);
        final ItemStack is = new ItemStack(ChocolateQuest.banner, 1, 0);
        RenderItemBase.doRenderItem(is.getIconIndex(), is, 0, false);
        renderEngine.bindTexture(BDHelper.getItemTexture());
        GL11.glDisable(2884);
        final float i1 = (spriteIndex % 16 * 16 + 0) / 256.0f;
        final float i2 = (spriteIndex % 16 * 16 + 16) / 256.0f;
        final float i3 = 0.125f;
        final float i4 = 0.25f;
        final float f3 = 1.0f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        final double desp = 0.05;
        final double timeDiff = 1500.0;
        final double d1 = desp + Math.cos(System.currentTimeMillis() / timeDiff) * desp;
        final double d2 = desp + Math.cos((System.currentTimeMillis() + timeDiff) / timeDiff) * desp;
        tessellator.addVertexWithUV(0.0, 0.10000000149011612, d1, (double)i1, (double)i4);
        tessellator.addVertexWithUV((double)f3, 0.10000000149011612, d2, (double)i2, (double)i4);
        tessellator.addVertexWithUV((double)f3, 1.0, 0.001, (double)i2, (double)i3);
        tessellator.addVertexWithUV(0.0, 1.0, 0.001, (double)i1, (double)i3);
        tessellator.draw();
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    public void renderSelector(final double x, final double y, final double z, final float width, final TextureManager renderEngine, final int color) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)(float)x, (double)(float)y, (double)(float)z);
        GL11.glColor4f(BDHelper.getColorRed(color), BDHelper.getColorGreen(color), BDHelper.getColorBlue(color), 1.0f);
        renderEngine.bindTexture(BDHelper.getItemTexture());
        GL11.glDisable(2884);
        GL11.glDisable(2896);
        GL11.glScalef(width, width, width);
        final float i1 = 0.0f;
        final float i2 = 0.125f;
        final float i3 = 0.6875f;
        final float i4 = i3 + 0.125f;
        float f5 = 1.0f;
        final Tessellator tessellator = Tessellator.instance;
        f5 = 1.0f;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f5), 0.0, (double)(-f5), (double)i1, (double)i4);
        tessellator.addVertexWithUV((double)f5, 0.0, (double)(-f5), (double)i2, (double)i4);
        tessellator.addVertexWithUV((double)f5, 0.0, (double)f5, (double)i2, (double)i3);
        tessellator.addVertexWithUV((double)(-f5), 0.0, (double)f5, (double)i1, (double)i3);
        tessellator.draw();
        GL11.glEnable(2884);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
}
