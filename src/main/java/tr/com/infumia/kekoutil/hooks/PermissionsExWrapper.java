package tr.com.infumia.kekoutil.hooks;

import java.util.Optional;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import tr.com.infumia.kekoutil.GroupWrapped;

public final class PermissionsExWrapper implements GroupWrapped {

    @NotNull
    private final PermissionsEx permissionsEx;

    public PermissionsExWrapper(@NotNull final PermissionsEx permissionsEx) {
        this.permissionsEx = permissionsEx;
    }

    @Override
    @NotNull
    public Optional<String> getGroup(@NotNull final Player player) {
        return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getUser(player).getIdentifier());
    }

    @NotNull
    @Override
    public Optional<String> getUserPrefix(@NotNull final Player player) {
        return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getUser(player).getPrefix());
    }

    @NotNull
    @Override
    public Optional<String> getUserSuffix(@NotNull final Player player) {
        return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getUser(player).getSuffix());
    }

    @NotNull
    @Override
    public Optional<String> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
        return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getGroup(group).getPrefix());
    }

    @NotNull
    @Override
    public Optional<String> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
        return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getGroup(group).getSuffix());
    }

}