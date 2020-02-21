package com.cwelth.industrialessentials.tileentities.renderers;

import com.cwelth.industrialessentials.IndustrialEssentials;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Vec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class UIUtils {
    public static final ResourceLocation UI_ATLAS = new ResourceLocation(IndustrialEssentials.MODID, "gui/ui");

    public static void addVertex(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    public static void addQuad(IVertexBuilder builder, MatrixStack matrix, Vec3f topLeft, Vec3f bottomRight, TextureAtlasSprite atlas, float uMin, float vMin, float uMax, float vMax) {
        //Assume texture: 256x256
        float wholeWidth = (atlas.getMaxU() - atlas.getMinU()) / atlas.getWidth();
        float wholeHeight = (atlas.getMaxV() - atlas.getMinV()) / atlas.getHeight();
        uMin = atlas.getMinU() + wholeWidth * uMin;
        vMin = atlas.getMinV() + wholeHeight * vMin;
        uMax = uMin + wholeWidth * uMax;
        vMax = vMin + wholeHeight * vMax;
        addVertex(builder, matrix, topLeft.x, topLeft.y, topLeft.z, uMin, vMin);
        addVertex(builder, matrix, topLeft.x, bottomRight.y, topLeft.z, uMin, vMax);
        addVertex(builder, matrix, bottomRight.x, bottomRight.y, bottomRight.z, uMax, vMax);
        addVertex(builder, matrix, bottomRight.x, topLeft.y, bottomRight.z, uMax, vMin);
    }

    public static void addAnvilProgressUI(IVertexBuilder builder, MatrixStack matrix, TextureAtlasSprite atlas, String wholeBar, int completedSteps)
    {
        float singleWidth = .15F;
        int totalLength = wholeBar.length();
        float startLeft = -(totalLength * singleWidth) / 2;
        for(int i = 0; i < totalLength; i++)
        {
            float minU = (wholeBar.charAt(i) == 'L')? 0F: 16F;
            float minV = (completedSteps > i)? 16F: 0F;
            UIUtils.addQuad(builder, matrix, new Vec3f(startLeft + (singleWidth * i), 0, 0), new Vec3f(startLeft + (singleWidth * (i + 1)), -singleWidth, 0), atlas, minU, minV, 16, 16);
        }
    }

}
