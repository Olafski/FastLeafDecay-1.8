package com.olafski.fastleafdecay;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Olaf on 29-12-2015.
 */
@Mod(modid = FastLeafDecay.MODID, version = FastLeafDecay.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.8.8]")
public class FastLeafDecay {
    public static final String MODID = "fastleafdecay";
    public static final String VERSION = "1.4";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FldConfiguration.init(event);
    }
}
