package utilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;


public class AccordFilter extends TokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected AccordFilter(TokenStream in) {
        super(in);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if(!input.incrementToken()) {
            return false;
        }
        int occurance = StringUtils.countMatches(termAtt, " ");
        if(occurance == 0) {
            termAtt.setEmpty();
            incrementToken();
        }
        return true;
    }
}
