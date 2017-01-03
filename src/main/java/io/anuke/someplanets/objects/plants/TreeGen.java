package io.anuke.someplanets.objects.plants;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class TreeGen extends ObjectGenerator {
	
	public TreeGen(){
		super(70);
	}

	@Override
	public void generate() {
		int height = 10 + rand.nextInt(6);
		float dirx = 0f, dirz = 0f;
		float offsetx = 0, offsetz = 0;
		float switchscl = 3f;

		for (int i = 0; i < height; i++) {
			disc((int) (x + offsetx), y + i, (int) (z + offsetz), 1 + 1.5f*scld(i,height), Blocks.log, 12);

			offsetx += dirx;
			offsetz += dirz;

			dirx = sclamp(dirx + rand.nextFloat() / switchscl - 0.5f / switchscl);
			dirz = sclamp(dirz + rand.nextFloat() / switchscl - 0.5f / switchscl);
		}

		sphere((int) (x + offsetx-dirx*2), y + height + 2, (int) (z + offsetz-dirz*2), 4f, Blocks.leaves);
		
		for(int i = 0; i < 7; i ++){
			vec3.setToRandomDirection();
			double scl = 4;
			sphere((int) (x + offsetx + vec3.x*scl) + rand.nextInt(4), (int)(y + height + 3 + vec3.y*scl), (int) (z + offsetz + vec3.z*scl), 2.3f + rand.nextFloat()*3, Blocks.leaves);
		}
	}
}
