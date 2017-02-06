package io.anuke.starflux.objects.geological;

import io.anuke.starflux.noise.Noise;
import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class LargeBoulderGen extends ObjectGenerator{
	
	public boolean add(PlanetData data){
		return chance(0.3) && data.temperature > 0.2f;
	}
	
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
