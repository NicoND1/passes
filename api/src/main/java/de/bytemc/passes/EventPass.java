package de.bytemc.passes;

import java.util.Date;

/**
 * @author Nico_ND1
 */
public interface EventPass extends Pass {

    Date getStart();

    Date getEnd();

}
