package com.cwelth.industrialessentials.tileentities;

import com.cwelth.industrialessentials.blocks.Anvil;
import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.inits.InitCommon;
import com.cwelth.industrialessentials.recipes.AnvilRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class AnvilTE extends TileEntity {
    private ItemStack containedItem = ItemStack.EMPTY;
    private int currentHits = 0;
    private ItemStack resultStack = ItemStack.EMPTY;
    private int targetHits = 0;

    public AnvilTE() {
        super(IEContent.ANVIL_TE.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        if(compound.contains("containedItem"))containedItem = ItemStack.read(compound.getCompound("containedItem"));
        if(compound.contains("currentHits"))currentHits = compound.getInt("currentHits");
        if(compound.contains("resultStack"))resultStack = ItemStack.read(compound.getCompound("resultStack"));
        if(compound.contains("targetHits"))targetHits = compound.getInt("targetHits");
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("containedItem", containedItem.serializeNBT());
        compound.putInt("currentHits", currentHits);
        compound.put("resultStack", resultStack.serializeNBT());
        compound.putInt("targetHits", targetHits);
        return super.write(compound);
    }

    public boolean isHammerPresent()
    {
        return !containedItem.isEmpty() && ( containedItem.getItem() == IEContent.HAMMER_PART.get() || containedItem.getItem() == IEContent.HAMMER_DIAMOND_PART.get() );
    }

    public ItemStack getContainedItem()
    {
        return containedItem;
    }

    public String getProgress()
    {
        if(targetHits == 0)return " no valid recipe found.";
        int maxLength = 20;
        int progress = currentHits * 100 / targetHits;
        int curLength = progress * 20 / 100;
        String retStr = "[";
        for(int i = 0; i < maxLength; i++)
        {
            if(i <= curLength)retStr += "#";
            else retStr += " ";
        }
        retStr += "] " + currentHits + "/" + targetHits;
        return retStr;
    }



    public ItemStack interact(ItemStack itemStack)
    {
        if(!containedItem.isEmpty())
        {
            if(itemStack.isEmpty())
            {
                ItemStack retVal = containedItem.copy();
                if(isHammerPresent())
                    getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).with(Anvil.HAMMER_PRESENT, false), 2);
                containedItem = ItemStack.EMPTY;
                currentHits = targetHits = 0;
                resultStack = ItemStack.EMPTY;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                return retVal;
            } else {
                if(itemStack.getItem() == containedItem.getItem())
                {
                    if(itemStack.getCount() + containedItem.getCount() <= itemStack.getMaxStackSize())
                    {
                        itemStack.grow(containedItem.getCount());
                        containedItem = ItemStack.EMPTY;
                        currentHits = targetHits = 0;
                        resultStack = ItemStack.EMPTY;
                        markDirty();
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                        return itemStack;
                    }
                }
                return itemStack;
            }
        } else
        {
            if(itemStack.isEmpty())
                return null;
            else
            {
                containedItem = itemStack.copy();
                containedItem.setCount(1);
                markDirty();
                if(isHammerPresent())
                    getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).with(Anvil.HAMMER_PRESENT, true), 2);
                itemStack.shrink(1);
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                return itemStack;
            }
        }
    }

    public void bash()
    {
        if(!containedItem.isEmpty())
        {
            if(!isHammerPresent())
            {
                //Process recipe
                if(currentHits == 0)
                {
                    AnvilRecipe recipe = InitCommon.anvilRecipes.getMatchedRecipe(containedItem);
                    if(recipe != null)
                    {
                        targetHits = recipe.hits;
                        resultStack = recipe.output.copy();
                        markDirty();
                    }
                }
                if(!resultStack.isEmpty())
                {
                    currentHits++;
                    if(currentHits > targetHits)
                    {
                        containedItem = resultStack.copy();
                        resultStack = ItemStack.EMPTY;
                        currentHits = targetHits = 0;
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                    }
                    markDirty();
                }
            }
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        nbtTag = write(nbtTag);
        return new SUpdateTileEntityPacket(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }


}
