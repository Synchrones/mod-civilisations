package fr.salsa.CVLST.world.biomes;

import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.world.feature.tree.WorldGenLupunaTree;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public class DenseJungle extends Biome {
    protected final WorldGenAbstractTree tree = new WorldGenLupunaTree();
    protected final WorldGenShrub shrubs = new WorldGenShrub(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE), ModBlocks.lupunaLeave.getDefaultState());
    public DenseJungle(){
        super(new BiomeProperties("Dense Jungle").setTemperature(0.95F).setHeightVariation(0.2F).setRainfall(0.7F).setBaseHeight(0.125F));
        this.decorator.treesPerChunk = 50;
        this.decorator.grassPerChunk = 20;
        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        if(rand.nextInt(6) == 0 ){
            return shrubs;
        }
        else return tree;
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return getModdedBiomeGrassColor(178735);
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return rand.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN ) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);

    }
}


