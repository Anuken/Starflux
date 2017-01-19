package io.anuke.starflux.objects.geological;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class RockGen extends ObjectGenerator {
	
	public boolean add(PlanetData data){
		return Math.random() < 0.3;
	}
	
	@Override
	public void generate() {
		disc(x, y - 1, z, 1.6f, Blocks.cobblestone);
		disc(x, y, z, 1.6f, Blocks.cobblestone);
		disc(x, y + 1, z, 1.2f, Blocks.cobblestone);
	}
}
