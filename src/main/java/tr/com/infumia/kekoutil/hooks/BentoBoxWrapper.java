package tr.com.infumia.kekoutil.hooks;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.AddonClassLoader;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

public final class BentoBoxWrapper implements IslandWrapped {

    private static boolean kekoUtil = false;

    @NotNull
    private final BentoBox bentoBox;

    @NotNull
    private final AddonClassLoader classLoader;

    public BentoBoxWrapper(@NotNull final BentoBox bentoBox, @NotNull final AddonClassLoader classLoader) {
        this.bentoBox = bentoBox;
        this.classLoader = classLoader;
    }

    public static boolean isKekoUtil() {
        return BentoBoxWrapper.kekoUtil;
    }

    public static void setKekoUtil(final boolean kekoUtil) {
        BentoBoxWrapper.kekoUtil = kekoUtil;
    }

    @Override
    public long getIslandLevel(@NotNull final UUID uuid) {
        final AtomicLong level = new AtomicLong(0L);
        this.findFirstIsland(uuid).ifPresent(island -> {
            try {
                level.addAndGet((long) this.classLoader
                    .findClass("world.bentobox.level.Level", false)
                    .getMethod("getIslandLevel", World.class, UUID.class)
                    .invoke(this.classLoader.getAddon(), island.getWorld(), uuid)
                );
            } catch (final IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
                // ignored
            }
        });
        return level.get();
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
        this.findFirstIsland(uuid).ifPresent(island -> {
            try {
                if (!BentoBoxWrapper.kekoUtil) {
                    BentoBoxWrapper.kekoUtil = true;
                    this.classLoader
                        .findClass("world.bentobox.level.Level", false)
                        .getMethod("calculateIslandLevel", World.class, User.class, UUID.class)
                        .invoke(this.classLoader.getAddon(), island.getWorld(), User.getInstance(uuid), uuid);
                }

                this.classLoader
                    .findClass("world.bentobox.level.Level", false)
                    .getMethod("setIslandLevel", World.class, UUID.class, long.class)
                    .invoke(this.classLoader.getAddon(), island.getWorld(), uuid, level);
            } catch (final Exception ignored) {
                // ignored
            }
        });
    }

    @NotNull
    public Optional<Island> findFirstIsland(@NotNull final UUID uuid) {
        for (final Island island : this.bentoBox.getIslands().getIslands()) {
            if (island.getWorld() != null &&
                island.getOwner() != null &&
                island.getOwner().equals(uuid)) {
                return Optional.of(island);
            }
        }

        return Optional.empty();
    }

}