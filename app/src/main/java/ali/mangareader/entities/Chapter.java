
package ali.mangareader.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {

    @SerializedName("chapterId")
    @Expose
    private String chapterId;
    @SerializedName("name")
    @Expose
    private String name;
    private String manganame;

    /**
     * 
     * @return
     *     The chapterId
     */
    public String getChapterId() {
        return chapterId;
    }

    /**
     * 
     * @param chapterId
     *     The chapterId
     */
    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setManganame(String manga){
        manganame=manga;
    }
    public String MangaName(){
        return manganame;
    }
}
