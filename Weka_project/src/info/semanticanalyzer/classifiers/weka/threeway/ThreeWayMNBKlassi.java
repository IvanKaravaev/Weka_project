package info.semanticanalyzer.classifiers.weka.threeway;

import com.google.inject.internal.util.Join;
import info.semanticanalyzer.classifiers.weka.SentimentClass;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;


public class ThreeWayMNBKlassi {
	
    private NaiveBayesMultinomialText classifier;
    private  String modelFile = "models/three-way-sentiment-mnb.model";
    private Instances dataRawMain;
    int neut = 0;
	 int neg = 0;
	 int pos = 0;
	 double prediction;
	 Instance NaKlassif;
	 final String Classification_Content_FILE = "src/test/resources/test.tsv";
	 String outputModel = "models/three-way-sentiment-mnb.model";
		ThreeWayMNBTrainer threeWayMNBTrainer = new ThreeWayMNBTrainer(outputModel);
		
		
	 
	 
	 ThreeWayMNBTrainer threeWayMnbTrainer;

    public ThreeWayMNBKlassi(String outputModel) {
       // classifier = new NaiveBayesMultinomialText();
      //  modelFile = outputModel;

       
        
       // создание ArrayListа для классификации конечных данных
        ArrayList<Attribute> nado = new ArrayList<Attribute>(1);
        nado.add(new Attribute("content",(ArrayList<String>)null));
        dataRawMain = new Instances("MainInstances",nado,10);

       
    }

   
    
    //Функция добавления данных для классификации в instances 
    public void addMainInstance(String[] words) throws Exception {
        double[] instanceValueMain = new double[dataRawMain.numAttributes()];
        instanceValueMain[0] = dataRawMain.attribute(0).addStringValue(Join.join(" ", words));
        
        dataRawMain.add(new DenseInstance(1.0, instanceValueMain));//?????
        
     
        
        	
       // Instance toClassifyMain = new DenseInstance(1.0, instanceValueMain);//???????скорее всего нет
            dataRawMain.setClassIndex(0);
            
           // toClassifyMain.setDataset(dataRawMain);//???????скорее всего нет
    }
    

    public int Show_pos(){
    	return pos;
    }
    
    public int Show_neg(){
    	return neg;
    }
    public int Show_neut(){
    	return neut;
    }
    
    
    public void showMainInstances() {
        System.out.println(dataRawMain);
    }

    public Instances getDataRawMain(Instances q) {
       q = dataRawMain;
        return q;
    }
    
   /* public void saveModel() throws Exception {
        weka.core.SerializationHelper.write(modelFile, classifier);
    }*/

   
    public void loadModel(String _modelFile) throws Exception {
        NaiveBayesMultinomialText classifier = (NaiveBayesMultinomialText) weka.core.SerializationHelper.read(_modelFile);
        this.classifier = classifier;
    }

    
   /* public void classificationGo() throws Exception
    {
    	
        String content = IOUtils.toString(new FileInputStream(Classification_Content_FILE), "UTF-8");
        String[] lines = content.split("\n");

        int wordsCount = getWordsCount(lines);

        threeWayMNBTrainer.loadModel(modelFile);

        test(lines, wordsCount, content.length()); // warm up

        test(lines, wordsCount, content.length()); // test
    }

    private int getWordsCount(String[] texts)
    {
        int count = 0;
        for (String str : texts) {
            count += str.split("\\s+").length;
        }
        return count;
    }

    private void test(String[] texts, int wordsCount, int totalLength) throws Exception {
    	int pos = 0;
    	int neg=0;
    	int neut=0;
        System.out.println("Testing on " + texts.length + " samples, " + wordsCount + " words, " + totalLength
                + " characters...");

        long startTime = System.currentTimeMillis();
        for (String str : texts) {
            // to print out the predicted labels, uncomment the line:
            
        	threeWayMNBTrainer.classify(str,pos,neut,neg);
        //    System.out.println(threeWayMNBTrainer.classify(str).name());

            	
            
            
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Time " + elapsedTime + " ms.");
        System.out.println("Speed " + ((double) totalLength / elapsedTime) + " chars/ms");
        System.out.println("Speed " + ((double) wordsCount / elapsedTime) + " words/ms");
        System.out.println("+++++++++=");
        System.out.println(pos);
        System.out.println(neg);
        System.out.println(neut);
        
        
        
        
    }*/
   
    
    /*public void classyfication() throws Exception {
    	 
    	 
      
      for(int i=0; i<dataRawMain.numInstances(); i++){
    	
    	 Instance NaKlassif = dataRawMain.instance(i);

         double prediction = this.classifier.classifyInstance(NaKlassif);

        // double distribution[] = this.classifier.distributionForInstance(NaKlassif);

        // if (distribution[0] != distribution[1]){
             if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.POSITIVE){
            	pos++; 
             }
         if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.NEGATIVE){
        	 neg++;
         }
             
      if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.NEUTRAL){
             neut++;
         
         }
      }
      System.out.println(neut);
      System.out.println(neg);
      System.out.println(pos);
     }*/



    
     
}
