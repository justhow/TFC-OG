package com.bioxx.tfc.api.Crafting;

import java.util.Stack;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Interfaces.IFood;

public class BarrelMultiItemRecipe extends BarrelRecipe
{
	boolean keepstacksize = true;
	public BarrelMultiItemRecipe(ItemStack inputItem, FluidStack inputFluid,
			ItemStack outIS, FluidStack outputFluid) {
		super(inputItem, inputFluid, outIS, outputFluid);
	}

	public BarrelMultiItemRecipe setKeepStackSize(boolean b)
	{
		keepstacksize = b;
		return this;
	}

	@Override
	public Stack<ItemStack> getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		ItemStack out = recipeOutIS.copy();
		if(inIS != null && inIS.getItem() instanceof IFood)
		{
			int w = (int)Math.floor(Food.getWeight(inIS));
			if(w * recipeOutFluid.amount <= inFS.amount)
			{
				Food.setWeight(out, w*Food.getWeight(recipeOutIS));
			}
		}
		else
		{
			if(keepstacksize)
				out.stackSize = inIS.stackSize;
			else
				out.stackSize *= inIS.stackSize;
		}

		Stack<ItemStack> result = new Stack<ItemStack>();
		result.push(out);

		return result;
	}

	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		FluidStack fs = recipeOutFluid.copy(); 

		if(inIS != null && inIS.getItem() instanceof IFood)
		{
			int w = (int)Math.floor(Food.getWeight(inIS));
			if(w * recipeOutFluid.amount <= inFS.amount)
			{
				fs.amount = (w * recipeOutFluid.amount);
			}
		}
		else
		{
			fs.amount *= inIS.stackSize;
		}
		return fs;
	}

    public boolean isKeepstacksize()
    {
        return keepstacksize;
    }

	@Override
	public Boolean matches(ItemStack inIS, FluidStack inFS)
	{
		if (inIS != null && inFS != null && inIS.getItem() instanceof IFood)
		{
			float w = ((IFood) inIS.getItem()).getFoodWeight(inIS);
			if (inFS.isFluidEqual(recipeFluid) && (w * recipeOutFluid.amount <= inFS.amount))
			{
				return true;
			}
			else
				return false;
		}
		return super.matches(inIS, inFS);
	}
}
