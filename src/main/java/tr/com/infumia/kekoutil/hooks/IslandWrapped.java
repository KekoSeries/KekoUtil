package tr.com.infumia.kekoutil.hooks;

import java.util.UUID;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface IslandWrapped extends Wrapped {

    long getIslandLevel(@NotNull UUID uuid);

    void removeIslandLevel(@NotNull Plugin plugin, @NotNull UUID uuid, long level);

    void addIslandLevel(@NotNull Plugin plugin, @NotNull UUID uuid, long level);

    void setIslandLevel(@NotNull Plugin plugin, @NotNull UUID uuid, long level);

}
