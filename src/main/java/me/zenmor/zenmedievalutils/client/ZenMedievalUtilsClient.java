package me.zenmor.zenmedievalutils.client;

import me.zenmor.zenmedievalutils.TitleStorage;
import me.zenmor.zenmedievalutils.config.MedievalUtilsConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

public class ZenMedievalUtilsClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    private boolean keyPressed = false;
    private static ZenMedievalUtilsClient instance;

    @Override
    public void onInitializeClient() {
        instance = this;
        MedievalUtilsConfig.load();

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTickEnd);

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.zenmedievalutils.test",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.zenmedievalutils.test"
        ));
    }

    public void onClientTickEnd(MinecraftClient client) {
        if (keyBinding.wasPressed()) {
            if (!keyPressed) {
                keyPressed = true;
                client.player.networkHandler.sendChatCommand("shopgui blocks");
            }
        } else {
            keyPressed = false;
        }

        if (client.player != null && client.player.currentScreenHandler != null && MedievalUtilsConfig.getInstance().isaUtoClose()) {
            String currentWorld = client.player.getEntityWorld().getRegistryKey().getValue().toString();
            List<String> targetWorlds = Arrays.asList("minecraft:sunken_0", "minecraft:sunken_1", "minecraft:sunken_2", "minecraft:sunken_3", "minecraft:sunken_4", "minecraft:sunken_5", "minecraft:sunken_6", "minecraft:sunken_7", "minecraft:sunken_8", "minecraft:sunken_9", "minecraft:sunken_10", "minecraft:sunken_11", "minecraft:sunken_12", "minecraft:sunken_13", "minecraft:sunken_14", "minecraft:sunken_15", "minecraft:sunken_16", "minecraft:sunken_17", "minecraft:sunken_18", "minecraft:sunken_19", "minecraft:sunken_20", "minecraft:sunken_21", "minecraft:sunken_22", "minecraft:sunken_23", "minecraft:sunken_24", "minecraft:sunken_25");

            if (targetWorlds.contains(currentWorld)) {
                if (client.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    GenericContainerScreenHandler chestHandler = (GenericContainerScreenHandler) client.player.currentScreenHandler;
                    Text currenttitle = TitleStorage.getCurrentTitle();

                    if (chestHandler.getRows() == 6 && currenttitle != null && currenttitle.getString().equals(" ") || chestHandler.getRows() == 6 && currenttitle != null && currenttitle.getString().equals("")) {
                        client.player.closeScreen();
                    }
                }
            }
        }
    }
}