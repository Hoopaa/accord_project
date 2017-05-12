package utilities;


public class Song {

    private String artistName;
    private String name;
    private String url;
    private String accords;

    public Song(String artist, String name, String url, String accords) {
        artistName = artist;
        this.name = name;
        this.url = url;
        this.accords = accords.replace(","," ");
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

    public String getAccords() {
        return accords;
    }

    @Override
    public String toString() {
        return artistName + " : " + name +  " : " + accords;
    }
}
