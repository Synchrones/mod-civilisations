package fr.salsa.CVLST.blocks;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;


public class CVLSTSlabs extends BlockSlab {
    public static final PropertyEnum<EnumHandler.EnumSlab> variant = PropertyEnum.<EnumHandler.EnumSlab>create("variant", EnumHandler.EnumSlab.class);
    public boolean isDoubleSlab;
    public CVLSTSlabs(Material materialIn, boolean isDouble) {
        super(materialIn);
        this.isDoubleSlab = isDouble;
        IBlockState state = this.blockState.getBaseState();
        this.useNeighborBrightness = !this.isDoubleSlab;
        this.setCreativeTab(ModMain.modtab);
        if (!this.isDouble()) state = state.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        this.setDefaultState(state.withProperty(variant, EnumHandler.EnumSlab.lupuna));
        this.setSoundType(SoundType.WOOD);
        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName() + "." + EnumHandler.EnumSlab.byMetadata(meta).getName();
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
        return EnumHandler.EnumSlab.byMetadata(stack.getMetadata() & 7);
    }
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.blockState.getBaseState().withProperty(variant, EnumHandler.EnumSlab.lupuna);
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
        return Item.getItemFromBlock(ModBlocks.slabs);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.slabs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this != ModBlocks.doubleSlabs) {
            for (EnumHandler.EnumSlab slab : EnumHandler.EnumSlab.values()) {
                list.add(new ItemStack(this, 1, slab.getMeta()));
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(variant).getMeta();
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {variant,HALF}): new BlockStateContainer(this, new IProperty[] {variant});
    }
}
