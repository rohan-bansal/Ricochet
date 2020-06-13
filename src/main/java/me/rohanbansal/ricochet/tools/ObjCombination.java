package me.rohanbansal.ricochet.tools;

public class ObjCombination<X, Y> implements Comparable<ObjCombination> {

    public final X obj1;
    public final Y obj2;
    public final String identifier;

    /**
     * Tuple-like class for use in optimizer priorityqueue
     * @param x value 1
     * @param y value 2
     * @param identifier an identifier to find this object
     */
    public ObjCombination(X x, Y y, String identifier) {
        this.obj1 = x;
        this.obj2 = y;
        this.identifier = identifier;
    }

    /**
     * compare two object combinations if and only if their first values are integers (priorityqueue)
     * @param t2 other objcombination
     * @return return 1 (true), -1 (false), or 0 (none)
     */
    @Override
    public int compareTo(ObjCombination t2) {
        if(this.obj2 instanceof Integer && t2.obj2 instanceof Integer) {
            int x = ((Integer) obj2).intValue();
            int y = (int) t2.obj2;
            if(x > y) {
                return 1;
            } else if(x < y) {
                return -1;
            }
        }
        return 0;
    }
}