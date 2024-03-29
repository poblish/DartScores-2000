package org.hiatusuk.darts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.hiatusuk.darts.DartScore.kCanFinishWithAnyDart;
import static org.hiatusuk.darts.DartScore.kLowestSingleScore;

public class DartsOutEnquiry {
    private DartScoreSet m_TempScoreSet;
    private final List<DartScoreSet> mDartsScoresList = new ArrayList<>(50);
    private int m_NumDartsAvailable;
    private final int mNumDartValues = DartScore.kHighestSingleScore - kLowestSingleScore + 2;
    private final int[] mDartScoreArray = new int[mNumDartValues];

    private int m_CurrentFinishingMultiple;
    private int m_ThePointsRemaining;
    private int m_TotalOnTheFinishingDart;
    private int m_ValueOfTheFinishingDart;
    private int[] m_Scores;
    private int[] m_Multiples;
    private boolean m_IgnoreNextOne;

    /*******************************************************************************
     *******************************************************************************/
    public DartsOutEnquiry(int inOriginalTarget, int how_many_darts_available, boolean inExactlyThatNumDarts) {
        if ((inOriginalTarget <= kLowestSingleScore) ||
            (inOriginalTarget > (DartScore.kHighestOneDartScore * how_many_darts_available))) {
            return;
        }

        //////////////////////////////////////////////////////////////

        m_ThePointsRemaining = 0;
        m_NumDartsAvailable = how_many_darts_available;

        m_Scores = new int[m_NumDartsAvailable - 1];
        m_Multiples = new int[m_NumDartsAvailable - 1];

        //////////////////////////////////////////////////////////////

        for (int theDartScore = kLowestSingleScore; theDartScore <= DartScore.kHighestSingleScore; theDartScore++) {
            mDartScoreArray[theDartScore - kLowestSingleScore] = theDartScore;
        }

        mDartScoreArray[mNumDartValues - 1] = DartScore.kBullValue;

        //////////////////////////////////////////////////////////////  See if it can be done in 1 dart first

        if (!kCanFinishWithAnyDart) {
            for (int theIndexOfTheFinishingDart = mNumDartValues - 1; theIndexOfTheFinishingDart >= 0; theIndexOfTheFinishingDart--) {
                m_ValueOfTheFinishingDart = DartIndexToValue(theIndexOfTheFinishingDart);
                m_TotalOnTheFinishingDart = DartScore.kMultipleNeededForLastDart * m_ValueOfTheFinishingDart;

                //////////////////////////////////////////////////////////////

                if (m_TotalOnTheFinishingDart == inOriginalTarget)         // eg. 40 needed - do it in one throw!
                {
                    if (!inExactlyThatNumDarts || (m_NumDartsAvailable == 1)) {
                        m_TempScoreSet = new DartScoreSet();
                        m_TempScoreSet.Add(new DartScore(m_ValueOfTheFinishingDart, DartScore.kMultipleNeededForLastDart));

                        mDartsScoresList.add(m_TempScoreSet);
                    }
                }
            }
        }

        //////////////////////////////////////////////////////////////  Now try for 2 darts or more, if available, up to the max number
        //////////////////////////////////////////////////////////////  If 'kCanFinishWithAnyDart' try EACH multiple for the final dart (phew!)

        if (kCanFinishWithAnyDart || m_NumDartsAvailable >= 2) {
            int theStartDart = kCanFinishWithAnyDart ? 1 : (inExactlyThatNumDarts ? m_NumDartsAvailable : 2);

            for (int theNumDarts = theStartDart; theNumDarts <= m_NumDartsAvailable; theNumDarts++) {
                InitScanArray();

                for (int theIndexOfTheFinishingDart = mNumDartValues - 1; theIndexOfTheFinishingDart >= 0; theIndexOfTheFinishingDart--) {
                    if (kCanFinishWithAnyDart) {
                        for (int theFinalDartsMultiple = 1; theFinalDartsMultiple <= DartScore.GetHighestMultipleForDart(theIndexOfTheFinishingDart); theFinalDartsMultiple++) {
                            m_CurrentFinishingMultiple = theFinalDartsMultiple;
                            StandardSearchLoop(inOriginalTarget, theNumDarts, theIndexOfTheFinishingDart);
                        }
                    } else {
                        m_CurrentFinishingMultiple = DartScore.kMultipleNeededForLastDart;
                        StandardSearchLoop(inOriginalTarget, theNumDarts, theIndexOfTheFinishingDart);
                    }
                }
            }
        }

        mDartsScoresList.sort(Comparator.comparingInt(DartScoreSet::getScore));
    }

