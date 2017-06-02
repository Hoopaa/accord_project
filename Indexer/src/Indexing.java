import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
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
        if(args.length < 1) {
            System.out.println("Wrong arguments numbers");
            System.out.println("Usage : <indexing(0) / search(1)> <request>");
            return;
        }
        boolean indexing;
        switch(args[0]) {
            case "0":
                indexing = true;
                break;
            default :
                indexing = false;
                break;
        }
        try {
            Path path = FileSystems.getDefault().getPath("../index_standard");
            Directory dir = FSDirectory.open(path);
            if(indexing) {
                ReadFiles tFiles;
                AccordAnalyzer tokenizer = new AccordAnalyzer(2, 8);
                IndexWriterConfig iwc = new IndexWriterConfig(tokenizer);
                if(args[1].equals("full_indexing")) {
                    tFiles = new ReadFiles("../../parsing/Lettre_parsee");
                    iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                    iwc.setUseCompoundFile(false);
                } else {
                    tFiles = new ReadFiles("../../parsing/Lettre_parsee", args[1]);
                    iwc.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
                    iwc.setUseCompoundFile(false);
                }
                IndexWriter indexWriter = new IndexWriter(dir, iwc);

                TokenStream tokenStream;
                for (Song s : tFiles.getSongList()) {
                    tokenStream = tokenizer
                            .tokenStream("song", new StringReader(s.getAccords()));
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
                        if (text.length() > 0) {
                            Field accords = new StringField("accord", text, Field.Store.YES);
                            d.add(accords);
                        }
                    }
                    tokenStream.reset();
                    indexWriter.addDocument(d);
                }
                indexWriter.close();
            } else {
                IndexReader reader = DirectoryReader.open(dir);
                IndexSearcher is = new IndexSearcher(reader);
                Query query = new TermQuery(new Term("accord", args[1]));
                ScoreDoc[] hits = is.search(query, Integer.MAX_VALUE).scoreDocs;
                System.out.println(hits.length);
                for (ScoreDoc hit : hits) {
                    Document doc = is.doc(hit.doc);
                    System.out.println(doc.get("name") + "^_^" + doc.get("author") + "^_^" + doc.get("url"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}