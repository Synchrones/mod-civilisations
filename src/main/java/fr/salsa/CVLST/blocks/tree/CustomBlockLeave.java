package fr.salsa.CVLST.blocks.trees;

import com.google.common.base.Predicate;
import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.blocks.items.ItemBlockVariants;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import fr.salsa.CVLST.utils.interfaces.IMetaName;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.List;

public class CustomBlockLeave extends BlockLeaves implements IMetaName {
    public static final PropertyEnum<EnumHandler.Enumtype> VARIANT = PropertyEnum.<EnumHandler.Enumtype>create("variant", EnumHandler.Enumtype.class, new Predicate<EnumHandler.Enumtype>(){
        public boolean apply(@Nullable EnumHandler.Enumtype apply){
            return apply.getMeta()< 2; //2 est le nombre d'enum dans EnumType
        }
    });

    private String name;
    public CustomBlockLeave(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setSoundType(SoundType.PLANT);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.Enumtype.DENSEJUNGLE).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
        this.name = name;
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
        setCreativeTab(ModMain.modtab);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumHandler.Enumtype.byMetadata(meta % 2)); //2 est le nombre d'enum dans EnumType
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta();
        if(!((Boolean)state.getValue(DECAYABLE)).booleanValue()) {
            i |= 2;

        }
        if(!((Boolean)state.getValue(CHECK_DECAY)).booleanValue()) {
            i |= 4;
        }
        return i;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumHandler.Enumtype enumtype : EnumHandler.Enumtype.values()) {
            items.add(new ItemStack(this, 1, enumtype.getMeta()));
        }
    }
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta());
    }
    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumHandler.Enumtype.values()[stack.getItemDamage()].getName();
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        return;
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return 30;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public List<ItemStack> onSheared( ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).getMeta()));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{VARIANT,DECAYABLE,CHECK_DECAY});
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}

