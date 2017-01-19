package io.anuke.starflux;

import java.util.HashMap;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import io.anuke.starflux.generation.ProceduralWorldProvider;
import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.planets.ProceduralPlanet;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.util.ResourceLocation;

@Mod(modid = Starflux.MODID, version = Starflux.VERSION, dependencies = "required-after:GalacticraftCore")
public class Starflux{
    public static final String MODID = "Starflux";
    public static final String VERSION = "1.0";
    public static final String ASSETPREFIX = "starflux";
    private static int lastID = 10;
    
    public static HashMap<Integer, ProceduralPlanet> planets = new HashMap<Integer, ProceduralPlanet>();
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	for(int i = 0; i < 5; i ++)
    	createNewPlanet();
    }
    
    public void createNewPlanet(){
    	int id = lastID ++;
    	
    	PlanetData data = PlanetData.createPlanetData(id);
    	
    	ProceduralPlanet planet = (ProceduralPlanet) new ProceduralPlanet(data, data.name).setParentSolarSystem(GalacticraftCore.solarSystemSol).setRelativeSize(1F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1F+planets.size()/2f, 1F+planets.size()/2f));
        planet.setRelativeOrbitTime(40F).setTierRequired(1).setBodyIcon(new ResourceLocation(ASSETPREFIX, "textures/gui/celestialbodies/amoon.png"));
        planet.setDimensionInfo(id, ProceduralWorldProvider.class);
       
        GalaxyRegistry.registerPlanet(planet);
        
        GalacticraftRegistry.registerTeleportType(ProceduralWorldProvider.class, new ProceduralWorldProvider());
        
        planets.put(id, planet);
    }
    
    public static ProceduralPlanet getPlanetByID(int id){
    	return planets.get(id);
    }
}

