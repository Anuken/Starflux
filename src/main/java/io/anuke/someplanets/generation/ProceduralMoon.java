package io.anuke.someplanets.generation;

import micdoodle8.mods.galacticraft.api.galaxies.Moon;

public class ProceduralMoon extends Moon {

	public ProceduralMoon(String moonName) {
		super(moonName);
	}
	
	//no localization for you
	@Override
	public String getLocalizedName() {
		return getName();
	}
}
