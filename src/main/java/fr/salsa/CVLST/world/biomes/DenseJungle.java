import fr.salsa.CVLST.world.feature.tree.WorldGenLupunaTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class DenseJungle extends Biome {
    protected final WorldGenAbstractTree tree = new WorldGenLupunaTree();
    public DenseJungle(){
        super(new BiomeProperties("Dense Jungle").setTemperature(0.95F).setHeightVariation(0.2F).setRainfall(0.7F).setBaseHeight(0.125F));
        this.decorator.treesPerChunk = 25;
        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return tree;
    }
}
