package utilities;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;

public class AccordAnalyzer extends Analyzer {

    private int min;
    private int max;

    public AccordAnalyzer(int min, int max) {
        this.min = min;
        this.max = max;
    }
    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer standardTokenizer = new WhitespaceTokenizer();
        TokenStream tok = new ShingleFilter(standardTokenizer, min, max);
        return new TokenStreamComponents(standardTokenizer, tok);
    }
}
