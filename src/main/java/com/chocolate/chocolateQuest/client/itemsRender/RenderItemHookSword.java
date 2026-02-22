package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.items.swords.ItemHookSword;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.item.ItemStack;

public class RenderItemHookSword extends RenderItemBase
{
    ItemStack hook;
    
    public RenderItemHookSword() {
        this.hook = new ItemStack(ChocolateQuest.material);
    }
    
    @Override
    protected void renderItem(final ItemStack is) {
        if (((ItemHookSword)is.getItem()).getHookID(is) == 0) {
            RenderItemBase.doRenderItem(is.getItem().getIconFromDamageForRenderPass(0, 1), is, 0, false);
        }
        RenderItemBase.doRenderItem(is);
    }
}
