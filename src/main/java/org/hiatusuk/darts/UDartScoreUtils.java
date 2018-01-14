//  "UDartScoreUtils.java". Created by AGR on 12th July 2000

package org.hiatusuk.darts;

import java.util.Vector;

/*******************************************************************************
 *******************************************************************************/
public class UDartScoreUtils
{
    /*******************************************************************************
     *******************************************************************************/
    public static void RemoveAllDuplicates( Vector<DartScoreSet> inVector)
    {
        DartScoreSet    iScore, jScore;
        int             lastIndex = inVector.size() - 1;
        boolean         endIt = false;

        for ( int i = 0; i < lastIndex; i++)
        {
            try
            {
                iScore = inVector.elementAt(i);

                for ( int j = i + 1; j <= lastIndex; j++)
                {
                    try
                    {
                        // System.out.println( i + "," + j);
                        jScore = inVector.elementAt(j);
                        if ( jScore.matches(iScore))
                        {
                            inVector.removeElement(jScore);
                            --j;
                        }
                    }
                    catch ( ArrayIndexOutOfBoundsException e)
                    {
                    }
                }
            }
            catch ( ArrayIndexOutOfBoundsException e)
            {
            }
        }
    }
}