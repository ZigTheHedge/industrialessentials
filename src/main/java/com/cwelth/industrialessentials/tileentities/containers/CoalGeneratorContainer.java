package com.cwelth.industrialessentials.tileentities.containers;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.tileentities.CoalGeneratorTE;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


public class CoalGeneratorContainer extends Container {

    private CoalGeneratorTE tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public CoalGeneratorContainer(int windowId, BlockPos pos, PlayerInventory playerInventory) {
        super(IEContent.COAL_GENERATOR_CONTAINER.get(), windowId);
        playerEntity = (PlayerEntity)playerInventory.player.getEntity();
        tileEntity = (CoalGeneratorTE)playerEntity.getEntityWorld().getTileEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);
        addPlayerSlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, IEContent.COAL_GENERATOR.get());
    }

    private void addPlayerSlots(int x, int y)
    {
        int idx = 9, sX = x, sY = y;
        for (int j = 0 ; j < 3 ; j++) {
            for(int i = 0; i < 9; i++) {
                addSlot(new SlotItemHandler(playerInventory, idx, sX, sY));
                sX += 18;
                idx++;
            }
            sX = x;
            sY += 18;
        }
        idx = 0; sX = x; sY = y + 58;
        for(int i = 0; i < 9; i++) {
            addSlot(new SlotItemHandler(playerInventory, idx, sX, sY));
            sX += 18;
            idx++;
        }
    }

    private void addOwnSlots()
    {

    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (stack.getItem() == Items.DIAMOND) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

}
