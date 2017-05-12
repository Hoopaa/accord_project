package utilities;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 * Created by luleu on 12.05.2017.
 */
public class AccordsFilter extends TokenFilter {
    private CharTermAttribute charTermAttr;
    protected AccordsFilter(TokenStream input) {
        super(input);
        this.charTermAttr = addAttribute(CharTermAttribute.class);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if(!input.incrementToken()) {
            return false;
        }
        int length = charTermAttr.length();
        char[] buffer = charTermAttr.buffer();
        char[] newBuffer = new char[length];
        for (int i = 0; i < length; i++) {
            newBuffer[i] = buffer[length - 1 - i];
        }
        charTermAttr.setEmpty();
        charTermAttr.copyBuffer(newBuffer, 0, newBuffer.length);
        return true;

    }
}
