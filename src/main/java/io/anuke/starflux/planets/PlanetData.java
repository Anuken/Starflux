package io.anuke.starflux.planets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.MathUtils;

import cofh.thermalfoundation.block.TFBlocks;
import cpw.mods.fml.common.Loader;
import io.anuke.starflux.objects.ObjectGenerator;
import io.anuke.starflux.objects.alien.*;
import io.anuke.starflux.objects.geological.*;
import io.anuke.starflux.objects.plants.*;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import tconstruct.smeltery.TinkerSmeltery;

/**
 * All float values range 1-0, with 0.5f being "normal", unless otherwise
 * specified. No null values allowed, at all.
 */
public class PlanetData{
	private static final Random rand = new Random();
	public String name; // DONE
	public int id; // DONE
	public float temperature; // DONE
	public float pressure; // DONE
	public float hillyness, spikyness; // DONE
	public float worldScale; // DONE
	public float gravity; // DONE
	public float meteorFrequency; // DONE
	// public PlantType plantType; //bad idea?
	public ArrayList<ObjectGenerator> objects; // DONE
	public float caveSize; // DONE
	public boolean hasCaves; // DONE
	public boolean hasSnow; // DONE
	public BlockMetaPair[][] topBlocks; // X
	public BlockMetaPair stalagctiteBlock;
	public BlockMetaPair stoneBlock;
	public ArrayList<IAtmosphericGas> gases; // DONE
	public ArrayList<MineralDeposit> minerals; // DONE
	public Vec3 skyColor; // DONE
	public Vec3 fogColor; // DONE
	public Vec3 cloudColor; // DONE
	public int waterLevel; // DONE
	public Block surfaceLiquid; // X
	public Block coreLiquid; // X
	public Block coreBlock; // X
	public BlockMetaPair surfaceLiquidSolid; // block used in lakes of the surface. In vanilla, this is sand or clay. [X]
	public CoreType coreType; // DONE
	public Block[][] blocks;

	public static Block[] coreBlocks;

	public static Block[] tempBlocks = { Blocks.packed_ice, Blocks.snow, Blocks.stone, Blocks.sand, Blocks.gravel, Blocks.grass, Blocks.grass, Blocks.clay, Blocks.hardened_clay, Blocks.stained_hardened_clay, Blocks.netherrack, Blocks.soul_sand };

	public static Block[] heightBlocks = { Blocks.grass, Blocks.stone, Blocks.gravel, Blocks.mossy_cobblestone, Blocks.cobblestone, Blocks.grass, Blocks.mycelium, Blocks.dirt, Blocks.clay, Blocks.ice, Blocks.stone, Blocks.snow };

	public static Block[] stoneBlocks = { Blocks.stone, Blocks.soul_sand, Blocks.packed_ice, Blocks.hardened_clay, Blocks.packed_ice, Blocks.obsidian };

	static String[] nameChunks = { "fi", "nl", "it", "num", "kez", "ga", "mu", "na", "inp", "rn", "or", "hy", "pl", "buv", "im", "we", "zu", "ut", "rev", "uf", "og", "wo", "ol", "kn", "zu", "tre", "nk", "ji", "pod", "ch", "mre", "ite", "rs" };

	static Class<?>[] objectClasses = { SpikeGen.class, BushGen.class, ClumpTreeGen.class, GrassPatchGen.class, PineTreeGen.class, TreeGen.class, BoulderGen.class, LargeBoulderGen.class, RockGen.class, RockSpikeGen.class, SpikeGen.class, ClawGen.class, CupFlowerGen.class, GrassTentacleGen.class, OrbFlowerGen.class, StarFlowerGen.class, TentacleGen.class, VineGen.class };

	static enum CoreType{
		molten, frozen, water, none
	}

	static enum PlantType{
		alien, normal, rocky, dead, none
	}

	public static enum Mineral{
		iron(Blocks.redstone_ore), lead(TFBlocks.blockOre, 3), diamond(Blocks.diamond_ore), copper(TFBlocks.blockOre), tin(TFBlocks.blockOre, 1), aluminum(GCBlocks.basicBlock, 7), silver(TFBlocks.blockOre, 2), gold(Blocks.gold_ore), redstone(Blocks.redstone_ore), emerald(Blocks.emerald_ore), coal(Blocks.coal_ore), clay(Blocks.clay), glowstone(Blocks.glowstone), oil(GalacticraftCore.fluidOil.getBlock());

		private BlockMetaPair block;

		private Mineral(Block block) {
			this.block = new BlockMetaPair(block, (byte) 0);
		}

		private Mineral(Block block, int data) {
			this.block = new BlockMetaPair(block, (byte) data);
		}

		public String capitalized(){
			return name().substring(0, 1).toUpperCase() + name().substring(1);
		}

		public BlockMetaPair getBlock(){
			return block;
		}
	}

	public static class MineralDeposit{
		public final Mineral mineral;
		public final float amount;

