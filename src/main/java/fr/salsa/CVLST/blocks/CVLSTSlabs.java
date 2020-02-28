package fr.salsa.CVLST.blocks.slabs;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;


public class CVLSTSlabs extends BlockSlab {
    public static final PropertyEnum<Variant> variant = PropertyEnum.<Variant>create("variant", Variant.class);
    Block half;

    public boolean isDoubleSlab;
    public CVLSTSlabs( String name, Material materialIn, BlockSlab half) {
        super(materialIn);
        setRegistryName(name).setUnlocalizedName(name);
        IBlockState state = this.blockState.getBaseState();
        this.useNeighborBrightness = !this.isDoubleSlab;
        this.setCreativeTab(ModMain.modtab);
        if (!this.isDouble()) state = state.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        this.half = half;
        this.setDefaultState(state.withProperty(variant, Variant.DEFAULT));
        this.setSoundType(SoundType.WOOD);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public boolean isDouble() {
        return this.isDoubleSlab;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return variant;
    }
    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Variant.DEFAULT;
    }
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.blockState.getBaseState().withProperty(variant, Variant.DEFAULT);
        if(!this.isDouble()) state = state.withProperty(HALF, ((meta&8) != 0) ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;
        if(!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) meta |= 8;
        return meta;
    }
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(half);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(half);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {variant,HALF}): new BlockStateContainer(this, new IProperty[] {variant});
    }

    public static enum Variant implements IStringSerializable {
        DEFAULT,
        lupuna;
        @Override
        public String getName() {
            return "default";
        }
    }


}
