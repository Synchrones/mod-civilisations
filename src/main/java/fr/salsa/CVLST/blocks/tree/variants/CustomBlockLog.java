package fr.salsa.CVLST.blocks.trees;

import com.google.common.base.Predicate;
import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.blocks.items.ItemBlockVariants;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import fr.salsa.CVLST.utils.handler.EnumHandler;
import fr.salsa.CVLST.utils.interfaces.IMetaName;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class CustomBlockLog extends BlockLog implements IMetaName {
    public static final PropertyEnum<EnumHandler.Enumtype> VARIANT = PropertyEnum.<EnumHandler.Enumtype>create("variant", EnumHandler.Enumtype.class, new Predicate<EnumHandler.Enumtype>(){
        public boolean apply(@Nullable EnumHandler.Enumtype apply){
            return apply.getMeta()< 2; //2 est le nombre d'enum dans EnumType
        }
    });
    private String name;
    public CustomBlockLog(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setSoundType(SoundType.WOOD);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.Enumtype.DENSEJUNGLE).withProperty(LOG_AXIS, EnumAxis.Y));
        this.name = name;
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
        setCreativeTab(ModMain.modtab);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumHandler.Enumtype enumtype : EnumHandler.Enumtype.values()) {
            items.add(new ItemStack(this, 1, enumtype.getMeta()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState().withProperty(VARIANT, EnumHandler.Enumtype.byMetadata((meta & 1) % 2));
        switch (meta & 6){
            case 0:
                state = state.withProperty(LOG_AXIS, EnumAxis.Y);
                break;
            case 2:
                state = state.withProperty(LOG_AXIS, EnumAxis.X);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, EnumAxis.Z);
                break;
            default:
                state.withProperty(LOG_AXIS, EnumAxis.NONE);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta();
        switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS)){
            case X:
                i|= 2;
                break;
            case Y:
                i|= 4;
                break;
            case Z:
                i|=0;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{VARIANT, LOG_AXIS});
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumHandler.Enumtype)state.getValue(VARIANT)).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumHandler.Enumtype.values()[stack.getItemDamage()].getName();
    }
}
