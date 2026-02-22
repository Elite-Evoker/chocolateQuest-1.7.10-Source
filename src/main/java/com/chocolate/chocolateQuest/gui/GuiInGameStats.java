package com.chocolate.chocolateQuest.gui;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.magic.IElementWeak;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetScouter;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.util.DamageSource;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.Gui;

public class GuiInGameStats extends Gui
{
    public static final ResourceLocation potionEffectIcons;
    private Minecraft mc;
    private int xPos;
    private int yPos;
    private static final int ICON_SIZE = 16;
    private static final int ICON_SPACING = 18;
    private static final int ICONS_PER_ROW = 16;
    private static final int COLOR = 16777215;
    DamageSource physic;
    DamageSource fire;
    DamageSource blast;
    DamageSource magic;
    DamageSource projectile;
    
    public GuiInGameStats(final Minecraft mc) {
        this.physic = new DamageSource("");
        this.fire = new DamageSource("").setFireDamage();
        this.blast = new DamageSource("").setExplosion();
        this.magic = new DamageSource("").setMagicDamage();
        this.projectile = new DamageSource("").setProjectile();
        this.mc = mc;
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(final RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        if (player.getCurrentEquippedItem() != null) {
            final ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack.getItem() instanceof ILoadableGun) {
                final ItemStack[] cargo = InventoryBag.getCargo(itemstack);
                int despX = 0;
                for (int i = 0; i < cargo.length; ++i) {
                    if (cargo[i] != null) {
                        final ItemStack cargoItem = cargo[i];
                        GL11.glPushMatrix();
                        final float scale = 16.0f;
                        GL11.glTranslatef(scale + despX, scale, 0.0f);
                        GL11.glScalef(-scale, -scale, 0.0f);
                        final ResourceLocation resourcelocation = this.mc.renderEngine.getResourceLocation(cargoItem.getItemSpriteNumber());
                        for (int renderPasses = cargoItem.getItem().getRenderPasses(cargoItem.getMetadata()), f = 0; f < renderPasses; ++f) {
                            final int color = cargoItem.getItem().getColorFromItemStack(cargoItem, f);
                            GL11.glColor4f(BDHelper.getColorRed(color), BDHelper.getColorGreen(color), BDHelper.getColorBlue(color), 1.0f);
                            final IIcon icon = cargoItem.getItem().getIconFromDamageForRenderPass(cargoItem.getMetadata(), f);
                            this.mc.renderEngine.bindTexture(resourcelocation);
                            ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
                        }
                        GL11.glPopMatrix();
                        if (cargoItem.stackSize > 1) {
                            this.drawString(this.mc.fontRendererObj, cargoItem.stackSize + "", despX + 8, 8, 16777215);
                        }
                        despX += (int)scale;
                    }
                }
                GL11.glTranslatef(0.0f, 16.0f, 0.0f);
            }
        }
        if (ItemArmorHelmetScouter.target != null) {
            this.drawScouterInfo();
        }
        GL11.glPopMatrix();
    }
    
