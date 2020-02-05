package fr.salsa.CVLST.world.feature.tree;

import fr.salsa.CVLST.blocks.trees.CVLSTSaplings;
import fr.salsa.CVLST.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import java.util.Random;

public class WorldGenLupunaTree extends WorldGenAbstractTree {
    public static final IBlockState log = ModBlocks.LupunaLog.getDefaultState();
    public static final IBlockState leaf = ModBlocks.LupunaLeave.getDefaultState();
    private int minHeight;

    public WorldGenLupunaTree() {
        super(false);
        this.minHeight = 30;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int height = this.minHeight + rand.nextInt(8);
        boolean flag = true;
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
            boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (CVLSTSaplings) ModBlocks.LupunaSapling);
            if (isSoil && y < world.getHeight() - height - 1) {
                state.getBlock().onPlantGrow(state, world, down, pos);
                this.genTrunk(world, pos, height);
                this.genTrunk(world, pos.add(1,0,1), height);
                this.genTrunk(world, pos.add(0,0,1), height);
                this.genTrunk(world, pos.add(1,0,0), height);
                this.genMiddleBranch(world, pos, height);
            }
        }
        return true;
    }

    private void genTrunk(World world, BlockPos pos, int height) {
        for (int logheight = 0; logheight < height - 8; logheight++) {
            BlockPos up = pos.up(logheight);
            IBlockState logState = world.getBlockState(up);
            if (logState.getBlock().isAir(logState, world, up) || logState.getBlock().isLeaves(logState, world, up)) {
                this.setBlockAndNotifyAdequately(world, pos.up(logheight), log);
            }
        }
    }
    private void genMiddleBranch(World world, BlockPos pos, int height){
        Random rand = new Random();
        int branchnumber = 2 + rand.nextInt(2);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for(int i = 0; i < branchnumber; i++){
            BlockPos branchpos = pos.add(0,5 + rand.nextInt(12), -1);
            switch(rand.nextInt(3)){
                case 0: //nord
                    for(int i1 = 0; i1 < 4 ; i1++) {
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0, 0, -i1), log);
                    }
                    for(int i2 = 0; i2 < 3; i2++){                                                                  //couche 1
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,-1, -2 -i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-1,-1,-3), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(1,-1,-3), leaf);
                    for (int i2 = 0; i2 < 5; i2++) {                                                                //couche 2
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1, 0, -1 - i2), leaf);
                    }
                    for(int i2 = 0; i2 < 5; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(1,0,-1 - i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2,0,-2 - i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2,0,-2 - i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,-4), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,-5), leaf);
                    for(int i2 = 0; i2 < 5; i2++){                                                                  //couche 3
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,1, -1 -i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(1,1, -2 -i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1,1, -2 -i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-2,1,-3), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(2,1,-3), leaf);
                    for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 4
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,2, -2 -i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-1,2,-3), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(1,2,-3), leaf);
                    break;
                case 1://sud
                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,3 + i1), log);
                    }
                    for(int i2 = 0; i2 < 3; i2++){                                                                  //couche 1
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,-1, 5 + i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-1,-1,6), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(1,-1,6), leaf);
                    for (int i2 = 0; i2 < 5; i2++) {                                                                //couche 2
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1, 0, 4 + i2), leaf);
                    }
                    for(int i2 = 0; i2 < 5; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(1,0,4 + i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2,0,5 + i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2,0,5 + i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,7), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(0,0,8), leaf);
                    for(int i2 = 0; i2 < 5; i2++){                                                                  //couche 3
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,1, 4 + i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(1,1, 5 + i2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1,1, 5 + i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-2,1,6), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(2,1,6), leaf);
                    for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 4
                        this.setBlockAndNotifyAdequately(world, branchpos.add(0,2, 5 +i2), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-1,2,6), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(1,2,6), leaf);
                    break;
                case 2://est
                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(3 + i1,0,1), log);
                    }
                    for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 1
                        this.setBlockAndNotifyAdequately(world, branchpos.add(3 + i2,-1, 1), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(4,-1,0), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(4,-1,2), leaf);
                    for (int i2 = 0; i2 < 5; i2++) {                                                                //couche 2
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2 + i2, 0, 0), leaf);
                    }
                    for(int i2 = 0; i2 < 5; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2 + i2,0,2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(3 + i2,0,-1 ), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(3 + i2,0,3 ), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(5,0,1), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(6,0,1), leaf);
                    for(int i2 = 0; i2 < 5; i2++){                                                                  //couche 3
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2 + i2,1, 1), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(3 + i2,1,2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(3 + i2,1, 0), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(4,1,-1), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(4,1,3), leaf);
                    for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 4
                        this.setBlockAndNotifyAdequately(world, branchpos.add(2 + i2,2, 1), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(3,2,0), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(3,2,2), leaf);
                    break;
                case 3://ouest
                    for(int i1 = 0; i1 < 4 ; i1++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(i1,0,1), log);
                    }
                    for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 1
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2 -i2,-1, 1), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-3,-1,0), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-3,-1,2), leaf);
                    for (int i2 = 0; i2 < 5; i2++) {                                                                //couche 2
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1 - i2, 0, 0), leaf);
                    }
                    for(int i2 = 0; i2 < 5; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1 - i2,0,2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2 - i2,0,-1 ), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2- i2,0,3 ), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-4,0,1), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-5,0,1), leaf);
                    for(int i2 = 0; i2 < 5; i2++){                                                                  //couche 3
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-1 -i2,1, 1), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2 -i2,1,2), leaf);
                    }
                    for(int i2 = 0; i2 < 3; i2++){
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2 -i2,1, 0), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-3,1,-1), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-3,1,3), leaf);
                    for(int i2 = 0; i2 < 3; i2++){                                                                 //couche 4
                        this.setBlockAndNotifyAdequately(world, branchpos.add(-2 -i2,2, 1), leaf);
                    }
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-3,2,0), leaf);
                    this.setBlockAndNotifyAdequately(world, branchpos.add(-3,2,2), leaf);
                   
            }
        }
    }
    private void genTreeBase(){
        
    }
}

