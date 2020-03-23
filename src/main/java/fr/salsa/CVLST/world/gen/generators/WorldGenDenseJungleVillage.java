package fr.salsa.CVLST.world.gen.generators;

import fr.salsa.CVLST.blocks.slabs.CVLSTSlabs;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.utils.References;
import fr.salsa.CVLST.utils.interfaces.IStructure;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class WorldGenDenseJungleVillage extends WorldGenerator implements IStructure {
    public static final IBlockState slabtop = ModBlocks.lupunaSlabHalf.getDefaultState().withProperty(CVLSTSlabs.HALF, BlockSlab.EnumBlockHalf.TOP);
    public static final IBlockState slabbottom = ModBlocks.lupunaSlabHalf.getDefaultState().withProperty(CVLSTSlabs.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
    public static final IBlockState doubleslab = ModBlocks.lupunaSlabDouble.getDefaultState();
    public static final IBlockState fence = ModBlocks.LupunaFence.getDefaultState();




    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        generateStructure(worldIn, pos, "townhall");
        generateStructure(worldIn, pos.add(0, 32, -2), "townhalltoptree");
        setBlockAndNotifyAdequately(worldIn, pos.add(11, 13, 0), ModBlocks.lupunaSlabHalf.getDefaultState());
        BlockPos pos2 = pos.add(15, 0, -11);
        generateStructure(worldIn, pos2.add(0, getSurfaceBlock(worldIn, pos2), 0), "richhouse1");
        generateStructure(worldIn, pos2.add(-4, getSurfaceBlock(worldIn, pos2) + 24, -4), "lupunatoptree");
        genBridge(worldIn, pos.add(11, 13, 0), pos.add(11, 5, -7));



        if(rand.nextInt(1) == 0){

        }













        return true;
    }


    public static void generateStructure(World world, BlockPos pos, String structureName){
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(References.MODID, structureName);
        Template template = manager.get(mcServer, location);
        if (template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state,3);
            template.addBlocksToWorldChunk(world, pos, settings);
        }
    }
    protected int getSurfaceBlock(World world, BlockPos pos){
        int i;
        int y = -1;
        int i2 = 0;
        boolean topBlock = false;
        for ( i=255; i>=63; i--) {
            IBlockState state = world.getBlockState(pos.add(0, i, 0));
            if (!state.getBlock().equals(Blocks.AIR)) {
                topBlock=true;
                i2++;
            }
            if (topBlock && (i2 == 1)) {
                y = i;
            }
        }
        return y;
    }

    private void genBridge(World world, BlockPos posstart, BlockPos posend){
        int bridgelenghtx = posend.getX() - posstart.getX();
        int bridgelenghty = posend.getY() - posstart.getY();
        int bridgelenghtz = posend.getZ() - posstart.getZ();
        if(Math.abs(bridgelenghty) * 2 > Math.abs(bridgelenghtz)){ //if true, the y variation is too high, so we need to do a different bridge
            genBridgewithoutyvariation(world, posstart, posstart.add(bridgelenghtx, 0, bridgelenghtz), bridgelenghtx, 0, bridgelenghtz);
            int i2 = 1;
            if(bridgelenghty < 0){
                i2 = -1;
            }

            if (Math.abs(bridgelenghtx) < Math.abs(bridgelenghtz))
                if(bridgelenghtz < 0){
                genLadder(world, posend.add(0, -bridgelenghty, 1), posend.add(0,0, 1), bridgelenghty, "z", i2, i2);
            }
            else genLadder(world, posend.add(0, -bridgelenghty, -1), posend.add(0,0, -1), bridgelenghty, "z", i2 * - 1, i2);
            else if(bridgelenghtx < 0){
                genLadder(world, posend.add(1, -bridgelenghty, 0), posend.add(0,0, 1), bridgelenghty, "x", i2, i2);
            }
            else genLadder(world, posend.add(- 1, -bridgelenghty, 0), posend.add(0,0, 1), bridgelenghty, "x", i2 * - 1, i2);



        }
        else {
            if (bridgelenghty != 0) {
                int i2;

                i2 = Math.abs(bridgelenghtx);
                if (bridgelenghtz < 0) {
                    if (bridgelenghtz * -1 > i2) {
                        i2 = bridgelenghtz * -1;
                        genBridgeBlocks(world, posstart, i2, bridgelenghtz, bridgelenghtx, bridgelenghty, -1, "z");
                    } else {
                        if (bridgelenghtx < 0)
                            genBridgeBlocks(world, posstart, i2, bridgelenghtx, bridgelenghtz, bridgelenghty, -1, "x");
                        else genBridgeBlocks(world, posstart, i2, bridgelenghtx, bridgelenghtz, bridgelenghty, 1, "x");
                    }
                } else if (bridgelenghtz > i2) {
                    i2 = bridgelenghtz;
                    genBridgeBlocks(world, posstart, i2, bridgelenghtz, bridgelenghtx, bridgelenghty, 1, "z");
                } else {
                    if (bridgelenghtx < 0)
                        genBridgeBlocks(world, posstart, i2, bridgelenghtx, bridgelenghtz, bridgelenghty, -1, "x");
                    else genBridgeBlocks(world, posstart, i2, bridgelenghtx, bridgelenghtz, bridgelenghty, 1, "x");
                }
            } else {
                genBridgewithoutyvariation(world, posstart, posend, bridgelenghtx, bridgelenghty, bridgelenghtz);
            }
        }
    }

    private void genBridgewithoutyvariation(World world, BlockPos posstart, BlockPos posend, int bridgelenghtx, int bridgelenghty, int bridgelenghtz){
        int i2;
        i2 = Math.abs(bridgelenghtx);
        if (bridgelenghtz < 0) {
            if (bridgelenghtz * -1 > i2) {
                i2 = bridgelenghtz * -1;
                genBridgeBlocks(world, posstart, i2 / 2, bridgelenghtz / 2, bridgelenghtx / 2, bridgelenghty - 1, -1, "z");
                genBridgeBlocks(world, posstart.add(bridgelenghtx / 2,-1,bridgelenghtz / 2), i2 / 2, bridgelenghtz / 2, bridgelenghtx / 2, bridgelenghty + 1, -1, "z");
            } else {
                if (bridgelenghtx < 0) {
                    genBridgeBlocks(world, posstart, i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty - 1, -1, "x");
                    genBridgeBlocks(world, posstart.add(bridgelenghtx / 2, -1, bridgelenghtz / 2), i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty + 1, -1, "x");
                }
                else {
                    genBridgeBlocks(world, posstart, i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty - 1, 1, "x");
                    genBridgeBlocks(world, posstart.add(bridgelenghtx / 2, -1, bridgelenghtz / 2), i2 / 2, bridgelenghtx/ 2, bridgelenghtz / 2, bridgelenghty + 1, 1, "x");
                }
            }
        } else if (bridgelenghtz > i2) {
            i2 = bridgelenghtz;
            genBridgeBlocks(world, posstart, i2 / 2, bridgelenghtz / 2, bridgelenghtx / 2, bridgelenghty - 1, 1, "z");
            genBridgeBlocks(world, posstart.add(bridgelenghtx / 2, -1, bridgelenghtz / 2), i2 / 2, bridgelenghtz / 2, bridgelenghtx / 2, bridgelenghty + 1, 1, "z");
        } else {
            if (bridgelenghtx < 0) {
                genBridgeBlocks(world, posstart, i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty - 1, -1, "x");
                genBridgeBlocks(world, posstart.add(bridgelenghtx / 2, -1, bridgelenghtz / 2), i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty + 1, -1, "x");
            }
            else {
                genBridgeBlocks(world, posstart, i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty - 1, 1, "x");
                genBridgeBlocks(world, posstart.add(bridgelenghtx / 2, -1, bridgelenghtz / 2), i2 / 2, bridgelenghtx / 2, bridgelenghtz / 2, bridgelenghty + 1, 1, "x");
            }
        }

        if((bridgelenghtx / 2) % 2 != 0 || (bridgelenghtz / 2) % 2 != 0) { // a block is missing when we divide a number not pair
            if (Math.abs(bridgelenghtx) < Math.abs(bridgelenghtz)) if(bridgelenghtz < 0) setBlockAndNotifyAdequately(world, posend.add(0, 0, 1), slabbottom); else setBlockAndNotifyAdequately(world, posend.add(0, 0, -1), slabbottom);
            else if(bridgelenghtx < 0) setBlockAndNotifyAdequately(world, posend.add(1, 0, 0), slabbottom); else setBlockAndNotifyAdequately(world, posend.add(-1, 0, 0), slabbottom);
        }
    }









    private void genBridgeBlocks(World world, BlockPos posstart, int i2, int highestbridgelenght, int lowestbridgelenght, int bridgelenghty, int HBLplusorminus, String dominantface){
        int i3;
        highestbridgelenght = Math.abs(highestbridgelenght);
        if(lowestbridgelenght != 0){
            if(lowestbridgelenght < 0) i3 = i2 / lowestbridgelenght * -1; else i3 = i2 / lowestbridgelenght; // here we got the number of blocks between the x or z coordinate of the bridge will change
        }
        else i3 = highestbridgelenght + lowestbridgelenght + 1;
        int i4;
        if(bridgelenghty < 0) i4 = i2 / (bridgelenghty * 2 -1)  * -1; else i4 = i2 / (bridgelenghty * 2 + 1);  // here we got the number of blocks between the y coordinate of the bridge will change

        int i5 = 0;
        int i6 = 0;
        int i8 = 0;
        double i9 = 0;
        IBlockState toporbottom;
        for(int i7 = 0; i7 < i2 ; i7++ ){
            boolean flag = false;
            boolean flag2 = false;
            if(i5 > i3) { //change the x or z coordinate at the right bloc
                if(lowestbridgelenght < 0) i6--; else i6++;
                i7--;// if the x or z coord change, the other one doesn't
                i5 = 0;
                if((i9 * 2) % 2 == 0) toporbottom = slabbottom; else toporbottom = slabtop; //check if the number is pair, if true, pos slab top, else bottom

                if(dominantface.equals("z")){
                    setBlockAndNotifyAdequately(world, posstart.add(i6 + 1 , i9, i7 * HBLplusorminus ), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i6 - 1, i9, i7 * HBLplusorminus), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i6 , i9, i7 * HBLplusorminus), toporbottom);//pos the block (we must check if the "dominantface" is x or z)

                }
                else {
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6 + 1), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6 - 1), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6), toporbottom);
                }
                flag = true;
                flag2 = true;
            }

            if(i8 > i4) { //change the y coordinate at the right bloc
                if(bridgelenghty < 0) i9 = i9 - 0.5; else i9 = i9 + 0.5;
                if(!flag2)i7--;// if the y coord change, the z or x doesn't (it doesn't substract if the other coordinate change too)
                i8 = 0;
                if((i9 * 2) % 2 == 0) toporbottom = slabbottom; else toporbottom = doubleslab;

                if(dominantface.equals("z")){
                    setBlockAndNotifyAdequately(world, posstart.add(i6 + 1, i9, i7 * HBLplusorminus ), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i6 - 1, i9, i7 * HBLplusorminus ), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i6 , i9, i7 * HBLplusorminus), toporbottom);//pos the block (we must check if the "dominantface" is x or z)

                }
                else {
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6 + 1), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6 - 1), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6), toporbottom);
                }
                flag =true;
            }

            if(!flag){
                if((i9 * 2) % 2 == 0) toporbottom = slabbottom; else toporbottom = slabtop; //check if the number is pair, if true, pos slab top, else bottom
                if(dominantface.equals("z")){
                    setBlockAndNotifyAdequately(world, posstart.add(i6 + 1, i9, i7 * HBLplusorminus), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i6 - 1, i9, i7 * HBLplusorminus), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i6 , i9, i7 * HBLplusorminus), toporbottom);//pos the block (we must check if the "dominantface" is x or z)

                }
                else{
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6 + 1), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6 - 1), fence);
                    setBlockAndNotifyAdequately(world, posstart.add(i7 * HBLplusorminus , i9, i6), toporbottom);
                }
                i8++;
                i5++;
            }
        }
    }

    private  void genLadder(World world, BlockPos ladderstart, BlockPos ladderend, int height, String dominantface, int plusorminus, int yplusorminus){
        height = Math.abs(height);
        int i2 = 0;
        for(int i = 0; i < height; i++){
            if(yplusorminus < 0) i2--; else i2++;
            if(dominantface.equals("z")){
                setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,0), ModBlocks.lupunaPlank.getDefaultState());
                setBlockAndNotifyAdequately(world, ladderstart.add(1, i2 ,0), fence);
                setBlockAndNotifyAdequately(world, ladderstart.add(- 1, i2 ,0), fence);
                setBlockAndNotifyAdequately(world, ladderstart.add(1, i2 ,plusorminus), fence);
                setBlockAndNotifyAdequately(world, ladderstart.add(- 1, i2 ,plusorminus), fence);
                IBlockState state = world.getBlockState(ladderstart.add(0,i2,plusorminus));
                if(state.getBlock() != ModBlocks.lupunaSlabHalf || state.getBlock() != ModBlocks.lupunaSlabDouble){
                    if(yplusorminus < 0){
                        if(plusorminus < 0)setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,plusorminus), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.NORTH));
                        else setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,plusorminus), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH));
                    }
                    else if(plusorminus < 0)setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,plusorminus), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH));
                    else setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,plusorminus), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.NORTH));
                }
            }
            else{
                setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,0), ModBlocks.lupunaPlank.getDefaultState());
                setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,1), fence);
                setBlockAndNotifyAdequately(world, ladderstart.add(0, i2 ,- 1), fence);
                setBlockAndNotifyAdequately(world, ladderstart.add(plusorminus, i2 ,1), fence);
                setBlockAndNotifyAdequately(world, ladderstart.add(plusorminus, i2 ,- 1), fence);
                IBlockState state = world.getBlockState(ladderstart.add(plusorminus, i2,0));
                if(state.getBlock() != ModBlocks.lupunaSlabHalf || state.getBlock() != ModBlocks.lupunaSlabDouble){
                    if(yplusorminus < 0){
                        if(plusorminus < 0)setBlockAndNotifyAdequately(world, ladderstart.add(plusorminus, i2 ,0), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.EAST));
                        else setBlockAndNotifyAdequately(world, ladderstart.add(plusorminus, i2 ,0), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST));
                    }
                    else if(plusorminus < 0)setBlockAndNotifyAdequately(world, ladderstart.add(plusorminus, i2 ,0), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST));
                    else setBlockAndNotifyAdequately(world, ladderstart.add(plusorminus, i2 ,0), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.EAST));
                }
            }
        }
    }
}
