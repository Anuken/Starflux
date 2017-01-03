package io.anuke.someplanets;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import io.anuke.someplanets.generation.ProceduralMoon;
import io.anuke.someplanets.generation.ProceduralWorldProvider;
import io.anuke.someplanets.planets.PlanetData;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.util.ResourceLocation;

@Mod(modid = Starflux.MODID, version = Starflux.VERSION, dependencies = "required-after:GalacticraftCore")
public class Starflux{
    public static final String MODID = "Starflux";
    public static final String VERSION = "1.0";
    public static final String ASSETPREFIX = "starflux";
    
    public static Moon moon;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	moon = (Moon) new ProceduralMoon("Unkown Planet").setParentPlanet(GalacticraftCore.planetOverworld).setRelativeSize(0.0017F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(8F, 8F));
        moon.setRelativeOrbitTime(40F).setTierRequired(1).setBodyIcon(new ResourceLocation(ASSETPREFIX, "textures/gui/celestialbodies/amoon.png"));
        moon.setDimensionInfo(3, ProceduralWorldProvider.class);
       
        GalaxyRegistry.registerMoon(moon);
        
        for(int i = 0; i < 6; i ++)
        PlanetData.createPlanetData();
        
        GalacticraftRegistry.registerTeleportType(ProceduralWorldProvider.class, new ProceduralWorldProvider());
    }
}

