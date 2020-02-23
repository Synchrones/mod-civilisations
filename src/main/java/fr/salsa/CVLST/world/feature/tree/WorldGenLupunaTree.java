package fr.salsa.CVLST.world.feature.tree;

import fr.salsa.CVLST.blocks.trees.CVLSTSaplings;
import fr.salsa.CVLST.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import java.util.Random;

public class WorldGenLupunaTree extends WorldGenAbstractTree{
    public static final IBlockState log = ModBlocks.lupunaLog.getDefaultState();
    public static final IBlockState leaf = ModBlocks.lupunaLeave.getDefaultState();
    private int minHeight;

    public WorldGenLupunaTree() {
        super(false);
        this.minHeight = 30;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int height = this.minHeight + rand.nextInt(8);
        boolean flag = true;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int yPos = y; yPos <= y + 1 + height; yPos++) {
            int b0 = 2;
            if (yPos == y) b0 = 1;
            if (yPos >= y + 1 + height - 2) b0 = 2;
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (int xPos = x - b0; xPos <= x + b0 && flag; xPos++) {
                for (int zPos = z - b0; zPos <= z + b0 && flag; zPos++) {
                    if (yPos >= 0 && yPos < world.getHeight()) {
                        if (!this.isReplaceable(world, mutable.setPos(x, y, z))) {
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        } else {
            BlockPos down = pos.down();
            IBlockState state = world.getBlockState(down);
            boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (CVLSTSaplings) ModBlocks.lupunaSapling);
            if (isSoil && y < world.getHeight() - height - 1) {
                state.getBlock().onPlantGrow(state, world, down, pos);


            }
        }
        return true;
    }

}