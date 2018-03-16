package info.semanticanalyzer.classifiers.weka.threeway;

import com.google.inject.internal.util.Join;
import info.semanticanalyzer.classifiers.weka.SentimentClass;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;


public class ThreeWayMNBTrainer {
	
    public NaiveBayesMultinomialText classifier;
    public NaiveBayes classifierMain;
    private String modelFile;
    private Instances dataRaw;
        private Instances dataRawMain;
        final String Classification_Content_FILE = "src/test/resources/test.tsv";
  
	 double prediction;
	 Instance NaKlassif;
	 StatDann main = new StatDann();
	 Parsing_rezult_tomita Parsing_rezult_tomita = new Parsing_rezult_tomita();
	 
	 ArrayList<ArrayList> ModifierRewievs; 
     ArrayList<String> RezultatParsinga1; 
     ArrayList<String> RezultatParsinga2;
     ArrayList<String> RezultatParsinga3; 
     ArrayList<ArrayList> ModifierMainRewievs; 
     

    public ThreeWayMNBTrainer(String outputModel) {
    	Parsing_rezult_tomita.Parsing_N_gramms();
        Parsing_rezult_tomita.Vectorize();
        Parsing_rezult_tomita.VectorizeMain();
         ModifierRewievs = Parsing_rezult_tomita.getModifierRewievs();
         RezultatParsinga1 = Parsing_rezult_tomita.getRezultatParsinga1();
         RezultatParsinga2 = Parsing_rezult_tomita.getRezultatParsinga2();
         RezultatParsinga3 = Parsing_rezult_tomita.getRezultatParsinga3();
         ModifierMainRewievs = Parsing_rezult_tomita.getModifierMainRewievs();
        classifier = new NaiveBayesMultinomialText();
        classifierMain = new NaiveBayes();
        modelFile = outputModel;
       // System.out.println(ModifierMainRewievs);

        //int[] value = new int[RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()-1];
      /*  for(int i=0;i<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()-1;i++)
        {
        	
        	value [i];
        }*/
        
        
        Attribute[] names = new Attribute[RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()]; 
        
        ArrayList<Attribute> attrs = new ArrayList<Attribute>(2);
        ArrayList<String> classVal = new ArrayList<String>();
        classVal.add(SentimentClass.ThreeWayClazz.NEGATIVE.name());
        classVal.add(SentimentClass.ThreeWayClazz.POSITIVE.name());
        attrs.add(new Attribute("@@class@@",classVal));
        for(int w=0;w<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()-1;w++)
        {
        	String nameatts = "name"+w;
        	
        	names[w] = new Attribute(nameatts); 
        	attrs.add(names[w]);
	    }
        
        
       
        
       /* ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
        ArrayList<String> classValue = new ArrayList<String>();
        classVal.add(SentimentClass.ThreeWayClazz.NEGATIVE.name());
        classVal.add(SentimentClass.ThreeWayClazz.POSITIVE.name());
        attrs.add(new Attribute("content",(ArrayList<String>)null));
        attrs.add(new Attribute("@@class@@",classValue));*/
        //создание ArrayListа для классификации конечных данных
        //ArrayList<Attrs> nado = new ArrauList<Attrs>(1);
        //nado.add(new Attrs("contents",(ArrayList<String>)null);
        //dataRawMain = new Instances("MainInstances",nado,10);

      //  dataRaw = new Instances("TrainingInstances",atts,10);
        dataRawMain = new Instances("TrainInst",attrs,10);
    }

    
    public void addTrainInst(SentimentClass.ThreeWayClazz threeWayClazz, int[] VectorValues){
    	double[] instanceValue = new double[dataRawMain.numAttributes()]; 	
    	
    	for(int i=1;i<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size();i++){//смена мест значений instances
    		instanceValue[i] = VectorValues[i-1];//был i-1
    	}
    	instanceValue[0] = threeWayClazz.ordinal();
    	//System.out.println(instanceValue[RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()]);
    	dataRawMain.add(new DenseInstance(1.0, instanceValue));
    	//System.out.println(dataRawMain);
        dataRawMain.setClassIndex(0);
    }
    
    
    
    
    
	public void addTrainingInstance(SentimentClass.ThreeWayClazz threeWayClazz, String[] words) {
        double[] instanceValue = new double[dataRaw.numAttributes()];
        instanceValue[0] = dataRaw.attribute(0).addStringValue(Join.join(" ", words));
        instanceValue[1] = threeWayClazz.ordinal();
        dataRaw.add(new DenseInstance(1.0, instanceValue));
        dataRaw.setClassIndex(0);
    }
    
