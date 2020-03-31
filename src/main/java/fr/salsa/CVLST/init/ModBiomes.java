package fr.salsa.CVLST.init;

import fr.salsa.CVLST.world.biomes.DenseJungle;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBiomes {
    public static final Biome DenseJungle = new DenseJungle();
    public static void registerBiomes() {
        initBiome(DenseJungle, "Dense Jungle", BiomeManager.BiomeType.COOL, 15, BiomeDictionary.Type.JUNGLE);

    }
    public static Biome initBiome(Biome biome, String name,BiomeManager.BiomeType biomeType,int weight, BiomeDictionary.Type... type) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, type);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));
        BiomeManager.addSpawnBiome(biome);
        return biome;
    }
}
