package io.anuke.starflux.objects.structures;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.planets.PlanetData;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BaseGen extends ObjectGenerator{

	public boolean add(PlanetData data){
		return true;
	}

	@Override
	public void generate(){
		int rad = 6;
		y += 8;
		
		for(int ry = 0; ry < 7; ry++){
			for(int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++){
				for(int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++){
					float dst = dst(rx, rz, 0, 0);
					boolean place = rand.nextFloat()*(ry/3f)+0.1f < 1f;
					if(place && dst < rad && ((ry == 0 || ry == 6) || dst > rad-1.5f)){
						Block block = GCBlocks.basicBlock;
						int data = 4;
						
						if(dst > rad-1.5f && (ry == 0 || ry == 6)){
							data = 11;
						}
							
						setBlock(x + rx, y + ry, z + rz, block, data);
					}
				}
			}
		}
		
		if(Math.random() < 0.5){
			Vector2 vector = new Vector2().setToRandomDirection();
			setBlock(x + (int)((rad-1)*vector.x), y + 1, z + (int)((rad-1)*vector.y), GCBlocks.oxygenSealer);
		}
		
		disc(x,y,z, rad+1.5f, GCBlocks.landingPad);
		
		setBlock(x, y+1, z+rad, Blocks.air);
		setBlock(x, y+2, z+rad, Blocks.air);
		
		rad --;
		
		int steps = 4;
		
		boolean[] stop = new boolean[steps];
		
		for(int des = y-1; des > 0; des --){
			
			boolean dostop = true;
			
			for(int i = 0; i < steps; i ++){
				dostop = dostop && stop[i];
				
				if(stop[i]) continue;
				int px = x + (int)(MathUtils.sin(i*MathUtils.PI*2/steps)*rad), pz = z + (int)(MathUtils.cos(i*MathUtils.PI*2/steps)*rad);
				
				stop[i] = world.getBlock(px, des, pz).getMaterial().isSolid();
				setBlock(px, des, pz, GCBlocks.basicBlock, 4);
			}
			
			if(dostop) break;
		}
	}
}
