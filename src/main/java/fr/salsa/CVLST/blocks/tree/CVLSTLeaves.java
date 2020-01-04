package fr.salsa.CVLST.blocks.trees;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class CVLSTLeaves extends BlockLeaves {
    public CVLSTLeaves(String name) {
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{DECAYABLE,CHECK_DECAY});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if(!((Boolean)state.getValue(DECAYABLE)).booleanValue()) {
            i |= 2;

        }
        if(!((Boolean)state.getValue(CHECK_DECAY)).booleanValue()) {
            i |= 4;
        }
        return i;
    }


    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1,new ItemStack(this, 1, 0));
    }
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        return;
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return 30;
    }
}
