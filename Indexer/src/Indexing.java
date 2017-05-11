import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import utilities.ReadFiles;
import org.apache.lucene.analysis.ngram.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import utilities.Song;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

public class Indexing {

    public static void main(String[] args) {
        try {
            ReadFiles tFiles = new ReadFiles("../parsing/Lettre parsee", "readThread");
            NGramTokenizer tokenizer = new NGramTokenizer(2, 3);
            StringReader test = new StringReader(tFiles.getSongList().get(0).getAccords());
            tokenizer.setReader(test);
            tokenizer.reset();
            CharTermAttribute termAtt = tokenizer.getAttribute(CharTermAttribute.class);
            while (tokenizer.incrementToken()) {
                String token = termAtt.toString();
                System.out.println(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
