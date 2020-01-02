package fr.salsa.CVLST.utils;

import fr.salsa.CVLST.entity.render.RenderEntityTest;
import net.minecraft.client.renderer.entity.Render;
import fr.salsa.CVLST.entity.EntityTest;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderEntity {
    public static void registerEntityRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory<EntityTest>(){
            @Override
            public Render<? super EntityTest> createRenderFor(RenderManager manager) {
                return new RenderEntityTest(manager);
            }
        });
    }
}
