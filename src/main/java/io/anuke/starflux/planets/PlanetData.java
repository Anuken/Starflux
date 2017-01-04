package io.anuke.starflux.planets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import cofh.thermalfoundation.block.TFBlocks;
import cpw.mods.fml.common.Loader;
import io.anuke.starflux.objects.ObjectGenerator;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import tconstruct.smeltery.TinkerSmeltery;

/**
 * All float values range 1-0, with 0.5f being "normal", unless otherwise specified. No null values allowed, at all.
 */
public class PlanetData {
	private static final Random rand = new Random();
	public String name; // DONE
	public int id; // DONE
	public float temperature; // DONE
	public float pressure; // DONE
	public float hillyness; // DONE
	public float worldScale; // DONE
	public float gravity; // DONE
	public float meteorFrequency; // DONE
	// public PlantType plantType; //bad idea?
	public ObjectGenerator[] objects; // DONE
	public float caveSize; // DONE
	public boolean hasCaves; // DONE
	public boolean hasSnow; // DONE
	public BlockMetaPair[] heightBlocks; // X
	public BlockMetaPair[] temperatureBlocks; // X
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

 public static Block[][] coreTemperatureBlocks;

	static String[] nameChunks = { "fi", "nl", "it", "num", "kez", "ga", "mu", "na", "inp", "rn", "or", "hy", "pl", "buv", "im", "we", "zu", "ut", "rev", "uf", "og", "wo", "ol", "kn", "zu", "tre", "nk", "ji", "pod",
			"ch", "mre", "ite", "rs" };

	static enum CoreType {
		molten, frozen, water, none
	}

	static enum PlantType {
		alien, normal, rocky, dead, none
	}

	static enum Mineral {
		iron, lead, diamond, copper, tin, aluminum, gold, redstone, emerald, coal, clay, glowstone;

		public String capitalized() {
			return name().substring(0, 1).toUpperCase() + name().substring(1);
		}
	}

	static class MineralDeposit {
		public final Mineral mineral;
		public final float amount;

		public MineralDeposit(Mineral mineral, float amount) {
			this.mineral = mineral;
			this.amount = amount;
		}

		public String toString() {
			return mineral + " x" + (int) (amount * 100);
		}
	}

	private PlanetData() {
	}

