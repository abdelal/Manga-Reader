
package ali.mangareader.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Manga {

    @SerializedName("mangaId")
    @Expose
    private String mangaId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cover")
    @Expose
    private String cover;

    /**
     * @return The mangaId
     */
    public String getMangaId() {
        return mangaId;
    }

    /**
     * @param mangaId The mangaId
     */
    public void setMangaId(String mangaId) {
        this.mangaId = mangaId;
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

}
