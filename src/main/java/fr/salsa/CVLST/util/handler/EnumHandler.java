package fr.salsa.CVLST.utils.handler;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {
    public static enum Enumtype implements IStringSerializable {
        DENSEJUNGLE(0,"dense_jungle"),
        EXAMPLE(1,"example");
        private static final EnumHandler.Enumtype[] METALOOKUP = new EnumHandler.Enumtype[values().length];
        private final int meta;
        private final String name, unlocalizedName;

        Enumtype(int meta, String name) {
            this(meta, name, name);
        }

        Enumtype(int meta, String name, String unlocalizedName) {
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
        public static EnumHandler.Enumtype byMetadata(int meta){
            return METALOOKUP[meta];
        }
        static {
            for(EnumHandler.Enumtype enumtype : values()){
                METALOOKUP[enumtype.getMeta()] = enumtype;
            }
        }
    }
}
