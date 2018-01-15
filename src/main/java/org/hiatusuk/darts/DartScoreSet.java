package org.hiatusuk.darts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*******************************************************************************
*******************************************************************************/
public class DartScoreSet
{
    private ArrayList<DartScore> mSet = new ArrayList<>();

    public void Add( DartScore newScore)
    {
        mSet.add(newScore);
    }

    /*******************************************************************************
    *******************************************************************************/
    public boolean matches(DartScoreSet obj)
    {
        if ( mSet.size() == obj.mSet.size())
        {
            List<DartScore>  ours  = (List<DartScore>) mSet.clone();
            List<DartScore> theirs = (List<DartScore>) obj.mSet.clone();

            DartScore   iScore;
            int         n = ours.size() - 1, j, count = 0;
            boolean     foundMatch;

            // Compare two sets of three objects, which are equal if they contain the same 3 objects
            // though IN ANY ORDER. ABC and CAB is ok. What we do is start with the first object in the
            // first set (A), then find its index in the second set (if present), which is 1. Delete
            // the A entry from each set, giving us BC and CB. Continue, finding the B in the second set,
            // and deleteing both entries, giving us one element in each set, which must be equal otherwise
            // we return false.

            // The previous strategy (wrong), when comparing AAB and CAB, would match each element
            // in the first set with ones in the second set. Item 0 (A) is present in second set, so is
            // item 1 (A again!), so is item 2 (B). But then we'd have to match the other way round to
            // find that C wasn't in the first set. Not a good strategy.

            while ( count < n) {
                iScore = ours.get(0);
                foundMatch = false;
                for ( j = 0; j <= ( n - count); ++j) {
                    if ( iScore.equals(theirs.get(j))) {
                        ours.remove(0);
                        theirs.remove(j);
                        foundMatch = true;
                        ++count;
                        break;
                    }
                }

                if (!foundMatch) {
                    return false;
                }
            }

            return ours.get(0).equals(theirs.get(0));
        }

        return false;
    }

//    public int intValue()
//    {
//        int     total = 0;
//        for (DartScore each : mSet) {
//            total += each.intValue();
//        }
//        return total;
//    }

    /*******************************************************************************
    *******************************************************************************/
    @Override
    public String toString()
    {
        return mSet.stream().map(DartScore::toString).collect(Collectors.joining(", "));
    }
}