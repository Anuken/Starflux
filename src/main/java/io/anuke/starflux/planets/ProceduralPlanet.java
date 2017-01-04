package io.anuke.starflux.planets;

import micdoodle8.mods.galacticraft.api.galaxies.Planet;

public class ProceduralPlanet extends Planet {
	public final PlanetData data;
	String defName;

	public ProceduralPlanet(PlanetData data, String planetName) {
		super(planetName);
		this.defName = planetName;
		this.data = data;
	}

	// no localization for you
	@Override
	public String getLocalizedName() {
		return defName;
	}
}
