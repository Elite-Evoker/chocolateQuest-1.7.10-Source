package com.chocolate.chocolateQuest.items.swords;

import net.minecraft.item.EnumAction;
import net.minecraft.entity.SharedMonsterAttributes;
import java.util.Iterator;
import java.util.List;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.util.MathHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;

public class ItemBaseBroadSword extends ItemBDSword implements ITwoHandedItem
{
    public static final int BASE_DAMAGE = 6;
    public static final float ELEMENT_MODIFIER = 1.4f;
    public static final int cooldown = 50;
    String texture;
    protected AttributeModifier speedInrease;
    protected AttributeModifier speedDecreaseSwing;
    
    public ItemBaseBroadSword(final Item.ToolMaterial mat, final String texture) {
        this(mat, texture, 6.0f);
        this.texture = texture;
    }
    
    public ItemBaseBroadSword(final Item.ToolMaterial mat, final String texture, final float baseDamage) {
        super(mat, baseDamage);
        this.texture = texture;
        this.elementModifier = 1.4f;
        this.speedInrease = new AttributeModifier(ItemBaseBroadSword.itemModifierUUID, "Weapon modifier", 0.24, 0).setSaved(false);
    }
    
    public ItemBaseBroadSword(final Item.ToolMaterial mat, final String texture, final float baseDamage, final float elementModifier) {
        this(mat, texture, baseDamage);
        this.elementModifier = elementModifier;
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = super.getItemAttributeModifiers();
        return multimap;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return super.onItemRightClick(itemstack, world, entityPlayer);
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer, int useTime) {
        useTime = this.getMaxItemUseDuration(itemstack) - useTime;
        useTime = Math.min(useTime + 1, 60);
        this.cachedDamage = this.weaponAttackDamage * useTime / 30.0f + 1.0f;
        entityPlayer.swingItem();
        boolean hitEntity = false;
        if (!world.isRemote) {
            final int dist = 1;
            final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)entityPlayer, entityPlayer.boundingBox.addCoord(entityPlayer.getLookVec().xCoord * dist, entityPlayer.getLookVec().yCoord * dist, entityPlayer.getLookVec().zCoord * dist).expand(2.0, 2.0, 2.0));
            for (final Entity e : list) {
                if (e instanceof EntityLivingBase) {
                    final double rotDiff = Math.abs(BDHelper.getAngleBetweenEntities((Entity)entityPlayer, e));
                    double rot = rotDiff - Math.abs(MathHelper.wrapAngleTo180_double((double)entityPlayer.rotationYaw));
                    rot = Math.abs(rot);
                    if (rot >= 40.0) {
                        continue;
                    }
                    this.attackEntityWithItem(entityPlayer, e);
                    itemstack.damageItem(1, (EntityLivingBase)entityPlayer);
                    hitEntity = true;
                }
            }
        }
        this.cachedDamage = 0.0f;
        if (world.isRemote) {
            float x = (float)(-Math.sin(Math.toRadians(entityPlayer.rotationYaw)));
            float z = (float)Math.cos(Math.toRadians(entityPlayer.rotationYaw));
            final float y = (float)(-Math.sin(Math.toRadians(entityPlayer.rotationPitch)));
            x *= 1.0f - Math.abs(y);
            z *= 1.0f - Math.abs(y);
            world.spawnParticle("largeexplode", entityPlayer.posX + x - 0.5, entityPlayer.posY + y, entityPlayer.posZ + z + 0.0, 0.0, 0.0, 0.0);
        }
        final int j = this.getMaxItemUseDuration(itemstack) - useTime;
        if (j > 50) {
            this.doSpecialSkill(itemstack, world, entityPlayer);
        }
        final int jumpLevel = Awakements.getEnchantLevel(itemstack, Awakements.backDodge);
        if (entityPlayer.onGround && (useTime > 30 || hitEntity)) {
            entityPlayer.onGround = false;
            float rot2 = entityPlayer.rotationYawHead * 3.1416f / 180.0f;
            double mx = -Math.sin(rot2);
            double mz = Math.cos(rot2);
            final double jumpSpeed = 1 + jumpLevel;
            final double backSpeed = -Math.abs(entityPlayer.moveForward) * jumpSpeed;
            entityPlayer.motionX += mx * backSpeed;
            entityPlayer.motionZ += mz * backSpeed;
            rot2 -= (float)1.57;
            mx = -Math.sin(rot2);
            mz = Math.cos(rot2);
            entityPlayer.motionX += mx * entityPlayer.moveStrafing * jumpSpeed;
            entityPlayer.motionZ += mz * entityPlayer.moveStrafing * jumpSpeed;
            entityPlayer.motionY = 0.3;
        }
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int par4, final boolean par5) {
        final EntityLivingBase e = (EntityLivingBase)entity;
        ItemStack entityWeapon = null;
        if (e instanceof EntityPlayer) {
            entityWeapon = ((EntityPlayer)entity).getHeldItem();
        }
        else if (e instanceof EntityLivingBase) {
            entityWeapon = ((EntityLivingBase)entity).getEquipmentInSlot(0);
        }
        if (entityWeapon == itemstack) {
            final EntityLivingBase entityLivingBase = e;
            entityLivingBase.jumpMovementFactor -= (float)0.01;
            if (e.isSwingInProgress && e.onGround) {
                e.motionX *= 0.4;
                e.motionZ *= 0.4;
            }
        }
        Label_0343: {
            if (entity instanceof EntityPlayer) {
                final int berserk = Awakements.getEnchantLevel(itemstack, Awakements.berserk);
                if (berserk > 0) {
                    final EntityPlayer ep = (EntityPlayer)entity;
                    final double duration = Math.min(ep.getItemInUseDuration(), 30) * berserk / 4;
                    final AttributeModifier speedInrease = new AttributeModifier(ItemBaseBroadSword.itemModifierUUID, "Weapon modifier", duration / 100.0, 0).setSaved(false);
                    final AttributeModifier knockBackResist = new AttributeModifier(ItemBaseBroadSword.itemModifierUUID, "Weapon modifier", 0.25 * berserk, 0).setSaved(false);
                    if (ep.getCurrentEquippedItem() == itemstack) {
                        ep.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(speedInrease);
                        ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(knockBackResist);
                        if (ep.isUsingItem()) {
                            ep.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(speedInrease);
                            ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(knockBackResist);
                        }
                    }
                    else {
                        if (ep.getCurrentEquippedItem() != null) {
                            if (ep.getCurrentEquippedItem().getItem() instanceof ItemBaseBroadSword) {
                                break Label_0343;
                            }
                        }
                        ep.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(speedInrease);
                        ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(knockBackResist);
                    }
                }
            }
        }
        super.onUpdate(itemstack, world, entity, par4, par5);
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    public void doSpecialSkill(final ItemStack itemstack, final World world, final EntityPlayer entityPlayer) {
    }
}
