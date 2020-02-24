package com.cwelth.industrialessentials.tileentities;

import com.cwelth.industrialessentials.blocks.Anvil;
import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.inits.InitCommon;
import com.cwelth.industrialessentials.networking.AnvilSync;
import com.cwelth.industrialessentials.networking.NetworkingSetup;
import com.cwelth.industrialessentials.recipes.AnvilRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class AnvilTE extends TileEntity {
    private ItemStack containedItem = ItemStack.EMPTY;
    private int currentHits = 0;
    private ItemStack resultStack = ItemStack.EMPTY;
    private String recipe = "";
    public boolean clientOnLookAt = false;

    public AnvilTE() {
        super(IEContent.ANVIL_TE.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        if(compound.contains("containedItem"))containedItem = ItemStack.read(compound.getCompound("containedItem"));
        if(compound.contains("currentHits"))currentHits = compound.getInt("currentHits");
        if(compound.contains("resultStack"))resultStack = ItemStack.read(compound.getCompound("resultStack"));
        if(compound.contains("recipe"))recipe = compound.getString("recipe");
        if(compound.contains("clientOnLookAt"))clientOnLookAt = compound.getBoolean("clientOnLookAt");
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("containedItem", containedItem.serializeNBT());
        compound.putInt("currentHits", currentHits);
        compound.put("resultStack", resultStack.serializeNBT());
        compound.putString("recipe", recipe);
        compound.putBoolean("clientOnLookAt", clientOnLookAt);
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

    public void setCurrentHits(int currentHits)
    {
        this.currentHits = currentHits;
        markDirty();
    }

    public boolean hasValidRecipe()
    {
        return !resultStack.isEmpty();
    }

    public String getRecipe()
    {
        return recipe;
    }

    public int getCurrentHits()
    {
        return currentHits;
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
                currentHits = 0;
                recipe = "";
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
                        currentHits = 0;
                        recipe = "";
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
                return ItemStack.EMPTY;
            else
            {
                containedItem = itemStack.copy();
                containedItem.setCount(1);
                markDirty();
                if(isHammerPresent())
                    getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).with(Anvil.HAMMER_PRESENT, true), 2);
                else
                {
                    AnvilRecipe recipe = InitCommon.anvilRecipes.getMatchedRecipe(containedItem);
                    if(recipe != null)
                    {
                        this.recipe = recipe.hits;
                        currentHits = 0;
                        resultStack = recipe.output.copy();
                        markDirty();
                    }
                }
                itemStack.shrink(1);
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                return itemStack;
            }
        }
    }

    public boolean bash(Hand hand)
    {
        if(!containedItem.isEmpty())
        {
            if(!isHammerPresent())
            {
                //Process recipe
                if(!resultStack.isEmpty())
                {
                    if(recipe.charAt(currentHits) == 'R' && hand == Hand.OFF_HAND || recipe.charAt(currentHits) == 'L' && hand == Hand.MAIN_HAND) {
                        currentHits++;
                        if(hand == Hand.OFF_HAND)
                            world.playSound(null, getPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat()*.1F + 0.6F);
                        else
                            world.playSound(null, getPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat()*.1F + 0.8F);
                    }
                    else {
                        currentHits = 0;
                        world.playSound(null, getPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 0.6F);
                        markDirty();
                        NetworkingSetup.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(getPos().getX(), getPos().getY(), getPos().getZ(), 16D, world.getDimension().getType())), new AnvilSync(currentHits, getPos()));
                        return false;
                    }
                    if(currentHits == recipe.length())
                    {
                        containedItem = resultStack.copy();
                        resultStack = ItemStack.EMPTY;
                        currentHits = 0;
                        recipe = "";
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                    }
                    markDirty();
                    NetworkingSetup.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(getPos().getX(), getPos().getY(), getPos().getZ(), 16D, world.getDimension().getType())), new AnvilSync(currentHits, getPos()));
                    return true;
                }
            }
        }
        return true;
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


    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbtTag = new CompoundNBT();
        nbtTag = write(nbtTag);
        return nbtTag;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }
}
