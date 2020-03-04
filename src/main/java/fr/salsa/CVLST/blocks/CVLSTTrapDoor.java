package fr.salsa.CVLST.blocks;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CVLSTTrapDoor extends BlockTrapDoor {
    public CVLSTTrapDoor(String name, Material materialIn) {
        super(materialIn);
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
}
