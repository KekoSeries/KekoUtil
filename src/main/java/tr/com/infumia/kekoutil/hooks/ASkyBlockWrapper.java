package tr.com.infumia.kekoutil.hooks;

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ASkyBlockWrapper implements IslandWrapped {

    public static Map<Plugin, Boolean> kekoUtil = new HashMap<>();

    @NotNull
    private final ASkyBlockAPI skyBlockAPI;

    public ASkyBlockWrapper(@NotNull final ASkyBlockAPI skyBlockAPI) {
        this.skyBlockAPI = skyBlockAPI;
    }

    public static boolean isKekoUtil(@NotNull final Plugin plugin) {
        return ASkyBlockWrapper.kekoUtil.getOrDefault(plugin, false);
    }

    public static void setKekoUtil(@NotNull final Plugin plugin, final boolean kekoUtil) {
        ASkyBlockWrapper.kekoUtil.put(plugin, kekoUtil);
    }

    @Override
    public long getIslandLevel(@NotNull final UUID uuid) {
        return this.skyBlockAPI.getLongIslandLevel(uuid);
    }

    @Override
    public void removeIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
        this.setIslandLevel(plugin, uuid, Math.max(0L, this.getIslandLevel(uuid) - level));
    }

    @Override
    public void addIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
        this.setIslandLevel(plugin, uuid, this.getIslandLevel(uuid) + level);
    }

    @Override
    public void setIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
        if (!ASkyBlockWrapper.isKekoUtil(plugin)) {
            ASkyBlockWrapper.setKekoUtil(plugin, true);
            this.skyBlockAPI.calculateIslandLevel(uuid);
        }
        ASkyBlock.getPlugin().getPlayers().setIslandLevel(uuid, level);
    }

}