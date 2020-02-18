package com.cwelth.industrialessentials.tileentities.renderers;

import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.opengl.GL11;

import static com.cwelth.industrialessentials.tileentities.renderers.UIUtils.UI_ATLAS;

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
        TextureAtlasSprite ui_atlas = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(UI_ATLAS);

        matrixStack.push();

        if(tileEntity.getContainedItem().getItem() instanceof BlockItem)
            matrixStack.translate(0.5, 0.92, 0.5);
        else
            matrixStack.translate(0.5, 0.82, 0.5);
        matrixStack.scale(.5F, .5F, .5F);
        float rotatorX = 270F, rotatorY = 0F, rotatorZ = 0F;
        if(tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST)
        {
            rotatorZ = 270F;
        }
        if(tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH)
        {
            rotatorX = 90F;
            rotatorY = 180F;
        }
        if(tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST)
        {
            rotatorZ = 90F;
        }
        Quaternion rotatorNion = new Quaternion(rotatorX, rotatorY, rotatorZ, true);
        matrixStack.rotate(rotatorNion);
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

        matrixStack.push();

        if(tileEntity.clientOnLookAt)
        {

            Quaternion panning = new Quaternion(Vector3f.XN, 45, true);
            float Yrot = 0F;
            if(tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST)
                Yrot = 90F;
            if(tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH)
                Yrot = 180F;
            if(tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST)
                Yrot = 270F;
            matrixStack.translate(0.5F, 1.2F, 0.5F);

            Quaternion rotator = new Quaternion(Vector3f.YN, Yrot, true);
            matrixStack.rotate(rotator);
            matrixStack.translate(0F, 0F, -0.5F);
            matrixStack.rotate(panning);


            IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());
            UIUtils.addAnvilProgressUI(builder, matrixStack, ui_atlas, tileEntity.getRecipe(), tileEntity.getCurrentHits());
        }

        matrixStack.pop();
    }
}
