package info.semanticanalyzer.classifiers.weka.threeway;


import info.semanticanalyzer.classifiers.weka.SentimentClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class KaggleCSVReaderThreeWay {

    private String line;
    private String lineMain;
    private CSVInstanceThreeWay csvInstanceThreeWay;
    private CSVInstanceThreeWayKlassi csvInstanceThreeWayKlassi;
    private int step = 0;
    private int stepMain = 0;

    private BufferedReader br;
    private BufferedReader brMain;

    private int showStatsAt = 1000;

    void readKaggleCSV(String csvFile) throws IOException {
        br = new BufferedReader(new FileReader(csvFile));

        line = br.readLine();

        if (line != null) {
            if (line.startsWith("PhraseId")) {
                line = br.readLine();
            }

            if (line != null) {
                extractInstance();
            }
        }
    }
    
    void readKaggleCSVMain(String csvFile) throws IOException {
        brMain = new BufferedReader(new FileReader(csvFile));

        lineMain = brMain.readLine();

        if (lineMain != null) {
            if (lineMain.startsWith("PhraseId")) {
                lineMain = brMain.readLine();
            }

            if (lineMain != null) {
                extractInstanceMain();
            }
        }
    }

    private void extractInstance() {
        String[] attrs = line.split("\t");

        if (csvInstanceThreeWay == null) {
            csvInstanceThreeWay = new CSVInstanceThreeWay();
        }
        csvInstanceThreeWay.phraseID = Integer.valueOf(attrs[0]);
        csvInstanceThreeWay.sentenceID = Integer.valueOf(attrs[1]);
        csvInstanceThreeWay.phrase = attrs[2];
        // there is additionally sentiment tag for training data
        if (attrs.length > 3) {
            Integer sentimentOrdinal = Integer.valueOf(attrs[3]);

            if (sentimentOrdinal <= 2) {
                csvInstanceThreeWay.sentiment = SentimentClass.ThreeWayClazz.values()[sentimentOrdinal];
                csvInstanceThreeWay.isValidInstance = true;
            } else {
                // can't process the instance, because the sentiment ordinal is out of the acceptable range of two classes
                csvInstanceThreeWay.isValidInstance = false;
            }
        }
    }
    
    
    
    private void extractInstanceMain() {
        String[] attrsMain = lineMain.split("\t");

        if (csvInstanceThreeWayKlassi == null) {
            csvInstanceThreeWayKlassi = new CSVInstanceThreeWayKlassi();
        }
        csvInstanceThreeWayKlassi.phraseID = Integer.valueOf(attrsMain[0]);
        csvInstanceThreeWayKlassi.sentenceID = Integer.valueOf(attrsMain[1]);
        csvInstanceThreeWayKlassi.phrase = attrsMain[2];
        // there is additionally sentiment tag for training data
        
    }

    CSVInstanceThreeWay next() {
        if (step == 0) {
            step++;
            return csvInstanceThreeWay;
        }

        if (step % showStatsAt == 0) {
            System.out.println("Processed instances: " + step);
        }

        try {
            line = br.readLine();
            if (line != null) {
                extractInstance();
            } else {
                return null;
            }
            step++;
            return csvInstanceThreeWay;
        } catch (IOException e) {
            return null;
        }
    }
    
    //метод next для работы с классом для классификации
   CSVInstanceThreeWayKlassi nextMain() {
        if (stepMain == 0) {
        	stepMain++;
            return csvInstanceThreeWayKlassi;
        }

        if (stepMain % showStatsAt == 0) {
            System.out.println("Processed instances: " + stepMain);
        }

        try {
        	lineMain = brMain.readLine();
            if (lineMain != null) {
            	extractInstanceMain();
            } else {
                return null;
            }
            stepMain++;
            return csvInstanceThreeWayKlassi;
        } catch (IOException e) {
            return null;
        }
    }

    void close() {
        try {
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void closeKlassi() {
        try {
           
            brMain.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class CSVInstanceThreeWay {
        int phraseID;
        int sentenceID;
        String phrase;
        SentimentClass.ThreeWayClazz sentiment;
        boolean isValidInstance;

        @Override
        public String toString() {
            return "CSVInstanceThreeWay{" +
                    "phraseID=" + phraseID +
                    ", sentenceID=" + sentenceID +
                    ", phrase='" + phrase + '\'' +
                    ", sentiment=" + sentiment +
                    '}';
        }
    }
    
    //Класс для классификации
    class CSVInstanceThreeWayKlassi {
        int phraseID;
        int sentenceID;
        String phrase;
       boolean isValidInstance;

        @Override
        public String toString() {
            return "CSVInstanceThreeWay{" +
                    "phraseID=" + phraseID +
                    ", sentenceID=" + sentenceID +
                    ", phrase='" + phrase + '\'' +
                    '}';
        }
    }

}
