package fr.salsa.CVLST.blocks.slabs;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public class CVLSTSlabDouble extends CVLSTSlabs {
    public CVLSTSlabDouble(  String name, Material materialIn, BlockSlab half){
        super( name, materialIn, half);
    }

    @Override
    public boolean isDouble(){
        return true;
    }
}
