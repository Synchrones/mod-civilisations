package fr.salsa.CVLST.items.tools;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.common.util.EnumHelper;

public class RubyAxe extends ItemAxe {
    public RubyAxe(ToolMaterial material, String name) {
        super(material, 3.0F,0.5F);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModItems.ITEMS.add(this);
    }
}

