package de.bytemc.passes.common;

import com.google.common.collect.Maps;
import de.bytemc.passes.Pass;
import de.bytemc.passes.PassLevel;
import de.bytemc.passes.icon.Icon;

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

    protected AbstractPass(int id, Icon icon, Set<PassLevel> levels) {
        this.id = id;
        this.icon = icon;
        this.levels = Maps.newConcurrentMap();
        for (PassLevel level : levels) {
            this.levels.put(level.getLevel(), level);
        }
    }

    protected AbstractPass(int id, Icon icon, Map<Integer, PassLevel> levels) {
        this.id = id;
        this.icon = icon;
        this.levels = Maps.newConcurrentMap();
        this.levels.putAll(levels);
    }

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
}
