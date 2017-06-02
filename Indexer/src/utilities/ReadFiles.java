package utilities;

import java.io.*;
import java.util.ArrayList;

public class ReadFiles{

    private String path;
    private File folder;
    private ArrayList<Song> songList;
    private File[] listFiles;
    public boolean failed = false;
    private String filename = "";

    public ReadFiles(String path, String filename) {
        this.path = path;
        this.filename = filename;
        listFiles = new File[1];
        listFiles[0] = new File(path + "/" + filename);
        songList = new ArrayList<>();
        run();
    }

    public ReadFiles(String path) {
        this.path = path;
        folder = new File(path);
        listFiles = folder.listFiles();
        songList = new ArrayList<>();
        run();
    }

    private void run() {
        for(File f : listFiles) {
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
                if(filename == f.getName()) {
                    f.delete();
                }
            } catch (FileNotFoundException e) {
                failed = true;
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Song> getSongList() {
        return this.songList;
    }
}
