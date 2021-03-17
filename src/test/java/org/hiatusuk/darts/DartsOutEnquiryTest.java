package org.hiatusuk.darts;

import org.junit.jupiter.api.Test;

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

        var scores_170 = new DartsOutEnquiry(170, 3, true).GetScores();
        assertThat( scores_170.size() ).isEqualTo(1);
        assertThat( setsString(scores_170) ).isEqualTo("{[Treble 20, Treble 20, Bull]}");

        var scores_51_2 = new DartsOutEnquiry(51, 2, true).GetScores();
        assertThat( setsString(scores_51_2) ).isEqualTo("{[19, Double 16], [11, Double 20], [15, Double 18], [1, Bull], [13, Double 19], [Treble 5, Double 18], [17, Double 17], [Outer Bull, Double 13], [Treble 9, Double 12], [Treble 13, Double 6], [Treble 15, Double 3], [Treble 7, Double 15], [Treble 11, Double 9]}");
        assertThat( scores_51_2.size() ).isEqualTo(13);

//        for ( int i = 0; i < 180; i++) {
//            for ( int j = 1; j < 3; j++) {
//                new DartsOutEnquiry(i, j, true).GetScores();
//                new DartsOutEnquiry(i, j, false).GetScores();
//            }
//        }

        final List<DartScoreSet> scores_24_2 = new DartsOutEnquiry(24, 2, false).GetScores();
        assertThat( setsString(scores_24_2) ).isEqualTo("{[8, Double 8], [Double 12], [4, Double 10], [Double 4, Double 8], [12, Double 6], [16, Double 4], [20, Double 2], [2, Double 11], [6, Double 9], [10, Double 7], [14, Double 5], [Treble 4, Double 6], [Double 8, Double 4], [Double 2, Double 10], [Double 6, Double 6], [Treble 6, Double 3], [Double 9, Double 3], [18, Double 3], [Double 10, Double 2], [Treble 2, Double 9], [Double 11, Double 1], [Double 1, Double 11], [Double 3, Double 9], [Double 5, Double 7], [Double 7, Double 5]}");
        assertThat( scores_24_2.size() ).isEqualTo(25);
    }

    private String setsString(final List<DartScoreSet> scores) {
        return scores.stream().map(set -> "[" + set + "]").collect(Collectors.joining(", ", "{", "}"));
    }
}
