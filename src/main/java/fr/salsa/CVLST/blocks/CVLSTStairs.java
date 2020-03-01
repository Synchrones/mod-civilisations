package fr.salsa.CVLST.blocks;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

public class CVLSTStairs extends BlockStairs {
    public CVLSTStairs(IBlockState modelState, String name) {
        super(modelState);
        setRegistryName(name).setUnlocalizedName(name);
        this.useNeighborBrightness = true;
        this.setCreativeTab(ModMain.modtab);
        this.setSoundType(SoundType.WOOD);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        ModBlocks.BLOCKS.add(this);
    }
}
