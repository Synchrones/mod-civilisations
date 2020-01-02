package fr.salsa.CVLST.blocks.trees;

import com.google.common.base.Predicate;
import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.blocks.items.ItemBlockVariants;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import fr.salsa.CVLST.utils.interfaces.IMetaName;
import fr.salsa.CVLST.world.feature.tree.WorldGenDenseJungleTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

public class CustomBlockSapling extends BlockBush implements IMetaName, IGrowable {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    public static final PropertyEnum<EnumHandler.Enumtype> VARIANT = PropertyEnum.<EnumHandler.Enumtype>create("variant", EnumHandler.Enumtype.class, new Predicate<EnumHandler.Enumtype>(){
        public boolean apply(@Nullable EnumHandler.Enumtype apply){
            return apply.getMeta()< 2; //2 est le nombre d'enum dans EnumType
        }
    });
    private String name;
    public CustomBlockSapling(String name) {
        super();
        setRegistryName(name);
        setUnlocalizedName(name);
        setSoundType(SoundType.PLANT);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.Enumtype.DENSEJUNGLE).withProperty(STAGE, Integer.valueOf(0)));
        this.name = name;
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
        setCreativeTab(ModMain.modtab);
    }
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumHandler.Enumtype enumtype : EnumHandler.Enumtype.values()) {
            items.add(new ItemStack(this, 1, enumtype.getMeta()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumHandler.Enumtype.byMetadata(meta & 1)).withProperty(STAGE, Integer.valueOf((meta & 8)>> 3)); // 1 est la plus haute id de l'enum
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta();
        i = i | ((Integer)state.getValue(STAGE)).intValue() <<3;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{VARIANT, STAGE});
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
    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumHandler.Enumtype.values()[stack.getItemDamage()].getName();
    }
    //code arbre

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
        if(TerrainGen.saplingGrowTree(world, rand, pos)) return;
        WorldGenerator gen = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(false) : new WorldGenTrees(false));
        int i = 0, j = 0;
        boolean flag = false;
        switch ((EnumHandler.Enumtype)state.getValue(VARIANT)){
            case DENSEJUNGLE:
                gen = new WorldGenDenseJungleTree();
                break;
            case EXAMPLE:
                //gen = new WorldGenExampleTree();
        }
        IBlockState iBlockState = Blocks.AIR.getDefaultState();
        if(flag){
            world.setBlockState(pos.add(0,0,0), iBlockState, 4);
            world.setBlockState(pos.add(1,0,0), iBlockState, 4);
            world.setBlockState(pos.add(0,0,1), iBlockState, 4);
            world.setBlockState(pos.add(1,0,1), iBlockState, 4);
        }
        else {
            world.setBlockState(pos, iBlockState, 4);
        }
        if(!gen.generate(world, rand, pos)){
            if(flag){
                world.setBlockState(pos.add(0,0,0), iBlockState, 4);
                world.setBlockState(pos.add(1,0,0), iBlockState, 4);
                world.setBlockState(pos.add(0,0,1), iBlockState, 4);
                world.setBlockState(pos.add(1,0,1), iBlockState, 4);
            }
            else {
                world.setBlockState(pos, iBlockState, 4);
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double)worldIn.rand.nextFloat() <0.45D;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
    }
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, rand, pos, state);
            }
        }
    }
}

