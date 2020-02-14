package com.cwelth.industrialessentials.items;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.inits.InitCommon;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class Hammer extends PickaxeItem {
    public Hammer(IItemTier tier) {
        super(tier, 2, -2.8F, new Item.Properties()
                .group(InitCommon.creativeTab)
                .maxStackSize(1)
        );
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && entityLiving instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)entityLiving;
            if(state.getBlockHardness(worldIn, pos) != 0.0F) {
                stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
            if(!player.isShiftKeyDown()) {

                float initialBlockResistance = state.getBlockHardness(worldIn, pos);

                BlockRayTraceResult result = (BlockRayTraceResult) rayTrace(worldIn, player, RayTraceContext.FluidMode.NONE);
                Direction sideHit = result.getFace();
                int xDist, yDist, zDist;
                yDist = xDist = zDist = 1;

                switch (sideHit) {
                    case UP:
                    case DOWN:
                        yDist = 0;
                        break;
                    case NORTH:
                    case SOUTH:
                        zDist = 0;
                        break;
                    case EAST:
                    case WEST:
                        xDist = 0;
                        break;
                }

                for (int x = pos.getX() - xDist; x <= pos.getX() + xDist; x++) {
                    for (int y = pos.getY() - yDist; y <= pos.getY() + yDist; y++) {
                        for (int z = pos.getZ() - zDist; z <= pos.getZ() + zDist; z++) {
                            BlockPos targetPos = new BlockPos(x, y, z);
                            BlockState targetBlock = worldIn.getBlockState(targetPos);
                            if (canHarvestBlock(targetBlock) && targetBlock.getBlockHardness(worldIn, targetPos) <= initialBlockResistance) {
                                if ((stack.getMaxDamage() - stack.getDamage()) >= 1 && targetBlock.getBlock() != Blocks.BEDROCK) {
                                    if (targetBlock.getBlock().getExpDrop(targetBlock, worldIn, targetPos, 0, 0) > 0) {
                                        worldIn.addEntity(new ExperienceOrbEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos).getBlock().getExpDrop(targetBlock, worldIn, targetPos, 0, 0)));
                                    }
                                    worldIn.destroyBlock(new BlockPos(x, y, z), true);
                                }
                                if (state.getBlockHardness(worldIn, targetPos) != 0.0F) {
                                    stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                                        p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if(state.getBlock() == IEContent.ANVIL.get())
            return 0.0F;
        else
            return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if(entity.getEntityWorld().isRemote) return false;
        PlayerEntity player = (PlayerEntity)entity;
        World world = player.getEntityWorld();
        BlockRayTraceResult result = (BlockRayTraceResult) rayTrace(world, player, RayTraceContext.FluidMode.NONE);
        BlockState block = world.getBlockState(result.getPos());
        if (block.getBlock() == IEContent.ANVIL.get()) {
            world.playSound(player, result.getPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            //Process recipe
            AnvilTE te = (AnvilTE)entity.getEntityWorld().getTileEntity(result.getPos());
            if(te != null)
            {
                te.bash();
                player.sendStatusMessage(new StringTextComponent("Hammering in progress: " + te.getProgress() ), false);
            }
        }
        return false;
    }

}
