package com.novinitygames.clientid.client.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.ArrayList;
import java.util.Collection;

public class ListerUtil {
    public static ArrayList<String> getMods() {
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        ArrayList<String> list = new ArrayList<>();
        mods.forEach(mod -> {
            String id = mod.getMetadata().getId();
            // Only include "fabric-api" and "clientid"
            if ("fabric-api".equals(id) || "clientid".equals(id)) {
                list.add(id);
            }
        });
        return list;
    }
}