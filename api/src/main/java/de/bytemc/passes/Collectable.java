package de.bytemc.passes;

/**
 * @author Nico_ND1
 */
public interface Collectable {

    /**
     * Get whether this is currently collecting or not.
     *
     * @return true, if this is collecting
     */
    boolean isCollecting();

    /**
     * Enable collecting
     */
    void enableCollecting();

    /**
     * Disable collecting
     */
    void disableCollecting();

}
