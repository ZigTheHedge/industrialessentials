package com.cwelth.industrialessentials.tileentities.renderers;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class AnvilTESR extends TileEntityRenderer<AnvilTE> {
    public AnvilTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(IEContent.ANVIL_TE.get(), AnvilTESR::new);
    }

    @Override
    public void render(AnvilTE tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if(tileEntity.getContainedItem().isEmpty() || tileEntity.isHammerPresent()) return;

        matrixStack.push();

        matrixStack.translate(0.5, 0.92, 0.5);
        matrixStack.scale(.5F, .5F, .5F);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntity.getContainedItem();
        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntity.getWorld(), null);
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, ibakedmodel);

        /*
        matrixStack.translate(-.5, 1, -.5);
        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        BlockState state = Blocks.ENDER_CHEST.getDefaultState();
        blockRenderer.renderBlock(state, matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
        */

        matrixStack.pop();
    }
}
