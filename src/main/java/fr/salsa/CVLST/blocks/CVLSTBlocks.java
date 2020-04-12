package fr.salsa.CVLST.blocks;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CVLSTBlocks extends Block {

    public CVLSTBlocks(String name, Material materialIn, float hardness, float resistance, int harvestLevel, String harvestType) {
        super(materialIn);
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        setHardness(hardness);
        setResistance(resistance);
        setHarvestLevel(harvestType, harvestLevel);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }
}
