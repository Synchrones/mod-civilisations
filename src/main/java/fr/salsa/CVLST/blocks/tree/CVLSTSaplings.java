package fr.salsa.CVLST.blocks.trees;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.world.feature.tree.WorldGenBigLupunaTree;
import fr.salsa.CVLST.world.feature.tree.WorldGenLupunaTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;
import java.util.Random;

public class CVLSTSaplings extends BlockBush implements IGrowable {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public CVLSTSaplings(String name) {
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        setSoundType(SoundType.PLANT);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{STAGE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((Integer) state.getValue(STAGE)).intValue() << 3;
        return i;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    //code arbre
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double) worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
    }
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, rand, pos, state);
            }
        }
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if(((Integer)state.getValue(STAGE)).intValue() == 0){
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }
        else {
            this.generateTree(worldIn, rand, pos, state);
        }
    }
    public void generateTree(World world, Random rand, BlockPos pos, IBlockState state){
        if(!TerrainGen.saplingGrowTree(world, rand, pos)) return;
        WorldGenerator gen = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(false) : new WorldGenTrees(false));
        int i = 0;
        int i2 = 0;
        boolean flag = false;
        if(state.getBlock() == ModBlocks.lupunaSapling){
            for (i = 0; i >= -1; i--) {
                for (i2 = 0; i2 >= -1; i2--) {
                    if (this.isTwoByTwoOfType(world, pos, i, i2)) {
                        gen = new WorldGenBigLupunaTree();
                        flag = true;
                    }
                }
            }
            if(!flag){
                gen = new WorldGenLupunaTree();
            }
        }
        IBlockState iBlockState = Blocks.AIR.getDefaultState();
        if(flag){
            world.setBlockState(pos, iBlockState, 4);
            world.setBlockState(pos.add(1,0,0), iBlockState, 4);
            world.setBlockState(pos.add(0,0,1), iBlockState, 4);
            world.setBlockState(pos.add(1,0,1), iBlockState, 4);
        }
        else {
            world.setBlockState(pos, iBlockState, 4);
        }
        if(!gen.generate(world, rand, pos)){
            if(flag){
                world.setBlockState(pos, iBlockState, 4);
                world.setBlockState(pos.add(1,0,0), iBlockState, 4);
                world.setBlockState(pos.add(0,0,1), iBlockState, 4);
                world.setBlockState(pos.add(1,0,1), iBlockState, 4);
            }
            else {
                world.setBlockState(pos, iBlockState, 4);
            }
        }
    }
    //from minacraft base code
    private boolean isTwoByTwoOfType(World worldIn, BlockPos pos, int p_181624_3_, int p_181624_4_){
        return this.isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_)) && this.isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_)) && this.isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_ + 1)) && this.isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_ + 1));
    }
    //from minacraft base code
    public boolean isTypeAt(World worldIn, BlockPos pos){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        return iblockstate.getBlock() == this;
    }
}
