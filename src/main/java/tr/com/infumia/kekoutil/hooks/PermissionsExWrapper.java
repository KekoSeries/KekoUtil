package tr.com.infumia.kekoutil.hooks;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public final class PermissionsExWrapper implements GroupWrapped {

    @NotNull
    private final PermissionsEx permissionsEx;

    public PermissionsExWrapper(@NotNull final PermissionsEx permissionsEx) {
        this.permissionsEx = permissionsEx;
    }

    @Override
    @NotNull
    public String getGroup(@NotNull final Player player) {
        return this.permissionsEx.getPermissionsManager().getUser(player).getIdentifier();
    }

}