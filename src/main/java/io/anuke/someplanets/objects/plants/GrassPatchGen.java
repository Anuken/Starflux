package io.anuke.someplanets.objects.plants;

import io.anuke.someplanets.objects.ObjectGenerator;
import io.anuke.someplanets.util.Trig;
import net.minecraft.init.Blocks;

public class GrassPatchGen extends ObjectGenerator{
	
	@Override
	public void generate() {
		float radmod = rand.nextFloat()*3+2;
		int sections = 2;
		float sinscl = 20f + rand.nextInt(35);
		float coscl = 25f + rand.nextInt(40);
		for(int i = 0; i < sections; i ++){
			float rad = (radmod)-i*1.1f;
			
			for(int rx = -((int)rad+2); rx <= ((int)rad+2); rx ++){
				for(int rz = -((int)rad+2); rz <= ((int)rad+2); rz ++){
					double ang = ang(rx, rz);
					
					if(dst(rx,rz,0,0) < rad - (Trig.sin(ang/sinscl) + Trig.cos(ang/coscl)/2.5f)){
						setBlock(x+rx,y+i-2,z+rz, Blocks.leaves);
					}
				}
			}
		}
	}
}
