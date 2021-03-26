package de.bytemc.passes.common.user;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import de.bytemc.passes.Pass;
import de.bytemc.passes.PassRepository;
import de.bytemc.passes.user.ActivePass;
import de.bytemc.passes.user.PassProgress;
import de.bytemc.passes.user.PassUser;
import de.bytemc.passes.user.PassUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Nico_ND1
 */
public class DatabasePassUserRepository implements PassUserRepository {

    private static final String SELECT_USER = "SELECT * FROM passes_users WHERE uuid=?;";
    private static final String INSERT_USER_PASS = "INSERT INTO passes_users(uuid, pass_id, level, exp, collectable_levels) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_USER_PASS = "UPDATE passes_users SET level=?, exp=?, collectable_levels=? WHERE uuid=? AND pass_id=?;";
    private static final String UPDATE_USER_PASS_PROGRESS = "UPDATE passes_users SET level=?, exp=? WHERE uuid=? AND pass_id=?;";
    private static final String UPDATE_USER_PASS_COLLECTABLE_LEVELS = "UPDATE passes_users SET collectable_levels=? WHERE uuid=? AND pass_id=?;";

    private final Map<UUID, ListenableFuture<PassUser>> userCache = Maps.newConcurrentMap();
    private final ConnectionFactory connectionFactory;
    private final PassRepository passRepository;
    private final Executor executor;

    public DatabasePassUserRepository(ConnectionFactory connectionFactory, PassRepository passRepository) {
        this.connectionFactory = connectionFactory;
        this.passRepository = passRepository;
        this.executor = Executors.newFixedThreadPool(3);
    }

    @Override
    public ListenableFuture<PassUser> loadUser(UUID uuid) {
        if (userCache.containsKey(uuid)) {
            return getUser(uuid);
        }

        SettableFuture<PassUser> future = SettableFuture.create();
        userCache.put(uuid, future);
        executor.execute(() -> {
            try (Connection connection = connectionFactory.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(SELECT_USER);
                statement.setString(1, uuid.toString());

                ResultSet resultSet = statement.executeQuery();
                Set<ActivePass> passes = Sets.newHashSet();
                PassUser user = new PassUserImpl(this, uuid, passes);
                while (resultSet.next()) {
                    int passID = resultSet.getInt("pass_id");
                    PassProgress progress = new PassProgress(resultSet.getInt("level"), resultSet.getDouble("exp"));
                    String levels = resultSet.getString("collectable_levels");

                    Pass pass = passRepository.getPass(passID);
                    ActivePass activePass = new ActivePass(user, pass, progress, readCollectableLevels(levels));
                    passes.add(activePass);
                }
                statement.close();

                checkUnassignedPasses(user);
                future.set(user);
            } catch (SQLException exception) {
                future.setException(exception);
            }
        });
        return future;
    }

    private Set<Integer> readCollectableLevels(String levels) {
        Set<Integer> collectableLevels = Arrays.stream(levels.split(",")).map(s -> {
            if (s.equals("")) {
                return -1;
            }
            return Integer.parseInt(s);
        }).collect(Collectors.toSet());
        collectableLevels.remove(-1);
        return collectableLevels;
    }

    private void checkUnassignedPasses(PassUser passUser) {
        outer:
        for (Pass pass : passRepository.getPasses()) {
            for (ActivePass activePass : passUser.activePasses()) {
                if (activePass.getPass().equals(pass)) {
                    continue outer;
                }
            }

            Futures.addCallback(pass.getPrice().canAfford(passUser), new FutureCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    if (aBoolean != null && aBoolean) {
                        passUser.addPass(pass);
                        pass.getPrice().use(passUser);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }

    @Override
    public ListenableFuture<PassUser> getUser(UUID uuid) {
        SettableFuture<PassUser> instantFuture = SettableFuture.create();
        instantFuture.set(null);
        return userCache.getOrDefault(uuid, instantFuture);
    }

    @Override
    public void invalidateUser(UUID uuid) {
        userCache.remove(uuid);
    }

    @Override
    public void disableCollecting(UUID... uuids) {
        for (UUID uuid : uuids) {
            ListenableFuture<PassUser> future = userCache.get(uuid);
            if (future != null) {
                Futures.addCallback(future, new FutureCallback<PassUser>() {
                    @Override
                    public void onSuccess(PassUser passUser) {
                        passUser.disableCollecting();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
            }
        }
    }

    void insertPass(ActivePass activePass) {
        Pass pass = activePass.getPass();
        PassProgress progress = activePass.getProgress();
        PassUser user = activePass.getUser();

        update(INSERT_USER_PASS, statement -> {
            statement.setString(1, user.getUUID().toString());
            statement.setInt(2, pass.getID());
            statement.setInt(3, progress.getLevel());
            statement.setDouble(4, progress.getExp());
            statement.setString(5, activePass.formatCollectableLevels());
        });
    }

    void update(ActivePass activePass) {
        Pass pass = activePass.getPass();
        PassProgress progress = activePass.getProgress();
        PassUser user = activePass.getUser();

        update(UPDATE_USER_PASS, statement -> {
            statement.setInt(1, progress.getLevel());
            statement.setDouble(2, progress.getExp());
            statement.setString(3, activePass.formatCollectableLevels());
            statement.setString(4, user.getUUID().toString());
            statement.setInt(5, pass.getID());
        });
    }

    void updateProgress(ActivePass activePass) {
        Pass pass = activePass.getPass();
        PassProgress progress = activePass.getProgress();
        PassUser user = activePass.getUser();

        update(UPDATE_USER_PASS_PROGRESS, statement -> {
            statement.setInt(1, progress.getLevel());
            statement.setDouble(2, progress.getExp());
            statement.setString(4, user.getUUID().toString());
            statement.setInt(5, pass.getID());
        });
    }

    void updateCollectableLevels(ActivePass activePass) {
        Pass pass = activePass.getPass();
        PassUser user = activePass.getUser();

        update(UPDATE_USER_PASS_COLLECTABLE_LEVELS, statement -> {
            statement.setString(1, activePass.formatCollectableLevels());
            statement.setString(2, user.getUUID().toString());
            statement.setInt(3, pass.getID());
        });
    }

    private void update(String sql, StatementConsumer statementConsumer) {
        executor.execute(() -> {
            try (Connection connection = connectionFactory.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statementConsumer.accept(statement);
                statement.execute();
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    private interface StatementConsumer {

        void accept(PreparedStatement statement) throws SQLException;

    }

}
