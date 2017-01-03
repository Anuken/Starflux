package io.anuke.starflux.objects.geological;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.util.Trig;
import net.minecraft.init.Blocks;

public class RockSpikeGen extends ObjectGenerator{
	
	{chance = 30;}

	@Override
	public void generate() {
		float baserad = rand.nextFloat()*3+2;
		float height = 6+rand.nextInt(15);
		float sinscl = 30f + rand.nextInt(35);
		float coscl = 35f + rand.nextInt(40);
		for(int i = 0; i < height; i ++){
			float rad = baserad*(1f-(i+1)/height);
			
			for(int rx = -((int)rad+2); rx <= ((int)rad+2); rx ++){
				for(int rz = -((int)rad+2); rz <= ((int)rad+2); rz ++){
					double ang = ang(rx, rz);
					
					if(dst(rx,rz,0,0) < rad - (Trig.sin(ang/sinscl) + Trig.cos(ang/coscl)/2.5f)){
						setBlock(x+rx,y+i-2,z+rz, Blocks.cobblestone);
					}
				}
			}
		}
	}
}
