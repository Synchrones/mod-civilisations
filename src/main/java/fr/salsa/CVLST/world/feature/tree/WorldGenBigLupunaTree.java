package fr.salsa.CVLST.world.feature.tree;

import fr.salsa.CVLST.blocks.trees.CVLSTLeaves;
import fr.salsa.CVLST.blocks.trees.CVLSTLog;
import fr.salsa.CVLST.blocks.trees.CVLSTSaplings;
import fr.salsa.CVLST.init.ModBlocks;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

import java.util.Random;

public class WorldGenBigLupunaTree extends WorldGenHugeTrees {
    public static final IBlockState log = ModBlocks.lupunaLog.getDefaultState();
    public static final IBlockState leaf = ModBlocks.lupunaLeave.getDefaultState().withProperty(CVLSTLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private int minHeight;

    public WorldGenBigLupunaTree() {
        super(false, 30, 8, null, null);
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

                BlockPos.MutableBlockPos saplingsNW = new BlockPos.MutableBlockPos().setPos(pos.getX(), pos.getY(), pos.getZ());
                if(isSapling(world, saplingsNW.west())){
                    if(isSapling(world, saplingsNW.west().south())){
                        saplingsNW.setPos(pos.getX() - 1, pos.getY(), pos.getZ());
                    }
                    else{
                        if(isSapling(world, saplingsNW.west().north())){
                            saplingsNW.setPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
                        }
                    }
                }
                else{
                    if(isSapling(world, saplingsNW.north())){
                        if(isSapling(world, saplingsNW.north().east())){
                            saplingsNW.setPos(pos.getX(), pos.getY(), pos.getZ() - 1);
                        }
                        else {
                            if(isSapling(world, saplingsNW.north().west())){
                                saplingsNW.setPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
                            }
                        }
                    }
                }

                this.genMiddleBranch(world, saplingsNW);

                this.genBase(world, saplingsNW.add(-1,0,-1));
                this.genBase(world, saplingsNW.add(2, 0, -1));
                this.genBase(world, saplingsNW.add(2,0,2));
                this.genBase(world, saplingsNW.add(-1,0,2));

                this.genTopTree(world, saplingsNW, height);

                this.genFallingLeaves(world, saplingsNW.add(-3 - rand.nextInt(2),0,4 + rand.nextInt(3)), height);
                this.genFallingLeaves(world, saplingsNW.add(5 + rand.nextInt(3),0,4 + rand.nextInt(3)), height);
                this.genFallingLeaves(world, saplingsNW.add(4 + rand.nextInt(3),0,-4), height);
                this.genFallingLeaves(world, saplingsNW.add(-3 - rand.nextInt(2),0,-3 + rand.nextInt(3)), height);

                this.genTrunk(world, saplingsNW, height);

            }
        }
        return true;
    }
    private boolean isSapling(World world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.lupunaSapling;
    }

    private void genTrunk(World world, BlockPos pos, int height) {
        for (int logheight = 0; logheight < height - 2; logheight++) {
            BlockPos up = pos.up(logheight);
            IBlockState logState = world.getBlockState(up);
            if (logState.getBlock().isAir(logState, world, up) || logState.getBlock().isLeaves(logState, world, up) || logState.getBlock() == ModBlocks.lupunaSapling){
                this.setBlockAndNotifyAdequately(world, pos.up(logheight), log);
            }
            if (logheight < height - 2){
                if (logState.getBlock().isAir(logState, world, up.add(1,0,0)) || logState.getBlock().isLeaves(logState, world, up) || logState.getBlock() == ModBlocks.lupunaSapling) {
                    this.setBlockAndNotifyAdequately(world, pos.up(logheight).add(1,0,0), log);
                }
                if (logState.getBlock().isAir(logState, world, up.add(1,0,1)) || logState.getBlock().isLeaves(logState, world, up) || logState.getBlock() == ModBlocks.lupunaSapling) {
                    this.setBlockAndNotifyAdequately(world, pos.up(logheight).add(1,0,1), log);
                }
                if (logState.getBlock().isAir(logState, world, up.add(0,0,1)) || logState.getBlock().isLeaves(logState, world, up) || logState.getBlock() == ModBlocks.lupunaSapling) {
                    this.setBlockAndNotifyAdequately(world, pos.up(logheight).add(0,0,1), log);
                }
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
                    this.setBlockAndNotifyAdequately(world, branchpos.add(0, 0, -i1), log.withProperty(CVLSTLog.LOG_AXIS, BlockLog.EnumAxis.Z));
                }
                    break;
                case 1://sud
                    this.genMiddleBranchLeaves(world, branchpos.add(0,0,6));

                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,3 + i1), log.withProperty(CVLSTLog.LOG_AXIS, BlockLog.EnumAxis.Z));
                    }
                    break;
                case 2://est
                    this.genMiddleBranchLeaves(world, branchpos.add(5,0,1));

                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2 + i1,0,1), log.withProperty(CVLSTLog.LOG_AXIS, BlockLog.EnumAxis.X));
                    }
                    break;
                case 3://ouest
                    this.genMiddleBranchLeaves(world, branchpos.add(-4,0,1));
                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1 - i1,0,1), log.withProperty(CVLSTLog.LOG_AXIS, BlockLog.EnumAxis.X));
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
        int i = 0;
        int rootheight = 3 + rand.nextInt(2);
        BlockPos topRoot = pos.add(0, rootheight, 0);
        IBlockState state = world.getBlockState(topRoot);
        while(state.getBlock().isLeaves(state, world, topRoot) || state.getBlock().isAir(state, world, topRoot)){
            i++;
            state = world.getBlockState(topRoot.add(0, -i,0));
            setBlockAndNotifyAdequately(world, topRoot.add(0, -i + 1,0), log);
        }
        for (int i1 = 0; i1 < 3; i1++){
            i = 0;
            BlockPos topRoot2 = pos.add(-1 + rand.nextInt(2) + i1 / 2, rootheight , -1 + rand.nextInt(2) + i1 / 2);
            state = world.getBlockState(topRoot2);
            while(state.getBlock().isLeaves(state, world, topRoot2) || state.getBlock().isAir(state, world, topRoot2)){
                i++;
                state = world.getBlockState(topRoot2.add(i1 / 3, -i - i1 - 1, i1 / 3));
                setBlockAndNotifyAdequately(world, topRoot2.add(i1 / 3, -i - i1 , i1 / 3), log);
            }
        }
    }

    private void genTopTree(World world, BlockPos pos, int logheight){
        BlockPos toplog = pos.add(7,logheight - 8,0);
        int ypos = logheight - 8;
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

            for(int i1 = 0; i1 < 12 ; i1++){
                setBlockAndNotifyAdequately(world, toplog.add(-1 - i1, -1 ,  -i5), leaf);
            }
            for (int i1 = 0; i1 < 14; i1++) {
                setBlockAndNotifyAdequately(world, toplog.add( - i1, -1, -1* plusorminus -i5), leaf);
            }
            for (int i = 0; i < 4; i++) {
                for (int i1 = 0; i1 < 14 - i * 2; i1++) {
                    setBlockAndNotifyAdequately(world, toplog.add( -i1 - i, -1, (-2 - i)* plusorminus -i5), leaf);
                }
            }
            for (int i = 0; i < 2; i++) {
                setBlockAndNotifyAdequately(world, toplog.add(-i - 4, -1, -6* plusorminus -i5), leaf);
            }
            for (int i = 0; i < 2; i++) {
                setBlockAndNotifyAdequately(world, toplog.add(-i - 8, -1, -6 * plusorminus -i5), leaf);
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

    private void genFallingLeaves(World world, BlockPos pos, int logheight){
        Random rand = new Random();
        BlockPos downleaves = pos.add(0,logheight - 8,0);
        int height = 6 + rand.nextInt(3);
        setBlockAndNotifyAdequately(world, downleaves.add(0,1,0), log);
        for(int i2 = 0; i2 < 2; i2++){
            for(int i1 = 0; i1 < 4; i1++){
                int x = -1 + rand.nextInt(2);
                int z = -1 + rand.nextInt(2);
                for(int i = 0; i < height - (i1 + rand.nextInt(1) + 1 + i2); i++){
                    setBlockAndNotifyAdequately(world, downleaves.add( x, -i, z), leaf);
                }
            }
        }
    }
}
