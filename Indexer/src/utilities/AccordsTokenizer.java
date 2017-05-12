package utilities;


import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class AccordsTokenizer extends Tokenizer {

    protected CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
    protected String stringToTokenize;

    @Override
    public boolean incrementToken() throws IOException {
        return false;
    }
}
