package me.zenmor.medievalutils.client;

import com.mojang.logging.LogUtils;
import me.zenmor.medievalutils.config.MedievalConfig;
import me.zenmor.medievalutils.gui.DungeonGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

import static me.zenmor.medievalutils.client.ClientChat.CONFIG;

public class MedievalUtilsClient implements ClientModInitializer {
    MedievalConfig.dungeoncompletetypes dungeoncomplete = CONFIG.dungeoncomplete();
    MedievalConfig.dungeontimerpos dungeonpos = CONFIG.dungeomtimer.dungeonpos();

    private static KeyBinding keyBinding;
    private static MedievalUtilsClient instance;
    private long starttime;
    private String previousworld;

    @Override
    public void onInitializeClient() {
        starttime = System.currentTimeMillis();
        instance = this;

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.medievalutils.test",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.medievalutils.test"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTickEnd);
        HudRenderCallback.EVENT.register(this::onRender);
        ClientTickEvents.START_CLIENT_TICK.register(new FishyAlert());

        // gonna keep disabled
        //ItemTooltipCallback.EVENT.register((ItemStack stack, TooltipContext context, List<Text> lines) -> {
        //    if (stack.isOf(Items.PAPER))
        //        lines.add(Text.empty());
        //    lines.add(Text.literal("The Warmer I Am - craft bread"));
        //    lines.add(Text.literal("North, East - craft compass"));
        //    lines.add(Text.literal("You Break Me, But - break glass"));
        //    lines.add(Text.literal("I Hold Things But I do not have - craft chest"));
        //    lines.add(Text.literal("I Hold Things But when you break - craft cauldron"));
        //    lines.add(Text.literal("I have towns - craft map"));
        //    lines.add(Text.literal("i have feathers - craft arrow"));
        //    lines.add(Text.literal("You can understand what - craft sign"));
        //    lines.add(Text.literal("Place teh portable item - place ender chest"));
        //    lines.add(Text.literal("This will be My Lighthouse - craft beacno"));
        //    lines.add(Text.literal("Place something that is hard - place obisdiajn"));
        //});
    }

    public void onClientTickEnd(MinecraftClient client) {
        if (keyBinding.wasPressed()) {
            client.player.networkHandler.sendChatCommand("owo-config medievalutils");
        }

        if (client.player != null && client.player.currentScreenHandler != null && CONFIG.dungeons.dungeonautoclose()) {
            String serverip = getserverip();
            if (serverip == null) return;

            String currentWorld = client.player.getEntityWorld().getRegistryKey().getValue().toString();
            List<String> dungeonworlds = Arrays.asList("minecraft:sunken_0", "minecraft:sunken_1", "minecraft:sunken_2", "minecraft:sunken_3", "minecraft:sunken_4", "minecraft:sunken_5", "minecraft:sunken_6", "minecraft:sunken_7", "minecraft:sunken_8", "minecraft:sunken_9", "minecraft:sunken_10", "minecraft:sunken_11", "minecraft:sunken_12", "minecraft:sunken_13", "minecraft:sunken_14", "minecraft:sunken_15", "minecraft:sunken_16", "minecraft:sunken_17", "minecraft:sunken_18", "minecraft:sunken_19", "minecraft:sunken_20", "minecraft:sunken_21", "minecraft:sunken_22", "minecraft:sunken_23", "minecraft:sunken_24", "minecraft:sunken_25");
            List<String> devworlds = Arrays.asList("minecraft:world_the_end");

            if (dungeonworlds.contains(currentWorld) || CONFIG.debugging() && devworlds.contains(currentWorld)) {
                if (client.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    GenericContainerScreenHandler chestHandler = (GenericContainerScreenHandler) client.player.currentScreenHandler;
                    Text currenttitle = TitleStorage.getCurrentTitle();

                    if (chestHandler.getRows() == 6 && currenttitle != null && currenttitle.getString().equals(" ") || chestHandler.getRows() == 6 && currenttitle != null && currenttitle.getString().equals("")) {
                        client.player.closeScreen();
                        client.player.sendMessage(Text.literal("rewards gui auto closed").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);
                    }

                    if (chestHandler.getRows() == 3 && currenttitle != null && currenttitle.getString().equals("Chest")) {
                        client.player.closeScreen();
                        client.player.sendMessage(Text.literal("u opened one of the fake chests").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);
                    }
                }
            }
        }
    }

    private void onRender(DrawContext context, RenderTickCounter tickDelta) {
        String currentWorld = MinecraftClient.getInstance().player.getEntityWorld().getRegistryKey().getValue().toString();
        List<String> dungeonworlds = Arrays.asList("minecraft:sunken_0", "minecraft:sunken_1", "minecraft:sunken_2", "minecraft:sunken_3", "minecraft:sunken_4", "minecraft:sunken_5", "minecraft:sunken_6", "minecraft:sunken_7", "minecraft:sunken_8", "minecraft:sunken_9", "minecraft:sunken_10", "minecraft:sunken_11", "minecraft:sunken_12", "minecraft:sunken_13", "minecraft:sunken_14", "minecraft:sunken_15", "minecraft:sunken_16", "minecraft:sunken_17", "minecraft:sunken_18", "minecraft:sunken_19", "minecraft:sunken_20", "minecraft:sunken_21", "minecraft:sunken_22", "minecraft:sunken_23", "minecraft:sunken_24", "minecraft:sunken_25");
        List<String> devworlds = Arrays.asList("minecraft:the_end");
        LogUtils.getLogger().info(currentWorld + " | " + CONFIG.dungeomtimer.dungeontimer() + " | " + devworlds.contains(currentWorld));
        if (dungeonworlds.contains(currentWorld) && CONFIG.dungeomtimer.dungeontimer() || dungeonworlds.contains(devworlds) && CONFIG.dungeomtimer.dungeontimer() && CONFIG.debugging()) {
            if (previousworld == null || !previousworld.equals(currentWorld) && CONFIG.dungeomtimer.dungeontimer()) {
                starttime = System.currentTimeMillis();
                MinecraftClient.getInstance().player.sendMessage(Text.literal("dungeon timer started").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);
            }

            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            long currenttime = System.currentTimeMillis() - starttime;
            long milliseconds = (currenttime % 1000) / 10;
            long seconds = (currenttime / 1000) % 60;
            long minutes = (currenttime / (1000 * 60)) % 60;
            String timeString = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);

            int x = 0, y = 10;
            x = switch (CONFIG.dungeomtimer.dungeonpos()) {
                case TOP_CENTER -> (client.getWindow().getScaledWidth() - textRenderer.getWidth(timeString)) / 2;
                case TOP_LEFT -> 10;
                case TOP_RIGHT -> client.getWindow().getScaledWidth() - textRenderer.getWidth(timeString) - 10;
            };

            MatrixStack matrices = new MatrixStack();
            matrices.push();
            matrices.translate(x, y, 0);
            textRenderer.draw(timeString, 0, 0, CONFIG.modcolor().rgb(), false, matrices.peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 0);
            matrices.pop();
        } else if (previousworld != null && dungeonworlds.contains(previousworld) && CONFIG.dungeomtimer.dungeontimer() || previousworld != null && devworlds.contains(previousworld) && CONFIG.dungeomtimer.dungeontimer() && CONFIG.debugging()) {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - starttime;
            long milliseconds = (elapsedTime % 1000) / 10;
            long seconds = (elapsedTime / 1000) % 60;
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            String timeString = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);

            // they should add advetnure/minimessage for fabri9c frr
            if (CONFIG.dungeoncomplete() == MedievalConfig.dungeoncompletetypes.GUI && CONFIG.dungeomtimer.dungeontimer()) {
                MinecraftClient.getInstance().execute(() -> {
                    MinecraftClient.getInstance().setScreen(new DungeonGUI(timeString));
                });
            } else if (CONFIG.dungeoncomplete() == MedievalConfig.dungeoncompletetypes.TOAST && CONFIG.dungeomtimer.dungeontimer()) {
                MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.literal("Dungeon Completed"), Text.literal("Completed in: " + timeString)));
            } else if (CONFIG.dungeoncomplete() == MedievalConfig.dungeoncompletetypes.ACTIONBAR && CONFIG.dungeomtimer.dungeontimer()) {
                MinecraftClient.getInstance().player.sendMessage(Text.literal("dungeon finished in: " + timeString).styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);
            } else if (CONFIG.dungeoncomplete() == MedievalConfig.dungeoncompletetypes.CHAT && CONFIG.dungeomtimer.dungeontimer()) {
                Text copybutton = Text.literal("[copy]")
                        .styled(style -> style
                                .withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "make this work later"))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("click to copy text")))
                        );

                Text startbutton = Text.literal("[start another]")
                        .styled(style -> style
                                .withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dungeon play sunken"))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("click to start another")))
                        );

                Text message = Text.literal("\ndungeon finished in: " + timeString + "\n")
                        .styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb())))
                        .append(" → ")
                        .append(copybutton)
                        .append(" ")
                        .append(startbutton)
                        .append("\n ");

                MinecraftClient.getInstance().player.sendMessage(message, false);
            }
        }
        previousworld = currentWorld;
    }

    private String getserverip() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.getCurrentServerEntry() != null) {
            return client.getCurrentServerEntry().address;
        }
        return null;
    }
}
