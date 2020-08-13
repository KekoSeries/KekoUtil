package tr.com.infumia.kekoutil.hooks;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface IslandWrapped extends Wrapped {

    long getIslandLevel(@NotNull UUID uuid);

    void removeIslandLevel(@NotNull UUID uuid, long level);

    void addIslandLevel(@NotNull UUID uuid, long level);

    void setIslandLevel(@NotNull UUID uuid, long level);

}
