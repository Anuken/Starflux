package io.anuke.someplanets.objects.plants;

import io.anuke.someplanets.objects.ObjectGenerator;
import io.anuke.someplanets.util.Trig;
import net.minecraft.init.Blocks;

public class BushGen extends ObjectGenerator{
	
	{chance = 50;}

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
