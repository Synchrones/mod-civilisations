package fr.salsa.CVLST.blocks.slabs;

import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSlab;

public class CVLSTSlabsHalf extends  CVLSTSlabs{
	public CVLSTSlabsHalf(String name, Material materialIn, BlockSlab half, BlockSlab doubleSlab, CreativeTabs tab){
	    super(name, materialIn, half, tab);
	    ModItems.ITEMS.add(new ItemSlab(this, this, doubleSlab).setRegistryName(name));
	}
    @Override
    public boolean isDouble() {
        return false;
    }
}
