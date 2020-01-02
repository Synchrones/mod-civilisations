package fr.salsa.CVLST.items.armors;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.References;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class RubyArmor extends ItemArmor {
    public static final ArmorMaterial material = EnumHelper.addArmorMaterial("ruby_armor", References.MODID + ":ruby_armor", 500, new int[]{4, 6, 7, 3}, 17, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND , 0);
    public RubyArmor(EntityEquipmentSlot equipmentSlotIn, String name) {
        super(material, 0, equipmentSlotIn);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModMain.modtab);
        ModItems.ITEMS.add(this);
    }
}

