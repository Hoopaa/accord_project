import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import utilities.AccordAnalyzer;
import utilities.ReadFiles;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Indexing {

    public static void main(String[] args) {
        try {
            ReadFiles tFiles = new ReadFiles("../parsing/Lettre parsee", "readThread");
            AccordAnalyzer tokenizer = new AccordAnalyzer(2, 8);
            IndexWriterConfig iwc = new IndexWriterConfig(tokenizer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            iwc.setUseCompoundFile(false);
            Path path = FileSystems.getDefault().getPath("index_standard");
            Directory dir = FSDirectory.open(path);

            IndexWriter indexWriter = new IndexWriter(dir, iwc);

            TokenStream tokenStream;
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