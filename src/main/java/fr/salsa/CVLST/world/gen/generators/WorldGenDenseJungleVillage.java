package fr.salsa.CVLST.world.gen.generators;

import fr.salsa.CVLST.blocks.slabs.CVLSTSlabs;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.utils.References;
import fr.salsa.CVLST.utils.interfaces.IStructure;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
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




    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        generateStructure(worldIn, pos, "townhall");
        generateStructure(worldIn, pos.add(0, 32, -2), "townhalltoptree");
        setBlockAndNotifyAdequately(worldIn, pos.add(11, 13, 0), ModBlocks.lupunaSlabHalf.getDefaultState());
        BlockPos pos2 = pos.add(15, 0, -11);
        generateStructure(worldIn, pos2.add(0, getSurfaceBlock(worldIn, pos2), 0), "richhouse1");
        generateStructure(worldIn, pos2.add(-4,getSurfaceBlock(worldIn, pos2) + 24,-4), "lupunatoptree");




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
        int y=-1;
        int i2=0;
        boolean topBlock = false;
        for ( i=255; i>=63; i--) {
            IBlockState state = world.getBlockState(pos.add(0, i, 0));
            if (!state.getBlock().equals(Blocks.AIR)) {
                topBlock=true;
                i2++;
            }
            if (topBlock && (i2 == 1)) {
                y=i;
            }
        }
        return y;
    }
    private void genBridge(World world, BlockPos posstart, BlockPos posend){
        int bridgelenghtx = posstart.getX() - posend.getX();
        int bridgelenghty = posstart.getY() - posend.getY();
        int bridgelenghtz = posstart.getZ() - posend.getZ();
        if(bridgelenghty != 0){
            int i2;

            if(bridgelenghtx < 0) i2 = bridgelenghtx * -1; else i2 = bridgelenghtx; //calculate the highest bridge lenght between x and z
            if(bridgelenghtz < 0){
                if(bridgelenghtz * -1 > i2) i2 = bridgelenghtz * -1;
            }
            else if(bridgelenghtz > i2){
                i2 = bridgelenghtx;
                int i3;
                if(bridgelenghtx != 0){
                    if(bridgelenghtx < 0) i3 = i2 / bridgelenghtx * -1; else i3 = i2 / bridgelenghtx; // here we got the number of blocks between the x coordinate of the bridge will change
                }
                else i3 = bridgelenghtz + 1;
                int i4;
                if(bridgelenghty < 0) i4 = i2 / bridgelenghty * -1; else i4 = i2 / bridgelenghty;     // here we got the number of blocks between the y coordinate of the bridge will change


                int i5;
                int i6;
                int i8 = 0;
                int i9 = 0;
                int i10 = 0;
                double i11 = 0;
                IBlockState toporbottom;
                if(bridgelenghtx < 0) i5 = bridgelenghtx * -1; else i5 = bridgelenghtx; // positive x
                if(bridgelenghty < 0) i6 = bridgelenghty * -1; else i6 = bridgelenghty; // positive y
                for(int i7 = 0; i7 < i2 +  i5 + i6; i7++ ){
                    if(i8 > i3) { //change the x coordinate at the right bloc
                        if(bridgelenghtx < 0) i9++; else i9--;
                        i8 = 0;
                    }
                    if(i10 > i4) { //change the y coordinate at the right bloc
                        if(bridgelenghty < 0) i11 = i11 + 0.5; else i11 = i11 - 0.5;
                        i10 = 0;
                    }
                    if(i11 % 2 == 0){   //check if the number is pair, if true, pos slab top, else bottom
                        toporbottom = slabtop;
                    }
                    else toporbottom = slabbottom;

                    setBlockAndNotifyAdequately(world, posstart.add(i9, i11, i7), toporbottom);
                    
                    i10++;
                    i8++;
                }






            }





        }

    }


}

