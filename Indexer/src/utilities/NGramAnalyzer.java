package utilities;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;

public class NGramAnalyzer extends Analyzer {

    private int min;
    private int max;

    public NGramAnalyzer(int min, int max) {
        this.min = min;
        this.max = max;
    }
    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer standardTokenizer = new WhitespaceTokenizer();
        TokenStream tok = new ShingleFilter(standardTokenizer, 3, 3);
        return new TokenStreamComponents(standardTokenizer, tok);
    }
}
