package io.anuke.starflux.objects.alien;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class GrassTentacleGen extends ObjectGenerator {
	
	{
		chance = 40;
		genBlock = Blocks.mycelium;
	}
	
	public boolean add(PlanetData data){
		return Math.random() < 0.15 && data.temperature < 0.8f && data.temperature > 0.4f;
	}

	@Override
	public void generate() {
		for (int j = 0; j < 5; j++) {
			int height = 5 + rand.nextInt(4);
			float dirx = rand.nextFloat()-0.5f, dirz = rand.nextFloat()-0.5f;
			float offsetx = 0, offsetz = 0;
			float switchscl = 3f;

			for (int i = 0; i < height; i++) {
				setBlock((int) (x + offsetx), y + i, (int) (z + offsetz), Blocks.melon_block);

				offsetx += dirx;
				offsetz += dirz;

				dirx = sclamp(dirx + rand.nextFloat() / switchscl - 0.5f / switchscl);
				dirz = sclamp(dirz + rand.nextFloat() / switchscl - 0.5f / switchscl);
			}
		}
	}
}
