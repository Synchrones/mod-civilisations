package fr.salsa.CVLST.blocks.trees;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import javax.annotation.Nonnull;
import java.util.List;

public class CVLSTLeaves extends BlockLeaves {
    public CVLSTLeaves(String name) {
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }


    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1,new ItemStack(this, 1, 0));
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
