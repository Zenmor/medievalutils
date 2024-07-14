package me.zenmor.zenmedievalutils.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuIntegration::createConfigScreen;
    }

    private static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("text.medievalutils.config.title"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        builder.getOrCreateCategory(Text.empty())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.autoclose"),
                                MedievalUtilsConfig.getInstance().aUtoClose)
                        .setDefaultValue(MedievalUtilsConfig.defaultAUtoClose)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoclose"))
                        .setSaveConsumer(autoclose -> MedievalUtilsConfig.getInstance().aUtoClose = autoclose)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.stfurewards"),
                                MedievalUtilsConfig.getInstance().stfurewards)
                        .setDefaultValue(MedievalUtilsConfig.defaultSTFUrewards)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfurewards"))
                        .setSaveConsumer(stfurewards -> MedievalUtilsConfig.getInstance().stfurewards = stfurewards)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.stfutips"),
                                MedievalUtilsConfig.getInstance().stfutips)
                        .setDefaultValue(MedievalUtilsConfig.defaultSTFUtips)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfutips"))
                        .setSaveConsumer(stfutips -> MedievalUtilsConfig.getInstance().stfutips = stfutips)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.stfuvotes"),
                                MedievalUtilsConfig.getInstance().stfuvotes)
                        .setDefaultValue(MedievalUtilsConfig.defaultSTFUvotes)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfuvotes"))
                        .setSaveConsumer(stfuvotes -> MedievalUtilsConfig.getInstance().stfuvotes = stfuvotes)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.stfuryan"),
                                MedievalUtilsConfig.getInstance().stfuryan)
                        .setDefaultValue(MedievalUtilsConfig.defaultSTFUryan)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfuryan"))
                        .setSaveConsumer(stfuryan -> MedievalUtilsConfig.getInstance().stfuryan = stfuryan)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.autoready"),
                                MedievalUtilsConfig.getInstance().autoready)
                        .setDefaultValue(MedievalUtilsConfig.defaultautoready)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoready"))
                        .setSaveConsumer(autoready -> MedievalUtilsConfig.getInstance().autoready = autoready)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.autoleave"),
                                MedievalUtilsConfig.getInstance().autoleave)
                        .setDefaultValue(MedievalUtilsConfig.defaultautoleave)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoleave"))
                        .setSaveConsumer(autoleave -> MedievalUtilsConfig.getInstance().autoleave = autoleave)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.dungeontimer"),
                                MedievalUtilsConfig.getInstance().dungeontimer)
                        .setDefaultValue(MedievalUtilsConfig.defaultdungeontimer)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.dungeontimer"))
                        .setSaveConsumer(dungeontimer -> MedievalUtilsConfig.getInstance().dungeontimer = dungeontimer)
                        .build())
                .addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("text.medievalutils.config.option.autowelcome"),
                                MedievalUtilsConfig.getInstance().welcome)
                        .setDefaultValue(MedievalUtilsConfig.defaultWelcome)
                        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autowelcome"))
                        .setSaveConsumer(welcome -> MedievalUtilsConfig.getInstance().welcome = welcome)
                        .build());
        return builder
                .setSavingRunnable(MedievalUtilsConfig.getInstance()::save)
                .build();
    }

    private static Optional<Text> emptyListErrorSupplier(List<?> list) {
        return list == null || list.isEmpty() ? Optional.of(Text.translatable("text.zenmedievalutils.config.error.empty_list")) : Optional.empty();
    }

    private static Optional<Text> emptyStringErrorSupplier(String str) {
        return str == null || str.isEmpty() ? Optional.of(Text.translatable("text.zenmedievalutils.config.error.empty_string")) : Optional.empty();
    }
}