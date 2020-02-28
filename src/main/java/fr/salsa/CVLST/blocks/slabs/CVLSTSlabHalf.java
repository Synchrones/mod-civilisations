package fr.salsa.CVLST.blocks.slabs;


import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemSlab;

public class CVLSTSlabHalf extends CVLSTSlabs {
    public CVLSTSlabHalf(  String name, Material materialIn, BlockSlab half, BlockSlab doubleSlab)
    {
        super( name, materialIn, half);
        ModItems.ITEMS.add(new ItemSlab(this, this, doubleSlab).setRegistryName(name));
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }
}
