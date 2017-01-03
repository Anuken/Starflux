package io.anuke.starflux.objects.plants;

import io.anuke.starflux.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class GrassThingGen extends ObjectGenerator {
	
	@Override
	public void generate() {
		setBlock(x, y, z, Blocks.leaves);
	}
}
