package com.chocolate.chocolateQuest.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.EntityLivingBase;

public class ItemArmorHelmetScouter extends ItemArmorBase
{
    public static EntityLivingBase target;
    public static int targetTimer;
    final int reachDistance = 40;
    
    public ItemArmorHelmetScouter() {
        super(ItemArmor.ArmorMaterial.CLOTH, 0);
        this.setMaxDurability(650);
    }
    
    @Override
    public void onUpdateEquiped(final ItemStack par1ItemStack, final World world, final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final MovingObjectPosition lookingBlock = HelperPlayer.getMovingObjectPositionFromPlayer((EntityLivingBase)ep, world, 40.0);
            float bright = 0.0f;
            if (lookingBlock != null) {
                int i = lookingBlock.blockX;
                int j = lookingBlock.blockY;
                int k = lookingBlock.blockZ;
                switch (lookingBlock.sideHit) {
                    case 1: {
                        ++j;
                        break;
                    }
                    case 0: {
                        --j;
                        break;
                    }
                    case 2: {
                        --k;
                        break;
                    }
                    case 3: {
                        ++k;
                        break;
                    }
                    case 5: {
                        ++i;
                        break;
                    }
                    case 4: {
                        --i;
                        break;
                    }
                }
                bright = world.getLightBrightness(i, j, k);
            }
            else {
                bright = world.getLightBrightness(MathHelper.floor_double(ep.posX), MathHelper.floor_double(ep.posY), MathHelper.floor_double(ep.posZ));
            }
            if (bright < 0.3) {
                ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 0));
            }
            if (world.isRemote) {
                final Entity mop = HelperPlayer.getTarget((EntityLivingBase)ep, world, 40.0);
                if (mop != null && mop instanceof EntityLivingBase) {
                    final EntityLivingBase el = ItemArmorHelmetScouter.target = (EntityLivingBase)mop;
                    ItemArmorHelmetScouter.targetTimer = 80;
                }
            }
        }
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return super.getItemStackDisplayName(itemstack);
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        return "chocolatequest:textures/armor/cloud_1.png";
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.rare;
    }
}
