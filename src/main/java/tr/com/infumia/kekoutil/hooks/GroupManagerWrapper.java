package tr.com.infumia.kekoutil.hooks;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
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
    public String getGroup(@NotNull final Player player) {
        final AnjoPermissionsHandler handler = this.groupManager.getWorldsHolder().getWorldPermissions(player);

        if (handler == null) {
            return "";
        }

        return handler.getGroup(player.getName());
    }

}