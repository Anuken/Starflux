package io.anuke.starflux.objects.plants;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class PineTreeGen extends ObjectGenerator {
	public boolean snow;
	
	{
		chance = 30;
		genBlock = Blocks.grass;
	}
	
	public boolean add(PlanetData data){
		return Math.random() < 0.1 && data.temperature < 0.5f && data.temperature > 0.1f;
	}

	@Override
	public void generate() {
		y--;

		int height = 13 + rand.nextInt(10);
		float rad = height / 2f;
		int bot = (int) (height / 6);

		for (int i = 0; i < bot; i++) {
			disc(x, y + i, z, 3f - i / 2f, Blocks.log, 12);
		}
		
		for(int i = bot; i < height-4; i ++){
			disc(x, y + i, z, 1.2f, Blocks.log, 12);
		}
		
		disc(x, y + height-3, z, 1f, Blocks.log, 12);

		for (int i = bot; i < height; i++) {
			float off = rand.nextFloat() * 6;
			star(x, y + i - 2, z, rad * scld(i, height), 6, off, 1, Blocks.leaves);
			if(snow)star(x, y + i - 1, z, rad * scld(i, height), 6, off, 1, Blocks.snow_layer);
		}
	}
}
