package fr.salsa.CVLST.ct;

        import fr.salsa.CVLST.init.ModBlocks;
        import net.minecraft.creativetab.CreativeTabs;
        import net.minecraft.item.ItemStack;

public class CVLSTTab extends CreativeTabs {


    public CVLSTTab(String label) {
        super(label);

        setBackgroundImageName("CVLSTTab.png");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModBlocks.rubyBlock);
    }
}
