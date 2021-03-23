package de.bytemc.passes.user;

import com.google.common.base.Objects;
import de.bytemc.passes.Pass;
import de.bytemc.passes.level.PassLevel;

import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Nico_ND1
 */
public class ActivePass {

    private final PassUser user;
    private final Pass pass;
    private final PassProgress progress;
    private final Set<Integer> collectableLevels;

    public ActivePass(PassUser user, Pass pass, PassProgress progress, Set<Integer> collectableLevels) {
        this.user = user;
        this.pass = pass;
        this.progress = progress;
        this.collectableLevels = collectableLevels;
    }

    public PassUser getUser() {
        return user;
    }

    public Pass getPass() {
        return pass;
    }

    public PassProgress getProgress() {
        return progress;
    }

    public int addExp(double exp) {
        int levelIncrement = 0;
        PassLevel level = pass.getLevel(progress.getLevel());

        while (exp + progress.getExp() >= level.getNeededExp()) {
            levelIncrement++;
            exp -= level.getNeededExp() - progress.getExp();
            level = pass.getLevel(progress.getLevel() + levelIncrement);
            collectableLevels.add(level.getLevel());
        }
        progress.update(progress.getLevel() + levelIncrement, exp);
        return levelIncrement;
    }

    public Set<Integer> getCollectableLevels() {
        return collectableLevels;
    }

    public String formatCollectableLevels() {
        return collectableLevels.stream()
            .map(i -> Integer.toString(i))
            .collect(Collectors.joining(","));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivePass that = (ActivePass) o;
        return Objects.equal(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUser());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ActivePass.class.getSimpleName() + "[", "]")
            .add("user=" + user)
            .add("pass=" + pass)
            .add("progress=" + progress)
            .add("collectableLevels=" + collectableLevels)
            .toString();
    }
}