    //Функция добавления данных для классификации в instances 
    /*public void addMainInstance(String[] words) {
        double[] instanceValueMain = new double[dataRawMain.numAttributes()];
        instanceValue[0] = dataRawMain.attribute(0).addStringValue(Join.join(" ", words));
        
        dataRaw.add(new DenseInstance(1.0, instanceValueMain));?????
        Instance toClassifyMain = new DenseInstance(1.0, instanceValueMain);???????скорее всего нет
            dataRawMain.setClassIndex(1);
            toClassifyMain.setDataset(dataRawMain);???????скорее всего нет
    }
*/    

   /* public void trainModel() throws Exception {
        classifier.buildClassifier(dataRaw);
    }

    public void testModel() throws Exception {
        Evaluation eTest = new Evaluation(dataRaw);
        eTest.evaluateModel(classifier, dataRaw);
       String strSummary = eTest.toSummaryString();
       System.out.println(strSummary);
        System.out.println(eTest.numInstances());
        System.out.println(eTest.numTruePositives(0));
       System.out.println(eTest.numTruePositives(1));
       System.out.println(eTest.numTruePositives(2));
       System.out.println(eTest.pctUnclassified());
        System.out.println(eTest.toMatrixString());
        
        
    }*/
    
    
    
    public void trainModelMain() throws Exception {
    	classifierMain.buildClassifier(dataRawMain);
    }

    public void testModelMain() throws Exception {
        Evaluation eTest = new Evaluation(dataRawMain);
        eTest.evaluateModel(classifierMain, dataRawMain);
       String strSummary = eTest.toSummaryString();
       System.out.println(strSummary);
        System.out.println(eTest.numInstances());
        System.out.println(eTest.numTruePositives(0));
       System.out.println(eTest.numTruePositives(1));
       System.out.println(eTest.numTruePositives(2));
       System.out.println(eTest.pctUnclassified());
        System.out.println(eTest.toMatrixString());
        
        
    }
    
    public void showInstances() {
        System.out.println(dataRaw);
    }

    public Instances getDataRaw() {
        return dataRaw;
    }

    public void saveModel() throws Exception {
        weka.core.SerializationHelper.write(modelFile, classifier);
    }

    public void loadModel(String _modelFile) throws Exception {
        NaiveBayesMultinomialText classifier = (NaiveBayesMultinomialText) weka.core.SerializationHelper.read(_modelFile);
        this.classifier = classifier;
    }
    
    
    
    public void saveModelMain() throws Exception {
        weka.core.SerializationHelper.write(modelFile, classifierMain);
    }

    public void loadModelMain(String _modelFile) throws Exception {
        NaiveBayes classifierMain = (NaiveBayes) weka.core.SerializationHelper.read(_modelFile);
        this.classifierMain = classifierMain;
    }
    
