package fr.salsa.CVLST.blocks.trees;

import fr.salsa.CVLST.ModMain;
import fr.salsa.CVLST.init.ModBlocks;
import fr.salsa.CVLST.init.ModItems;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

public class CVLSTLog extends BlockLog {
    public CVLSTLog(String name) {
        setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
        setRegistryName(name).setUnlocalizedName(name);
        this.setHarvestLevel("axe", 0);
        setCreativeTab(ModMain.modtab);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{LOG_AXIS});
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
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
        switch (state.getValue(LOG_AXIS)){
            case X:
                i|= 2;
                break;
            case Y:
                i|= 0;
                break;
            case Z:
                i|= 4;
        }
        return i;
    }
}
