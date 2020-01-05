package fr.salsa.CVLST.init;

import fr.salsa.CVLST.items.CVLSTItems;
import fr.salsa.CVLST.items.armors.RubyArmor;
import fr.salsa.CVLST.items.tools.RubyAxe;
import fr.salsa.CVLST.items.tools.RubyPickaxe;
import fr.salsa.CVLST.items.tools.RubySpade;
import fr.salsa.CVLST.items.tools.RubySword;
import fr.salsa.CVLST.utils.References;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final ModItems INSTANCE = new ModItems();
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item.ToolMaterial RubyTool = EnumHelper.addToolMaterial("ruby_tools", 2, 1250, 30.0F, 20.0F, 20);
    public static final Item ruby = new CVLSTItems("ruby");
    public static final Item saphir = new CVLSTItems("saphir");
    public static final Item ruby_helmet = new RubyArmor(EntityEquipmentSlot.HEAD, "ruby_helmet");
    public static final Item ruby_chestplate = new RubyArmor(EntityEquipmentSlot.CHEST, "ruby_chestplate");
    public static final Item ruby_leggins = new RubyArmor(EntityEquipmentSlot.LEGS, "ruby_leggins");
    public static final Item ruby_boots = new RubyArmor(EntityEquipmentSlot.FEET, "ruby_boots");
    public static final Item ruby_sword = new RubySword(RubyTool, "ruby_sword");
    public static final Item ruby_pickaxe = new RubyPickaxe(RubyTool, "ruby_pickaxe");
    public static final Item ruby_axe = new RubyAxe(RubyTool, "ruby_Axe");
    public static final Item ruby_shovel = new RubySpade(RubyTool, "ruby_shovel");


    @SubscribeEvent
    public void registerModels(ModelRegistryEvent e) {
        for (Item item : ITEMS) {
            registerModel(item);
        }
    }

    @SideOnly(Side.CLIENT)
    private void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
