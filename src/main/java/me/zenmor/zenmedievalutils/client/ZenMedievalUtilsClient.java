package me.zenmor.zenmedievalutils.client;

import me.zenmor.zenmedievalutils.TitleStorage;
import me.zenmor.zenmedievalutils.ZenMedievalUtils;
import me.zenmor.zenmedievalutils.config.MedievalUtilsConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

public class ZenMedievalUtilsClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    private boolean keyPressed = false;
    private static ZenMedievalUtilsClient instance;
    private long starttime;
    private String previousworld;

    @Override
    public void onInitializeClient() {
        starttime = System.currentTimeMillis();
        instance = this;
        MedievalUtilsConfig.load();

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTickEnd);

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.zenmedievalutils.test",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.zenmedievalutils.test"
        ));

        HudRenderCallback.EVENT.register(this::onRender);
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
            List<String> targetWorlds = Arrays.asList("minecraft:the_end", "minecraft:sunken_0", "minecraft:sunken_1", "minecraft:sunken_2", "minecraft:sunken_3", "minecraft:sunken_4", "minecraft:sunken_5", "minecraft:sunken_6", "minecraft:sunken_7", "minecraft:sunken_8", "minecraft:sunken_9", "minecraft:sunken_10", "minecraft:sunken_11", "minecraft:sunken_12", "minecraft:sunken_13", "minecraft:sunken_14", "minecraft:sunken_15", "minecraft:sunken_16", "minecraft:sunken_17", "minecraft:sunken_18", "minecraft:sunken_19", "minecraft:sunken_20", "minecraft:sunken_21", "minecraft:sunken_22", "minecraft:sunken_23", "minecraft:sunken_24", "minecraft:sunken_25");

            if (targetWorlds.contains(currentWorld)) {
                if (client.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    GenericContainerScreenHandler chestHandler = (GenericContainerScreenHandler) client.player.currentScreenHandler;
                    Text currenttitle = TitleStorage.getCurrentTitle();

                    if (chestHandler.getRows() == 6 && currenttitle != null && currenttitle.getString().equals(" ") || chestHandler.getRows() == 6 && currenttitle != null && currenttitle.getString().equals("")) {
                        client.player.closeScreen();
                        client.player.sendMessage(Text.literal("rewards gui auto closed").styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), true);
                    }

                    if (chestHandler.getRows() == 3 && currenttitle != null && currenttitle.getString().equals("Chest")) {
                        client.player.closeScreen();
                        client.player.sendMessage(Text.literal("u opened one of the fake chests").styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), true);
                    }
                }
            }
        }
    }

    private void onRender(DrawContext context, float tickDelta) {
        String currentWorld = MinecraftClient.getInstance().player.getEntityWorld().getRegistryKey().getValue().toString();
        List<String> targetWorlds = Arrays.asList("minecraft:the_end", "minecraft:sunken_0", "minecraft:sunken_1", "minecraft:sunken_2", "minecraft:sunken_3", "minecraft:sunken_4", "minecraft:sunken_5", "minecraft:sunken_6", "minecraft:sunken_7", "minecraft:sunken_8", "minecraft:sunken_9", "minecraft:sunken_10", "minecraft:sunken_11", "minecraft:sunken_12", "minecraft:sunken_13", "minecraft:sunken_14", "minecraft:sunken_15", "minecraft:sunken_16", "minecraft:sunken_17", "minecraft:sunken_18", "minecraft:sunken_19", "minecraft:sunken_20", "minecraft:sunken_21", "minecraft:sunken_22", "minecraft:sunken_23", "minecraft:sunken_24", "minecraft:sunken_25");
        if (targetWorlds.contains(currentWorld) && MedievalUtilsConfig.getInstance().isdungeontimersenabled()) {
            if (previousworld == null || !previousworld.equals(currentWorld) && MedievalUtilsConfig.getInstance().isdungeontimersenabled()) {
                starttime = System.currentTimeMillis();
                MinecraftClient.getInstance().player.sendMessage(Text.literal("dungeon trimer started").styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), true);

                if (MedievalUtilsConfig.getInstance().isdungeondreamenabled()) {
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(ZenMedievalUtils.DREAMSPEEDRUN_EVENT, 1.0f, 1.0f));
                }
            }

            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            long currenttime = System.currentTimeMillis() - starttime;
            long milliseconds = (currenttime % 1000) / 10;
            long seconds = (currenttime / 1000) % 60;
            long minutes = (currenttime / (1000 * 60)) % 60;
            String timeString = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
            int x = (client.getWindow().getScaledWidth() - textRenderer.getWidth(timeString)) / 2;
            int y = 10;
            MatrixStack matrices = new MatrixStack();
            matrices.push();
            matrices.translate(x, y, 0);
            textRenderer.draw(timeString, 0, 0, 0xfcf300, false, matrices.peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 0);
            matrices.pop();
        } else if (previousworld != null && targetWorlds.contains(previousworld) && MedievalUtilsConfig.getInstance().isdungeontimersenabled()) {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - starttime;
            long milliseconds = (elapsedTime % 1000) / 10;
            long seconds = (elapsedTime / 1000) % 60;
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            String timeString = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);

            // they should add advetnure/minimessage for fabri9c frr
            MinecraftClient.getInstance().player.sendMessage(Text.literal("dungeon finished in: " + timeString).styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), true);
            MinecraftClient.getInstance().player.sendMessage(Text.literal("dungeon finished in: " + timeString).styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), false);
        }

        previousworld = currentWorld;
    }
}