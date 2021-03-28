package de.bytemc.passes.user;

import com.google.common.base.Objects;
import de.bytemc.passes.Pass;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.payment.Payment;

import java.util.Collections;
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
        PassLevel nextLevel = pass.getLevel(progress.getLevel() + 1);

        while (nextLevel != null && exp + progress.getExp() >= nextLevel.getNeededExp()) {
            collectableLevels.add(nextLevel.getLevel());
            levelIncrement++;
            System.out.println("Level up! exp is " + exp + " needed " + nextLevel.getNeededExp() + " : " + progress.getExp());
            exp -= nextLevel.getNeededExp() - progress.getExp();
            System.out.println("Level up!2 exp is " + exp + " needed " + nextLevel.getNeededExp() + " : " + progress.getExp());
            progress.update(progress.getLevel(), 0);
            nextLevel = pass.getLevel(nextLevel.getLevel() + 1);
        }
        System.out.println("Finished! exp is " + exp + " needed " + (nextLevel == null ? -1 : nextLevel.getNeededExp()) + " : " + progress.getExp());
        progress.update(progress.getLevel() + levelIncrement, progress.getExp() + exp);
        return levelIncrement;
    }

    public Set<Integer> getCollectableLevels() {
        return Collections.unmodifiableSet(collectableLevels);
    }

    public boolean collectLevel(int level) {
        boolean result = collectableLevels.remove(level);
        if (result) {
            PassLevel passLevel = pass.getLevel(level);
            for (Payment reward : passLevel.getRewards()) {
                reward.use(user);
            }
        }
        return result;
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
        return Objects.equal(getUser(), that.getUser()) &&
            Objects.equal(getPass(), that.getPass());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUser());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ActivePass.class.getSimpleName() + "[", "]")
            .add("user=" + user.getUUID())
            .add("pass=" + pass)
            .add("progress=" + progress)
            .add("collectableLevels=" + collectableLevels)
            .toString();
    }
}
