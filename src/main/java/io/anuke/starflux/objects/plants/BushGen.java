package io.anuke.starflux.objects.plants;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.util.Trig;
import net.minecraft.init.Blocks;

public class BushGen extends ObjectGenerator{
	
	{
		chance = 15;
		genBlock = Blocks.grass;
	}
	
	public boolean add(PlanetData data){
		return Math.random() < 0.35 && data.temperature < 0.7f && data.temperature > 0.3f;
	}

	@Override
	public void generate() {
		int height = 2;

		for (int i = 0; i < height; i++) {
			disc(x, y + i, z, 2.5f - i/1.3f, Blocks.log, 12);
		}
		
		int rad = 4;
		float phase = rand.nextFloat()*8;

		for(int rx = -((int)rad+2); rx <= ((int)rad+2); rx ++){
			for(int rz = -((int)rad+2); rz <= ((int)rad+2); rz ++){
				double ang = ang(rx, rz);
				
				double arad = rad - (Trig.sin(ang/10f + phase)*1.5f);
				double dst = dst(rx,rz,0,0);
				
				if(dst < arad)
					setBlock(x+rx,y+height,z+rz, Blocks.leaves);
				
				if(dst < arad-1f)
					setBlock(x+rx,y+height+1,z+rz, Blocks.leaves);
				if(dst < arad-2f)
					setBlock(x+rx,y+height+2,z+rz, Blocks.leaves);
				
			}
		}
	}
}
