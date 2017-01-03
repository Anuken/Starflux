package io.anuke.someplanets.util;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.ChunkProviderSpace;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class ChunkProviderAdapter extends ChunkProviderSpace{
	BiomeGenBase[] biomes = {BiomeGenBase.forest};

	public ChunkProviderAdapter(World par1World, long seed, boolean mapFeaturesEnabled) {
		super(par1World, seed, mapFeaturesEnabled);
	}

	@Override
	protected BiomeGenBase[] getBiomesForGeneration() {
		return biomes;
	}

	@Override
	public int getCraterProbability() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(Blocks.dirt, (byte)0);
	}

	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(Blocks.dirt, (byte)0);
	}

	@Override
	public double getHeightModifier() {
		return 0;
	}

	@Override
	public double getMountainHeightModifier() {
		return 0;
	}

	@Override
	protected int getSeaLevel() {
		return 0;
	}

	@Override
	public double getSmallFeatureHeightModifier() {
		return 0;
	}

	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(Blocks.dirt, (byte)0);
	}

	@Override
	public double getValleyHeightModifier() {
		return 0;
	}

	@Override
	public void onChunkProvide(int arg0, int arg1, Block[] arg2, byte[] arg3) {
		
	}

	@Override
	public void onPopulate(IChunkProvider arg0, int arg1, int arg2) {
		
	}
}
