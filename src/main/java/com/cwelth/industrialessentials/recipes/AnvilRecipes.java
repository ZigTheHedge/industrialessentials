package com.cwelth.industrialessentials.recipes;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class AnvilRecipes {
    public List<AnvilRecipe> recipes = new ArrayList<>();

    public void init()
    {
        recipes.add(new AnvilRecipe(500, new ItemStack(Items.IRON_BARS, 32), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK))));
    }

    public AnvilRecipe getMatchedRecipe(ItemStack input)
    {
        for(AnvilRecipe recipe: recipes)
        {
            if(recipe.match(input))
            {
                return recipe;
            }
        }
        return null;
    }
}
