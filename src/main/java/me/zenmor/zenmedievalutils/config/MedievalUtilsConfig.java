package me.zenmor.zenmedievalutils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;

public final class MedievalUtilsConfig {
    private static final String FILE_NAME = "zenmedievalutils.json";
    private static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private static MedievalUtilsConfig instance;

    
    static final boolean defaultAUtoClose = false;
    static final boolean defaultWelcome = false;
    static final boolean defaultSTFUrewards = false;
    static final boolean defaultSTFUtips = false;
    static final boolean defaultautoready = false;
    static final boolean defaultautoleave = false;
    static final boolean defaultSTFUvotes = false;
    static final boolean defaultdungeontimer = true;
    static final boolean defaultSTFUryan = false;
    static final boolean defaultdungeondream = false;

    boolean aUtoClose = defaultAUtoClose;
    boolean welcome = defaultWelcome;
    boolean stfurewards = defaultSTFUrewards;
    boolean stfutips = defaultSTFUtips;
    boolean autoready = defaultautoready;
    boolean autoleave = defaultautoleave;
    boolean stfuvotes = defaultSTFUvotes;
    boolean dungeontimer = defaultdungeontimer;
    boolean stfuryan = defaultSTFUryan;
    boolean dungeondream = defaultdungeondream;

    private MedievalUtilsConfig() {
    }

    public static MedievalUtilsConfig getInstance() {
        return instance;
    }

    public static void load() {
        try {
            instance = GSON.fromJson(
                    Files.readString(FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME)),
                    MedievalUtilsConfig.class);
        } catch (IOException | JsonSyntaxException ex) {
            LogUtils.getLogger().warn("could not load the config", ex);
            instance = new MedievalUtilsConfig();
            instance.save();
        }
    }

    public void save() {
        try {
            Files.writeString(FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME), GSON.toJson(this));
        } catch (IOException ex) {
            LogUtils.getLogger().error("could not save the config", ex);
        }
    }

    public boolean isaUtoClose() {
        return aUtoClose;
    }

    public boolean iswelcomeenabled() {
        return welcome;
    }

    public boolean isstfurewardsenabled() {
        return stfurewards;
    }

    public boolean isstfutipsenabled() {
        return stfutips;
    }

    public boolean isautoreadyenabled() {
        return autoready;
    }

    public boolean isautoleaveenabled() {
        return autoleave;
    }

    public boolean isstfuvotesenabled() {
        return stfuvotes;
    }

    public boolean isdungeontimersenabled() {
        return dungeontimer;
    }

    public boolean isstfuryanenabled() {
        return stfuryan;
    }

    public boolean isdungeondreamenabled() {
        return dungeondream;
    }
}