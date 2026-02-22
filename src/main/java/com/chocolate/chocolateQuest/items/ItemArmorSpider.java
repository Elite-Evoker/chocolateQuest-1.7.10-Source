package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemArmorSpider extends ItemArmorBase
{
    int type;
    String name;
    
    public ItemArmorSpider(final int type, final String name) {
        super(ItemArmorSpider.TURTLE, type);
        this.type = type;
        this.name = name;
        this.setEpic();
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (slot == 2) {
            return "chocolatequest:textures/armor/armorSpider_2.png";
        }
        return "chocolatequest:textures/armor/armorSpider.png";
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entity) {
        final boolean isCollidedVertically = false;
        if (this.isFullSet(entity, par1ItemStack)) {
            if (entity.isCollidedHorizontally || isCollidedVertically) {
                if (!entity.isSneaking()) {
                    entity.motionY = 0.0;
                    if (entity.moveForward > 0.0f) {
                        entity.motionY = 0.2;
                    }
                }
                entity.onGround = true;
            }
            entity.fallDistance = 0.0f;
        }
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return (itemMaterial.getItem() == ChocolateQuest.material && itemMaterial.getMetadata() == 3) || super.getIsRepairable(itemToRepair, itemMaterial);
    }
}
