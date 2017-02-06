package io.anuke.starflux.objects;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.anuke.starflux.planets.PlanetData;
import io.anuke.starflux.util.Trig;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public abstract class ObjectGenerator extends WorldGenAbstractTree {
	public final Vector2 vec2 = new Vector2();
	public final Vector3 vec3 = new Vector3();
	public int x, y, z;
	public World world;
	public Random rand;
	public int chance;
	public Block genBlock;

	public ObjectGenerator(int chance) {
		super(false);
		this.chance = chance;
	}

	public ObjectGenerator() {
		this(40);
	}
	
	public boolean add(PlanetData data){
		return false;
	}
	
	public void setup(PlanetData data){
		
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		this.world = world;
		this.rand = random;
		this.x = x;
		this.y = y;
		this.z = z;
		if (random.nextInt(chance/2) == 0 && (genBlock == null || world.getBlock(x, y-1, z) == genBlock))
			generate();
		return false;
	}

	abstract public void generate();

	public void setBlock(Block block) {
		setBlock(x, y, z, block);
	}

	public Block getBlock(int x, int y, int z) {
		return world.getBlock(x, y, z);
	}

	public void setBlock(int x, int y, int z, Block block, int data) {
		if (!world.getBlock(x, y, z).getMaterial().isSolid())
			setBlockAndNotifyAdequately(world, x, y, z, block,data);
	}
	
	public void setBlock(float x, float y, float z, Block block, int data) {
		setBlock((int)x, (int)y, (int)z, block, data);
	}

	public void setBlock(int x, int y, int z, Block block) {
		setBlock(x, y, z, block, 0);
	}

	public void disc(int x, int y, int z, float rad, Block block) {
		disc(x, y, z, rad, block, 0);
	}

	public void disc(int x, int y, int z, float rad, Block block, int data) {
		for (int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++) {
			for (int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++) {
				if (dst(rx, rz, 0, 0) < rad) {
					setBlock(x + rx, y, z + rz, block, data);
				}
			}
		}
	}

	public void sphere(int x, int y, int z, float rad, Block block) {
		sphere(x, y, z, rad, block, 0);
	}

	public void sphere(int x, int y, int z, float rad, Block block, int data) {
		for (int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++) {
			for (int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++) {
				for (int ry = -((int) rad + 1); ry <= ((int) rad + 1); ry++) {
					if (dst(rx, ry, rz, 0, 0, 0) < rad) {
						setBlock(x + rx, y + ry, z + rz, block, data);
					}
				}
			}
		}
	}

	public void disc(int x, int y, int z, float rad, Block a, Block b, float chance) {
		for (int rx = -((int) rad + 1); rx <= ((int) rad + 1); rx++) {
			for (int rz = -((int) rad + 1); rz <= ((int) rad + 1); rz++) {
				if (dst(rx, rz, 0, 0) < rad) {
					setBlock(x + rx, y, z + rz, rand.nextFloat() > chance ? a : b);
				}
			}
		}
	}
	
	
	public void star(int x, int y, int z, float rad, int phase, float offset, float scl, Block block){
		for(int rx = -((int)rad+2); rx <= ((int)rad+2); rx ++){
			for(int rz = -((int)rad+2); rz <= ((int)rad+2); rz ++){
				double ang = angrad(rx, rz);
				
				double arad = rad - (Trig.sin(ang*phase + offset)*scl);
				double dst = dst(rx,rz,0,0);
				
				if(dst < arad)
					setBlock(x+rx,y+2,z+rz, block);
			}
		}
	}

	public static float ang(float x, float y) {
		float angle = (float) Math.toDegrees(Math.atan2(y, x));
		if (angle < 0)
			angle += 360;
		return angle;
	}
	
	public static float angrad(float x, float y) {
		return (float)Math.toRadians(ang(x,y));
	}

	public static float dst(float x1, float y1, float x2, float y2) {
		final float x_d = x2 - x1;
		final float y_d = y2 - y1;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	public static float dst(float x1, float y1, float z1, float x2, float y2, float z2) {
		final float x_d = x2 - x1;
		final float y_d = y2 - y1;
		final float z_d = z2 - z1;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d + z_d * z_d);
	}
	
	public float scld(float a, float b){
		return 1f-(a/b);
	}
	
	public float scld1(float a, float b){
		return 1f-((a+1)/b);
	}
	
	public int rsign() {
		return rand.nextBoolean() ? -1 : 1;
	}
	
	public boolean chance(double c){
		return rand.nextFloat() < c;
	}
	
	public float sclamp(float f) {
		if (f < -1f)
			return -1f;
		if (f > 1f)
			return 1f;
		return f;
	}
}
