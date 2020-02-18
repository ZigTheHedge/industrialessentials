package com.cwelth.industrialessentials.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class AnvilRecipe {
    public String hits;
    public ItemStack output;
    public Ingredient input;

    public AnvilRecipe(String hits, ItemStack output, Ingredient input)
    {
        this.hits = hits;
        this.output = output;
        this.input = input;
    }

    public boolean match(ItemStack subject)
    {
        return input.test(subject);
    }
}
