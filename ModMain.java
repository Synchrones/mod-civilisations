package fr.salsa.CVLST;
import fr.salsa.CVLST.ct.CVLSTTab;
import fr.salsa.CVLST.events.RegisteringEvent;
import fr.salsa.CVLST.proxy.CommonProxy;
import fr.salsa.CVLST.utils.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)

public class ModMain {
    @Mod.Instance(References.MODID)

public static ModMain instance;

    @SidedProxy(clientSide = References.ClientProxy, serverSide = References.ServerProxy)
    public static CommonProxy proxy;
    public static final CreativeTabs modtab = new CVLSTTab("CVLSTTab");
    public ModMain(){
        MinecraftForge.EVENT_BUS.register(new RegisteringEvent());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent e) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit();
    }
}
