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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import jdk.internal.jline.internal.Nullable;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class TaskUtilities {

    @Nullable
    private Plugin plugin;

    public BukkitTask sync(@Nullable final Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(TaskUtilities.plugin);
    }

    public BukkitTask syncLater(final long delay, @NotNull final Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(TaskUtilities.plugin, delay);
    }

    public BukkitTask async(@NotNull final Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(TaskUtilities.plugin);
    }

    public BukkitTask asyncLater(final long delay, @NotNull final Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLaterAsynchronously(TaskUtilities.plugin, delay);
    }

    public BukkitTask syncTimer(final long period, @NotNull final Runnable runnable) {
        return TaskUtilities.syncTimerLater(0, period, runnable);
    }

    public BukkitTask syncTimerLater(final long delay, final long period, @NotNull final Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(TaskUtilities.plugin, delay, period);
    }

    public BukkitTask syncTimer(final long period, @NotNull final Supplier<Boolean> runnable) {
        return TaskUtilities.syncTimerLater(0, period, runnable);
    }

    public BukkitTask syncTimerLater(final long delay, final long period, @NotNull final Supplier<Boolean> runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (!runnable.get()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(TaskUtilities.plugin, delay, period);
    }

    public BukkitTask asyncTimer(final long period, @NotNull final Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimerAsynchronously(TaskUtilities.plugin, 0L, period);
    }

    public BukkitTask asyncTimer(final long period, @NotNull final Supplier<Boolean> runnable) {
        return TaskUtilities.asyncTimerLater(0L, period, runnable);
    }

    public BukkitTask asyncTimerLater(final long delay, final long period, @NotNull final Supplier<Boolean> runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (!runnable.get()) {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(TaskUtilities.plugin, delay, period);
    }

    public <T> BukkitTask forAll(@NotNull final List<T> objects, @NotNull final Consumer<T> runArgument) {
        return TaskUtilities.forAll(1, objects, runArgument);
    }

    public <T> BukkitTask forAll(final int perTick, final List<T> objects, @NotNull final Consumer<T> runArgument) {
        return TaskUtilities.forAll(perTick, objects, runArgument, () -> {
        });
    }

    public <T> BukkitTask forAll(final int perTick, @NotNull final List<T> objects,
                                 @NotNull final Consumer<T> runArgument, @NotNull final Runnable onDone) {
        return new BukkitRunnable() {
            private int current = 0;

            @Override
            public void run() {
                for (int i = 0; i < perTick; i++) {
                    if (this.current >= objects.size()) {
                        break;
                    }
                    final T object = objects.get(this.current);
                    try {
                        runArgument.accept(object);
                    } catch (final Exception e) {
                        TaskUtilities.plugin.getLogger().log(
                            Level.SEVERE,
                            "TaskUtilities#forAll() iteration failed for object: " +
                                object + (object != null ? " (" + object.getClass().getName() + ")" : ""),
                            e);
                    }
                    this.current++;
                }
                if (this.current >= objects.size()) {
                    this.cancel();
                    onDone.run();
                }
            }
        }.runTaskTimer(TaskUtilities.plugin, 1L, 1L);
    }

    void init(@NotNull final Plugin p) {
        TaskUtilities.plugin = p;
    }

}
