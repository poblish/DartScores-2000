package org.hiatusuk.darts;import java.util.Vector;/**************************************************************************************************************************************************************/public class DartScoreSet{    private Vector      mSet;    /*******************************************************************************    *******************************************************************************/    public DartScoreSet()    {        mSet = new Vector();    }    /*******************************************************************************    *******************************************************************************/    void Clear()    {        if ( mSet != null)        {            mSet.removeAllElements();        }    }    /*******************************************************************************    *******************************************************************************/    public void Add( DartScore newScore)    {        mSet.addElement(newScore);    }    /*******************************************************************************    *******************************************************************************/    public boolean equals( DartScoreSet obj)    {        if ( mSet.size() == obj.mSet.size())        {            Vector      ours   = (Vector) mSet.clone();            Vector      theirs = (Vector) obj.mSet.clone();            DartScore   iScore;            int         n = ours.size() - 1, j, count = 0;            boolean     foundMatch;            // Compare two sets of three objects, which are equal if they contain the same 3 objects            // though IN ANY ORDER. ABC and CAB is ok. What we do is start with the first object in the            // first set (A), then find its index in the second set (if present), which is 1. Delete            // the A entry from each set, giving us BC and CB. Continue, finding the B in the second set,            // and deleteing both entries, giving us one element in each set, which must be equal otherwise            // we return false.            // The previous strategy (wrong), when comparing AAB and CAB, would match each element            // in the first set with ones in the second set. Item 0 (A) is present in second set, so is            // item 1 (A again!), so is item 2 (B). But then we'd have to match the other way round to            // find that C wasn't in the first set. Not a good strategy.            while ( count < n)            {                iScore = (DartScore) ours.elementAt(0);                foundMatch = false;                for ( j = 0; j <= ( n - count); ++j)                {                    if ( iScore.equals((DartScore) theirs.elementAt(j)))                    {                        ours.removeElementAt(0);                        theirs.removeElementAt(j);                        foundMatch = true;                        ++count;                        break;                    }                }                if (!foundMatch)    return false;            }            return (((DartScore) ours.elementAt(0)).equals((DartScore) theirs.elementAt(0)));        }        else    return false;    }    /*******************************************************************************    *******************************************************************************/    public int intValue()    {        int     total = 0;        for ( int i = 0; i <= mSet.size() - 1; ++i)     total += ((DartScore) mSet.elementAt(i)).intValue();        return total;    }    /*******************************************************************************    *******************************************************************************/    public String toString()    {        StringBuffer    theBuffer = new StringBuffer();        int             lastIndex = mSet.size() - 1;        for ( int i = 0; i <= lastIndex; ++i)        {            theBuffer.append( (DartScore) mSet.elementAt(i) + (( i < lastIndex) ? ", " : "."));        }        return theBuffer.toString();    }};