package org.hiatusuk.darts;

import java.util.Objects;

/*******************************************************************************
 *******************************************************************************/
class DartScore {
    public final static int kLowestSingleScore = 1, kHighestSingleScore = 20, kBullValue = 25;
    public final static int kHighestMultipleAllowed = 3, kHighestMultipleForBull = 2;
    public final static boolean kCanFinishWithAnyDart = false;
    public final static int kMultipleNeededForLastDart = 2;
    public final static int kHighestOneDartScore = kHighestSingleScore * kHighestMultipleAllowed;
    private int mNumber, mMultiple;

    /*******************************************************************************
     *******************************************************************************/
    public DartScore(int inNumber, int inMultiple) {
        Assign(inNumber, inMultiple);
    }

    public int getNumber() {
        return mNumber;
    }

    public int getMultiple() {
        return mMultiple;
    }

    public void Assign(int inNumber, int inMultiple) {
        mNumber = inNumber;
        mMultiple = inMultiple;
    }

    @SuppressWarnings("EqualsGetClass")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DartScore dartScore = (DartScore) o;
        return mNumber == dartScore.mNumber && mMultiple == dartScore.mMultiple;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNumber, mMultiple);
    }

    public static int GetHighestMultipleForDart(int inIndex) {
        return (inIndex < kHighestSingleScore) ? kHighestMultipleAllowed : kHighestMultipleForBull;
    }

    @Override
    public String toString() {
        if (mNumber == kBullValue) {
            if (mMultiple == 2) {
                return "Bull";
            } else if (mMultiple == 1) {
                return "Outer Bull";
            } else {
                return "***" + mMultiple + "***";
            }
        } else {
            String thePrefix;

            if (mMultiple == 4)        // !!!!
            {
                thePrefix = "Quadruple ";
            } else if (mMultiple == 3) {
                thePrefix = "Treble ";
            } else if (mMultiple == 2) {
                thePrefix = "Double ";
            } else thePrefix = "";

            return thePrefix + mNumber;
        }
    }
}
