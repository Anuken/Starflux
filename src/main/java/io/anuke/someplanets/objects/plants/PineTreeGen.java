package io.anuke.someplanets.objects.plants;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class PineTreeGen extends ObjectGenerator {
	public boolean snow;

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
