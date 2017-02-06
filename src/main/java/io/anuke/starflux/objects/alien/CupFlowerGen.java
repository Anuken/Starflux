package io.anuke.starflux.objects.alien;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class CupFlowerGen extends ObjectGenerator {
	
	{
		chance = 30;
		genBlock = Blocks.mycelium;
	}
	
	public boolean add(PlanetData data){
		return chance(0.2) && data.temperature < 0.8f && data.temperature > 0.4f;
	}

	@Override
	public void generate() {
		setBlock(x, y, z,  Blocks.melon_block);
		disc(x, y + 1, z, 1.2f,  Blocks.melon_block);
		setBlock(x, y + 2, z, Blocks.glowstone);
	}
}
