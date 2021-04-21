/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author sbmpc.student
 */
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


/*
	public static void init() {
		pipeline = new StanfordCoreNLP("MyPropFile.properties");
	}
public class SentimentAnalyzer {
 */
public class NLP {
	static StanfordCoreNLP pipeline;
        public static void init() {
		pipeline = new StanfordCoreNLP("MyPropFile.properties");
        }
        /*
   
        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
            return 0;
        }
        return mainSentiment;
 
    }
}
	public static int findSentiment(String tweet) {

		int mainSentiment = 0;
		if (tweet != null && tweet.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(tweet);
			for (CoreMap sentence : annotation
					.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence
						.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}

			}
		}
		return mainSentiment;
	}*/
        public static int computeSentiment(String tweet)
        {
            int score= 0;
            String scoreStr;
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                scoreStr=sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                score = RNNCoreAnnotations.getPredictedClass(tree);
                System.out.println(scoreStr);
            }
            return score;
        }
}
