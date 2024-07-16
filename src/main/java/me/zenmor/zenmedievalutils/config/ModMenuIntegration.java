package me.zenmor.zenmedievalutils.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
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

    public enum hudposition {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        ABOVE_ACTIONBAR;
    }

    private static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("text.medievalutils.config.title"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();


        ConfigCategory qolcategory = builder.getOrCreateCategory(Text.translatable("text.medievalutils.config.category.qol"));
        qolcategory.addEntry(entryBuilder.startTextDescription(Text.translatable("text.medievalutils.config.bugreports.qol"))
                .build());
        qolcategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.autoleave"),
                        MedievalUtilsConfig.getInstance().autoleave)
                .setDefaultValue(MedievalUtilsConfig.defaultautoleave)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoleave"))
                .setSaveConsumer(autoleave -> MedievalUtilsConfig.getInstance().autoleave = autoleave)
                .build());
        qolcategory.addEntry(entryBuilder.startIntSlider(Text.translatable("text.medievalutils.config.option.autowelcomedelay"), MedievalUtilsConfig.getInstance().getwelcomedelay(), 0, 3)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autowelcomedelay"))
                .setSaveConsumer(newValue -> MedievalUtilsConfig.getInstance().welceomdelay = newValue)
                .build());
        qolcategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.autowelcome"),
                        MedievalUtilsConfig.getInstance().welcome)
                .setDefaultValue(MedievalUtilsConfig.defaultWelcome)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autowelcome"))
                .setSaveConsumer(welcome -> MedievalUtilsConfig.getInstance().welcome = welcome)
                .build());
        qolcategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.autoskipnight"),
                        MedievalUtilsConfig.getInstance().autoskipnight)
                .setDefaultValue(MedievalUtilsConfig.defaultautoskipnight)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoskipnight"))
                .setSaveConsumer(autoskipnight -> MedievalUtilsConfig.getInstance().autoskipnight = autoskipnight)
                .build());
        qolcategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.autoclaimdaily"),
                        MedievalUtilsConfig.getInstance().autoclaimdaily)
                .setDefaultValue(MedievalUtilsConfig.defaultautoclaimdaily)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoclaimdaily"))
                .setSaveConsumer(autoclaimdaily -> MedievalUtilsConfig.getInstance().autoclaimdaily = autoclaimdaily)
                .build());
        qolcategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.skiphub"),
                        MedievalUtilsConfig.getInstance().skiphub)
                .setDefaultValue(MedievalUtilsConfig.defaultskiphub)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.skiphub"))
                .setSaveConsumer(skiphub -> MedievalUtilsConfig.getInstance().skiphub = skiphub)
                .build());

        ConfigCategory stfucategory = builder.getOrCreateCategory(Text.translatable("text.medievalutils.config.category.stfu"));
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfurewards"),
                        MedievalUtilsConfig.getInstance().stfurewards)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUrewards)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfurewards"))
                .setSaveConsumer(stfurewards -> MedievalUtilsConfig.getInstance().stfurewards = stfurewards)
                .build());
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfutips"),
                        MedievalUtilsConfig.getInstance().stfutips)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUtips)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfutips"))
                .setSaveConsumer(stfutips -> MedievalUtilsConfig.getInstance().stfutips = stfutips)
                .build());
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfudaily"),
                        MedievalUtilsConfig.getInstance().stfudaily)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUdaily)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfudaily"))
                .setSaveConsumer(stfudaily -> MedievalUtilsConfig.getInstance().stfudaily = stfudaily)
                .build());
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfuskipnight"),
                        MedievalUtilsConfig.getInstance().stfuskipnight)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUskipnight)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfuskipnight"))
                .setSaveConsumer(stfuskipnight -> MedievalUtilsConfig.getInstance().stfuskipnight = stfuskipnight)
                .build());
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfuvotes"),
                        MedievalUtilsConfig.getInstance().stfuvotes)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUvotes)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfuvotes"))
                .setSaveConsumer(stfuvotes -> MedievalUtilsConfig.getInstance().stfuvotes = stfuvotes)
                .build());
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfuworldguard"),
                        MedievalUtilsConfig.getInstance().stfuworldguard)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUworldguard)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfuworldguard"))
                .setSaveConsumer(stfuworldguard -> MedievalUtilsConfig.getInstance().stfuworldguard = stfuworldguard)
                .build());
        stfucategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.stfuryan"),
                        MedievalUtilsConfig.getInstance().stfuryan)
                .setDefaultValue(MedievalUtilsConfig.defaultSTFUryan)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.stfuryan"))
                .setSaveConsumer(stfuryan -> MedievalUtilsConfig.getInstance().stfuryan = stfuryan)
                .build());

        ConfigCategory dungeoncategory = builder.getOrCreateCategory(Text.translatable("text.medievalutils.config.category.dungeons"));
        dungeoncategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.dungeontimer"),
                        MedievalUtilsConfig.getInstance().dungeontimer)
                .setDefaultValue(MedievalUtilsConfig.defaultdungeontimer)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.dungeontimer"))
                .setSaveConsumer(dungeontimer -> MedievalUtilsConfig.getInstance().dungeontimer = dungeontimer)
                .build());
        dungeoncategory.addEntry(entryBuilder.startEnumSelector(Text.translatable("text.medievalutils.config.option.hudposition"), hudposition.class, hudposition.valueOf(MedievalUtilsConfig.getInstance().gethudposition()))
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.hudposition"))
                .setSaveConsumer(newValue -> MedievalUtilsConfig.getInstance().hudposition = newValue.name())
                .build());
        dungeoncategory.addEntry(entryBuilder.startColorField(Text.translatable("text.medievalutils.config.option.hudcolor"), MedievalUtilsConfig.getInstance().gethudcolor())
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.hudcolor"))
                .setSaveConsumer(newValue -> MedievalUtilsConfig.getInstance().hudcolor = newValue)
                .build());
        //dungeoncategory.addEntry(entryBuilder.startBooleanToggle(
        //                Text.translatable("text.medievalutils.config.option.dungeontimerDREAM"),
        //                MedievalUtilsConfig.getInstance().dungeondream)
        //        .setDefaultValue(MedievalUtilsConfig.defaultdungeondream)
        //        .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.dungeondream"))
        //        .setSaveConsumer(dungeondream -> MedievalUtilsConfig.getInstance().dungeondream = dungeondream)
        //        .build());
        dungeoncategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.autoready"),
                        MedievalUtilsConfig.getInstance().autoready)
                .setDefaultValue(MedievalUtilsConfig.defaultautoready)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoready"))
                .setSaveConsumer(autoready -> MedievalUtilsConfig.getInstance().autoready = autoready)
                .build());
        dungeoncategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("text.medievalutils.config.option.autoclose"),
                        MedievalUtilsConfig.getInstance().aUtoClose)
                .setDefaultValue(MedievalUtilsConfig.defaultAUtoClose)
                .setTooltip(Text.translatable("text.medievalutils.config.tooltip.option.autoclose"))
                .setSaveConsumer(autoclose -> MedievalUtilsConfig.getInstance().aUtoClose = autoclose)
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