package io.anuke.someplanets.objects.alien;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class CupFlowerGen extends ObjectGenerator {
	
	{chance = 30;}

	@Override
	public void generate() {
		setBlock(x, y, z,  Blocks.melon_block);
		disc(x, y + 1, z, 1.2f,  Blocks.melon_block);
		setBlock(x, y + 2, z, Blocks.glowstone);
	}
}
