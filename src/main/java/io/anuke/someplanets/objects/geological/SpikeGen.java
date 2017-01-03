package io.anuke.someplanets.objects.geological;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class SpikeGen extends ObjectGenerator{
	
	public SpikeGen() {
		super(100);
	}

	Block blocka, blockb;
	int minrad, maxrad;
	int clusterchance;
	int chance;

	public void generate() {
		if(getBlock(x, y-1, z) == Blocks.quartz_block || getBlock(x, y-1, z) == Blocks.packed_ice
				|| getBlock(x, y, z) == Blocks.quartz_block || getBlock(x, y, z) == Blocks.packed_ice) return;
		
		genSpike();
		
		if(rand.nextInt(4) == 0) genSpike();
		
		if(rand.nextInt(6) == 0)
			for(int i = 0; i < 3; i ++)
				genSpike();
	}
	
	void genSpike(){
		float rad = 4 + rand.nextFloat()*4;
		float slope = 0.07f+rand.nextFloat()/12f;
		float angx = rand.nextFloat()*2f-2f/2;
		float angz = rand.nextFloat()*2f-2f/2;
		
		//generate the base
		for(int i = 0; i > -16; i --){
			disc(x, y+i, z, rad-i/3f, Blocks.quartz_block);
		}
		
		//generate actual spike
		for(int i = 0; rad >= 0.5f; i ++){
			disc((int)(x+i*angx),y+i,(int)(z+i*angz), rad, Blocks.quartz_block, Blocks.packed_ice, i/(rad/slope));
			rad -= slope;
		}
	}
}
