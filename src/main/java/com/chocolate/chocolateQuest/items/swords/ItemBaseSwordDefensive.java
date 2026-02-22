package com.chocolate.chocolateQuest.items.swords;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.projectile.EntityArrow;
import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;
import net.minecraft.item.EnumRarity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ItemBaseSwordDefensive extends ItemBDSword
{
    String texture;
    protected final AttributeModifier knockBackShield;
    int shiedID;
    
    public ItemBaseSwordDefensive() {
        this(Item.ToolMaterial.IRON, "ChocolateQuest:swordDefensive");
    }
    
    public ItemBaseSwordDefensive(final Item.ToolMaterial mat, final String texture) {
        super(mat);
        this.texture = "ChocolateQuest:swordDefensive";
        this.knockBackShield = new AttributeModifier(ItemBaseSwordDefensive.itemModifierUUID, "Weapon modifier", 1.0, 0).setSaved(false);
        this.shiedID = 0;
        this.texture = texture;
    }
    
    public ItemBaseSwordDefensive(final Item.ToolMaterial mat, final String texture, final int baseDamage, final float elementModifier) {
        super(mat, (float)baseDamage);
        this.texture = "ChocolateQuest:swordDefensive";
        this.knockBackShield = new AttributeModifier(ItemBaseSwordDefensive.itemModifierUUID, "Weapon modifier", 1.0, 0).setSaved(false);
        this.shiedID = 0;
        this.texture = texture;
        this.elementModifier = elementModifier;
    }
    
    public ItemBaseSwordDefensive setShieldId(final int id) {
        this.shiedID = id;
        return this;
    }
    
    public int getShieldID(final ItemStack is) {
        return this.shiedID;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
    }
    
    public int getMaxDurability() {
        return 2048;
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int par4, final boolean par5) {
        Label_0109: {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer ep = (EntityPlayer)entity;
                if (ep.getCurrentEquippedItem() == itemStack) {
                    ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(this.knockBackShield);
                    if (ep.isUsingItem()) {
                        ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(this.knockBackShield);
                    }
                }
                else {
                    if (ep.getCurrentEquippedItem() != null) {
                        if (ep.getCurrentEquippedItem().getItem() instanceof ItemBaseSwordDefensive) {
                            break Label_0109;
                        }
                    }
                    ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(this.knockBackShield);
                }
            }
        }
        super.onUpdate(itemStack, world, entity, par4, par5);
    }
    
    public boolean getIsRepairable(final ItemStack itemToRepair, final ItemStack itemMaterial) {
        return super.getIsRepairable(itemToRepair, itemMaterial);
    }
    
    public EnumRarity getRarity(final ItemStack itemstack) {
        return EnumRarity.epic;
    }
    
    public static void projectileDeflection(final Entity source, final double dist) {
        final AxisAlignedBB aabb = source.boundingBox.expand(dist - 1.0, 3.0, dist - 1.0);
        final List<Entity> list = source.worldObj.getEntitiesWithinAABB((Class)Entity.class, aabb);
        for (final Entity e : list) {
            if (e != source) {
                final Vec3 d = Vec3.createVectorHelper(source.posX - e.posX, source.posY - e.posY - 1.0, source.posZ - e.posZ);
                final double distToEntity = Math.max(40.0, (d.xCoord * d.xCoord + d.zCoord * d.zCoord) * 10.0);
                d.normalize();
                final double x = d.xCoord / distToEntity;
                final double y = d.yCoord / Math.max(15.0, d.yCoord * d.yCoord * 10.0) / distToEntity;
                final double z = d.zCoord / distToEntity;
                final double speedModifier = 1.0;
                double dist3D = source.getDistanceSqToEntity(e) - 2.0;
                if (dist3D >= 10.0 || e instanceof EntityItem || e instanceof EntityLivingBase || e == source.ridingEntity || e == source.riddenByEntity) {
                    continue;
                }
                if (e instanceof EntityFireball) {
                    e.addVelocity(-d.xCoord / dist3D, -d.yCoord / dist3D, -d.zCoord / dist3D);
                }
                else if (e instanceof IThrowableEntity) {
                    if (((IThrowableEntity)e).getThrower() != source) {
                        e.addVelocity(-d.xCoord / dist3D, -d.yCoord / dist3D, -d.zCoord / dist3D);
                    }
                }
                else if (e instanceof EntityArrow) {
                    dist3D = 4.0;
                    e.addVelocity(-d.xCoord / dist3D, -d.yCoord / dist3D, -d.zCoord / dist3D);
                }
                else {
                    e.addVelocity(-d.xCoord / dist3D * 4.0, -d.yCoord / dist3D * 4.0, -d.zCoord / dist3D * 4.0);
                }
                if (!source.worldObj.isRemote) {
                    continue;
                }
                source.worldObj.spawnParticle("enchantmenttable", e.posX + ItemBaseSwordDefensive.itemRand.nextFloat() - 0.5, e.posY + ItemBaseSwordDefensive.itemRand.nextFloat() - 0.5, e.posZ + ItemBaseSwordDefensive.itemRand.nextFloat() - 0.5, d.xCoord, d.yCoord, d.zCoord);
            }
        }
    }
}
