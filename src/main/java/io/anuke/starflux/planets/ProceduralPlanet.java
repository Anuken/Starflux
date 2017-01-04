package io.anuke.starflux.planets;

import micdoodle8.mods.galacticraft.api.galaxies.Planet;

public class ProceduralPlanet extends Planet {
	String defName;

	public ProceduralPlanet(String planetName) {
		super(planetName);
		this.defName = planetName;
	}

	// no localization for you
	@Override
	public String getLocalizedName() {
		return defName;
	}

}
