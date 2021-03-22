package de.bytemc.passes.common.user;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Nico_ND1
 */
public interface ConnectionFactory {

    Connection getConnection() throws SQLException;

}
