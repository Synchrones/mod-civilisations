package fr.salsa.CVLST.world.feature.tree;

import fr.salsa.CVLST.blocks.trees.CVLSTSaplings;
import fr.salsa.CVLST.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import java.util.Random;

public class WorldGenBigLupunaTree extends WorldGenAbstractTree {
    public static final IBlockState log = ModBlocks.lupunaLog.getDefaultState();
    public static final IBlockState leaf = ModBlocks.lupunaLeave.getDefaultState();
    private int minHeight;

    public WorldGenBigLupunaTree() {
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
                    }
                    else {
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
                this.genTrunk(world, pos, height);
                this.genTrunk(world, pos.add(1,0,1), height);
                this.genTrunk(world, pos.add(0,0,1), height);
                this.genTrunk(world, pos.add(1,0,0), height);
                this.genMiddleBranch(world, pos);

                this.genBase(world, pos.add(-1,0,-1));
                this.genBase(world, pos.add(2, 0, -1));
                this.genBase(world, pos.add(2,0,2));
                this.genBase(world, pos.add(-1,0,2));
                this.genTopTree(world, pos, height);
            }
        }
        return true;
    }

    private void genTrunk(World world, BlockPos pos, int height) {
        for (int logheight = 0; logheight < height - 2; logheight++) {
            BlockPos up = pos.up(logheight);
            IBlockState logState = world.getBlockState(up);
            if (logState.getBlock().isAir(logState, world, up) || logState.getBlock().isLeaves(logState, world, up)) {
                this.setBlockAndNotifyAdequately(world, pos.up(logheight), log);
            }
        }
    }

    private void genMiddleBranch(World world, BlockPos pos){
        Random rand = new Random();
        int branchnumber = 2 + rand.nextInt(2);
        for(int i = 0; i < branchnumber; i++){
            BlockPos branchpos = pos.add(0,8 + rand.nextInt(12), -1);
            switch(rand.nextInt(4)){
                case 0: //nord
                    this.genMiddleBranchLeaves(world, branchpos.add(0,0,-3));

                    for(int i1 = 0; i1 < 4 ; i1++) {
                    this.setBlockAndNotifyAdequately(world, branchpos.add(0, 0, -i1), log);
                }
                    break;
                case 1://sud
                    this.genMiddleBranchLeaves(world, branchpos.add(0,0,6));

                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,3 + i1), log);
                    }
                    break;
                case 2://est
                    this.genMiddleBranchLeaves(world, branchpos.add(5,0,1));

                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2 + i1,0,1), log);
                    }
                    break;
                case 3://ouest
                    this.genMiddleBranchLeaves(world, branchpos.add(-4,0,1));
                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1 - i1,0,1), log);
                    }
            }
        }
    }
    private void genMiddleBranchLeaves(World world, BlockPos branchpos){
        for(int i2 = 0; i2 < 3; i2++){                                                                  //couche 1
            this.setBlockAndNotifyAdequately(world, branchpos.add(0,-1, 1 -i2), leaf);
        }
        this.setBlockAndNotifyAdequately(world, branchpos.add(-1,-1,0), leaf);
        this.setBlockAndNotifyAdequately(world, branchpos.add(1,-1,0), leaf);
        for(int i3 = 0; i3 < 3; i3++) {
            for (int i2 = 0; i2 < 5; i2++) {                                                                //couche 2
                this.setBlockAndNotifyAdequately(world, branchpos.add(-1 + i3, 0, 2 - i2), leaf);
            }
        }
        for(int i2 = 0; i2 < 3; i2++){
            this.setBlockAndNotifyAdequately(world, branchpos.add(-2,0,1 - i2), leaf);
        }
        for(int i2 = 0; i2 < 3; i2++){
            this.setBlockAndNotifyAdequately(world, branchpos.add(2,0,1 - i2), leaf);
        }
        for(int i2 = 0; i2 < 5; i2++){                                                                  //couche 3
            this.setBlockAndNotifyAdequately(world, branchpos.add(0,1, 2 -i2), leaf);
        }
        for(int i2 = 0; i2 < 3; i2++){
            this.setBlockAndNotifyAdequately(world, branchpos.add(1,1, 1 -i2), leaf);
        }
        for(int i2 = 0; i2 < 3; i2++){
            this.setBlockAndNotifyAdequately(world, branchpos.add(-1,1, 1 -i2), leaf);
        }
        this.setBlockAndNotifyAdequately(world, branchpos.add(-2,1,0), leaf);
        this.setBlockAndNotifyAdequately(world, branchpos.add(2,1,0), leaf);
        for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 4
            this.setBlockAndNotifyAdequately(world, branchpos.add(0,2, 1 -i2), leaf);
        }
        this.setBlockAndNotifyAdequately(world, branchpos.add(-1,2,0), leaf);
        this.setBlockAndNotifyAdequately(world, branchpos.add(1,2,0), leaf);
    }





    private void genBase(World world, BlockPos pos){
        Random rand = new Random();
        int rootheight = 3 + rand.nextInt(2);
        for(int i1 = 0; i1 < rootheight; i1++){
            this.setBlockAndNotifyAdequately(world, pos.add(0, i1,0), log);
        }
        for(int i1 = 0; i1 < rootheight - 1 - rand.nextInt(1); i1++){
            this.setBlockAndNotifyAdequately(world, pos.add(-1 + rand.nextInt(2), i1, -1 + rand.nextInt(2)), log);
        }
        for(int i1 = 0; i1 < rootheight - 2 - rand.nextInt(1); i1++){
            this.setBlockAndNotifyAdequately(world, pos.add(-1 + rand.nextInt(2), i1, -1 + rand.nextInt(2)), log);
        }
        for(int i1 = 0; i1 < rootheight - 3 - rand.nextInt(1); i1++){
            this.setBlockAndNotifyAdequately(world, pos.add(-1 + rand.nextInt(2), i1, -1 + rand.nextInt(2)), log);
        }
    }
    private void genTopTree(World world, BlockPos pos, int logheight){
        BlockPos toplog = pos.add(7,logheight - 8,0);
        int ypos = toplog.getY() - 4;
        for(int mirror = 0; mirror < 2; mirror++) {
            int plusorminus = 0;
            int i5 = 0;
            if(mirror == 0){
                plusorminus = 1;
            }
            if(mirror == 1){
                plusorminus = -1;
                i5 = -1;
            }
            for (int i2 = 0; i2 < 4; i2++) {

                for (int i = 0; i < 14; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i, i2, -i5), leaf);
                }
                for (int i = 0; i < 16; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i + 1, i2, -1 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 3; i++) {
                    for (int i1 = 0; i1 < 18; i1++) {
                        setBlockAndNotifyAdequately(world, toplog.add(-i1 + 2, i2, (-2 - i) * plusorminus -i5), leaf);
                    }
                }
                for (int i = 0; i < 16; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i + 1, i2, -5 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 14; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i, i2, -6 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 5; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 1, i2, -7 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 5; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 8, i2, -7 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 3; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 2, i2, -8 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 3; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 9, i2, -8 * plusorminus -i5), leaf);
                }
            }


            for (int i3 = 0; i3 < 2; i3++) {

                for (int i = 0; i < 12; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 1, 4 + i3, -i5), leaf);
                }
                for (int i = 0; i < 14; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i, 4 + i3, -1 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 3; i++) {
                    for (int i1 = 0; i1 < 16; i1++) {
                        setBlockAndNotifyAdequately(world, toplog.add(-i1 + 1, 4 + i3, (-2 - i) * plusorminus -i5), leaf);
                    }
                }
                for (int i = 0; i < 14; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i, 4 + i3, -5 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 5; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 1, 4 + i3, -6 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 5; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 8, 4 + i3, -6 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 3; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 2, 4 + i3, -7 * plusorminus -i5), leaf);
                }
                for (int i = 0; i < 3; i++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-i - 9, 4 + i3, -7 * plusorminus -i5), leaf);
                }

            }

            for (int i3 = 0; i3 < 3; i3++) {
                for (int i2 = 0; i2 < 5 - i3; i2++) {
                    for (int i1 = 0; i1 < 10 - i3 - i3; i1++) {
                        setBlockAndNotifyAdequately(world, toplog.add(-11 + i1 + i3, 6 + i3, (-4 + i2 + i3) * plusorminus -i5), leaf);
                    }
                }
                for (int i1 = 0; i1 < 4 - i3; i1++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-1 - i3, 6 + i3, (-5 + i1 + i3) * plusorminus -i5), leaf);
                }
                for (int i1 = 0; i1 < 4 - i3; i1++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-12 + i3, 6 + i3, (-5 + i1 + i3) * plusorminus -i5), leaf);
                }
                for (int i1 = 0; i1 < 3 - i3; i1++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-4 + i1, 6 + i3, (-5 + i3) * plusorminus -i5), leaf);
                }
                for (int i1 = 0; i1 < 3 - i3; i1++) {
                    setBlockAndNotifyAdequately(world, toplog.add(-9 - i1, 6 + i3, (-5 + i3) * plusorminus -i5), leaf);
                }
            }
        }

        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add(i + 1 ,ypos,i + 1), log);
        }
        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add( 5, ypos + i , 5), log);
        }


        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add( -i ,ypos,i + 1), log);
        }
        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add( -4, ypos + i , 5), log);
        }


        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add(-i  ,ypos, -i), log);
        }
        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add( -4, ypos + i , -4), log);
        }


        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add(i + 1,ypos, -i ), log);
        }
        for(int i = 0; i < 5; i++){
            setBlockAndNotifyAdequately(world, pos.add( 5, ypos + i , -4), log);
        }
        for(int i = 0; i < 4; i++) {
            setBlockAndNotifyAdequately(world, toplog.add(-8, i, -6), log);
            setBlockAndNotifyAdequately(world, toplog.add(-5, i, -6), log);
            setBlockAndNotifyAdequately(world, toplog.add(-8, i, 7), log);
            setBlockAndNotifyAdequately(world, toplog.add(-5, i, 7), log);
        }
        setBlockAndNotifyAdequately(world, pos.add(-2, 6 + ypos, -3), log);
        setBlockAndNotifyAdequately(world, pos.add(3, 6 + ypos, -3), log);
        setBlockAndNotifyAdequately(world, pos.add(-2, 6 + ypos, 4), log);
        setBlockAndNotifyAdequately(world, pos.add(3, 6 + ypos, 4), log);
        for(int i = 0; i < 2; i++) {
            setBlockAndNotifyAdequately(world, pos.add(-4 , 5 + ypos, i), log);
        }
        for(int i = 0; i < 2; i++) {
            setBlockAndNotifyAdequately(world, pos.add(5 , 5 + ypos, i), log);
        }
        for(int i = 0; i < 2; i++) {
            setBlockAndNotifyAdequately(world, pos.add(i , 7 + ypos, -1), log);
        }
        for(int i = 0; i < 2; i++) {
            setBlockAndNotifyAdequately(world, pos.add( i , 7 + ypos, 2), log);
        }
        for(int i = 0; i < 4; i++){
            setBlockAndNotifyAdequately(world, pos.add( -6, ypos + i + 1 , -2), log);
            setBlockAndNotifyAdequately(world, pos.add( 7, ypos + i + 1 , -2), log);
            setBlockAndNotifyAdequately(world, pos.add( -6, ypos + i + 1 , 3), log);
            setBlockAndNotifyAdequately(world, pos.add( 7, ypos + i + 1 , 3), log);
        }
    }
}
