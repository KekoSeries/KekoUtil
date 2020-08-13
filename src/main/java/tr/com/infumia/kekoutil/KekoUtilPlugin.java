package tr.com.infumia.kekoutil;

import io.github.portlek.smartinventory.manager.BasicSmartInventory;

public final class KekoUtilPlugin extends KekoUtil {

    @Override
    public void onLoad() {
        KekoUtil.setInstance(this);
        KekoUtil.setInventory(new BasicSmartInventory(this));
        TaskUtilities.init(this);
        CfgSection.addProvidedClass(FileElement.class, new FileElement.Provider());
    }

    @Override
    public void onEnable() {
        Hooks.loadHooks();
        KekoUtil.getInventory().init();
    }

}
