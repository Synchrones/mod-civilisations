package fr.salsa.CVLST.world.biomes;

import fr.salsa.CVLST.init.ModBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public class DenseJungle extends Biome {
    public DenseJungle(){
        super(new BiomeProperties("Dense Jungle").setTemperature(0.95F).setHeightVariation(0.2F).setRainfall(0.7F));
        this.decorator.treesPerChunk = 10;
        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
    }
}
