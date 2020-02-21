package com.cwelth.industrialessentials.items;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.inits.InitCommon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

import java.util.Random;

public class MetalScissors extends ShearsItem {
    public MetalScissors() {
        super(new Item.Properties()
                .maxDamage(512)
                .group(InitCommon.creativeTab));
    }

    @Override
    public boolean hasContainerItem() {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack toRet = itemStack.copy();
        if(toRet.attemptDamageItem(4, new Random(), null)) return ItemStack.EMPTY;
        return toRet;
    }
}
