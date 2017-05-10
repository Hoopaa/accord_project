package utilities;

import java.io.*;
import java.util.List;

public class ReadFiles implements Runnable{

    private String path;
    private File folder;
    private Thread t;
    private String threadName;

    public ReadFiles(String path, String name) {
        this.path = path;
        threadName = name;
        folder = new File(path);
    }

    @Override
    public void run() {
        for(File f : folder.listFiles()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(this.path+"/"+f.getName()));
                String line;
                while((line = in.readLine()) != null)
                {
                    System.out.println(line);
                }
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
