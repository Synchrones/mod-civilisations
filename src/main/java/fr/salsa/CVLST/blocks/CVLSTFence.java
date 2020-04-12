package fr.salsa.CVLST.blocks;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CVLSTFence extends BlockFence {
    public CVLSTFence(String name, Material materialIn) {
        super(materialIn, MapColor.BROWN);
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }
}
