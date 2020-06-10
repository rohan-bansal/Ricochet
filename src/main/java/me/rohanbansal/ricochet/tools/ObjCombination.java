package me.rohanbansal.ricochet.tools;

public class ObjCombination<X, Y> implements Comparable<ObjCombination> {

    public final X obj1;
    public final Y obj2;

    public ObjCombination(X x, Y y) {
        this.obj1 = x;
        this.obj2 = y;
    }

    @Override
    public int compareTo(ObjCombination t2) {
        if(this.obj2 instanceof Integer && t2.obj2 instanceof Integer) {
            int x = (int) obj2;
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