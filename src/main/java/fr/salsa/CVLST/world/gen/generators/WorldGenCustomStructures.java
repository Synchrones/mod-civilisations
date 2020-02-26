package fr.salsa.CVLST.world.gen.generators;

import fr.salsa.CVLST.world.biomes.DenseJungle;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldGenCustomStructures implements IWorldGenerator {
    public static final WorldGenStructure parenttree = new WorldGenStructure("parenttree");

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()){
            case 1:
                break;
            case 0:
                generateStructure(parenttree, world, random, chunkX, chunkZ, 5, Blocks.GRASS, DenseJungle.class);
                break;
            case -1:
        }
    }

    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topblock, Class<?>...classes){
        ArrayList<Class<?>> classeslist = new ArrayList<Class<?>>(Arrays.asList(classes));
        int x = (chunkX * 16) + random.nextInt(15);
        int z = (chunkZ * 16) + random.nextInt(15);
        int y = calculateGenerationHeight(world, x, z, topblock);
        BlockPos pos = new BlockPos(x,y,z);
        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
        if (world.getWorldType() != WorldType.FLAT) {
            if (classeslist.contains(biome)) {
                if (random.nextInt(chance) == 0) {
                    generator.generate(world, random, pos);
                }
            }
        }
    }
    private static int calculateGenerationHeight(World world, int x, int z, Block topBlock){
        int y = world.getHeight();
        boolean foundGround = false;
        while (!foundGround && y-- >=0){
            Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
            foundGround = block == topBlock;
        }
        return y;
    }
}
