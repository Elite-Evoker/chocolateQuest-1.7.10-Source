package com.chocolate.chocolateQuest.client.model;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelArmorTurtle extends ModelArmor
{
    public ModelRenderer turtleShell;
    public ModelRenderer turtleShellPart;
    
    public ModelArmorTurtle(final int type) {
        this(0.0f, type);
    }
    
    public ModelArmorTurtle(float f, final int type) {
        super(f, type);
        this.type = type;
        f = 1.5f;
        (this.turtleShell = new ModelRenderer((ModelBase)this, 32, 0)).addBox(0.0f, 6.0f, 2.0f, 10, 12, 3, f);
        this.turtleShell.setRotationPoint(-5.0f, -6.0f, 0.0f);
        (this.turtleShellPart = new ModelRenderer((ModelBase)this, 42, 4)).addBox(0.0f, 6.0f, 2.0f, 6, 8, 1, f);
        this.turtleShellPart.setRotationPoint(-3.0f, -4.0f, 3.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.type == 0) {
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.turtleShell.render(par7);
            this.turtleShellPart.render(par7);
            if (par1Entity instanceof EntityLivingBase) {
                final ItemStack cachedItem = ((EntityLivingBase)par1Entity).getEquipmentInSlot(3);
                this.renderFront(cachedItem);
            }
        }
        else {
            this.bipedHead.render(par7);
        }
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
