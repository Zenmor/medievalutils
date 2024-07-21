package me.zenmor.medievalutils.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class DungeonGUI extends BaseOwoScreen<FlowLayout> {
    private final String completionTime;

    public DungeonGUI(String completionTime) {
        super(Text.of("Dungeon Completion Time"));
        this.completionTime = completionTime;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        System.out.println("Creating adapter for DungeonGUI...");
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalFlow(Sizing.fixed(200), Sizing.fixed(150))
                        .child(
                                Components.label(Text.translatable("completedscreen.dungeongg", completionTime))
                        )
                        .child(
                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(20))
                                        .child(Components.label(Text.of(" ")))
                        )
                        .child(
                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(20))
                                        .child(Components.label(Text.of(" ")))
                        )
                        .child(
                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(20))
                                        .child(Components.label(Text.of(" ")))
                        )
                        .child(
                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(20))
                                        .child(Components.label(Text.of(" ")))
                        )
                        .child(
                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(10))
                                        .child(Components.label(Text.of(" ")))
                        )
                        .child(
                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(20))
                                        .child(
                                                Components.button(Text.literal("another"), button -> {
                                                    assert MinecraftClient.getInstance().player != null;
                                                    MinecraftClient.getInstance().setScreen(null);
                                                    MinecraftClient.getInstance().player.networkHandler.sendChatCommand("dungeon play sunken");
                                                })
                                        )
                                        .child(
                                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(10))
                                                        .child(Components.label(Text.of(" ")))
                                        )
                                        .child(
                                                Components.button(Text.literal("rewards"), button -> {
                                                    assert MinecraftClient.getInstance().player != null;
                                                    MinecraftClient.getInstance().setScreen(null);
                                                    MinecraftClient.getInstance().player.networkHandler.sendChatCommand("rewards");
                                                })
                                        )
                                        .child(
                                                Containers.horizontalFlow(Sizing.content(), Sizing.fixed(10))
                                                        .child(Components.label(Text.of(" ")))
                                        )
                                        .child(
                                                Components.button(Text.literal("close"), button -> {
                                                    assert MinecraftClient.getInstance().player != null;
                                                    MinecraftClient.getInstance().setScreen(null);
                                                })
                                        )
                                        .horizontalAlignment(HorizontalAlignment.CENTER)
                        )
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }
}