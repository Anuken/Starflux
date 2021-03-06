package io.anuke.starflux.generation;

import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.planets.PlanetData.MineralDeposit;

public class ProceduralBiomeDecorator extends BasicDecorator{
	final PlanetData data;
	
	public ProceduralBiomeDecorator(PlanetData data){
		this.data = data;
		
		for(MineralDeposit mineral : data.minerals){
			addGenerator(mineral.mineral.getBlock().getBlock(), mineral.mineral.getBlock().getMetadata(), data.stoneBlock.getBlock(), (int)(mineral.amount*20), (int)(mineral.amount*60), 0, 120);
		}
		
		addObjectGenerators(data.objects);
	}
}
