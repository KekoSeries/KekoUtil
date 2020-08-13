package tr.com.infumia.kekoutil.hooks;

import org.jetbrains.annotations.NotNull;

public interface Hook {

    @NotNull
    String id();

    boolean initiate();

    @NotNull
    Wrapped create();

}
