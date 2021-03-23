package de.bytemc.passes;

/**
 * @author Nico_ND1
 */
public interface Collectable {

    boolean isCollecting();

    void enableCollecting();

    void disableCollecting();

}
