package com.codemonkey85.stardust;

import com.codemonkey85.stardust.item.ModCreativeTabs;
import com.codemonkey85.stardust.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StardustMod.MOD_ID)
public class StardustMod {
    public static final String MOD_ID = "stardust";

    public StardustMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
    }
}
