package tr.com.infumia.kekoutil.hooks;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.GroupWrapped;

@RequiredArgsConstructor
public final class LuckPermsWrapper implements GroupWrapped {

    @NotNull
    private final LuckPerms luckPerms;

    @NotNull
    @Override
    public String getGroup(@NotNull final Player player) {
        return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
            .map(User::getPrimaryGroup)
            .orElse("");
    }

}