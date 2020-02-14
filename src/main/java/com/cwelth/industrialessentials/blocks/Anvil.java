package com.cwelth.industrialessentials.blocks;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Anvil extends ModelledBlock {
    public static final BooleanProperty HAMMER_PRESENT = BooleanProperty.create("hammer_present");


    public Anvil() {
        setDefaultState(getDefaultState().with(HAMMER_PRESENT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HAMMER_PRESENT);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AnvilTE();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if(!worldIn.isRemote) {
            AnvilTE te = (AnvilTE)worldIn.getTileEntity(pos);
            if(te != null)
            {
                if(player.getHeldItem(handIn).isEmpty())
                {
                    if(te.getContainedItem() != ItemStack.EMPTY)
                    {
                        player.setHeldItem(handIn, te.interact(ItemStack.EMPTY));
                    }
                } else
                {
                    player.setHeldItem(handIn, te.interact(player.getHeldItem(handIn)));
                }

            }
        }
        return ActionResultType.SUCCESS;
    }
}
