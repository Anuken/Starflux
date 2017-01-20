package io.anuke.starflux.generation;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.anuke.starflux.Starflux;
import io.anuke.starflux.planets.ProceduralPlanet;
import io.anuke.starflux.util.DefaultCloudRenderer;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class ProceduralWorldProvider extends WorldProviderSpace implements IExitHeight, ISolarLevel, ITeleportType{
	public ProceduralPlanet planet;
	
	//this gets called right after creation.
	//use it to load the planet data
	@Override
    public void setDimension(int dim){
        this.dimensionId = dim;
        this.planet = Starflux.getPlanetByID(dim);
    }
	
    @Override
    public boolean canSpaceshipTierPass(int tier) {
        return tier >= 1;
    }
 
    @Override
    public float getFallDamageModifier() {
        return 0.75f;
    }
 
    @Override
    public double getFuelUsageMultiplier() {
        return 0.8;
    }
    
    @Override
    public boolean canDoLightning(Chunk chunk){
        return true;
    }
 
    @Override
    public float getGravity() {
        return 0.03F;
    }
 
    @Override
    public double getMeteorFrequency() {
        return planet.data.meteorFrequency;
    }
 
    @Override
    public float getSoundVolReductionAmount() {
        return 1f;
    }
 
    @Override
    public float getThermalLevelModifier() {
        return (planet.data.temperature - 0.5f)*6;
    }
 
    @Override
    public float getWindLevel() {
        return 2f;
    }
 
    @Override
    public boolean canRainOrSnow() {
        return false;
    }
 
     //Created later
    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ProceduralChunkProvider.class;
    }
 
    @Override
    public long getDayLength() {
        return 24000L;
    }
 
    @Override
    public Vector3 getFogColor() {
        return new Vector3(planet.data.fogColor);
    }
 
    @Override
    public Vector3 getSkyColor() {
        return new Vector3(planet.data.skyColor);
    }
    
    @Override
    public IChunkProvider createChunkGenerator(){
    	return new ProceduralChunkProvider(planet.data, this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
    }
 
     //Created Later
    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return ProceduralChunkManager.class;
    }
 
    @Override
    public boolean hasSunset() {
        return false;
    }
 
    //Can players respawn here?
    @Override
    public boolean shouldForceRespawn() {
        return false;
    }
 
    @Override
    public double getSolarEnergyMultiplier() {
        return 1f;
    }
 
    @Override
    public double getYCoordinateToTeleport() {
        return 800;
    }
 
    @Override
    public Vector3 getEntitySpawnLocation(WorldServer arg0, Entity arg1) {
        return new Vector3(arg1.posX, 140, arg1.posZ);
    }
 
    @Override
    public Vector3 getParaChestSpawnLocation(WorldServer arg0, EntityPlayerMP arg1, Random arg2) {
        return new Vector3(arg1.posX - 5 + arg2.nextInt(10), 150, arg1.posZ - 5 + arg2.nextInt(10));
    }
 
    @Override
    public Vector3 getPlayerSpawnLocation(WorldServer arg0, EntityPlayerMP arg1) {
        return getEntitySpawnLocation(arg0, arg1);
    }
 
    @Override
    public void onSpaceDimensionChanged(World arg0, EntityPlayerMP arg1, boolean arg2) {}
 
    @Override
    public boolean useParachute() {
        return true;
    }

	@Override
	public CelestialBody getCelestialBody() {
		return planet;
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP arg0) {
		
	}

	@Override
	public String getDimensionName() {
		return planet.data.name;
	}
	
	@Override
    public Vec3 drawClouds(float partialTicks){
        return planet.data.cloudColor;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1){
		return 1f;
	}
	
	@Override
	public float getCloudHeight(){
		return 90f + planet.data.hillyness*120 ;
	}
	
	@Override
    public IRenderHandler getCloudRenderer(){
        return planet.data.hasClouds ? new DefaultCloudRenderer() : new CloudRenderer();
    }
}
