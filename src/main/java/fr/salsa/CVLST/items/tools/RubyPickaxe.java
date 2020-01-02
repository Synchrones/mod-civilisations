package fr.salsa.CVLST.items.tools;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class RubyPickaxe extends ItemPickaxe{
    public RubyPickaxe(ToolMaterial material,String name) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModItems.ITEMS.add(this);
    }
}