	public static PlanetData createPlanetData(int id) {
		PlanetData data = new PlanetData();

		data.name = "";

		int length = range(2, 5);
		for (int i = 0; i < length; i++)
			data.name += nameChunks[range(nameChunks.length)];

		data.name = data.name.substring(0, 1).toUpperCase() + data.name.substring(1);

		data.id = id;

		data.temperature = range(0f, 1f);
		data.hillyness = range(0f, 1f);
		data.pressure = range(0f, 1f);
		data.worldScale = range(0.2f, 0.9f);
		data.gravity = 0.04f + range(-0.03f, 0.03f);
		
		data.stoneBlock = new BlockMetaPair(Blocks.stone, (byte)0);

		data.gases = new ArrayList<IAtmosphericGas>();

		if (range(6) != 0) // 1 in 6 planets will be dead planets with no atmosphere
			for (IAtmosphericGas gas : IAtmosphericGas.values())
				if (range(3) == 0)
					data.gases.add(gas);

		data.meteorFrequency = range(0.2f, 4f) + data.gases.size();

		data.skyColor = Vec3.createVectorHelper( //
				range(data.temperature / 2f, 1f), // hotter planet, redder sky
				range(0f, 1f), // TODO make green vary as well, maybe with foliage or toxicity?
				range((1f - data.temperature) / 2f, 1f)); // colder planet, bluer sky

		// very similar, for now...
		data.fogColor = data.skyColor.addVector(range(-0.1f, 0.1f), range(-0.1f, 0.1f), range(-0.1f, 0.1f));

		data.cloudColor = data.skyColor.addVector(range(-0.2f, 0.2f), range(-0.2f, 0.2f), range(-0.2f, 0.2f));

		data.minerals = new ArrayList<MineralDeposit>();

		if (data.pressure > 0.6f && data.temperature > 0.6f)
			data.minerals.add(new MineralDeposit(Mineral.diamond, range(0.2f, data.pressure)));

		// TODO make material deposit amounts depend on pressure and heat

		float add = data.pressure / 8f;
		for (Mineral m : Mineral.values()) {
			if (m != Mineral.diamond)
				data.minerals.add(new MineralDeposit(m, (range(5) == 0 ? range(0f, 0.1f) : add + (range(4) == 0 ? range(0.3f, 1f) : range(0, 0.6f)))));
		}

		if (data.temperature <= 0.5 && range(3) == 0) {
			data.coreType = CoreType.frozen;
		} else if (data.temperature <= 0.5 && range(5) == 0) {
			data.coreType = CoreType.water;
		} else if (range(2) == 0) {
			data.coreType = CoreType.molten;
		} else {
			data.coreType = CoreType.none;
		}

		// pick the surface liquid
		Block surfaceLiquid = Blocks.air;

		// high temperature
		if (data.temperature > 0.8f) {

			// try to use a molten metal liquid, if possible
			if (Loader.isModLoaded("TConstruct") && range(2) == 0) {
				MineralDeposit max = null;
				for (MineralDeposit mineral : data.minerals) {
					if (max == null || mineral.amount > max.amount) {
						max = mineral;
					}
				}
				//use the block if its amount is > 0.8
				if (max != null && max.amount > 0.8f) {
					try {
						Field field = TinkerSmeltery.class.getField("molten" + max.mineral.capitalized() + "Fluid");
						surfaceLiquid = (Block)field.get(null);
					} catch (Exception e) {}
				}else if(data.pressure > 0.8f){
					surfaceLiquid = TinkerSmeltery.moltenGlass;
				}else{
					surfaceLiquid = Blocks.lava;
				}
			} else if (Loader.isModLoaded("ThermalFoundation")) {
				if(data.pressure > 0.9f){
					surfaceLiquid = TFBlocks.blockFluidCoal;
				}else if(range(2) == 0){
					surfaceLiquid = (new Block[]{TFBlocks.blockFluidEnder, TFBlocks.blockFluidRedstone, TFBlocks.blockFluidAerotheum})[range(3)];
				}else{
					surfaceLiquid = Blocks.lava;
				}
			} else { // no mods loaded/usable, just use lava for the liquid
				surfaceLiquid = Blocks.lava;
			}
		}else if(data.temperature <  0.2f){ //low temperature
			if(Loader.isModLoaded("ThermalFoundation")){
				
			}
		}

		data.surfaceLiquid = surfaceLiquid;

		Block coreLiquid = Blocks.air;

		if (data.coreType == CoreType.molten) {
			//TODO remove this
			data.coreBlock = Blocks.netherrack;
			data.coreLiquid = TFBlocks.blockFluidPyrotheum;
			if (data.temperature > 0.8f) {

				// try to use a molten metal liquid, if possible
				if (Loader.isModLoaded("TConstruct")) {

				} else if (Loader.isModLoaded("ThermalFoundation")) {

				} else { // no mods loaded, just use lava for the liquid
					surfaceLiquid = Blocks.lava;
				}
			}
			// TODO
		} else if (data.coreType == CoreType.frozen) {
			data.coreBlock = Blocks.packed_ice;
			coreLiquid = TFBlocks.blockFluidCryotheum;

			if (Loader.isModLoaded("ThermalFoundation")) {
				if (data.temperature < 0.2) {
					coreLiquid = TFBlocks.blockFluidCryotheum;
				}
				// TODO
			}
		}else if(data.coreType == CoreType.water){
			data.coreBlock = data.stoneBlock.getBlock();
			coreLiquid = Blocks.water;
		}

		data.coreLiquid = coreLiquid;

		// data.plantType = (data.temperature < 0.12f || data.temperature > 0.78f) ? (range(2) == 0 ? PlantType.dead : PlantType.rocky)

		data.objects = new ObjectGenerator[] {};

		data.hasSnow = data.temperature < 0.3f && range(3) != 0; // snow only in low temperatures

		data.hasCaves = data.pressure < 0.8 && range(8) != 0; // very high pressure planets don't have caves, and 1 in 8 don't have them either

		data.caveSize = range(0f, 1f);

		data.waterLevel = range(30, 70);

		// ocean planets are 1 out of 8
		if (range(8) == 0)
			data.waterLevel = range(90, 140);

		System.out.println("Generated planet data: \n" + data.toString());

		return data;
	}

	public boolean hasAtmosphere() {
		return !gases.isEmpty();
	}

	public String toString() {
		try {
			StringBuilder build = new StringBuilder();

			for (Field field : getClass().getFields()) {
				build.append("" + field.getName() + ": [");
				build.append(field.get(this));
				build.append("]\n");
			}

			return build.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Illegal Access Exception!";
		}
	}

	static float range(float a, float b) {
		return a + rand.nextFloat() * (b - a);
	}

	static int range(int a) {
		return rand.nextInt(a);
	}

	static int range(int a, int b) {
		return a + rand.nextInt(b - a);
	}
}
