package tr.com.infumia.kekoutil.hooks;

import java.util.Optional;
import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.GroupWrapped;

public final class GroupManagerWrapper implements GroupWrapped {

    @NotNull
    private final GroupManager groupManager;

    public GroupManagerWrapper(@NotNull final GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @NotNull
    @Override
    public Optional<String> getGroup(@NotNull final Player player) {
        return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
            .map(handler -> handler.getGroup(player.getName()));
    }

    @NotNull
    @Override
    public Optional<String> getUserPrefix(@NotNull final Player player) {
        return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
            .map(handler -> handler.getUserPrefix(player.getUniqueId().toString()));
    }

    @NotNull
    @Override
    public Optional<String> getUserSuffix(@NotNull final Player player) {
        return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
            .map(handler -> handler.getUserSuffix(player.getUniqueId().toString()));
    }

    @NotNull
    @Override
    public Optional<String> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
        return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(world))
            .map(handler -> handler.getGroupPrefix(group));
    }

    @NotNull
    @Override
    public Optional<String> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
        return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(world))
            .map(handler -> handler.getGroupSuffix(group));
    }

}