package tr.com.infumia.kekoutil.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class NewWGUtil {

    public boolean canBuild(@NotNull final Player player, @NotNull final Location location) {
        return NewWGUtil.hasBypass(player, location) ||
            WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .createQuery()
                .testState(
                    BukkitAdapter.adapt(location),
                    NewWGUtil.getLocalPlayer(player),
                    Flags.BUILD);
    }

    public boolean hasBypass(@NotNull final Player player, @NotNull final Location location) {
        return Optional.ofNullable(location.getWorld())
            .map(world ->
                WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(
                    NewWGUtil.getLocalPlayer(player),
                    NewWGUtil.getLocalWorld(world)))
            .orElse(true);
    }

    @NotNull
    private LocalPlayer getLocalPlayer(@NotNull final Player player) {
        return WorldGuardPlugin.inst().wrapPlayer(player);
    }

    @NotNull
    private World getLocalWorld(@NotNull final org.bukkit.World world) {
        return new BukkitWorld(world);
    }

}
