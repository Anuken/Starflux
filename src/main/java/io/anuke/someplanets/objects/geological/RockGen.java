package io.anuke.someplanets.objects.geological;

import io.anuke.someplanets.objects.ObjectGenerator;
import net.minecraft.init.Blocks;

public class RockGen extends ObjectGenerator {
	@Override
	public void generate() {
		disc(x, y - 1, z, 1.6f, Blocks.cobblestone);
		disc(x, y, z, 1.6f, Blocks.cobblestone);
		disc(x, y + 1, z, 1.2f, Blocks.cobblestone);
	}
}
