package io.anuke.starflux.objects.alien;

import io.anuke.starflux.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class TentacleGen extends ObjectGenerator{
	{chance = 40;}

	@Override
	public void generate() {
		this.y --;
		
		int height = 16 + rand.nextInt(22);
		float dirx = 0f, dirz = 0f;
		float offsetx = 0, offsetz = 0;
		float switchscl = 3f;
		float rad = height/8f;

		for (int i = 0; i < height; i++) {
			disc((int) (x + offsetx), y + i, (int) (z + offsetz), rad*scld1(i, height), Blocks.melon_block);

			if (rand.nextFloat() < 0f) {
				boolean rshift = rand.nextBoolean();
				setBlock((int) (x + offsetx + (rshift ? 0 : rsign())), y + i,
						(int) (z + offsetz + (rshift ? rsign() : 0)), Blocks.glowstone);
			}

			offsetx += dirx;
			offsetz += dirz;

			dirx = sclamp(dirx + rand.nextFloat() / switchscl - 0.5f / switchscl);
			dirz = sclamp(dirz + rand.nextFloat() / switchscl - 0.5f / switchscl);
		}
	}
}
