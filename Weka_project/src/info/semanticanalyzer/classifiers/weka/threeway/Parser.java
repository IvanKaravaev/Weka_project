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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Parser {
	 public static void main(String args[]){
		 Document doc;
		 ArrayList<String> RezultatParsinga = new ArrayList<String>();
		 int i=1;
		String addres=new String();
		addres="https://market.yandex.ru/product/14206682/reviews?hid=91491&page="+i;
	for( i=1; i<8;i++)
	{
		addres="https://market.yandex.ru/product/14206682/reviews?hid=91491&page="+i;
		 try {
			 
	            doc = Jsoup.connect(addres).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.137 YaBrowser/17.4.1.758 Yowser/2.5 Safari/537.36") .timeout(10000).get();
	            		
	            		
	            Elements description = doc.getElementsByClass("n-product-review-item__text");
	           // Elements description = doc.select(".n-product-review-item__text");
	            
	            for (Element e: description) {
	                String Otziv = e.getElementsByClass("n-product-review-item__text").text();
	               
	                RezultatParsinga.add(Otziv);
	                
	                
	            }
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 
		 
}	
		 System.out.println(RezultatParsinga);
			System.out.println(RezultatParsinga.size());
			System.out.println(RezultatParsinga.get(0));
			Writer writer = null;
	        try {
	            writer = new FileWriter("file.txt");
	            for (String line : RezultatParsinga) {
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
	 }

}
