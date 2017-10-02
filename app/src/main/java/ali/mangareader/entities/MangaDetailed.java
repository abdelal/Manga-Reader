
package ali.mangareader.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MangaDetailed {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("author")
    @Expose
    private List<String> author = new ArrayList<String>();
    @SerializedName("artist")
    @Expose
    private List<String> artist = new ArrayList<String>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("yearOfRelease")
    @Expose
    private Integer yearOfRelease;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = new ArrayList<Chapter>();

    public MangaDetailed() {
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href The href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return The author
     */
    public List<String> getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(List<String> author) {
        this.author = author;
    }

    /**
     * @return The artist
     */
    public List<String> getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(List<String> artist) {
        this.artist = artist;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The yearOfRelease
     */
    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    /**
     * @param yearOfRelease The yearOfRelease
     */
    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * @return The info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return The cover
     */
    public String getCover() {
        return cover;
    }

    /**
     * @param cover The cover
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * @return The lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate The lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return The chapters
     */
    public List<Chapter> getChapters() {
        return chapters;
    }

    /**
     * @param chapters The chapters
     */
    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

}
