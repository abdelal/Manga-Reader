
package ali.mangareader.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("pageId")
    @Expose
    private Integer pageId;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * 
     * @return
     *     The pageId
     */
    public Integer getPageId() {
        return pageId;
    }

    /**
     * 
     * @param pageId
     *     The pageId
     */
    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
