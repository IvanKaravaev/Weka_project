package info.semanticanalyzer.classifiers.weka.threeway;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;

import com.google.inject.internal.util.Join;

import info.semanticanalyzer.classifiers.weka.SentimentClass;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class ThreeWayMNBTrainerRunner {
	

	final String Classification_Content_FILE = "src/test/resources/test.tsv";
	String outputModel = "models/three-way-sentiment-mnb.model";
	//ThreeWayMNBTrainer threeWayMNBTrainer = new ThreeWayMNBTrainer(outputModel);
	final static String trainfile = "src/test/resources/train.txt";
	String modelFile = outputModel;
	
	
	
	
    

    public static void main(String[] args) throws Exception {
        KaggleCSVReaderThreeWay kaggleCSVReaderThreeWay = new KaggleCSVReaderThreeWay();
        KaggleCSVReaderThreeWay kaggleCSVReaderThreeWayMain = new KaggleCSVReaderThreeWay();//открытие потока чтения данных для классификации
        kaggleCSVReaderThreeWay.readKaggleCSV("kaggle/train.tsv");
        kaggleCSVReaderThreeWayMain.readKaggleCSVMain("kaggle/klassi.tsv");
        KaggleCSVReaderThreeWay.CSVInstanceThreeWay csvInstanceThreeWay;
        KaggleCSVReaderThreeWay.CSVInstanceThreeWayKlassi CSVInstanceThreeWayKlassi;// создание объекта типа класса для классификации
        
        Runtime.getRuntime().exec("cmd /c start C:/Users/Student/workspace/example/diplom_tomita/tomita.bat").waitFor();
        Thread.currentThread().sleep(5000);
       //ProcessBuilder processBuilder = new ProcessBuilder("cmd","/C","C:/Users/Student/workspace/example/diplom_tomita/tomita.bat");
      //Process process = processBuilder.start();
        
        String outputModel = "models/three-way-sentiment-mnb.model";
        Parsing_rezult_tomita Parsing_rezult_tomita = new Parsing_rezult_tomita();
        
     //   ThreeWayMNBKlassi threeWayMNBKlassi = new ThreeWayMNBKlassi(outputModel); //создание объекта threeWayMNBKlassi и не забудь создать сам класс
        

        int sentimentPositiveCount = 0;
        int sentimentNegativeCount = 0;
        int sentimentOtherCount = 0;
        
        
        

      /* System.out.println("Adding training instances");
        int addedNum = 0;
        while ((csvInstanceThreeWay = kaggleCSVReaderThreeWay.next()) != null) {
            if (csvInstanceThreeWay.isValidInstance) {
                if (csvInstanceThreeWay.sentiment.equals(SentimentClass.ThreeWayClazz.POSITIVE) && sentimentPositiveCount < 7072) {
                    sentimentPositiveCount++;
                    threeWayMNBTrainer.addTrainingInstance(csvInstanceThreeWay.sentiment, csvInstanceThreeWay.phrase.split("\\s+"));
                    addedNum++;
                }
                else if (csvInstanceThreeWay.sentiment.equals(SentimentClass.ThreeWayClazz.NEGATIVE) && sentimentNegativeCount < 7072) {
                    sentimentNegativeCount++;
                    threeWayMNBTrainer.addTrainingInstance(csvInstanceThreeWay.sentiment, csvInstanceThreeWay.phrase.split("\\s+"));
                    addedNum++;
                }
                else {
                    sentimentOtherCount++;
                    addedNum++;
                }

                if (sentimentPositiveCount >= 7072 && sentimentNegativeCount >= 7072)
                    break;
            }
        }
        */
        Parsing_rezult_tomita.Parsing_N_gramms();
        Parsing_rezult_tomita.Vectorize();
        Parsing_rezult_tomita.VectorizeMain();
        ArrayList<ArrayList> ModifierRewievs = Parsing_rezult_tomita.getModifierRewievs();
        ArrayList<String> RezultatParsinga1 = Parsing_rezult_tomita.getRezultatParsinga1();
        ArrayList<String> RezultatParsinga2 = Parsing_rezult_tomita.getRezultatParsinga2();
        ArrayList<String> RezultatParsinga3 = Parsing_rezult_tomita.getRezultatParsinga3();
        ArrayList<ArrayList> ModifierMainRewievs = Parsing_rezult_tomita.getModifierMainRewievs();
        
        ThreeWayMNBTrainer threeWayMNBTrainer = new ThreeWayMNBTrainer(outputModel);
        
        String tonalnost = IOUtils.toString(new FileInputStream(trainfile));
        String[] tonal = tonalnost.split("\n");
        //ArrayList<Integer> file_sent = new ArrayList<Integer>();
        
        System.out.println(ModifierRewievs.size());
        for(int w=0; w<=ModifierRewievs.size()-1;w++)
        {
        	
        	int[] rewi = new int[RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()];
        for(int i=0;i<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()-1;i++)
        {
        	
        	rewi[i] =  (int) ModifierRewievs.get(w).get(i);
        	
        }
        String tonalRemove = tonal[w];
        String tonalRemoves = tonalRemove.replaceAll("[^\\d]", "");
        
        Integer sentiment = Integer.parseInt(tonalRemoves);
        System.out.println(tonalRemoves);
        
        		SentimentClass.ThreeWayClazz sent = SentimentClass.ThreeWayClazz.values()[sentiment];
        		//System.out.println(sent);
        threeWayMNBTrainer.addTrainInst(sent,rewi);
        
        }
        
        
       
        
        
        
        
        System.out.println("Adding Klassi instances");
        int addedNumeric = 0;
        while ((CSVInstanceThreeWayKlassi = kaggleCSVReaderThreeWayMain.nextMain()) != null) {
           // if (CSVInstanceThreeWayKlassi.isValidInstance) {
       
            	//threeWayMNBKlassi.addMainInstance(CSVInstanceThreeWayKlassi.phrase.split("\\s+"));
            	addedNumeric++;
            	
                
           // }
        } 

        kaggleCSVReaderThreeWay.close();
        kaggleCSVReaderThreeWayMain.closeKlassi();

      /*  System.out.println("Added " + addedNum + " instances");
        System.out.println("Of which " + sentimentPositiveCount + " positive instances, " +
                           sentimentNegativeCount + " negative instances and " +
                           sentimentOtherCount + " other sentiment instances");
        System.out.println("Added " + addedNumeric + " qqq");
      
        */
        
        
        
       

        System.out.println("Training and saving Model");
        threeWayMNBTrainer.trainModelMain();
        threeWayMNBTrainer.saveModelMain();
        
       // threeWayMNBTrainer.classificationGo();
        
       System.out.println("Testing model");
       threeWayMNBTrainer.testModelMain();
        
        
      // System.out.println(ModifierMainRewievs);
       
       threeWayMNBTrainer.classificationGo();
    }
    
    
    
    
}
