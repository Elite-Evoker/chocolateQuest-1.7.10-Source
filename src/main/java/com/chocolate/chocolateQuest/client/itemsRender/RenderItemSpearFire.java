package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class RenderItemSpearFire extends RenderItemSpear
{
    @Override
    public void doRender(final EntityLivingBase par1EntityLiving, final ItemStack itemstack) {
        if (par1EntityLiving instanceof EntityPlayer) {
            final Minecraft mc = Minecraft.getMinecraft();
            final EntityPlayer entityplayer = (EntityPlayer)par1EntityLiving;
            if (entityplayer.getItemInUse() == itemstack) {
                if (itemstack.getMaxItemUseDuration() - entityplayer.getItemInUseCount() <= ((ItemBaseSpear)itemstack.getItem()).cooldown) {
                    RenderItemBase.doRenderItem(itemstack, 16737894, true);
                }
                else {
                    RenderItemBase.doRenderItem(itemstack);
                }
            }
            else {
                RenderItemBase.doRenderItem(itemstack);
            }
        }
        else {
            RenderItemBase.doRenderItem(itemstack);
        }
    }
}
