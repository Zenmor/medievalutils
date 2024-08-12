package me.zenmor.medievalutils.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Nest;
import io.wispforest.owo.config.annotation.SectionHeader;
import io.wispforest.owo.ui.core.Color;
import net.minecraft.util.Identifier;

@Modmenu(modId = "medievalutils")
@Config(name = "medievalutils-config", wrapperName = "MyConfig")
// idk how to change wrapper name without it crashing i just followed example
public class MedievalConfig {
    public static final Identifier COMPONENT_ID = Identifier.of("medievalutils", "my_ui_screen");

    //public int anIntOption = 16;
    //public boolean aBooleanToggle = false;
    //public String firstOption = "1";
    public Color modcolor = Color.ofRgb(0xA4BEF3);
    public boolean debugging = false;

    @Nest
    public qoloptions qol = new qoloptions();

    public enum fishalerttypes {
        simple, medium, annoying;
    }

    public enum kothremindertypes {
        toast, title, chat;
    }

    public static class qoloptions {
        public boolean petusereminder = false;
        public int autowelcomedelay = 5;
        public boolean autowelcome = false;
        public boolean autoskipnight = false;
        public boolean skiphub = false;
        public boolean autoclaimdaily = false;
        public boolean fishalert = false;
        public MedievalConfig.fishalerttypes fishalerttype = fishalerttypes.simple;
        public boolean kothreminder = false;
        public MedievalConfig.kothremindertypes kothremindertypes = MedievalConfig.kothremindertypes.title;
    }

    @Nest
    public dungeonoptions dungeons = new dungeonoptions();

    public static class dungeonoptions {
        public boolean dungeonautoclose = false;
    }

    @Nest
    public stfuoptions stfu = new stfuoptions();

    public static class stfuoptions {
        public boolean stfurewards = false;
        public boolean stfutips = true;
        public boolean stfudaily = false;
        public boolean stfuskipnight = false;
        public boolean stfuworldguard = false;
        public boolean stfuvotes = false;
        public boolean stfufish = false;
        public boolean stfutrapped = false;
        public boolean stfuryan = false;
    }

    @SectionHeader("dungeons")
    public dungeoncompletetypes dungeoncomplete = dungeoncompletetypes.GUI;

    public enum dungeoncompletetypes {
        GUI, TOAST, ACTIONBAR, CHAT;
    }

    public enum dungeontimerpos {
        TOP_CENTER, TOP_LEFT, TOP_RIGHT;
    }

    @Nest
    public dungeontimertypes dungeomtimer = new dungeontimertypes();

    public static class dungeontimertypes {
        public boolean dungeontimer = true;
        public MedievalConfig.dungeontimerpos dungeonpos = dungeontimerpos.TOP_CENTER;
    }
}
