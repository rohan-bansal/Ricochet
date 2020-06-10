package me.rohanbansal.ricochet.tools;

public class ObjCombination<X, Y> implements Comparable<ObjCombination> {

    public final X obj1;
    public final Y obj2;
    public final String identifier;

    public ObjCombination(X x, Y y, String identifier) {
        this.obj1 = x;
        this.obj2 = y;
        this.identifier = identifier;
    }

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