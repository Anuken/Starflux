package io.anuke.someplanets.generation;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class ProceduralChunkManager extends WorldChunkManagerSpace{
	@Override
    public BiomeGenBase getBiome() {
        return BiomeGenBase.extremeHills;
    }
}
