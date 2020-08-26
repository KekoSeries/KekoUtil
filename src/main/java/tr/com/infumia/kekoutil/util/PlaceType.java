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

package tr.com.infumia.kekoutil.util;

import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.util.Pattern;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.TriConsumer;

@RequiredArgsConstructor
public enum PlaceType {

    SLOTS((icon, contents, objects) ->
        Arrays.stream(objects)
            .map(o -> (int) o)
            .forEach(slot ->
                contents.set(slot, icon)),
        int[].class),
    INSERT_INDEX((icon, contents, objects) ->
        contents.set((int) objects[0], icon),
        Collections.singletonList("index"),
        int.class),
    INSERT((icon, contents, objects) ->
        contents.set((int) objects[0], (int) objects[1], icon),
        Arrays.asList("row", "column"),
        int.class, int.class),
    FILL((icon, contents, objects) ->
        contents.fill(icon)),
    FILL_EMPTIES((icon, contents, objects) ->
        contents.fillEmpties(icon)),
    FILL_ROW((icon, contents, objects) ->
        contents.fillRow((int) objects[0], icon),
        Collections.singletonList("row"),
        int.class),
    FILL_COLUMN((icon, contents, objects) ->
        contents.fillColumn((int) objects[0], icon),
        Collections.singletonList("column"),
        int.class),
    FILL_BORDERS((icon, contents, objects) ->
        contents.fillBorders(icon)),
    FILL_RECT_INDEX((icon, contents, objects) ->
        contents.fillRect((int) objects[0], (int) objects[1], icon),
        Arrays.asList("from-index", "to-index"),
        int.class, int.class),
    FILL_RECT_FROM_TO((icon, contents, objects) ->
        contents.fillRect((int) objects[0], (int) objects[1], (int) objects[2], (int) objects[3], icon),
        Arrays.asList("from-row", "from-column", "to-row", "to-column"),
        int.class, int.class, int.class, int.class),
    FILL_SQUARE_INDEX((icon, contents, objects) ->
        contents.fillSquare((int) objects[0], (int) objects[1], icon),
        Arrays.asList("from-index", "to-index"),
        int.class, int.class),
    FILL_SQUARE_FROM_TO((icon, contents, objects) ->
        contents.fillSquare((int) objects[0], (int) objects[1], (int) objects[2], (int) objects[3], icon),
        Arrays.asList("from-row", "from-column", "to-row", "to-column"),
        int.class, int.class, int.class, int.class),
    FILL_PATTERN((icon, contents, objects) ->
        contents.fillPattern(new Pattern<>((boolean) objects[0], (String[]) objects[1])),
        Arrays.asList("wrap-around", "pattern"),
        boolean.class, String[].class),
    FILL_PATTERN_START_INDEX((icon, contents, objects) ->
        contents.fillPattern(new Pattern<>((boolean) objects[0], (String[]) objects[1]), (int) objects[2]),
        Arrays.asList("wrap-around", "pattern", "start-index"),
        boolean.class, String[].class, int.class),
    FILL_PATTERN_START((icon, contents, objects) ->
        contents.fillPattern(new Pattern<>((boolean) objects[0], (String[]) objects[1]), (int) objects[2], (int) objects[3]),
        Arrays.asList("wrap-around", "pattern", "start-row", "start-column"),
        boolean.class, String[].class, int.class, int.class),
    FILL_REPEATING_PATTERN((icon, contents, objects) ->
        contents.fillPatternRepeating(new Pattern<>((boolean) objects[0], (String[]) objects[1])),
        Arrays.asList("wrap-around", "pattern"),
        boolean.class, String[].class),
    FILL_REPEATING_PATTERN_START_INDEX((icon, contents, objects) ->
        contents.fillPatternRepeating(new Pattern<>((boolean) objects[0], (String[]) objects[1]), (int) objects[2], (int) objects[3]),
        Arrays.asList("wrap-around", "pattern", "start-index", "end-index"),
        boolean.class, String[].class, int.class, int.class),
    FILL_REPEATING_PATTERN_START((icon, contents, objects) ->
        contents.fillPatternRepeating(new Pattern<>((boolean) objects[0], (String[]) objects[1]), (int) objects[2], (int) objects[3], (int) objects[4], (int) objects[5]),
        Arrays.asList("wrap-around", "pattern", "start-row", "start-column", "end-row", "end-column"),
        boolean.class, String[].class, int.class, int.class, int.class, int.class),
    NONE();

    @NotNull
    private final TriConsumer<Icon, InventoryContents, Object[]> consumer;

    @NotNull
    private final List<String> keys;

    @NotNull
    private final List<Class<?>> types;

    @SafeVarargs
    PlaceType(@NotNull final TriConsumer<Icon, InventoryContents, Object[]> consumer, @NotNull final List<String> keys,
              @NotNull final Class<?>... types) {
        this(consumer, keys, Arrays.asList(types));
    }

    @SafeVarargs
    PlaceType(@NotNull final TriConsumer<Icon, InventoryContents, Object[]> consumer,
              @NotNull final Class<?>... types) {
        this(consumer, Collections.emptyList(), Arrays.asList(types));
    }

    @SafeVarargs
    PlaceType(@NotNull final Class<?>... types) {
        this((left, middle, right) -> {
        }, types);
    }

    @NotNull
    public static PlaceType fromString(@NotNull final String type) {
        return Arrays.stream(PlaceType.values())
            .filter(placeType -> placeType.name().trim().toLowerCase(Locale.ENGLISH).equalsIgnoreCase(type.trim().toLowerCase(Locale.ENGLISH)))
            .findFirst()
            .orElse(PlaceType.NONE);
    }

    public boolean control(@NotNull final Object... objects) {
        if (this == PlaceType.SLOTS) {
            if (objects.length == 0) {
                return true;
            } else {
                return Arrays.stream(objects)
                    .allMatch(o -> o.getClass().equals(Integer.class));
            }
        }
        if (objects.length != this.types.size()) {
            return false;
        }
        if (objects.length == 0) {
            return true;
        }
        return IntStream.range(0, objects.length - 1)
            .allMatch(value -> objects[value].getClass().equals(this.types.get(value)));
    }

    public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents,
                      @NotNull final Object[] objects) {
        this.consumer.accept(icon, contents, objects);
    }

    @NotNull
    public Map<String, Object> parse(@NotNull final Object... objects) {
        if (this == PlaceType.SLOTS) {
            return ((List<Integer>) objects[0]).stream()
                .collect(Collectors.toMap(slot -> "slot-" + slot, slot -> slot));
        }
        final Map<String, Object> map = new HashMap<>();
        for (int index = 0; index < objects.length; index++) {
            final String key = this.keys.get(index);
            final Object value = objects[index];
            map.put(key, value);
        }
        return map;
    }

    @NotNull
    public List<Object> defaultValues() {
        if (this == PlaceType.SLOTS) {
            return Collections.singletonList("Slot numbers(0, 1, 2, 3, ...");
        }
        if (this.types.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.keys);
    }

}
