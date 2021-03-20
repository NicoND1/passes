package de.bytemc.passes.user;

import com.google.common.base.Objects;
import de.bytemc.passes.Pass;

import java.util.Set;
import java.util.StringJoiner;

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

    public Set<Integer> getCollectableLevels() {
        return collectableLevels;
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
