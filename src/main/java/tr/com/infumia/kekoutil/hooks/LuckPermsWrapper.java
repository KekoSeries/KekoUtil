package tr.com.infumia.kekoutil.hooks;

import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.GroupWrapped;

@RequiredArgsConstructor
public final class LuckPermsWrapper implements GroupWrapped {

    @NotNull
    private final LuckPerms luckPerms;

    @NotNull
    @Override
    public Optional<String> getGroup(@NotNull final Player player) {
        return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
            .map(User::getPrimaryGroup);
    }

    @NotNull
    @Override
    public Optional<String> getUserPrefix(@NotNull final Player player) {
        return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
            .map(gr -> new ArrayList<>(gr.getNodes(NodeType.PREFIX)))
            .filter(nodes -> !nodes.isEmpty())
            .map(nodes -> nodes.get(0).getMetaValue());
    }

    @NotNull
    @Override
    public Optional<String> getUserSuffix(@NotNull final Player player) {
        return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
            .map(gr -> new ArrayList<>(gr.getNodes(NodeType.SUFFIX)))
            .filter(nodes -> !nodes.isEmpty())
            .map(nodes -> nodes.get(0).getMetaValue());
    }

    @NotNull
    @Override
    public Optional<String> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
        return Optional.ofNullable(this.luckPerms.getGroupManager().getGroup(group))
            .map(gr -> new ArrayList<>(gr.getNodes(NodeType.PREFIX)))
            .filter(nodes -> !nodes.isEmpty())
            .map(nodes -> nodes.get(0).getMetaValue());
    }

    @NotNull
    @Override
    public Optional<String> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
        return Optional.ofNullable(this.luckPerms.getGroupManager().getGroup(group))
            .map(gr -> new ArrayList<>(gr.getNodes(NodeType.SUFFIX)))
            .filter(nodes -> !nodes.isEmpty())
            .map(nodes -> nodes.get(0).getMetaValue());
    }

}