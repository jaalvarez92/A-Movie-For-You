package com.example.jalvarez.amovieforyou.data.dummy;

import com.example.jalvarez.amovieforyou.data.Recommendation;

import java.util.Date;
import java.util.Random;

/**
 * Created by jalvarez on 1/16/17.
 */

public class RecommendationsDummyData {


    private final static Recommendation[] mRecommendations =
        new Recommendation[] {
                new Recommendation("id1", new Date(), MovieDummyData.getRandomMovie()),
                new Recommendation("id2", new Date(), MovieDummyData.getRandomMovie()),
                new Recommendation("id3", new Date(), MovieDummyData.getRandomMovie()),
                new Recommendation("id4", new Date(), MovieDummyData.getRandomMovie()),
        };


    public final static Recommendation[] getRecommendations(){
        return mRecommendations;
    }


    public final static Recommendation getRandomRecommendation(){
        return mRecommendations[new Random().nextInt(mRecommendations.length)];
    }

}
