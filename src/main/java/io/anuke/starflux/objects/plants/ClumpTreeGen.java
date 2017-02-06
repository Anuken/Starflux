package io.anuke.starflux.objects.plants;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import net.minecraft.init.Blocks;

public class ClumpTreeGen extends ObjectGenerator{
	public float baserad = 1f;
	public float srad = 0.3f;
	
	{
		chance = 40;
		genBlock = Blocks.grass;
	}
	
	public boolean add(PlanetData data){
		return chance(0.24) && data.temperature < 0.7f && data.temperature > 0.3f;
	}

	@Override
	public void generate() {
		
		int branches = 4 + rand.nextInt(4);
		for(int i = 0; i < branches; i ++)
			genBranch();
	}
	
	void genBranch(){
		int height = 10+rand.nextInt(7);
		float offsetx=0, offsetz=0;
		vec2.setToRandomDirection();
		float vscl = 0.85f+rand.nextFloat()/10f;
		
		for(int i = 0; i < height; i ++){
			disc((int)(x+offsetx),y+i,(int)(z+offsetz), baserad + srad*scld(i,height), Blocks.log,12);
			offsetx += vec2.x;
			offsetz += vec2.y;
			vec2.scl(vscl);
		}
		
		sphere((int)(x+offsetx-vec2.x), (int)(y+height), (int)(z+offsetz-vec2.y), 2f+rand.nextFloat()*1.5f, Blocks.leaves, 0);
	}
}
