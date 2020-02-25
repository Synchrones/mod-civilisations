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


    public WorldGenLupunaTree() {
        super(false);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int height = 13 + rand.nextInt(3);
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
                double logheight = height - 2;
                genMiddleLeaves(world, pos.add(0,2,0));
                for(int i1 = 0; i1 < 2; i1++){
                    int x1 = -1 + rand.nextInt(2);
                    int z1 = -1 + rand.nextInt(2);
                    genMiddleLeaves(world, pos.add(x1 ,logheight / 3 - 0.5 + logheight%3 + ((logheight / 3 - 0.5) * i1 ),z1));
                    //haut de l'abre
                    if (i1 == 1) {
                        for (int i = 0; i < 2; i++) {
                            for (int i2 = 0; i2 < 3; i2++) {
                                for (int i3 = 0; i3 < 5; i3++) {
                                    setBlockAndNotifyAdequately(world, pos.add(i3 - 2 + x1, i + logheight - 2, i2 - 1 + z1), leaf);
                                }
                            }
                        }
                        for (int i = 0; i < 2; i++) {
                            for (int i2 = 0; i2 < 3; i2++) {
                                setBlockAndNotifyAdequately(world, pos.add(i2 - 1 + x1, i + logheight - 2, -2 + z1), leaf);
                            }
                            for (int i2 = 0; i2 < 3; i2++) {
                                setBlockAndNotifyAdequately(world, pos.add(i2 - 1 + x1, i + logheight - 2, 2 + z1), leaf);
                            }
                        }
                        genMiddleLeaves(world, pos.add(x1, logheight , z1));
                    }
                    for(int i = 0; i < logheight / 3 - 0.5; i++){
                        setBlockAndNotifyAdequately(world, pos.add(x1, i + logheight / 3 - 0.5 + logheight%3 + ((logheight / 3 - 0.5) * i1),z1), log);
                    }
                }
                for(int i = 0; i < logheight / 3 - 0.5 + logheight%3; i++){
                    setBlockAndNotifyAdequately(world, pos.add(0, i,0), log);
                }


            }
        }
        return true;
    }
    private void genMiddleLeaves(World world, BlockPos pos){
        for(int i = 0; i < 3; i++){
            for(int i1 = 0; i1 < 5 - i * 2; i1++){
                setBlockAndNotifyAdequately(world, pos.add(i1 + i - 2,0, i), leaf);
            }
        }
        for(int i = 0; i < 2; i++){
            for(int i1 = 0; i1 < 2; i1++) {
                for (int i2 = 0; i2 < 3 - i1 * 2; i2++) {
                    setBlockAndNotifyAdequately(world, pos.add(i2 + i1 - 1, i, -i1 - (1 -i)), leaf);
                }
            }
        }
        setBlockAndNotifyAdequately(world, pos.add(0,1,1), leaf);
    }
}