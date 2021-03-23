package de.bytemc.passes.common;

import com.google.common.collect.Maps;
import de.bytemc.passes.Pass;
import de.bytemc.passes.icon.Icon;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public abstract class AbstractPass implements Pass {

    protected final int id;
    protected final Icon icon;
    protected final Map<Integer, PassLevel> levels;
    protected final PassLevelConfiguration levelConfiguration;

    protected AbstractPass(int id, Icon icon, Set<PassLevel> levels, PassLevelConfiguration levelConfiguration) {
        this.id = id;
        this.icon = icon;
        this.levels = Maps.newConcurrentMap();
        for (PassLevel level : levels) {
            this.levels.put(level.getLevel(), level);
        }
        this.levelConfiguration = levelConfiguration;
    }

    protected AbstractPass(int id, Icon icon, Map<Integer, PassLevel> levels, PassLevelConfiguration levelConfiguration) {
        this.id = id;
        this.icon = icon;
        this.levels = Maps.newConcurrentMap();
        this.levels.putAll(levels);
        this.levelConfiguration = levelConfiguration;
    }

    public abstract PassType getType();

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public Collection<PassLevel> getLevels() {
        return levels.values();
    }

    @Override
    public PassLevel getLevel(int level) {
        return levels.get(level);
    }

    @Override
    public PassLevelConfiguration levelConfiguration() {
        return levelConfiguration;
    }
}
