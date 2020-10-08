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

import io.github.portlek.mapentry.MapEntry;
import io.github.portlek.reflection.RefFieldExecuted;
import io.github.portlek.reflection.clazz.ClassOf;
import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.util.Pattern;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.TriConsumer;

@RequiredArgsConstructor
public enum PlaceType {

  SLOTS((icon, contents, objects) ->
    objects.values().stream()
      .map(o -> (List<Integer>) o)
      .forEach(slots ->
        slots.forEach(slot ->
          contents.set(slot, icon))),
    "slots", int[].class),
  INSERT_INDEX((icon, contents, objects) ->
    contents.set((int) objects.get("index"), icon),
    "index", int.class),
  INSERT((icon, contents, objects) ->
    contents.set((int) objects.get("row"), (int) objects.get("column"), icon),
    "row", int.class, "column", int.class),
  FILL((icon, contents, objects) ->
    contents.fill(icon)),
  FILL_EMPTIES((icon, contents, objects) ->
    contents.fillEmpties(icon)),
  FILL_ROW((icon, contents, objects) ->
    contents.fillRow((int) objects.get("row"), icon),
    "row", int.class),
  FILL_COLUMN((icon, contents, objects) ->
    contents.fillColumn((int) objects.get("column"), icon),
    "column", int.class),
  FILL_BORDERS((icon, contents, objects) ->
    contents.fillBorders(icon)),
  FILL_RECT_INDEX((icon, contents, objects) ->
    contents.fillRect((int) objects.get("from-index"), (int) objects.get("to-index"), icon),
    "from-index", int.class, "to-index", int.class),
  FILL_RECT_FROM_TO((icon, contents, objects) ->
    contents.fillRect((int) objects.get("from-row"), (int) objects.get("from-column"), (int) objects.get("to-row"), (int) objects.get("to-column"), icon),
    "from-row", int.class, "from-column", int.class, "to-row", int.class, "to-column", int.class),
  FILL_SQUARE_INDEX((icon, contents, objects) ->
    contents.fillSquare((int) objects.get("from-index"), (int) objects.get("to-index"), icon),
    "from-index", int.class, "to-index", int.class),
  FILL_SQUARE_FROM_TO((icon, contents, objects) ->
    contents.fillSquare((int) objects.get("from-row"), (int) objects.get("from-column"), (int) objects.get("to-row"), (int) objects.get("to-column"), icon),
    "from-row", int.class, "from-column", int.class, "to-row", int.class, "to-column", int.class),
  FILL_PATTERN((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern"))),
    "wrap-around", boolean.class, "pattern", String[].class),
  FILL_PATTERN_START_INDEX((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-index")),
    "wrap-around", boolean.class, "pattern", String[].class, "start-index", int.class),
  FILL_PATTERN_START((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-row"), (int) objects.get("start-column")),
    "wrap-around", boolean.class, "pattern", String[].class, "start-row", int.class, "start-column", int.class),
  FILL_REPEATING_PATTERN((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern"))),
    "wrap-around", boolean.class, "pattern", String[].class),
  FILL_REPEATING_PATTERN_START_INDEX((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-index"), (int) objects.get("end-index")),
    "wrap-around", boolean.class, "pattern", String[].class, "start-index", int.class, "end-index", int.class),
  FILL_REPEATING_PATTERN_START((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-row"), (int) objects.get("start-column"), (int) objects.get("end-row"), (int) objects.get("end-column")),
    "wrap-around", boolean.class, "pattern", String[].class, "start-row", int.class, "start-column", int.class, "end-row", int.class, "end-column", int.class),
  NONE((left, middle, right) -> {
  });

  @NotNull
  private final TriConsumer<Icon, InventoryContents, Map<String, Object>> consumer;

  @NotNull
  private final Map<String, Class<?>> keyAndTypes;

  PlaceType(@NotNull final TriConsumer<Icon, InventoryContents, Map<String, Object>> consumer,
            @NotNull final Object... objects) {
    this(consumer, PlaceType.parse(objects));
  }

  @NotNull
  public static PlaceType fromString(@NotNull final String type) {
    return Arrays.stream(PlaceType.values())
      .filter(placeType -> placeType.name().trim().toLowerCase(Locale.ENGLISH).equalsIgnoreCase(type.trim().toLowerCase(Locale.ENGLISH)))
      .findFirst()
      .orElse(PlaceType.NONE);
  }

  @NotNull
  public static <T> Map<String, T> parse(@NotNull final Object... objects) {
    final Map<String, T> map = new HashMap<>();
    boolean isKey = true;
    String previousKey = "";
    for (final Object object : objects) {
      if (isKey) {
        isKey = false;
        previousKey = (String) object;
      } else {
        isKey = true;
        map.put(previousKey, (T) object);
      }
    }
    return map;
  }

  @NotNull
  private static Object def(@NotNull final Class<?> type) {
    if (type.equals(Integer.class)) {
      return 1;
    }
    if (type.equals(String.class)) {
      return "test";
    }
    if (type.equals(String[].class)) {
      return new String[]{"element-1", "element-2"};
    }
    if (type.equals(int[].class)) {
      return new int[]{0, 1, 2};
    }
    if (type.equals(boolean.class)) {
      return true;
    }
    return "empty";
  }

  public boolean control(@NotNull final Map<String, Object> objects) {
    if (this == PlaceType.SLOTS) {
      return objects.isEmpty() || objects.values().stream()
        .allMatch(o -> Number.class.isAssignableFrom(o.getClass()));
    }
    if (objects.size() != this.keyAndTypes.size()) {
      return false;
    }
    if (objects.isEmpty()) {
      return true;
    }
    return this.keyAndTypes.entrySet().stream()
      .allMatch(entry -> {
        final Object o = objects.get(entry.getKey());
        if (o == null) {
          return false;
        }
        final Class<?> aClass = o.getClass();
        return new ClassOf<>(aClass)
          .field("TYPE")
          .map(refField -> refField.of(o))
          .map(RefFieldExecuted::get)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(o1 -> o1.equals(entry.getValue()))
          .orElse(aClass.equals(entry.getValue()));
      });
  }

  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents,
                    @NotNull final Map<String, Object> objects) {
    this.consumer.accept(icon, contents, objects);
  }

  @NotNull
  public Map<String, Object> defaultValues() {
    return this.keyAndTypes.entrySet().stream()
      .map(entry -> MapEntry.from(entry.getKey(), PlaceType.def(entry.getValue())))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
