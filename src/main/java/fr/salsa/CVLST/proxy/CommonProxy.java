package fr.salsa.CVLST.proxy;


import fr.salsa.CVLST.events.RegisteringEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e){
        RegisteringEvent.preInitRegistries(e);
        RegisteringEvent.otherRegistries();
    }
    public void init(){

    }
    public void postInit(){

    }
}