package fr.salsa.CVLST.utils.handler;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {
    public static enum EnumSlab implements IStringSerializable {
        lupuna(0,"lupuna");
        private static final EnumSlab[] METALOOKUP = new EnumSlab[values().length];
        private final int meta;
        private final String name, unlocalizedName;

        EnumSlab(int meta, String name) {
            this(meta, name, name);
        }

        EnumSlab(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        @Override
        public String getName() {
            return this.name;
        }
        public int getMeta() {
            return this.meta;
        }
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        @Override
        public String toString() {
            return this.name;
        }
        public static EnumSlab byMetadata(int meta){
            return METALOOKUP[meta];
        }
        static {
            for(EnumSlab enumtype : values()){
                METALOOKUP[enumtype.getMeta()] = enumtype;
            }
        }
    }
}
