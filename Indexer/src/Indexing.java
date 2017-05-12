import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import utilities.AccordAnalyzer;
import utilities.ReadFiles;

import java.io.IOException;
import java.io.StringReader;

public class Indexing {

    public static void main(String[] args) {
        ReadFiles tFiles = new ReadFiles("../parsing/Lettre parsee", "readThread");
        AccordAnalyzer tokenizer = new AccordAnalyzer(2, 8);
        TokenStream tokenStream;
        try {
            tokenStream = tokenizer
                    .tokenStream("dummy", new StringReader(tFiles.getSongList().get(0).getAccords()));
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String text = tokenStream.getAttribute(CharTermAttribute.class)
                        .toString();
                int occurance = StringUtils.countMatches(text, " ");
                if(occurance > 0)
                    System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}