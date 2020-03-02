package fr.salsa.CVLST.blocks.slabs;

import fr.salsa.CVLST.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import java.util.Random;


public abstract class CVLSTSlabs extends BlockSlab {
    public static final PropertyEnum<Variant> variant = PropertyEnum.<Variant>create("variant", Variant.class);
    Block half;
    public CVLSTSlabs(String name, Material materialIn, BlockSlab half, CreativeTabs tab) {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.useNeighborBrightness = !this.isDouble();
        IBlockState state = this.blockState.getBaseState().withProperty(variant, Variant.DEFAULT);
        if(!this.isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(state);
        this.half = half;
        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if(!this.isDouble()) return Item.getItemFromBlock(state.getBlock()); else return Item.getItemFromBlock(getSlabHalf(state));
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
    protected BlockStateContainer createBlockState() {
        return (!this.isDouble()) ? new BlockStateContainer(this, new IProperty[]{variant, HALF}): new BlockStateContainer(this, new IProperty[]{variant});
    }


    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }


    @Override
    public IProperty<?> getVariantProperty() {
        return variant;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Variant.DEFAULT;
    }
    public Block getSlabHalf(IBlockState state){
        if (ModBlocks.lupunaSlabDouble.equals(state.getBlock())) {
            return(ModBlocks.lupunaSlabHalf);
        }
        else{
            return null;
        }
    }

    public static enum Variant implements IStringSerializable {
        DEFAULT;
        @Override
        public String getName() {
            return "default";
        }
    }
}
