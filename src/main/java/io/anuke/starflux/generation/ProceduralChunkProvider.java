package io.anuke.starflux.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.anuke.starflux.noise.Noise;
import io.anuke.starflux.noise.RidgedPerlin;
import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.util.ChunkProviderAdapter;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class ProceduralChunkProvider extends ChunkProviderAdapter {
	float scale = 0.3f;
	int water = 40;
	int lava = 7;
	int bedrock = 1;
	int lavacave = 18;
	RidgedPerlin rid = new RidgedPerlin(2, 1, 0.4f);
	RidgedPerlin cave = new RidgedPerlin(3, 2, 0.4f);
	RidgedPerlin cave2 = new RidgedPerlin(4, 1, 0.4f);
	BiomeDecoratorSpace decorator;
	List<MapGenBaseMeta> generators = new ArrayList<MapGenBaseMeta>();
	SpawnListEntry[] creatures = {};
	SpawnListEntry[] monsters = {};
	private Block[] ids;
	private byte[] metas;
	private Block[] writeids = new Block[32768 * 2];
	Random rand = new Random();
	private int chunkx, chunkz;
	public PlanetData data;

	public ProceduralChunkProvider(PlanetData data, World world, long seed, boolean mapFeaturesEnabled) {
		super(world, seed, mapFeaturesEnabled);
		rand.setSeed(seed);
		this.data = data;
		decorator = new ProceduralBiomeDecorator(data);
	}

	@Override
	public void generateTerrain(int chunkX, int chunkZ, Block[] idArray, byte[] metaArray) {
		this.ids = idArray;
		this.metas = metaArray;
		this.chunkx = chunkX;
		this.chunkz = chunkZ;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int wx = chunkX * 16 + x, wz = chunkZ * 16 + z;

				int nx = wx + 99999;
				int nz = wz + 99999;

				float elevation = getElevation(wx, wz);
				double elevationmaterial = (Noise.nnoise(wx, wz, 60, 0.125f / 2));
				double temp = getTemperature(wx, wz);
				double height = terrainHeight(elevation);
				double lavacaveheight = lavacave + Noise.nnoise(nx, nz, 70, 8f) + Noise.nnoise(nx, nz, 10, 8f)
						+ Noise.nnoise(nx, nz, 30, 8f) + Noise.nnoise(nx, wz, 5, 6f);
				double lavafloorheight = bedrock + 2 + Noise.nnoise(nx, nz, 20, 20f) + Noise.nnoise(nx, nz, 10, 18f)
						+ Noise.nnoise(nx, nz, 3, 12f) + Noise.nnoise(nx, nz, 7, 27f);
				double bedrockheight = bedrock + Noise.nnoise(wx, nz, 30, 2f) + Noise.nnoise(wx, nz, 10, 2f);

				for (int y = 0; y < Math.max(height, water); y++) {
					Block block = Blocks.stone;

					// top block
					if (y + 1 >= height) {
						if (temp < 0.4f) {
							block = Blocks.grass;
						} else if (temp < 0.56f) {
							block = Blocks.clay;
						} else if (temp < 0.8f) {
							block = Blocks.hardened_clay;
						} else if (temp < 1f) {
							block = Blocks.obsidian;
						}
					}

					if (y + 1 >= height)
						if (elevationmaterial > 0.8) {
							block = Blocks.packed_ice;
						} else if (elevationmaterial > 0.7) {
							block = Blocks.snow;
						} else if (elevationmaterial > 0.6) {
							block = Blocks.stone;
						}

					if (y >= height)
						block = data.surfaceLiquid;
					
					if (data.hasCaves && cave.getValue(wx, y, wz, 0.009f) / 2.3f + cave2.getValue(wx, y, wz, 0.01f) / 3.3f
							+ Noise.normalNoise(wx, y, wz, 50f, 0.7f) + Noise.normalNoise(wx, y, wz, 25f, 0.25f)
							+ Noise.normalNoise(wx, y, wz, 11f, 0.25f)
							+ Noise.normalNoise(wx, y, wz, 8f, 0.15f) >= 0.78f && block != data.surfaceLiquid)
						block = Blocks.air;

					if (y < lavacaveheight)
						block = Blocks.air;

					if (y < lava)
						block = data.coreLiquid;

					if (y <= lavafloorheight)
						block = Blocks.stone;

					if (y <= bedrockheight)
						block = Blocks.bedrock;

					idArray[getIndex(x, y, z)] = block;
					metaArray[getIndex(x, y, z)] = 0;

					if (y + 1 >= height) {
						genTopBlock(x, y, z);
					}
				}
			}
		}

		beginWrite();

		postGenerate();

		endWrite();
	}

	void beginWrite() {
		for (int i = 0; i < ids.length; i++)
			writeids[i] = ids[i];
	}

	void endWrite() {
		for (int i = 0; i < ids.length; i++)
			ids[i] = writeids[i];
	}

	float terrainHeight(float elevation) {
		return 30 + elevation * 160;
	}

	void genTopBlock(int x, int y, int z) {
		if (!data.hasSnow) {
			if (getBlock(x, y, z) == Blocks.grass && rand.nextFloat() < 0.2f)
				setBlock(x, y + 1, z, Blocks.tallgrass, 1);
		} else if(getBlock(x, y, z) != null && getBlock(x, y, z).getMaterial().isSolid()){
			float elevation = terrainHeight(getElevation(x+chunkx*16, z+chunkz*16));
			setBlock(x, y + 1, z, Blocks.snow_layer, (int)((elevation%1)*8));
		}
	}

	void postGenerate() {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int wx = chunkx * 16 + x, wz = chunkz * 16 + z;
				float height = terrainHeight(getElevation(wx, wz));
				float temp = getTemperature(wx, wz);
				for (int y = 0; y < height; y++) {
					Block above = ids[getIndex(x, y + 1, z)];
					Block block = ids[getIndex(x, y, z)];
					// Block below = ids[getIndex(x, y-1, z)];

					if (rand.nextInt(12) == 0)
						if (Blocks.air == block && Blocks.stone == above) {
							int len = this.rand.nextInt(4) + 1;
							for (int i = 0; i < len && y - i > 0; i++) {
								setWriteBlock(x, y - i, z, Blocks.stone);
							}
						}

					if (above == Blocks.air && block == Blocks.stone) {
						if (temp > 0.7f) {
							setWriteBlock(x, y, z, Blocks.netherrack);
						} else if (temp < 0.35f && y > lavacave) {
							setWriteBlock(x, y, z, Blocks.packed_ice);
						}
					}
				}

			}
		}
	}

	double clamp(double a) {
		return a < 0f ? 0f : (a > 1f ? 1f : a);
	}

	int getIndex(int x, int y, int z) {
		return (x * 16 + z) * 256 + y;
	}

	public int getTopBlock(int x, int z) {
		for (int y = 255; y > 0; y--)
			if (getBlock(x, y, z) != null && getBlock(x, y, z).getMaterial().isSolid())
				return y;

		return 0;
	}

	void setBlock(int x, int y, int z, Block block) {
		ids[getIndex(x, y, z)] = block;
	}

	void setBlock(int x, int y, int z, Block block, int data) {
		ids[getIndex(x, y, z)] = block;
		metas[getIndex(x, y, z)] = (byte) data;
	}

	Block getBlock(int x, int y, int z) {
		return ids[getIndex(x, y, z)];
	}

	void setWriteBlock(int x, int y, int z, Block block) {
		writeids[getIndex(x, y, z)] = block;
	}

	public float getElevation(int x, int y) {
		x += 999999;
		y += 999999;

		double elevation = 0.4f;
		float octave = 1200f * scale;

		elevation += (Noise.nnoise(x, y, octave, 1f));
		elevation += (Noise.nnoise(x, y, octave / 2, 0.5f));
		elevation += (Noise.nnoise(x, y, octave / 4, 0.25f));
		elevation += (Noise.nnoise(x, y, octave / 8, 0.125f));
		elevation += (Noise.nnoise(x, y, octave / 16, 0.125f / 2));
		elevation += (Noise.nnoise(x, y, octave / 32, 0.125f / 4));
		elevation += (Noise.nnoise(x, y, octave / 128, 0.125f / 16));
		// elevation += (Noise.nnoise(x, y, octave / 128, 0.125f));
		elevation += (rid.getValue(x, y, 0, 0.006f) + 0.5f) / 20f;
		elevation += (Noise.nnoise(x, y, octave / 32, 0.125f / 4));

		elevation /= 0.84;

		elevation = clamp(elevation);

		return (float) elevation;
	}

	public float getTemperature(int x, int y) {
		x += 99999 * 2;
		y += 99999 * 2;

		double temp = 0.5f;
		float octave = 800f;

		temp += (Noise.nnoise(x, y, octave, 1f));
		temp += (Noise.nnoise(x, y, octave / 2, 0.5f));
		temp += (Noise.nnoise(x, y, octave / 4, 0.25f));
		temp += (Noise.nnoise(x, y, octave / 8, 0.125f));
		// temp += (Noise.nnoise(x, y, octave/16, 0.25f));
		temp += (Noise.nnoise(x, y, octave / 32, 0.125f));
		temp += (Noise.nnoise(x, y, octave / 64, 0.125f / 2));
		temp += (Noise.nnoise(x, y, octave / 128, 0.125f / 2));

		temp /= 1.05;

		temp = clamp(temp);

		return (float) temp;
	}

	// do nothing, we don't want this crap
	@Override
	public void replaceBlocksForBiome(int par1, int par2, Block[] arrayOfIDs, byte[] arrayOfMeta,
			BiomeGenBase[] par4ArrayOfBiomeGenBase) {
	}

	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
		return decorator;
	}

	@Override
	protected SpawnListEntry[] getCreatures() {
		return creatures;
	}

	@Override
	protected SpawnListEntry[] getMonsters() {
		return monsters;
	}

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators() {
		return generators;
	}
}
