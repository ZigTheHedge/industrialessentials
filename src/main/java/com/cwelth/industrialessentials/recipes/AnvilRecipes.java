package com.cwelth.industrialessentials.recipes;

import com.cwelth.industrialessentials.inits.IEContent;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnvilRecipes {
    public List<AnvilRecipe> recipes = new ArrayList<>();

    public void init()
    {
        recipes.add(new AnvilRecipe("LLRLRRLLRLR", new ItemStack(Items.IRON_BARS, 32), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK))));
        addDustRecipes();
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

    public String getRandomSequence(int length)
    {
        Random rnd = new Random();
        String toRet = "";
        for(int i = 0; i < length; i++)
        {
            if(rnd.nextInt(2) == 1)toRet += 'L';
            else toRet += 'R';
        }
        return toRet;
    }

    public void addDustRecipes() {
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(IEContent.IRON_ORE_DUST.get(), 2), Ingredient.fromStacks(new ItemStack(Blocks.IRON_ORE))));
        recipes.add(new AnvilRecipe(getRandomSequence(10), new ItemStack(IEContent.GOLD_ORE_DUST.get(), 2), Ingredient.fromStacks(new ItemStack(Blocks.GOLD_ORE))));
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(IEContent.COPPER_ORE_DUST.get(), 2), Ingredient.fromStacks(new ItemStack(IEContent.COPPER_ORE.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(IEContent.TIN_ORE_DUST.get(), 2), Ingredient.fromStacks(new ItemStack(IEContent.TIN_ORE.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(IEContent.LEAD_ORE_DUST.get(), 2), Ingredient.fromStacks(new ItemStack(IEContent.LEAD_ORE.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(10), new ItemStack(IEContent.SILVER_ORE_DUST.get(), 2), Ingredient.fromStacks(new ItemStack(IEContent.SILVER_ORE.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(14), new ItemStack(Items.DIAMOND, 2), Ingredient.fromStacks(new ItemStack(Blocks.DIAMOND_ORE))));
        recipes.add(new AnvilRecipe(getRandomSequence(14), new ItemStack(Items.EMERALD, 2), Ingredient.fromStacks(new ItemStack(Blocks.EMERALD_ORE))));
    }
}


