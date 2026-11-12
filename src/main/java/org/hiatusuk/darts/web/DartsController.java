package org.hiatusuk.darts.web;

import org.hiatusuk.darts.DartScoreSet;
import org.hiatusuk.darts.DartsOutEnquiry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DartsController {

    @GetMapping("/")
    public String index(@RequestParam(name = "total", defaultValue = "170") int total,
                        @RequestParam(name = "darts_left", defaultValue = "3") int dartsLeft,
                        @RequestParam(name = "exactly_n", defaultValue = "No") String exactlyN,
                        @RequestParam(name = "max_results", defaultValue = "100") int maxResults,
                        Model model) {
        boolean exactly = "Yes".equalsIgnoreCase(exactlyN);
        List<DartScoreSet> results = new DartsOutEnquiry(total, dartsLeft, exactly).GetScores();

        model.addAttribute("total", total);
        model.addAttribute("dartsLeft", dartsLeft);
        model.addAttribute("exactly", exactly);
        model.addAttribute("maxResults", maxResults);
        model.addAttribute("results", results.stream().limit(maxResults).toList());
        return "index";
    }
}
