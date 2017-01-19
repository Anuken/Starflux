package io.anuke.starflux.objects.alien;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class VineGen extends ObjectGenerator {

	{
		chance = 40;
		genBlock = Blocks.mycelium;
	}
	
	public boolean add(PlanetData data){
		return Math.random() < 0.12 && data.temperature < 0.8f && data.temperature > 0.4f;
	}
	
	boolean stars = true;

	@Override
	public void generate() {
		
		int height = 8 + rand.nextInt(20);
		float dirx = 0f, dirz = 0f;
		float offsetx = 0, offsetz = 0;
		float switchscl = 3f;

		for (int i = 0; i < height; i++) {
			setBlock((int) (x + offsetx), y + i, (int) (z + offsetz), Blocks.melon_block);

			if (rand.nextFloat() < 0.1f) {
				boolean rshift = rand.nextBoolean();
				setBlock((int) (x + offsetx + (rshift ? 0 : rsign())), y + i,
						(int) (z + offsetz + (rshift ? rsign() : 0)), Blocks.glowstone);
			}
			
			if(stars && rand.nextFloat() < 0.08f && i < height-4){
				disc((int) (x + offsetx), y + i, (int) (z + offsetz), 1.3f, Blocks.melon_block);
			}

			offsetx += dirx;
			offsetz += dirz;

			dirx = sclamp(dirx + rand.nextFloat() / switchscl - 0.5f / switchscl);
			dirz = sclamp(dirz + rand.nextFloat() / switchscl - 0.5f / switchscl);
		}

		sphere((int) (x + offsetx), y + height, (int) (z + offsetz), 2.5f, Blocks.glowstone);
	}
}
