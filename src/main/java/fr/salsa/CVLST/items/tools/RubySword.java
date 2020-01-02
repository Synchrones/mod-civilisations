package fr.salsa.CVLST.items.tools;

import net.minecraft.item.ItemSword;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import net.minecraftforge.common.util.EnumHelper;

public class RubySword extends ItemSword {
    public RubySword(ToolMaterial material,String name) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModItems.ITEMS.add(this);
    }
}


