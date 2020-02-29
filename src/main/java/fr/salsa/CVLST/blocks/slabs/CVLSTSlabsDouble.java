package fr.salsa.CVLST.blocks.slabs;

import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class CVLSTSlabsDouble extends CVLSTSlabs{
    public CVLSTSlabsDouble(String name, Material materialIn, BlockSlab half, CreativeTabs tab){
        super(name, materialIn, half, tab);
    }
    @Override
    public boolean isDouble(){
        return true;
    }
}
