package fr.salsa.CVLST.init;

import fr.salsa.CVLST.blocks.*;
import fr.salsa.CVLST.blocks.CVLSTSlabs;
import fr.salsa.CVLST.blocks.trees.CVLSTLeaves;
import fr.salsa.CVLST.blocks.trees.CVLSTLog;
import fr.salsa.CVLST.blocks.trees.CVLSTSaplings;
import fr.salsa.CVLST.utils.References;
import net.minecraft.block.Block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final ModItems INSTANCE = new ModItems();
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final Block rubyBlock = new CVLSTBlocks("ruby_block", Material.IRON, 5.0f, 30.0f, 1, "pickaxe");
    public static final Block saphirBlock = new CVLSTBlocks("saphir_block", Material.IRON, 5.0f, 30.0f, 1, "pickaxe");
    public static final Block lupunaLog = new CVLSTLog("lupuna_log");
    public static final Block lupunaPlank = new CVLSTBlocks("lupuna_plank", Material.WOOD,2.0F, 5.0F, 0, "axe");
    public static final Block lupunaLeave = new CVLSTLeaves("lupuna_leave");
    public static final Block lupunaSapling = new CVLSTSaplings("lupuna_sapling");
    public static final Block lupuna_stair = new CVLSTStairs(Blocks.PLANKS.getDefaultState(), "lupuna_stair");
    public static final BlockSlab cvlstSlabDouble = new CVLSTSlabs("cvlst_slab_double", Material.WOOD,true);



    @SubscribeEvent
    public void registerModels(ModelRegistryEvent e) {
        for (Block b : BLOCKS) {
            registerModel(b);
        }
    }

    @SideOnly(Side.CLIENT)
    private void registerModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(References.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }
}
