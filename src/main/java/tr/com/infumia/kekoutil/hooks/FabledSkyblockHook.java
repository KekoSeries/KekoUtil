package tr.com.infumia.kekoutil.hooks;

import com.songoda.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.Hook;

public final class FabledSkyblockHook implements Hook {

    public static final String FABLEDSKYBLOCK_ID = "FabledSkyblock";

    private SkyBlock skyBlock;

    @NotNull
    @Override
    public String id() {
        return FabledSkyblockHook.FABLEDSKYBLOCK_ID;
    }

    @Override
    public boolean initiate() {
        if (Bukkit.getPluginManager().getPlugin("FabledSkyblock") != null) {
            this.skyBlock = SkyBlock.getInstance();
        }

        return this.skyBlock != null;
    }

    @NotNull
    @Override
    public FabledSkyblockWrapper create() {
        if (this.skyBlock == null) {
            throw new IllegalStateException("FabledSkyblock not initiated! Use FabledSkyblockHook#initiate() method.");
        }
        return new FabledSkyblockWrapper(this.skyBlock);
    }

}