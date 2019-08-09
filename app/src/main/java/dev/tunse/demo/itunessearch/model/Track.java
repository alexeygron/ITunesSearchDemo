package dev.tunse.demo.itunessearch.model;

public class Track {

    private String artistName;
    private String trackName;
    private String artworkUrl;
    private String trackPrice;
    private String genre;

    public Track(String artistName, String artworkUrl, String genre, String trackName, String trackPrice) {
        this.artistName = artistName;
        this.artworkUrl = artworkUrl;
        this.genre = genre;
        this.trackName = trackName;
        this.trackPrice = trackPrice;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public String getGenre() {
        return genre;
    }

    public String getTrackName() {
        return trackName;
    }

    @Override
    public String toString() {
        return "Track{" +
                "artistName='" + artistName + '\'' +
                ", trackName='" + trackName + '\'' +
                ", artworkUrl='" + artworkUrl + '\'' +
                ", trackPrice='" + trackPrice + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}

