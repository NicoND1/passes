package de.bytemc.passes.common;

import de.bytemc.passes.EventPass;
import de.bytemc.passes.icon.Icon;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;

import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class EventPassImpl extends AbstractPass implements EventPass {

    private final Date start;
    private final Date end;

    public EventPassImpl(int id, Icon icon, Date start, Date end, Set<PassLevel> levels, PassLevelConfiguration levelConfiguration) {
        super(id, icon, levels, levelConfiguration);
        this.start = start;
        this.end = end;
    }

    @Override
    public PassType getType() {
        return PassType.EVENT;
    }

    @Override
    public Date getStart() {
        return start;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    @Override
    public boolean isCollecting() {
        Date current = new Date();
        return start.before(current) && end.after(current);
    }

    @Override
    public void enableCollecting() {
    }

    @Override
    public void disableCollecting() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EventPassImpl.class.getSimpleName() + "[", "]")
            .add("start=" + start)
            .add("end=" + end)
            .add("id=" + id)
            .add("icon=" + icon)
            .add("levels=" + levels)
            .toString();
    }
}
