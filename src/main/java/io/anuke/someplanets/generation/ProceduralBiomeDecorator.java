package io.anuke.someplanets.generation;

import io.anuke.someplanets.objects.alien.ClawGen;
import io.anuke.someplanets.objects.alien.CupFlowerGen;
import io.anuke.someplanets.objects.alien.GrassTentacleGen;
import io.anuke.someplanets.objects.alien.OrbFlowerGen;
import io.anuke.someplanets.objects.alien.StarFlowerGen;
import io.anuke.someplanets.objects.alien.TentacleGen;
import io.anuke.someplanets.objects.alien.VineGen;
import io.anuke.someplanets.objects.geological.LargeBoulderGen;
import io.anuke.someplanets.objects.geological.RockGen;
import io.anuke.someplanets.objects.geological.RockSpikeGen;
import io.anuke.someplanets.objects.plants.BushGen;
import io.anuke.someplanets.objects.plants.ClumpTreeGen;
import io.anuke.someplanets.objects.plants.GrassPatchGen;
import io.anuke.someplanets.objects.plants.GrassThingGen;
import io.anuke.someplanets.objects.plants.PineTreeGen;
import io.anuke.someplanets.objects.plants.TreeGen;
import io.anuke.someplanets.util.BasicDecorator;
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
