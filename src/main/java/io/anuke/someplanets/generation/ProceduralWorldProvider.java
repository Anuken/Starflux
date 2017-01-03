package io.anuke.someplanets.generation;

import java.util.Random;

import io.anuke.someplanets.planets.PlanetData;
import io.anuke.someplanets.util.DefaultCloudRenderer;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
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
	public PlanetData data;
	
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
        return 7;
    }
 
    @Override
    public float getSoundVolReductionAmount() {
        return 1f;
    }
 
    @Override
    public float getThermalLevelModifier() {
        return -2;
    }
 
    @Override
    public float getWindLevel() {
        return 2f;
    }
 
    @Override
    public boolean canRainOrSnow() {
        return true;
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
        return new Vector3(0.8f, 0.7f, 0.2f);
    }
 
    @Override
    public Vector3 getSkyColor() {
        return new Vector3(0.7f, 0.3f, 0.2f);
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
        return true;
    }
 
    @Override
    public double getSolarEnergyMultiplier() {
        return 1.1f;
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
		return GalacticraftCore.planetOverworld;
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP arg0) {
		
	}

	@Override
	public String getDimensionName() {
		return "Unknown Planet";
	}
	
	@Override
    public Vec3 drawClouds(float partialTicks){
        return Vec3.createVectorHelper(1f,0.2f,0.2f);//worldObj.drawCloudsBody(partialTicks);
    }
	
	@Override
	public float getCloudHeight(){
		return 128f;
	}
	
	@Override
    public IRenderHandler getCloudRenderer(){
        return new DefaultCloudRenderer();
    }
}