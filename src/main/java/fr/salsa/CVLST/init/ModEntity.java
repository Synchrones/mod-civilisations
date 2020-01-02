package fr.salsa.CVLST.init;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.entity.EntityTest;
import fr.salsa.CVLST.utils.References;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntity {
    public static void registerEntities(){
        registerEntity("entity_test", EntityTest.class, References.Entity_Test, 50, 11437146, 000000);
    }


    private static void registerEntity(String name, Class <? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":" + name), entity, name, id, ModMain.instance, range, 1, true, color1, color2);


    }



}