    public int drawScouterInfo() {
        GL11.glPushMatrix();
        final float scale = 1.0f;
        GL11.glScalef(scale, scale, scale);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        this.xPos = 2;
        this.yPos = 2;
        this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
        final int iconBar = 25;
        for (int i = 0; i < 4; ++i) {
            this.drawTexturedModalRect(this.xPos, this.yPos + 16 * i, iconBar % 16 * 16, iconBar / 16 * 16, 64, 16);
        }
        for (int i = 0; i < 3; ++i) {
            this.drawIcon(iconBar + 4 + i, this.xPos, this.yPos + 16 * (i + 1));
        }
        final int TEXT_OFFSET = 3;
        final int xResist = this.xPos + 16;
        final int yResist = this.yPos + 64;
        final int icon = 9;
        this.drawIcon(icon + 1, this.xPos, yResist);
        this.drawIcon(icon + 2, this.xPos + 32, yResist);
        this.drawIcon(icon + 3, this.xPos, yResist + 16);
        this.drawIcon(icon + 4, this.xPos + 32, yResist + 16);
        this.drawIcon(icon + 5, this.xPos, yResist + 32);
        this.drawIcon(icon + 6, this.xPos + 32, yResist + 32);
        this.drawIcon(icon, xResist, yResist);
        this.drawIcon(icon, xResist + 32, yResist);
        this.drawIcon(icon, xResist, yResist + 16);
        this.drawIcon(icon, xResist + 32, yResist + 16);
        this.drawIcon(icon, xResist, yResist + 32);
        this.drawIcon(icon, xResist + 32, yResist + 32);
        final EntityLivingBase el = ItemArmorHelmetScouter.target;
        final String s = el.getCommandSenderName();
        this.drawStat(s);
        this.drawStat("   " + this.toString(el.getHealth()) + "/" + this.toString(el.getMaxHealth()));
        double damage = 0.0;
        int baseDamage = 0;
        final IAttributeInstance attribute = el.getEntityAttribute(SharedMonsterAttributes.attackDamage);
        if (attribute != null) {
            damage = attribute.getAttributeValue();
            baseDamage = (int)attribute.getBaseValue();
            final ItemStack weapon = el.getEquipmentInSlot(0);
            if (weapon != null) {
                damage += BDHelper.getWeaponDamage(weapon);
            }
        }
        this.drawStat("   " + damage + " (" + baseDamage + ")");
        this.drawStat("     " + el.getTotalArmorValue());
        this.yPos += 16;
        final int protection = this.getEnchantmentProtection(Enchantment.protection, el, this.physic);
        int fireProtection = this.getEnchantmentProtection(Enchantment.fireProtection, el, this.fire);
        int projectileProtection = this.getEnchantmentProtection(Enchantment.projectileProtection, el, this.projectile);
        int blastProtection = this.getEnchantmentProtection(Enchantment.blastProtection, el, this.blast);
        int magicProtection = this.getEnchantmentProtection(ChocolateQuest.enchantmentMagicDefense, el, this.magic);
        int physicProtection = 0;
        if (el instanceof IElementWeak) {
            projectileProtection += ((IElementWeak)el).getProjectileDefense();
            fireProtection += ((IElementWeak)el).getFireDefense();
            blastProtection += ((IElementWeak)el).getBlastDefense();
            magicProtection += ((IElementWeak)el).getMagicDefense();
            physicProtection += ((IElementWeak)el).getPhysicDefense();
        }
        String text = protection + "";
        this.drawString(this.mc.fontRendererObj, text, xResist + TEXT_OFFSET, yResist + TEXT_OFFSET, 16777215);
        text = projectileProtection + "";
        this.drawString(this.mc.fontRendererObj, text, xResist + 32 + TEXT_OFFSET, yResist + TEXT_OFFSET, 16777215);
        text = physicProtection + "";
        this.drawString(this.mc.fontRendererObj, text, xResist + TEXT_OFFSET, yResist + 16 + TEXT_OFFSET, 16777215);
        text = fireProtection + "";
        this.drawString(this.mc.fontRendererObj, text, xResist + 32 + TEXT_OFFSET, yResist + 16 + TEXT_OFFSET, 16777215);
        text = blastProtection + "";
        this.drawString(this.mc.fontRendererObj, text, xResist + TEXT_OFFSET, yResist + 32 + TEXT_OFFSET, 16777215);
        text = magicProtection + "";
        this.drawString(this.mc.fontRendererObj, text, xResist + 32 + TEXT_OFFSET, yResist + 32 + TEXT_OFFSET, 16777215);
        --ItemArmorHelmetScouter.targetTimer;
        if (ItemArmorHelmetScouter.targetTimer == 0) {
            ItemArmorHelmetScouter.target = null;
        }
        GL11.glPopMatrix();
        return 0;
    }
    
    public int getEnchantmentProtection(final Enchantment enchantment, final EntityLivingBase el, final DamageSource ds) {
        int f = 0;
        for (int i = 1; i <= 4; ++i) {
            final ItemStack is = el.getEquipmentInSlot(i);
            if (is != null) {
                final int lvl = EnchantmentHelper.getEnchantmentLevel(enchantment.effectId, is);
                if (lvl > 0) {
                    f += enchantment.calcModifierDamage(lvl, ds);
                }
            }
        }
        return f;
    }
    
    public void drawIcon(final int icon, final int xPos, final int yPos) {
        this.drawTexturedModalRect(xPos, yPos, icon % 16 * 16, icon / 16 * 16, 16, 16);
    }
    
    public void drawStat(String text) {
        if (text.length() > 14) {
            text = text.substring(0, 12);
        }
        this.drawString(this.mc.fontRendererObj, text, this.xPos + 3, this.yPos + 3, 16777215);
        this.yPos += 16;
    }
    
    public String toString(final double d) {
        String value = Double.toString(d);
        int index = value.indexOf(".");
        if (index < 3) {
            index += 2;
        }
        value = value.substring(0, index);
        return value;
    }
    
    static {
        potionEffectIcons = new ResourceLocation("gui/container/inventory.png");
    }
}
