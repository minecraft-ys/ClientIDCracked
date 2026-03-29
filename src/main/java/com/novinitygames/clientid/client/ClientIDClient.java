package com.novinitygames.clientid.client;

import java.util.ArrayList;
import java.util.Collection;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
public class ClientIDClient {

    public static boolean pieChartDisabled = false;
    public static boolean isConnectedToServer = false;

    public static String getClientID() {
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        ArrayList<String> modIDs = new ArrayList<>();
        for (ModContainer mod : mods) {
            modIDs.add(mod.getMetadata().getId());
        }
        return String.join(",", modIDs);
    }
}