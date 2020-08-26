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

package tr.com.infumia.kekoutil.element;

import io.github.portlek.smartinventory.event.abs.ClickEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.FileElement;
import tr.com.infumia.kekoutil.util.PlaceType;

@AllArgsConstructor
public final class FileElementBasic implements FileElement {

    @NotNull
    private ItemStack itemStack;

    @NotNull
    private PlaceType placeType;

    @NotNull
    private Map<String, Object> objects;

    @NotNull
    private List<Consumer<ClickEvent>> events;

    @Override
    @NotNull
    public ItemStack itemStack() {
        return this.itemStack;
    }

    @NotNull
    @Override
    public List<Consumer<ClickEvent>> events() {
        return Collections.unmodifiableList(this.events);
    }

    @Override
    @NotNull
    public PlaceType type() {
        return this.placeType;
    }

    @Override
    @NotNull
    public Map<String, Object> objects() {
        return Collections.unmodifiableMap(this.objects);
    }

    @Override
    @NotNull
    public FileElement addObject(@NotNull final String key, @NotNull final Object object) {
        this.objects.put(key, object);
        return this;
    }

    @Override
    @NotNull
    public FileElement removeObject(@NotNull final String key) {
        this.objects.remove(key);
        return this;
    }

    @Override
    @NotNull
    public FileElement changeItemStack(@NotNull final ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @Override
    @NotNull
    public FileElement changeType(@NotNull final PlaceType type) {
        this.placeType = type;
        return this;
    }

    @Override
    @NotNull
    public FileElement changeObjects(@NotNull final Map<String, Object> objects) {
        this.objects = objects;
        return this;
    }

    @Override
    public FileElement changeEvent(@NotNull final List<Consumer<ClickEvent>> events) {
        this.events = events;
        return this;
    }

}
