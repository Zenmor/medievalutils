package me.zenmor.zenmedievalutils.mixin;

import me.zenmor.zenmedievalutils.TitleStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class GuiTitle {

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void onSetScreen(Screen screen, CallbackInfo ci) {
        if (screen instanceof HandledScreen) {
            HandledScreen<?> handledScreen = (HandledScreen<?>) screen;
            Text title = handledScreen.getTitle();
            TitleStorage.setCurrentTitle(title);
            System.out.println("opened screen with title: " + (title != null ? title.getString() : "null"));
        } else {
            TitleStorage.setCurrentTitle(null);
        }
    }
}
