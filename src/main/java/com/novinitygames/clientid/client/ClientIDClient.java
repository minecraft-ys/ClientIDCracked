package com.novinitygames.clientid.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.novinitygames.clientid.client.records.ModCheckC2SPayload;
import com.novinitygames.clientid.client.records.ModListC2SPayload;
import com.novinitygames.clientid.client.records.PackListC2SPayload;
import com.novinitygames.clientid.client.records.VersionC2SPayload;
import com.novinitygames.clientid.client.util.ListerUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class ClientIDClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientIDClient.isConnectedToServer = true;

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            client.execute(() -> {
                String uuid = client.player.getUuidAsString();
                ClientPlayNetworking.send(new ModListC2SPayload(getClientID()));
                ClientPlayNetworking.send(new PackListC2SPayload(String.join(",", ListerUtil.getEnabledPacks())));
                ClientPlayNetworking.send(new VersionC2SPayload("1.1.1"));
                ClientPlayNetworking.send(new ModCheckC2SPayload(uuid));
            });
        });
    }

    public static boolean pieChartDisabled = false;
    public static boolean isConnectedToServer = false;

    public static String getClientID() {
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        ArrayList<String> modIDs = new ArrayList<>();

        List<String> allowed = List.of(
            "clientid",
            "fabric-api",
            "fabric-api-base",
            "fabric-api-lookup-api-v1",
            "fabric-biome-api-v1",
            "fabric-block-api-v1",
            "fabric-block-view-api-v2",
            "fabric-command-api-v2",
            "fabric-content-registries-v0",
            "fabric-convention-tags-v1",
            "fabric-convention-tags-v2",
            "fabric-crash-report-info-v1",
            "fabric-data-attachment-api-v1",
            "fabric-data-generation-api-v1",
            "fabric-dimensions-v1",
            "fabric-entity-events-v1",
            "fabric-events-interaction-v0",
            "fabric-game-rule-api-v1",
            "fabric-item-api-v1",
            "fabric-item-group-api-v1",
            "fabric-key-binding-api-v1",
            "fabric-lifecycle-events-v1",
            "fabric-loot-api-v2",
            "fabric-loot-api-v3",
            "fabric-message-api-v1",
            "fabric-model-loading-api-v1",
            "fabric-networking-api-v1",
            "fabric-object-builder-api-v1",
            "fabric-particles-v1",
            "fabric-recipe-api-v1",
            "fabric-registry-sync-v0",
            "fabric-renderer-api-v1",
            "fabric-renderer-indigo",
            "fabric-rendering-fluids-v1",
            "fabric-rendering-v1",
            "fabric-resource-conditions-api-v1",
            "fabric-resource-loader-v0",
            "fabric-resource-loader-v1",
            "fabric-screen-api-v1",
            "fabric-screen-handler-api-v1",
            "fabric-serialization-api-v1",
            "fabric-sound-api-v1",
            "fabric-tag-api-v1",
            "fabric-transfer-api-v1",
            "fabric-transitive-access-wideners-v1",
            "fabricloader",
            "java",
            "minecraft",
            "mixinextras"
        );

        for (ModContainer mod : mods) {
            String id = mod.getMetadata().getId();
            if (allowed.contains(id)) {
                modIDs.add(id);
            }
        }
        return String.join(",", modIDs);
    }
}