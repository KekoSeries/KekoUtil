package tr.com.infumia.kekoutil.hooks;

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.Wrapped;

public final class ASkyBlockWrapper implements Wrapped {

  private static final Map<Plugin, Boolean> CACHE = new HashMap<>();

  @NotNull
  private final ASkyBlockAPI skyBlockAPI;

  public ASkyBlockWrapper(@NotNull final ASkyBlockAPI skyBlockAPI) {
    this.skyBlockAPI = skyBlockAPI;
  }

  public static boolean isKekoUtil(@NotNull final Plugin plugin) {
    return ASkyBlockWrapper.CACHE.getOrDefault(plugin, false);
  }

  public static void setKekoUtil(@NotNull final Plugin plugin, final boolean kekoUtil) {
    ASkyBlockWrapper.CACHE.put(plugin, kekoUtil);
  }

  public long getIslandLevel(@NotNull final UUID uuid) {
    return this.skyBlockAPI.getLongIslandLevel(uuid);
  }

  public void removeIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, Math.max(0L, this.getIslandLevel(uuid) - level));
  }

  public void addIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, this.getIslandLevel(uuid) + level);
  }

  public void setIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    if (!ASkyBlockWrapper.isKekoUtil(plugin)) {
      ASkyBlockWrapper.setKekoUtil(plugin, true);
      this.skyBlockAPI.calculateIslandLevel(uuid);
    }
    ASkyBlock.getPlugin().getPlayers().setIslandLevel(uuid, level);
  }
}