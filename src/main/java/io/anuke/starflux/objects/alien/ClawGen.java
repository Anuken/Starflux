package io.anuke.starflux.objects.alien;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.util.Trig;
import net.minecraft.init.Blocks;

public class ClawGen extends ObjectGenerator {
	{
		chance = 50;
	}

	@Override
	public void generate() {
		this.y--;

		float rad = 5f + rand.nextFloat() * 4;

		for (int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++) {
			for (int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++) {
				float ang = angrad(rx, rz);
				double sin = Trig.sin(ang * 4f + Math.PI / 2);
				if (Math.abs(sin) > 0.5f)
					for (int ry = -((int) rad * 2 + 1); ry <= (int) (rad + 1f + sin); ry++) {
						float dst = dst(rx, ry / 2f, rz, 0, 0, 0);
						if (dst < rad && dst > rad - 1.5f) {
							setBlock((int) (x + rx), (int) (y + ry + rad * 2f + 1), (int) (z + rz), Blocks.melon_block);
						}
					}
			}
		}
		sphere(x, (int) (y + rad - 1), z, rad - 3, Blocks.glowstone);

		for (int i = 0; i < 3; i++)
			disc(x, y + i, z, 3.5f, Blocks.melon_block);
	}
}
