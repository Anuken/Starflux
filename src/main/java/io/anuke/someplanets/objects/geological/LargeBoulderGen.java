package io.anuke.someplanets.objects.geological;

import io.anuke.someplanets.noise.Noise;
import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class LargeBoulderGen extends ObjectGenerator{
	
	@Override
	public void generate(){
		int rad = 4 + rand.nextInt(5);
		for (int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++) {
			for (int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++) {
				for (int ry = -((int) rad*2 + 1); ry <= ((int) rad*2 + 1); ry++) {
					if (dst(rx, ry/2, rz, 0, 0, 0) < rad + Noise.normalNoise(rx+x, ry+y, rz+z, 11, 2)) {
						setBlock(x + rx, y + ry, z + rz, Blocks.stained_hardened_clay, Noise.normalNoise(rx+x, ry+y, rz+z, 10, 2) > 0.3f ? 0 : 8);
					}
				}
			}
		}
	}
}