    /*******************************************************************************
     *******************************************************************************/
    private void StandardSearchLoop(int inOriginalTarget, int inNumDarts, int inCurrDartIndex) {
        m_ValueOfTheFinishingDart = DartIndexToValue(inCurrDartIndex);
        m_TotalOnTheFinishingDart = m_CurrentFinishingMultiple * m_ValueOfTheFinishingDart;

        //////////////////////////////////////////////////////////////

        if (m_TotalOnTheFinishingDart < inOriginalTarget) {
            m_ThePointsRemaining = inOriginalTarget - m_TotalOnTheFinishingDart;
            m_IgnoreNextOne = false;

            PrintFunc(inNumDarts, 0);
        }
    }

    /*******************************************************************************
     *******************************************************************************/
    private void InitScanArray() {
        for (int i = 0; i < m_NumDartsAvailable - 1; i++) {
            m_Scores[i] = kLowestSingleScore;     // don't allow any dart score under the minimum (1!)
            m_Multiples[i] = 1;
        }
    }

    /*******************************************************************************
     *******************************************************************************/
    private void PrintFunc(int inDartsAvailable, int inDartIndex) {
        for (int theDartIndex = inDartIndex; theDartIndex < (inDartsAvailable - 1); theDartIndex++) {
            for (int theCurrDartIndex = 0; theCurrDartIndex < mNumDartValues; theCurrDartIndex++) {
                int theHighestMultiple = DartScore.GetHighestMultipleForDart(theCurrDartIndex);

                for (int theNextMultiple = 1; theNextMultiple <= theHighestMultiple; theNextMultiple++) {
                    m_Scores[theDartIndex] = theCurrDartIndex + kLowestSingleScore;
                    m_Multiples[theDartIndex] = theNextMultiple;

                    if (m_IgnoreNextOne) {
                        m_IgnoreNextOne = false;
                    } else {
                        int theTotal = 0;

                        for (int i = 0; i < inDartsAvailable - 1; i++) {
                            theTotal += (DartIndexToValue(m_Scores[i] - kLowestSingleScore) * m_Multiples[i]);
                        }

                        ///////////////////////////////////////////

                        if (theTotal == m_ThePointsRemaining) {
                            m_TempScoreSet = new DartScoreSet();

                            for (int i = 0; i < inDartsAvailable - 1; i++) {
                                m_TempScoreSet.Add(new DartScore(DartIndexToValue(m_Scores[i] - kLowestSingleScore), m_Multiples[i]));
                            }

                            m_TempScoreSet.Add(new DartScore(m_ValueOfTheFinishingDart, m_CurrentFinishingMultiple));

                            ///////////////////////////////////////////

                            mDartsScoresList.add(m_TempScoreSet);
                            // System.out.println(m_TempScoreSet);
                        }
                    }

                    if ((theCurrDartIndex == (mNumDartValues - 1)) && (theNextMultiple == theHighestMultiple)) {
                        m_IgnoreNextOne = true;
                    }

                    if (theDartIndex < (inDartsAvailable - 1)) {
                        PrintFunc(inDartsAvailable, theDartIndex + 1);
                    }
                }
            }
        }
    }

    public int DartIndexToValue(int inDartIndex) {
        return mDartScoreArray[inDartIndex];
    }

    public List<DartScoreSet> GetScores() {
        // removeAllDuplicates(mDartsScoresList);
        return mDartsScoresList;
    }

    // This is OK-ish, but algorithm seems overly aggressive
//    private static void removeAllDuplicates(List<DartScoreSet> inVector) {
//        int lastIndex = inVector.size() - 1;
//
//        for (int i = 0; i < lastIndex; i++) {
//            try {
//                DartScoreSet iScore = inVector.get(i);
//
//                for (int j = i + 1; j <= lastIndex; j++) {
//                    try {
//                        DartScoreSet jScore = inVector.get(j);
//
//                        if (jScore.matches(iScore)) {
//                            inVector.remove(jScore);
//                            --j;
//                        }
//                    } catch (IndexOutOfBoundsException e) {
//                        break;
//                    }
//                }
//            } catch (IndexOutOfBoundsException e) {
//                break;
//            }
//        }
//    }

}
