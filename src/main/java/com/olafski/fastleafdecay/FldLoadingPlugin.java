package com.olafski.fastleafdecay;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by Olaf on 29-12-2015.
 */
@IFMLLoadingPlugin.SortingIndex(1001)
public class FldLoadingPlugin implements IFMLLoadingPlugin {
    public static boolean IN_MCP = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { FldTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        IN_MCP = !(Boolean)data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
