package de.bytemc.passes.level;

import de.bytemc.passes.icon.Icon;

import java.util.Map;

/**
 * @author Nico_ND1
 */
public class PassLevelConfiguration {

    private final Map<PassLevelState, Icon> icons;

    public PassLevelConfiguration(Map<PassLevelState, Icon> icons) {
        for (PassLevelState value : PassLevelState.values()) {
            if (!icons.containsKey(value)) {
                throw new RuntimeException("Pass level configurations needs all states, but misses " + value);
            }
        }

        this.icons = icons;
    }

    public Icon getIcon(PassLevelState state) {
        return icons.get(state);
    }

}
