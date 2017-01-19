package io.anuke.starflux.objects.geological;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.util.Trig;
import net.minecraft.init.Blocks;

public class BoulderGen extends ObjectGenerator{
	
	public BoulderGen(){
		super(5);
	}
	
	public boolean add(PlanetData data){
		return Math.random() < 0.3;
	}

	@Override
	public void generate() {
		float radmod = rand.nextFloat()*3+2;
		int sections = 3+rand.nextInt(3);
		float sinscl = 20f + rand.nextInt(35);
		float coscl = 25f + rand.nextInt(40);
		for(int i = 0; i < sections; i ++){
			float rad = (radmod)-Math.abs(i-sections/2);
			
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
