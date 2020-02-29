package fr.salsa.CVLST.blocks;

import com.google.common.base.Predicate;
import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.blocks.items.ItemBlockVariants;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import fr.salsa.CVLST.utils.interfaces.IMetaName;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CVLSTSlabs extends BlockSlab implements IMetaName {

    public static final PropertyEnum<EnumHandler.EnumSlab> variant = PropertyEnum.<EnumHandler.EnumSlab>create("variant", EnumHandler.EnumSlab.class, new Predicate<EnumHandler.EnumSlab>(){
        public boolean apply(@Nullable EnumHandler.EnumSlab apply){
            return apply.getMeta()< 2; //le nombre est le nombre d'enum dans EnumSlab
        }
    });
    public boolean isDoubleSlab;
    public CVLSTSlabs(String name, Material materialIn, boolean isDouble) {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        this.isDoubleSlab = isDouble;
        IBlockState state = this.blockState.getBaseState();
        this.useNeighborBrightness = !this.isDoubleSlab;
        this.setCreativeTab(ModMain.modtab);
        if (!this.isDouble()){
            state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
            ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
        }
        this.setDefaultState(state.withProperty(variant, EnumHandler.EnumSlab.lupuna));
        this.setSoundType(SoundType.WOOD);
        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public String getUnlocalizedName(int meta) {
    return super.getUnlocalizedName() + "." + EnumHandler.EnumSlab.byMetadata(meta).getName();    
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.CvlstSlabHalf, 1, ((EnumHandler.EnumSlab)state.getValue(variant)).getMeta());

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
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumHandler.EnumSlab enumSlab : EnumHandler.EnumSlab.values()) {
            items.add(new ItemStack(this, 1, enumSlab.getMeta()));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(variant).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return (!this.isDouble()) ? new BlockStateContainer(this, new IProperty[]{variant, HALF}): new BlockStateContainer(this, new IProperty[]{variant});
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumHandler.EnumSlab.values()[stack.getItemDamage()].getName();
    }
}
