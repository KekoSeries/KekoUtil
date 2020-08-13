package tr.com.infumia.kekoutil.hooks;

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class ASkyBlockWrapper implements IslandWrapped {

    public static boolean kekoUtil = false;

    @NotNull
    private final ASkyBlockAPI skyBlockAPI;

    public ASkyBlockWrapper(@NotNull final ASkyBlockAPI skyBlockAPI) {
        this.skyBlockAPI = skyBlockAPI;
    }

    public static boolean isKekoUtil() {
        return ASkyBlockWrapper.kekoUtil;
    }

    public static void setKekoUtil(final boolean kekoUtil) {
        ASkyBlockWrapper.kekoUtil = kekoUtil;
    }

    @Override
    public long getIslandLevel(@NotNull final UUID uuid) {
        return this.skyBlockAPI.getLongIslandLevel(uuid);
    }

    @Override
    public void removeIslandLevel(@NotNull final UUID uuid, final long level) {
        this.setIslandLevel(uuid, Math.max(0L, this.getIslandLevel(uuid) - level));
    }

    @Override
    public void addIslandLevel(@NotNull final UUID uuid, final long level) {
        this.setIslandLevel(uuid, this.getIslandLevel(uuid) + level);
    }

    @Override
    public void setIslandLevel(@NotNull final UUID uuid, final long level) {
        if (!ASkyBlockWrapper.kekoUtil) {
            ASkyBlockWrapper.kekoUtil = true;
            this.skyBlockAPI.calculateIslandLevel(uuid);
        }
        ASkyBlock.getPlugin().getPlayers().setIslandLevel(uuid, level);
    }

}