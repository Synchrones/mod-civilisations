package fr.salsa.CVLST.items;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.item.Item;

public class CVLSTItems extends Item {
    public CVLSTItems(String name){
        setRegistryName(name).setUnlocalizedName(name);
        setMaxStackSize(64);
        setCreativeTab(ModMain.modtab);
        ModItems.ITEMS.add(this);
    }
}
