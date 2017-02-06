package io.anuke.starflux.objects.alien;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.util.Trig;
import net.minecraft.init.Blocks;

public class StarFlowerGen extends ObjectGenerator{
	
	{
		chance = 30;
		genBlock = Blocks.mycelium;
	}
	
	public boolean add(PlanetData data){
		return chance(0.3) && data.temperature < 0.8f && data.temperature > 0.4f;
	}
	
	@Override
	public void generate() {
		for(int i = 0; i < 2; i ++)
		disc(x, y+i, z, 2, Blocks.melon_block);
		
		float rad = 4+rand.nextInt(2);
		int phase = rand.nextBoolean() ? 8 : rand.nextBoolean() ? 4 : 6;
		float offset = rand.nextFloat()*6;

		for(int rx = -((int)rad+2); rx <= ((int)rad+2); rx ++){
			for(int rz = -((int)rad+2); rz <= ((int)rad+2); rz ++){
				double ang = angrad(rx, rz);
				
				double arad = rad - (Trig.sin(ang*phase + offset)*1.5f);
				double dst = dst(rx,rz,0,0);
				
				if(dst < arad)
					setBlock(x+rx,y+2,z+rz, dst > arad-1 ? Blocks.wool : Blocks.melon_block, 14);
			}
		}
		
		sphere(x,y+3,z,1.5f,Blocks.glowstone);
	}
}
