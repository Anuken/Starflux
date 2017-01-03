package io.anuke.someplanets.objects.plants;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class GrassThingGen extends ObjectGenerator {
	
	@Override
	public void generate() {
		setBlock(x, y, z, Blocks.leaves);
	}
}
