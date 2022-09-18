package net.paradoxsubject.nostromoutils.tools;

public class EMath {
    /**
     * Method for clamping in Math
     */
    public static double clamp(double val, double min, double max){
        return Math.max(min, Math.min(max, val));
    }
}
