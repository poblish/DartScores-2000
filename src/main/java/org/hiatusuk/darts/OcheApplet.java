package org.hiatusuk.darts;

import java.util.Vector;
import java.applet.Applet;

public class OcheApplet extends Applet
{
	/*******************************************************************************
	*******************************************************************************/
	public void init()
	{
		String	theTotParam = getParameter("total");
		String	theLeftParam = getParameter("darts_left");
		String	theMaxParam = getParameter("max_results");
		String	theExactParam = getParameter("exactly_that_number");

		PrintInfoForTotal( ( theTotParam == null) ? 170 : Integer.valueOf(theTotParam).intValue(),
						   ( theLeftParam == null) ? 3 : Integer.valueOf(theLeftParam).intValue(),
						   ( theMaxParam == null) ? 100 : Integer.valueOf(theMaxParam).intValue(),
						   ( theExactParam == null) ? false : Boolean.valueOf(theExactParam).booleanValue());
	}

	/*******************************************************************************
	*******************************************************************************/
	public void PrintInfoForTotal( int total, int dartsLeft, int inMaxResults, boolean inExactlyThatNumDarts)
	{
		System.out.print( total + "...... ");

		long				theStartTime = System.currentTimeMillis();
		DartsOutEnquiry		theEnquiry = new DartsOutEnquiry( total, dartsLeft, inExactlyThatNumDarts);
		long				theEndTime = System.currentTimeMillis();
		Vector				theResults = theEnquiry.GetScores();

		UDartScoreUtils.RemoveAllDuplicates(theResults);

		if ( theResults.size() > 0)
		{
			int	theNumMatchesToShow = ( theResults.size() > inMaxResults) ? inMaxResults : theResults.size();

			System.out.println( theNumMatchesToShow + " matches found.");
			System.out.println("---------------------------");

			for ( int i = 0; i < theNumMatchesToShow; i++)
			{
				System.out.println(theResults.elementAt(i));
			}
		}
		else
		{
			System.out.println("is impossible! ");
		}

		System.out.println( "��� Time was: " + ( theEndTime - theStartTime) + " msecs.");
		System.out.println();
	}
}