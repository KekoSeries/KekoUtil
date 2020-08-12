/*
 * MIT License
 *
 * Copyright (c) 2020 Infumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package tr.com.infumia.kekoutil;

import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ListenerUtilities {

    public <T extends Event> void register(@NotNull final Class<T> tClass, @NotNull final Predicate<T> predicate,
                                           @NotNull final Consumer<T> consumer, @NotNull final Plugin plugin) {
        ListenerUtilities.register(tClass, predicate, consumer, EventPriority.NORMAL, plugin);
    }

    public <T extends Event> void register(@NotNull final Class<T> tClass, @NotNull final EventPriority eventPriority,
                                           @NotNull final Consumer<T> consumer, @NotNull final Plugin plugin) {
        ListenerUtilities.register(tClass, t -> true, consumer, eventPriority, plugin);
    }

    public <T extends Event> void register(@NotNull final Class<T> tClass, @NotNull final Consumer<T> consumer,
                                           @NotNull final Plugin plugin) {
        ListenerUtilities.register(tClass, t -> true, consumer, EventPriority.NORMAL, plugin);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void register(@NotNull final Class<T> tClass, @NotNull final Predicate<T> predicate,
                                           @NotNull final Consumer<T> consumer, @NotNull final EventPriority priority,
                                           @NotNull final Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvent(
            tClass,
            new Listener() {
            },
            priority,
            (listener, event) -> {
                if (event.getClass().equals(tClass) && predicate.test((T) event)) {
                    consumer.accept((T) event);
                }
            },
            plugin);
    }

}
