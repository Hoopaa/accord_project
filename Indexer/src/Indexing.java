import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import utilities.NGramAnalyzer;
import utilities.ReadFiles;
import org.apache.lucene.analysis.ngram.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Indexing {

    public static void main(String[] args) {
        ReadFiles tFiles = new ReadFiles("../parsing/Lettre parsee", "readThread");
            /*NGramTokenizer tokenizer = new NGramTokenizer(3, 3);
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
        }*/
        NGramAnalyzer tokenizer = new NGramAnalyzer(3, 3);
        TokenStream tokenStream;
        try {
            tokenStream = tokenizer
                    .tokenStream("dummy", new StringReader(tFiles.getSongList().get(0).getAccords()));
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String text = tokenStream.getAttribute(CharTermAttribute.class)
                        .toString();
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}