		public MineralDeposit(Mineral mineral, float amount) {
			this.mineral = mineral;
			this.amount = amount;
		}

		public String toString(){
			return mineral + " x" + (int) (amount * 100);
		}
	}

	private PlanetData() {
	}

	public static void main(String[] args){
		for(int ik = 0; ik < 10; ik++){
			String name = "";

			int length = range(2, 5);
			boolean endsvowel = Math.random() < 0.5;
			for(int i = 0; i < length; i++){
				String next = nameChunks[range(nameChunks.length)];

				while(startsVowel(next) == endsvowel){
					next = nameChunks[range(nameChunks.length)];
				}

				name += next;
				endsvowel = endsVowel(next);
			}

			System.out.println(name);
		}
	}

	public static PlanetData createPlanetData(int id){
		boolean tf = Loader.isModLoaded("ThermalFoundation");
		boolean tc = Loader.isModLoaded("TConstruct");

		PlanetData data = new PlanetData();

		data.name = "";

		int length = range(2, 5);
		boolean endsvowel = Math.random() < 0.5;
		for(int i = 0; i < length; i++){
			String next = nameChunks[range(nameChunks.length)];

			while(startsVowel(next) == endsvowel){
				next = nameChunks[range(nameChunks.length)];
			}

			data.name += next;
			endsvowel = endsVowel(next);
		}

		data.name = data.name.substring(0, 1).toUpperCase() + data.name.substring(1);

		data.id = id;

		data.temperature = range(0f, 1f);
		data.hillyness = range(0f, 1f);
		data.spikyness = range(-0.07f, 0.07f);
		data.worldScale = range(0.2f, 0.9f);
		data.gravity = 0.04f + range(-0.03f, 0.03f);

		data.stoneBlock = new BlockMetaPair(stoneBlocks[range(stoneBlocks.length)], (byte) 0);

		data.gases = new ArrayList<IAtmosphericGas>();

		if(range(6) != 0) // 1 in 6 planets will be dead planets with no
								// atmosphere
			for(IAtmosphericGas gas : IAtmosphericGas.values())
				if(range(3) == 0)
					data.gases.add(gas);

		if(!data.gases.isEmpty())
			data.pressure = range(0f, 1f);

		data.meteorFrequency = range(0.2f, 4f) + data.gases.size();

		data.skyColor = Vec3.createVectorHelper( //
				range(data.temperature / 2f, 1f), // hotter planet, redder sky
				range(0f, 1f), // TODO make green vary as well, maybe with
				// foliage or toxicity?
				range((1f - data.temperature) / 2f, 1f)); // colder planet,
		// bluer sky

		// very similar, for now...
		data.fogColor = data.skyColor.addVector(range(-0.1f, 0.1f), range(-0.1f, 0.1f), range(-0.1f, 0.1f));

		data.cloudColor = data.skyColor.addVector(range(-0.2f, 0.2f), range(-0.2f, 0.2f), range(-0.2f, 0.2f));

		data.minerals = new ArrayList<MineralDeposit>();

		if(data.pressure > 0.6f && data.temperature > 0.6f)
			data.minerals.add(new MineralDeposit(Mineral.diamond, range(0.2f, data.pressure)));

		// TODO make material deposit amounts depend on pressure and heat

		float add = data.pressure / 8f;
		for(Mineral m : Mineral.values()){
			if(m != Mineral.diamond)
				data.minerals.add(new MineralDeposit(m, (range(3) == 0 ? range(0f, 0.1f) : add + (range(4) == 0 ? range(0.3f, 1f) : range(0, 0.6f)))));
		}

		if(data.temperature <= 0.5 && range(3) == 0){
			data.coreType = CoreType.frozen;
		}else if(data.temperature <= 0.5 && range(5) == 0){
			data.coreType = CoreType.water;
		}else if(range(2) == 0){
			data.coreType = CoreType.molten;
		}else{
			data.coreType = CoreType.none;
		}

		// pick the surface liquid
		Block surfaceLiquid = Blocks.air;

		// high temperature
		if(data.temperature > 0.8f){

			// try to use a molten metal liquid, if possible
			if(tc && range(2) == 0){
				MineralDeposit max = null;
				for(MineralDeposit mineral : data.minerals){
					if(max == null || mineral.amount > max.amount){
						max = mineral;
					}
				}
				// use the block if its amount is > 0.8
				if(max != null && max.amount > 0.8f){
					try{
						Field field = TinkerSmeltery.class.getField("molten" + max.mineral.capitalized() + "Fluid");
						surfaceLiquid = (Block) field.get(null);
					}catch(Exception e){
					}
				}else if(data.pressure > 0.8f){
					surfaceLiquid = TinkerSmeltery.moltenGlass;
				}else{
					surfaceLiquid = Blocks.lava;
				}
			}else if(tf){
				if(data.pressure > 0.9f){
					surfaceLiquid = TFBlocks.blockFluidCoal;
				}else if(range(2) == 0){
					surfaceLiquid = (new Block[] { TFBlocks.blockFluidEnder, TFBlocks.blockFluidRedstone, TFBlocks.blockFluidPetrotheum })[range(3)];
				}else{
					surfaceLiquid = Blocks.lava;
				}
			}else{ // no mods loaded/usable, just use lava for the liquid
				surfaceLiquid = Blocks.lava;
			}
		}else if(data.temperature < 0.2f){ // low temperature
			if(tf){
				surfaceLiquid = Math.random() < 0.5 ? TFBlocks.blockFluidCryotheum : TFBlocks.blockFluidEnder;
			}else{
				surfaceLiquid = Blocks.ice;
			}
		}else{ //normal temperature
			if(range(2) == 0){
				surfaceLiquid = Blocks.water;
			}else if(tf && range(3) == 0){
				surfaceLiquid = TFBlocks.blockFluidRedstone;
			}else{
				surfaceLiquid = Blocks.water;
			}
		}

		data.surfaceLiquid = surfaceLiquid;

		Block coreLiquid = Blocks.air;

		if(data.coreType == CoreType.molten){
			// TODO remove this
			data.coreBlock = Blocks.netherrack;
			coreLiquid = TFBlocks.blockFluidPyrotheum;
			if(data.temperature > 0.7f){

				// try to use a molten metal liquid, if possible
				if(tc){

				}else if(tf){

				}else{ // no mods loaded, just use lava for the liquid
					surfaceLiquid = Blocks.lava;
				}
			}
			// TODO
		}else if(data.coreType == CoreType.frozen){
			data.coreBlock = Blocks.packed_ice;
			coreLiquid = TFBlocks.blockFluidCryotheum;

			if(tf){
				if(data.temperature < 0.2){
					coreLiquid = TFBlocks.blockFluidCryotheum;
				}
				// TODO
			}
		}else if(data.coreType == CoreType.water){
			data.coreBlock = data.stoneBlock.getBlock();
			coreLiquid = Blocks.water;
		}

		data.coreBlock = stoneBlocks[range(stoneBlocks.length)];

		data.coreLiquid = coreLiquid;

		// data.plantType = (data.temperature < 0.12f || data.temperature >
		// 0.78f) ? (range(2) == 0 ? PlantType.dead : PlantType.rocky)

		data.hasSnow = data.temperature < 0.3f && range(3) != 0; // snow only in low temperatures

		data.hasCaves = data.pressure < 0.8 && range(8) != 0; // very high pressure planets don't have caves, and 1 in 8don't have them either

		data.caveSize = range(0f, 1f);

		data.waterLevel = range(30, 70);

		// ocean planets are 1 out of 6
		if(range(6) == 0)
			data.waterLevel = range(90, 140);

		int bsize = 6;

		data.blocks = new Block[bsize][bsize];

		for(int x = 0; x < bsize; x++){
			for(int y = 0; y < bsize; y++){
				data.blocks[x][y] = x < y ? tempBlocks[clamp((float) x / bsize * tempBlocks.length / 2 + data.temperature * tempBlocks.length / 2 + MathUtils.random(-2, 2), 0, tempBlocks.length - 1)] : heightBlocks[clamp((float) y / bsize * heightBlocks.length / 2 + data.hillyness * heightBlocks.length / 2 + MathUtils.random(-2, 2), 0, heightBlocks.length - 1)];
			}
		}

		data.objects = new ArrayList<ObjectGenerator>();

		for(Class<?> c : objectClasses){
			try{
				for(int i = 0; i < range(1, 2); i++){
					ObjectGenerator gen = (ObjectGenerator) c.newInstance();
					if(gen.add(data)){
						gen.setup(data);
						data.objects.add(gen);
					}
				}
			}catch(Exception e){

			}
		}
		
		data.surfaceLiquidSolid = new BlockMetaPair(stoneBlocks[range(stoneBlocks.length)], (byte)0);

		System.out.println("Generated planet data: \n" + data.toString());

		return data;
	}

	static int clamp(float a, int b, int c){
		if(a < b)
			return b;
		if(a > c)
			return b;
		return (int) a;
	}

	public boolean hasAtmosphere(){
		return !gases.isEmpty();
	}

	public String toString(){
		try{
			StringBuilder build = new StringBuilder();

			for(Field field : getClass().getFields()){
				build.append("" + field.getName() + ": [");
				build.append(field.get(this));
				build.append("]\n");
			}

			return build.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "Illegal Access Exception!";
		}
	}

	static boolean startsVowel(String s){
		return "auioye".indexOf(s.charAt(0)) != -1;
	}

	static boolean endsVowel(String s){
		return "auioye".indexOf(s.charAt(s.length() - 1)) != -1;
	}

	static float range(float a, float b){
		return a + rand.nextFloat() * (b - a);
	}

	static int range(int a){
		return rand.nextInt(a);
	}

	static int range(int a, int b){
		return a + rand.nextInt(b - a);
	}
}
