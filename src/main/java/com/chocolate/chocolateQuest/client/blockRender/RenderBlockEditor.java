package com.chocolate.chocolateQuest.client.blockRender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderBlockEditor extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float f) {
        final BlockEditorTileEntity eb = (BlockEditorTileEntity)tileentity;
        final Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(2884);
        this.bindTexture(TextureMap.locationBlocksTexture);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        int line = (int)(Minecraft.getMinecraft().thePlayer.getDistance((double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord) * 0.8);
        line = 3;
        GL11.glLineWidth((float)line);
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16733525);
        tessellator.addVertex(x + 1.0, y, z + 1.0);
        tessellator.addVertex(x + eb.red + 1.0, y, z + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16777184);
        tessellator.addVertex(x + 1.0, y, z + 1.0);
        tessellator.addVertex(x + 1.0, y, z + 1.0 + eb.yellow);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16755370);
        tessellator.addVertex(x + 1.0, y, z + 1.0);
        tessellator.addVertex(x + 1.0, y + eb.height, z + 1.0);
        GL11.glColor3f(1.0f, 1.0f, 0.8f);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16777184);
        tessellator.addVertex(x + eb.red + 1.0, y + eb.height, z + eb.yellow + 1.0);
        tessellator.addVertex(x + 1.0, y + eb.height, z + eb.yellow + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16733525);
        tessellator.addVertex(x + eb.red + 1.0, y + eb.height, z + eb.yellow + 1.0);
        tessellator.addVertex(x + eb.red + 1.0, y + eb.height, z + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16755370);
        tessellator.addVertex(x + eb.red + 1.0, y + eb.height, z + eb.yellow + 1.0);
        tessellator.addVertex(x + eb.red + 1.0, y, z + eb.yellow + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16733525);
        tessellator.addVertex(x + 1.0, y + eb.height, z + 1.0);
        tessellator.addVertex(x + eb.red + 1.0, y + eb.height, z + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16777184);
        tessellator.addVertex(x + 1.0, y + eb.height, z + 1.0);
        tessellator.addVertex(x + 1.0, y + eb.height, z + 1.0 + eb.yellow);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16733525);
        tessellator.addVertex(x + eb.red + 1.0, y, z + 1.0);
        tessellator.addVertex(x + eb.red + 1.0, y + eb.height, z + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16777184);
        tessellator.addVertex(x + 1.0, y, z + 1.0 + eb.yellow);
        tessellator.addVertex(x + 1.0, y + eb.height, z + 1.0 + eb.yellow);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16777184);
        tessellator.addVertex(x + eb.red + 1.0, y, z + eb.yellow + 1.0);
        tessellator.addVertex(x + 1.0, y, z + eb.yellow + 1.0);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorOpaque_I(16733525);
        tessellator.addVertex(x + eb.red + 1.0, y, z + eb.yellow + 1.0 + 1.0);
        tessellator.addVertex(x + eb.red + 1.0, y, z + 1.0);
        tessellator.draw();
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
    
    public void renderPart() {
    }
}
