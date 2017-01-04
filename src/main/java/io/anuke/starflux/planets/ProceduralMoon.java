package io.anuke.starflux.planets;

import micdoodle8.mods.galacticraft.api.galaxies.Moon;

public class ProceduralMoon extends Moon {
	String defName;

	public ProceduralMoon(String moonName) {
		super(moonName);
		defName = moonName;
	}
	
	//no localization for you
	@Override
	public String getLocalizedName() {
		return defName;
	}
}
