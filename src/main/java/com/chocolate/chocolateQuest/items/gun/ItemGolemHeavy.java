package com.chocolate.chocolateQuest.items.gun;

import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemGolemHeavy extends ItemGolem
{
    public ItemGolemHeavy() {
        super("mechaElite");
    }
    
    @Override
    public EntityGolemMecha getGolem(final World world, final EntityPlayer entityPlayer) {
        return new EntityGolemMechaHeavy(world, (EntityLivingBase)entityPlayer);
    }
}
