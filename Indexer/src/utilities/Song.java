package utilities;

import java.util.ArrayList;
import java.util.Arrays;

public class Song {

    private String artistName;
    private String name;
    private String url;
    private ArrayList<String> accords;

    public Song(String artist, String name, String url, String accords) {
        artistName = artist;
        this.name = name;
        this.url = url;
        this.accords = new ArrayList<>();
        String[] accordsSplit = accords.split(",");
        this.accords.addAll(Arrays.asList(accordsSplit));
    }

    public String getArtistName() {
        return artistName;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getAccords() {
        return accords;
    }

    @Override
    public String toString() {
        return artistName + " : " + name +  " : " + accords;
    }
}
