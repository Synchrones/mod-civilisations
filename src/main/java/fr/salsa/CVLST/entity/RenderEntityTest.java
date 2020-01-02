package fr.salsa.CVLST.entity.render;

import fr.salsa.CVLST.entity.EntityTest;
import fr.salsa.CVLST.utils.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;



public class RenderEntityTest extends RenderLiving<EntityTest> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(References.MODID + ":textures/entity/entity_test.png");

    public RenderEntityTest(RenderManager manager){
        super(manager, new fr.salsa.CVLST.entity.models.EntityTest(), 0.5F);

    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTest entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityTest entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}
