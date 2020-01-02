package fr.salsa.CVLST.items.tools;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.common.util.EnumHelper;

public class RubySpade extends ItemSpade{
    public RubySpade(ToolMaterial material,String name) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModItems.ITEMS.add(this);
    }
}