package org.hiatusuk.darts;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DartsOutEnquiryTest {

    @Test
    public void dartIndexToValue() {
    }

    @Test
    public void getScores() {
//        new DartsOutEnquiry(15, 3, true).GetScores();

        final List<DartScoreSet> scores_170 = new DartsOutEnquiry(170, 3, true).GetScores();
        assertThat( scores_170.size() ).isEqualTo(1);
        assertThat( setsString(scores_170) ).isEqualTo("{[Treble 20, Treble 20, Bull]}");

        final List<DartScoreSet> scores_51_2 = new DartsOutEnquiry(51, 2, true).GetScores();
        assertThat( setsString(scores_51_2) ).isEqualTo("{[1, Bull], [11, Double 20], [13, Double 19], [Treble 5, Double 18], [15, Double 18], [17, Double 17], [19, Double 16], [Treble 7, Double 15], [Outer Bull, Double 13], [Treble 9, Double 12], [Treble 11, Double 9], [Treble 13, Double 6], [Treble 15, Double 3]}");
        assertThat( scores_51_2.size() ).isEqualTo(13);

//        for ( int i = 0; i < 180; i++) {
//            for ( int j = 1; j < 3; j++) {
//                new DartsOutEnquiry(i, j, true).GetScores();
//                new DartsOutEnquiry(i, j, false).GetScores();
//            }
//        }

        final List<DartScoreSet> scores_24_2 = new DartsOutEnquiry(24, 2, false).GetScores();
        assertThat( setsString(scores_24_2) ).isEqualTo("{[Double 12], [Double 1, Double 11], [2, Double 11], [Double 2, Double 10], [4, Double 10], [Treble 2, Double 9], [Double 3, Double 9], [6, Double 9], [Double 4, Double 8], [8, Double 8], [Double 5, Double 7], [10, Double 7], [Treble 4, Double 6], [Double 6, Double 6], [12, Double 6], [Double 7, Double 5], [14, Double 5], [Double 8, Double 4], [16, Double 4], [Treble 6, Double 3], [Double 9, Double 3], [18, Double 3], [Double 10, Double 2], [20, Double 2], [Double 11, Double 1]}");
        assertThat( scores_24_2.size() ).isEqualTo(25);
    }

    private String setsString(final List<DartScoreSet> scores) {
        return scores.stream().map(set -> "[" + set + "]").collect(Collectors.joining(", ", "{", "}"));
    }
}