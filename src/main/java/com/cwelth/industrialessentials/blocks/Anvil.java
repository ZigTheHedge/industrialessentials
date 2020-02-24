package com.cwelth.industrialessentials.blocks;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class Anvil extends ModelledBlock {
    public static final BooleanProperty HAMMER_PRESENT = BooleanProperty.create("hammer_present");

    VoxelShape NORTH, EAST, SOUTH, WEST;

    public Anvil() {
        setDefaultState(getDefaultState().with(HAMMER_PRESENT, false));
        NORTH = Block.makeCuboidShape(0, 0, 3, 15, 13, 13);
        SOUTH = Block.makeCuboidShape(1, 0, 3, 16, 13, 13);
        EAST = Block.makeCuboidShape(3, 0, 0, 13, 13, 15);
        WEST = Block.makeCuboidShape(3, 0, 1, 13, 13, 16);
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
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    private void missClick(AnvilTE tileEntity, PlayerEntity player, World worldIn)
    {
        boolean itsHurt = worldIn.rand.nextInt(2) == 1;
        if(itsHurt) player.attackEntityFrom(DamageSource.ANVIL, 1.0F);
        else
        {
            InventoryHelper.spawnItemStack(worldIn, tileEntity.getPos().getX(), tileEntity.getPos().getY() + 1, tileEntity.getPos().getZ(), tileEntity.getContainedItem());
            tileEntity.markDirty();
            worldIn.notifyBlockUpdate(tileEntity.getPos(), worldIn.getBlockState(tileEntity.getPos()), worldIn.getBlockState(tileEntity.getPos()), 2);
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if(!worldIn.isRemote)
        {
            AnvilTE te = (AnvilTE)worldIn.getTileEntity(pos);
            if(te.hasValidRecipe() && (player.getHeldItem(Hand.MAIN_HAND).getItem() == IEContent.HAMMER_PART.get() || player.getHeldItem(Hand.MAIN_HAND).getItem() == IEContent.HAMMER_DIAMOND_PART.get())) {
                if(!te.bash(Hand.MAIN_HAND))missClick(te, player, worldIn);
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if(handIn == Hand.OFF_HAND)return ActionResultType.PASS;
        AnvilTE te = (AnvilTE)worldIn.getTileEntity(pos);
        if(worldIn.isRemote)
        {
            return ActionResultType.SUCCESS;
        } else
        {
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
                    if(te.hasValidRecipe() && (player.getHeldItem(handIn).getItem() == IEContent.HAMMER_PART.get() || player.getHeldItem(handIn).getItem() == IEContent.HAMMER_DIAMOND_PART.get())) {
                        player.getHeldItem(Hand.MAIN_HAND).damageItem(1, player, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                        if(!te.bash(Hand.OFF_HAND))missClick(te, player, worldIn);
                    } else
                        player.setHeldItem(handIn, te.interact(player.getHeldItem(handIn)));
                }

            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.NORTH)return NORTH;
        if(state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST)return EAST;
        if(state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH)return SOUTH;
        if(state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST)return WEST;
        return null;
    }
}
