package io.anuke.someplanets.objects.alien;

import io.anuke.someplanets.objects.ObjectGenerator;
import io.anuke.someplanets.util.Trig;
import net.minecraft.init.Blocks;

public class OrbFlowerGen extends ObjectGenerator {

	{
		chance = 60;
	}

	@Override
	public void generate() {
		this.y --;
		
		float rad = 5f+rand.nextFloat()*4;
		
		int height = 6 + rand.nextInt(8);
		float dirx = 0f, dirz = 0f;
		float offsetx = 0, offsetz = 0;
		float switchscl = 3f;

		for (int i = 0; i < height; i++) {
			disc((int) (x + offsetx), y + i, (int) (z + offsetz), rad/3f + 2*scld(i,height), Blocks.red_mushroom_block, 0);

			offsetx += dirx;
			offsetz += dirz;

			dirx = sclamp(dirx + rand.nextFloat() / switchscl - 0.5f / switchscl);
			dirz = sclamp(dirz + rand.nextFloat() / switchscl - 0.5f / switchscl);
		}
		
		for (int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++) {
			for (int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++) {
				float ang = angrad(rx, rz);
				double sin = Trig.sin(ang * 4f+Math.PI/2);
				if (Math.abs(sin) > 0.5f)
					for (int ry = -((int) rad + 1); ry <= (int)(rad/2f+sin); ry++) {
						float dst = dst(rx, ry, rz, 0, 0, 0);
						if (dst < rad && dst > rad-1.5f) {
							setBlock((int)(x + rx + offsetx), (int)(height + y + ry + rad - 1), (int)(z + rz + offsetz), Blocks.red_mushroom_block, 14);
						}
					}
			}
		}
		sphere((int)(x + offsetx), (int)(y + rad + height - 1), (int)(z + offsetz), rad-3, Blocks.glowstone);

		for(int i = 0; i < 4; i ++)
			disc((int)(x + offsetx), (int)(y + height+i), (int)(z + offsetz), 3.5f, Blocks.red_mushroom_block, 14);
	}
}
