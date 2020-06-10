package me.rohanbansal.ricochet.tools;

import java.util.Comparator;

public class UpdateComparator implements Comparator<ObjCombination> {

    @Override
    public int compare(ObjCombination t1, ObjCombination t2) {
        if(t1.obj2 instanceof Integer && t2.obj2 instanceof Integer) {
            int x = (int) t1.obj2;
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
