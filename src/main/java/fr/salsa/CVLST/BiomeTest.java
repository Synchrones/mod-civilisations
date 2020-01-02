package fr.salsa.CVLST.world.biomes;

import fr.salsa.CVLST.entity.EntityTest;
import fr.salsa.CVLST.init.ModBiomes;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;


public class BiomeTest extends Biome {
    public BiomeTest(){
        super(new BiomeProperties("Biometest").setBaseHeight(2.0F).setHeightVariation(2.0F).setSnowEnabled().setWaterColor(16711680));
        this.decorator.treesPerChunk = 5;
        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityLlama.class, 10, 8,15));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTest.class, 10, 3,3));
    }
}