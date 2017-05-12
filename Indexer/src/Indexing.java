import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import utilities.AccordAnalyzer;
import utilities.ReadFiles;
import utilities.Song;

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
            for(Song s : tFiles.getSongList()) {
                tokenStream = tokenizer
                        .tokenStream("dummy", new StringReader(s.getAccords()));
                tokenStream.reset();
                Document d = new Document();
                Field name = new StringField("name", s.getName(), Field.Store.YES);
                Field author = new StringField("author", s.getArtistName(), Field.Store.YES);
                Field url = new StringField("url", s.getUrl(), Field.Store.YES);
                d.add(name);
                d.add(author);
                d.add(url);
                while (tokenStream.incrementToken()) {
                    String text = tokenStream.getAttribute(CharTermAttribute.class)
                            .toString();
                    Field accords = new StringField("accord", text, Field.Store.YES);
                    d.add(accords);
                }
                tokenStream.reset();
                indexWriter.addDocument(d);
            }
            indexWriter.close();
            IndexReader reader = DirectoryReader.open(dir);
            /*for(IndexableField i :reader.document(0).getFields("accord")) {
                System.out.println(i.stringValue());
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}