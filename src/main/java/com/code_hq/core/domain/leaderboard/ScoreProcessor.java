package com.code_hq.core.domain.leaderboard;

import com.code_hq.core.application.query.model.leaderboard.Score;
import com.code_hq.core.application.query.model.leaderboard.ScoreList;

import java.util.*;

public class ScoreProcessor {

    public List<ScoreList> processScores(List<Score> scores)
    {
        Map<String, ScoreList> scoreLists = new HashMap();

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            ScoreList scoreList;

            if (!scoreLists.containsKey(score.getApplicationId())) {
                 scoreList = new ScoreList(
                        score.getApplicationId(),
                        score.getApplicationName(),
                        null,
                        new HashMap<String, Score>()
                );
            } else {
                scoreList = scoreLists.get(score.getApplicationId());
            }

            scoreList.getIndividualScores().put(score.getMetricId(), score);

            scoreLists.put(scoreList.getApplicationId(), scoreList);
        }

        List<ScoreList> rankedScoreLists = new ArrayList<>();
        Iterator it = scoreLists.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, ScoreList> pair = (Map.Entry) it.next();
            ScoreList scoreList = pair.getValue();

            Double sum = 0.0;
            for (Score score : scoreList.getIndividualScores().values()) {
                sum += score.getValue(); // TODO: Use direction to determine how to add value to the overall score
            }

            rankedScoreLists.add(scoreList.withOverallScore(
                    scoreList.getIndividualScores().size() == 0
                    ? null // If an application has had no scores then that feels different than an application with a score of 0
                    : (sum / scoreList.getIndividualScores().size())
            ));
        }

        rankedScoreLists.sort(Comparator.comparing(ScoreList::getOverallScore).reversed());

        return rankedScoreLists;
    }
}
