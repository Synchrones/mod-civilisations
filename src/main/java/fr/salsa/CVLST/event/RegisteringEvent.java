package fr.salsa.CVLST.events;

import fr.salsa.CVLST.init.ModBiomes;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModEntity;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.world.gen.DenseJungleTreeGen;
import fr.salsa.CVLST.world.gen.generators.WorldGenCustomStructures;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
@Mod.EventBusSubscriber
public class RegisteringEvent {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        e.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }
    public static void preInitRegistries(FMLPreInitializationEvent e){
        ModEntity.registerEntities();
    }

    public static void otherRegistries() {
        ModBiomes.registerBiomes();
        GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
        GameRegistry.registerWorldGenerator(new DenseJungleTreeGen(), 0);
    }
}
