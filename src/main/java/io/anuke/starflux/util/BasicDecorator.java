package io.anuke.starflux.util;

import java.util.ArrayList;
import java.util.Random;

import io.anuke.starflux.objects.ObjectGenerator;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class BasicDecorator extends BiomeDecoratorSpace {
	private World world;
	private ArrayList<OreGenerator> oregenerators = new ArrayList<OreGenerator>();
	private ArrayList<ObjectGenerator> objectgenerators = new ArrayList<ObjectGenerator>();
	private boolean locked; //prevents nested decoration

	public void addGenerator(Block block, int data, Block replace, int count, int amount, int miny, int maxy) {
		WorldGenerator gen = new WorldGenMinableMeta(block, count, data, true, replace, 0);
		OreGenerator ore = new OreGenerator();
		ore.amount = amount;
		ore.miny = miny;
		ore.maxy = maxy;
		ore.gen = gen;
		oregenerators.add(ore);
	}

	public void addObjectGenerator(ObjectGenerator gen) {
		objectgenerators.add(gen);
	}
	
	public void addObjectGenerators(Iterable<ObjectGenerator> gens) {
		for(ObjectGenerator gen : gens)
			objectgenerators.add(gen);
	}

	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ) {
		if(locked) return;
		locked = true;
		this.setCurrentWorld(world);
		this.rand = random;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.decorate();
		locked = false;
	}

	@Override
	protected void decorate() {
		for (OreGenerator gen : oregenerators)
			generateOre(gen.amount, gen.gen, gen.miny, gen.maxy);

		for (ObjectGenerator gen : objectgenerators)
			gen.generate(this.getCurrentWorld(), this.rand, this.chunkX+8,
					getCurrentWorld().getTopSolidOrLiquidBlock(chunkX+8, chunkZ+8), this.chunkZ+8);

	}

	@Override
	protected World getCurrentWorld() {
		return world;
	}

	@Override
	protected void setCurrentWorld(World w) {
		this.world = w;
	}

	class OreGenerator {
		int amount;
		int miny, maxy;
		WorldGenerator gen;
	}
}
