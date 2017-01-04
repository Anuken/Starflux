package io.anuke.starflux.generation;

import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.util.BasicDecorator;
import net.minecraft.init.Blocks;

public class ProceduralBiomeDecorator extends BasicDecorator{
	final PlanetData data;
	
	public ProceduralBiomeDecorator(PlanetData data){
		this.data = data;
		
		addGenerator(Blocks.iron_ore, 0, Blocks.stone, 20, 30, 0, 120);
		addGenerator(Blocks.redstone_ore, 0, Blocks.stone, 20, 10, 0, 120);
		
		//addObjectGenerator(new BoulderGen());
		//addObjectGenerator(new SpikeGen());
		
		addObjectGenerators(data.objects);
	}
}
