package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles implements Runnable{

    private String path;
    private File folder;
    private Thread t;
    private String threadName;
    private ArrayList<Song> songList;

    public ReadFiles(String path, String name) {
        this.path = path;
        threadName = name;
        folder = new File(path);
        songList = new ArrayList<>();
    }

    @Override
    public void run() {
        for(File f : folder.listFiles()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(this.path+"/"+f.getName()));
                String line;
                while((line = in.readLine()) != null)
                {
                    String[] songs = line.split("\\[");
                    String[] songDetails = songs[0].split(",");
                    songList.add(new Song(songDetails[0], songDetails[1], songDetails[2], songs[1].substring(0, songs[1].length()-1)));
                }
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Song s : songList) {
            System.out.println(s);
        }
    }

    public void start() {
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
