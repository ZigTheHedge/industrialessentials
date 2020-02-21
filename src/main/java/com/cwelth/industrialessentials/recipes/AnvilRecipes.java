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
        recipes.add(new AnvilRecipe(getRandomSequence(11), new ItemStack(Items.IRON_BARS, 32), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK))));
        addDustRecipes();
        addPlateRecipes();
    }

    public AnvilRecipe getMatchedRecipe(ItemStack input)
    {
        for(AnvilRecipe recipe: recipes)
        {
            if(recipe.match(input))
            {
                recipe.hits = getRandomSequence(recipe.hits.length());
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
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(Items.REDSTONE, 8), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_ORE))));
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(Items.LAPIS_LAZULI, 8), Ingredient.fromStacks(new ItemStack(Blocks.LAPIS_ORE))));
    }

    public void addPlateRecipes() {
        recipes.add(new AnvilRecipe(getRandomSequence(11), new ItemStack(IEContent.IRON_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT))));
        recipes.add(new AnvilRecipe(getRandomSequence(8), new ItemStack(IEContent.GOLD_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT))));
        recipes.add(new AnvilRecipe(getRandomSequence(9), new ItemStack(IEContent.COPPER_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(IEContent.COPPER_INGOT.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(7), new ItemStack(IEContent.TIN_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(IEContent.TIN_INGOT.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(9), new ItemStack(IEContent.LEAD_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(IEContent.LEAD_INGOT.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(9), new ItemStack(IEContent.SILVER_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(IEContent.SILVER_INGOT.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(15), new ItemStack(IEContent.BRONZE_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(IEContent.BRONZE_INGOT.get()))));
        recipes.add(new AnvilRecipe(getRandomSequence(15), new ItemStack(IEContent.ELECTRUM_PLATE.get(), 1), Ingredient.fromStacks(new ItemStack(IEContent.ELECTRUM_INGOT.get()))));

    }
}


