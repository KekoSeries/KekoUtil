package tr.com.infumia.kekoutil.hooks;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface GroupWrapped extends Wrapped {

    @NotNull
    String getGroup(@NotNull Player player);

}
