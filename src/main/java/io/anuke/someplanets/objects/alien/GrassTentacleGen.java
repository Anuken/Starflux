package io.anuke.someplanets.objects.alien;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class GrassTentacleGen extends ObjectGenerator {

	{
		chance = 40;
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
