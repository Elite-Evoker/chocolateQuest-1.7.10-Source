package com.chocolate.chocolateQuest.client.blockRender;

import net.minecraft.util.IIcon;
import net.minecraft.init.Blocks;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.client.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTableItem implements ISimpleBlockRenderingHandler
{
    public int getRenderId() {
        return ClientProxy.tableRenderID;
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        this.renderBlock();
        tessellator.draw();
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        ChocolateQuest.table.setBlockBoundsBasedOnState(world, x, y, z);
        this.renderBlock();
        return false;
    }
    
    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }
    
    public void renderBlock() {
        final Tessellator tessellator = Tessellator.instance;
        final IIcon icon = Blocks.planks.getBlockTextureFromSide(0);
        final double tx0 = icon.getMinU();
        final double tx2 = icon.getMaxU();
        final double ty0 = icon.getMinV();
        final double ty2 = icon.getMaxV();
        final double y0 = 0.9;
        final double y2 = 1.0;
        final double x0 = ChocolateQuest.table.getBlockBoundsMinX();
        final double x2 = ChocolateQuest.table.getBlockBoundsMaxX();
        final double z0 = ChocolateQuest.table.getBlockBoundsMinZ();
        final double z2 = ChocolateQuest.table.getBlockBoundsMaxZ();
        this.drawBox(tessellator, x0, x2, y0, y2, z0, z2, tx0, tx2, ty0, ty2, 1.0f);
        this.drawBox(tessellator, 0.4, 0.6, 0.0, 0.92, 0.4, 0.6, tx0, tx2, ty0, ty2, 1.0f);
    }
    
    public void drawBox(final Tessellator tessellator, final double x0, final double x1, final double y0, final double y1, final double z0, final double z1, final double tx0, final double tx1, final double ty0, final double ty1, final float b) {
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y0, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y0, z0, tx1, ty1);
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y1, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z1, tx1, ty0);
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty1);
        tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
        tessellator.addVertexWithUV(x0, y0, z1, tx1, ty0);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV(x0, y1, z1, tx0, ty0);
        tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
        tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
        tessellator.addVertexWithUV(x0, y1, z0, tx1, ty0);
    }
}
