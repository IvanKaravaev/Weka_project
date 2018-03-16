package info.semanticanalyzer.classifiers.weka.threeway;
import com.google.inject.internal.util.Join;
import info.semanticanalyzer.classifiers.weka.SentimentClass;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Parsing_rezult_tomita {
	//public static void main(String args[]) throws IOException{
		Document docum=null;
		ArrayList<String> RezultatParsinga2 = new ArrayList<String>();
		ArrayList<String> RezultatParsinga1 = new ArrayList<String>();
		ArrayList<String> RezultatParsinga3 = new ArrayList<String>();
		final String FileWithTrainReviews = "file.txt";
		final String FileWithReviews = "fileWithReviews.txt";
		ArrayList<ArrayList> ModifierRewievs = new ArrayList<ArrayList>();
		ArrayList<ArrayList> ModifierMainRewievs = new ArrayList<ArrayList>();
		public  ArrayList<String> Parsing_N_gramms(){	 
		try {
			docum = Jsoup.parse(new File("N_Gramms.html"), "utf8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            		
            Element table = docum.select("table").first();
            Elements rows = table.select("tr");
            for (int i = 2; i < rows.size(); i++) {
                Element row = rows.get(i); //по номеру индекса получает строку
                Elements cols = row.select("td");// разбиваем полученную строку по тегу <td> на столбцы
               String Slovosochet = cols.get(0).text();
               int size = Slovosochet.split("\\s+").length;
               if(size==2){
               RezultatParsinga2.add(Slovosochet);
               }
               if(size==1){
            	   RezultatParsinga1.add(Slovosochet);
               }
               if(size==3){
                   RezultatParsinga3.add(Slovosochet);
                   }
            }
            
          
            Writer writer = null;
	        try {
	            writer = new FileWriter("file2.txt");
	            for (String line : RezultatParsinga2) {
	                writer.write(line);
//	                
	                writer.write(System.getProperty("line.separator"));
	            }
	            writer.flush();
	        } catch (Exception e) {
	            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, e);
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException ex) {
	                }
	            }
	        }
	        return RezultatParsinga3;
		}
        
		
		//System.out.println(RezultatParsinga2);
		//System.out.println(RezultatParsinga2.size());
		//System.out.println(RezultatParsinga.get(0));
		
		
		public void Vectorize(){
	        
	        
	        
	        String Reviews = null;
			try {
				Reviews = IOUtils.toString(new FileInputStream(FileWithTrainReviews));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String[] Rewiev = Reviews.split("\n");
	        for(String str : Rewiev){
	        	
	        	ArrayList<Integer> RewievList = new ArrayList<Integer>();
	        	for(int q=1;q<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size();q++){
	        		int def = 0;
	        		RewievList.add(def);
	        	}
	        	
	        	String[] lines = str.split(" ");
	        	for(int i=0; i<lines.length;i++){
	        		String unigramma = lines[i];
	        		//System.out.println(unigramma);
	        		for (String looking : RezultatParsinga1){
	    	        	
	        			  //if (looking.startsWith(unigramma)){
	        				 int index = RezultatParsinga1.indexOf(unigramma);
	        				 int find = 1;
	        				 //System.out.println(unigramma);
	        				 if(index!=-1){
	        				 RewievList.set(index, find);
	        				 }
	        			//  }
	        			   
	        	}
	        }
	        	for(int i=0; i<lines.length-1;i++){
	        		String bigramma = lines[i]+" "+lines[i+1];
	        		//System.out.println(bigramma);
	        		for(String looking : RezultatParsinga2){
	        			
	        			//if(looking.startsWith(bigramma)){
	        				int index = RezultatParsinga2.indexOf(bigramma);
	        				int find = 1;
	        				//System.out.println(index);
	        				//System.out.println(RezultatParsinga1.size());
	        				//System.out.println(index+RezultatParsinga1.size());
	        				if(index!=-1){
	        				RewievList.set(index+RezultatParsinga1.size(), find);
	        				}
	        			//}
	        		}
	        	}
	        	for(int i=0; i<lines.length-2;i++){
	        		String trigramma = lines[i]+" "+lines[i+1]+" "+lines[i+2];
	        		//System.out.println(trigramma);
	        		for(String looking : RezultatParsinga2){
	        			
	        			//if(looking.startsWith(trigramma)){
	        				int index = RezultatParsinga2.indexOf(trigramma);
	        				int find = 1;
	        				if(index!=-1){
	        				RewievList.set(index+RezultatParsinga1.size()+RezultatParsinga2.size(), find);
	        				}
	        			//}
	        		}
	        	}
	        	ModifierRewievs.add(RewievList);
	        	

	}
	        //System.out.println(ModifierRewievs);
	       //return ModifierRewievs;
		}      
	//}	
		
		
		
		
public void VectorizeMain(){
	        
	        
	        
	        String ReviewsMain = null;
			try {
				ReviewsMain = IOUtils.toString(new FileInputStream(FileWithReviews));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String[] RewievMain = ReviewsMain.split("\n");
	        for(String str : RewievMain){
	        	
	        	ArrayList<Integer> RewievListMain = new ArrayList<Integer>();
	        	for(int q=1;q<=RezultatParsinga1.size()+RezultatParsinga2.size()+RezultatParsinga3.size();q++){
	        		int def = 0;
	        		RewievListMain.add(def);
	        	}
	        	
	        	String[] lines = str.split(" ");
	        	for(int i=0; i<lines.length;i++){
	        		String unigramma = lines[i];
	        		//System.out.println(unigramma);
	        		for (String looking : RezultatParsinga1){
	    	        	
	        			//  if (looking.startsWith(unigramma)){
	        				 int index = RezultatParsinga1.indexOf(unigramma);
	        				 int find = 1;
	        				 if(index!=-1)
	        				 {
	        				 RewievListMain.set(index, find);
	        				 }
	        			  }
	        			   
	        	
	        }
	        	for(int i=0; i<lines.length-1;i++){
	        		String bigramma = lines[i]+" "+lines[i+1];
	        		//System.out.println(bigramma);
	        		for(String looking : RezultatParsinga2){
	        			
	        			//if(looking.startsWith(bigramma)){
	        				int index = RezultatParsinga2.indexOf(bigramma);
	        				int find = 1;
	        				//System.out.println(index);
	        				//System.out.println(RezultatParsinga1.size());
	        				//System.out.println(index+RezultatParsinga1.size());
	        				if(index!=-1)
	        				{
	        				RewievListMain.set(index+RezultatParsinga1.size(), find);
	        				}
	        			}
	        		
	        	}
	        	for(int i=0; i<lines.length-2;i++){
	        		String trigramma = lines[i]+" "+lines[i+1]+" "+lines[i+2];
	        		//System.out.println(trigramma);
	        		for(String looking : RezultatParsinga2){
	        			
	        			//if(looking.startsWith(trigramma)){
	        				int index = RezultatParsinga2.indexOf(trigramma);
	        				int find = 1;
	        				if(index!=-1)
	        				{
	        				RewievListMain.set(index+RezultatParsinga1.size()+RezultatParsinga2.size(), find);
	        				}
	        			}
	        		
	        	}
	        	ModifierMainRewievs.add(RewievListMain);
	        	

	}
	       // System.out.println(ModifierMainRewievs);
	       // System.out.println(ModifierMainRewievs.size());
	       //return ModifierRewievs;
		}      
		
		
		
		
		
		
		
		
		
		public ArrayList<ArrayList> getModifierRewievs(){
			return ModifierRewievs;
		}
		public ArrayList<String> getRezultatParsinga1(){
			return RezultatParsinga1;
		}
		public ArrayList<String> getRezultatParsinga2(){
			return RezultatParsinga2;
		}
		public ArrayList<String> getRezultatParsinga3(){
			return RezultatParsinga3;
		}
		public ArrayList<ArrayList> getModifierMainRewievs(){
			return ModifierMainRewievs;
		}
}