    public void classificationGo() throws Exception
    {
    	double[] instanceValue = new double[dataRawMain.numAttributes()]; 
    	loadModelMain(modelFile);
    	
    	for(int w=0; w<=ModifierMainRewievs.size()-1;w++)
        {
    		
        	int[] rewi = new int[RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()];
        for(int i=0;i<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size()-1;i++)
        {
        	
        	rewi[i] =  (int) ModifierMainRewievs.get(w).get(i);
        	
        }
        
        for(int i=1;i<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size();i++){//смена мест значений instances
    		instanceValue[i] = rewi[i-1];
    		
    	}
        Instance toClassify = new DenseInstance(1.0, instanceValue);
        dataRawMain.setClassIndex(0);
        toClassify.setDataset(dataRawMain);
        //System.out.println(dataRawMain);
        double prediction = this.classifierMain.classifyInstance(toClassify);
        
if(SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.POSITIVE){
	
        	main.getf1();
        	main.f1=1+main.f1;
        	main.setf1(main.f1);
        	//System.out.println("qqq");
        	}
     if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.NEGATIVE){
    	 
     	main.getf2();
     	main.f2=1+main.f2;
     	main.setf2(main.f2);
     	//System.out.println("ddd");
     }
         
  if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.NEUTRAL){
	  
   	main.getf3();
   	main.f3=1+main.f3;
   	main.setf3(main.f3);
     
     }
        }
	
    	
    		
    	main.info();
  
    }
    
    
    class StatDann{
    	int f1=0;
    	int f2=0;
    	int f3=0;
    	int ola1;
    	int ola2;
    	int ola3;
    	
    	
    	public void setf1(int ola1) {
            ola1 = f1;
        }

        public void setf3(int f3) {
            this.f3=f3; 
        }

        public void setf2(int f2) {
            this.f2 = f2;
        }
        public int getf1() {
        	ola1=f1;
            return ola1;
        }
        public int getf2() {
        	//ola2=f2;
            return f2;
        }
        public int getf3() {
        	ola3=f3;
            return ola3;
        }
        public void info(){
        	int pl=f1+f2;
        	float pos = (float)f1/pl*100;
        	float neg = (float)f2/pl*100;
            System.out.println("процент положительных отзывов " + pos +"  процент негативных отзывов " + neg +" всего отзывов " + pl);
        }   
    }
        
    
    
   /* public void classificationGo() throws Exception
    {
    	
        String content = IOUtils.toString(new FileInputStream(Classification_Content_FILE), "UTF-8");
        String[] lines = content.split("\n");

        int wordsCount = getWordsCount(lines);

        loadModel(modelFile);

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
    	
        System.out.println("Testing on " + texts.length + " samples, " + wordsCount + " words, " + totalLength
                + " characters...");

        long startTime = System.currentTimeMillis();
        for (String str : texts) {
            // to print out the predicted labels, uncomment the line:
            
        	classify(str,main);
        //    System.out.println(threeWayMNBTrainer.classify(str).name());

            	
            
            
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Time " + elapsedTime + " ms.");
        System.out.println("Speed " + ((double) totalLength / elapsedTime) + " chars/ms");
        System.out.println("Speed " + ((double) wordsCount / elapsedTime) + " words/ms");
        System.out.println("+++++++++=");
        
        main.info();
        
        
        
    }

    public void classify(String sentence,StatDann main) throws Exception {
    	
        double[] instanceValue = new double[dataRaw.numAttributes()];
        instanceValue[0] = dataRaw.attribute(0).addStringValue(sentence);

        Instance toClassify = new DenseInstance(1.0, instanceValue);
        dataRaw.setClassIndex(1);
        toClassify.setDataset(dataRaw);

        double prediction = this.classifier.classifyInstance(toClassify);*/

     //   double distribution[] = this.classifier.distributionForInstance(toClassify);

      /*  if (distribution[0] != distribution[1]){
            return SentimentClass.ThreeWayClazz.values()[(int)prediction];}
        else
        { return SentimentClass.ThreeWayClazz.NEUTRAL;}*/
       /* if(SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.POSITIVE){
        	
        	main.getf1();
        	main.f1=1+main.f1;
        	main.setf1(main.f1);
        	}
     if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.NEGATIVE){
    
     	main.getf2();
     	main.f2=1+main.f2;
     	main.setf2(main.f2);
     }
         
  if( SentimentClass.ThreeWayClazz.values()[(int)prediction] == SentimentClass.ThreeWayClazz.NEUTRAL){
	  
   	main.getf3();
   	main.f3=1+main.f3;
   	main.setf3(main.f3);
     
     }
  
    }
    
    
    class StatDann{
    	int f1=0;
    	int f2=0;
    	int f3=0;
    	int ola1;
    	int ola2;
    	int ola3;
    	
    	
    	public void setf1(int ola1) {
            ola1 = f1;
        }

        public void setf3(int f3) {
            this.f3=f3; 
        }

        public void setf2(int f2) {
            this.f2 = f2;
        }
        public int getf1() {
        	ola1=f1;
            return ola1;
        }
        public int getf2() {
        	//ola2=f2;
            return f2;
        }
        public int getf3() {
        	ola3=f3;
            return ola3;
        }
        public void info(){
        	int pl=f1+f2;
        	float pos = (float)f1/pl*100;
        	float neg = (float)f2/pl*100;
            System.out.println("процент положительных отзывов " + pos +"  процент негативных отзывов " + neg +" всего отзывов " + pl);
        }   
    }
    
    
    /* public SentimentClass.ThreeWayClazz classyfication(Instance toClassifyMain) throws Exception {// ??????параметр
      
       Instance toClassifyMain;//???тоже возможно даже наиболее вероятно
       Instance toClassifyMain = new DenseInstance(1.0, instanceValueMain);//???????
         dataRawMain.setClassIndex(1);
         toClassifyMain.setDataset(dataRawMain);//???????  

         double prediction = this.classifier.classifyInstance(toClassify);

         double distribution[] = this.classifier.distributionForInstance(toClassifyMain);

         if (distribution[0] != distribution[1])
             return SentimentClass.ThreeWayClazz.values()[(int)prediction];
         else
             return SentimentClass.ThreeWayClazz.NEUTRAL;
     }*/
    
    
   
}
