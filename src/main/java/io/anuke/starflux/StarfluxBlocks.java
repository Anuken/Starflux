package io.anuke.starflux;

import cpw.mods.fml.common.registry.GameRegistry;
import io.anuke.starflux.blocks.BasicBlock;
import net.minecraft.block.Block;

public class StarfluxBlocks{
	public static Block smoothStone;
	
	public static void create(){
		GameRegistry.registerBlock(smoothStone = new BasicBlock("smooth_stone"), "smooth_stone");
		
		//Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    //.register(Item.getItemFromBlock(block), meta, new ModelResourceLocation("modid:blockname", "inventory"));
	}
}
