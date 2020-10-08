package tr.com.infumia.kekoutil.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class OldWGUtil {

  public boolean canBuild(@NotNull final Player player, @NotNull final Location location) {
    try {
      final Class<?> worldGuardPluginClass = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin");
      final Object worldGuardPlugin = worldGuardPluginClass.getMethod("inst").invoke(null);
      return (boolean) worldGuardPluginClass
        .getMethod("canBuild", Player.class, Block.class)
        .invoke(worldGuardPlugin, player, location.getBlock());
    } catch (final Throwable ignored) {
    }
    return true;
  }
}
