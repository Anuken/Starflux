package io.anuke.starflux.generation;

import io.anuke.starflux.objects.alien.ClawGen;
import io.anuke.starflux.objects.alien.CupFlowerGen;
import io.anuke.starflux.objects.alien.GrassTentacleGen;
import io.anuke.starflux.objects.alien.OrbFlowerGen;
import io.anuke.starflux.objects.alien.StarFlowerGen;
import io.anuke.starflux.objects.alien.TentacleGen;
import io.anuke.starflux.objects.alien.VineGen;
import io.anuke.starflux.objects.geological.LargeBoulderGen;
import io.anuke.starflux.objects.geological.RockGen;
import io.anuke.starflux.objects.geological.RockSpikeGen;
import io.anuke.starflux.objects.plants.BushGen;
import io.anuke.starflux.objects.plants.ClumpTreeGen;
import io.anuke.starflux.objects.plants.GrassPatchGen;
import io.anuke.starflux.objects.plants.GrassThingGen;
import io.anuke.starflux.objects.plants.PineTreeGen;
import io.anuke.starflux.objects.plants.TreeGen;
import io.anuke.starflux.util.BasicDecorator;
import net.minecraft.init.Blocks;

public class ProceduralBiomeDecorator extends BasicDecorator{
	
	public ProceduralBiomeDecorator(){
		addGenerator(Blocks.iron_ore, 0, Blocks.stone, 20, 30, 0, 120);
		addGenerator(Blocks.redstone_ore, 0, Blocks.stone, 20, 10, 0, 120);
		
		//addObjectGenerator(new BoulderGen());
		//addObjectGenerator(new SpikeGen());
		addObjectGenerators(
		new RockSpikeGen(),
		new BushGen(),
		new VineGen(),
		new OrbFlowerGen(),
		new CupFlowerGen(),
		new StarFlowerGen(),
		new ClawGen(),
		new TentacleGen(),
		new GrassTentacleGen(),
		new GrassPatchGen(),
		new GrassThingGen(),
		new PineTreeGen(),
		new PineTreeGen(){{snow=true;}},
		new RockGen(),
		new LargeBoulderGen(),
		new TreeGen(),
		new ClumpTreeGen()
		);
	}
}
