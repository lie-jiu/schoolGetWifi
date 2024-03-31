package SchoolWIFI;

public class WIFIParameter {
    private String url;
    private String user;
    private String password;

    public WIFIParameter() {
    }

    public WIFIParameter(String url, String user, String password) {
        setUrl(url);
        this.user = user;
        this.password = password;
    }

    /**
     * 获取
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置
     * @param url
     */
    public void setUrl(String url) {
        if(!url.endsWith("/")){
            url=url+"/";
        }
        this.url = url;
    }

    /**
     * 获取
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "wifi{url = " + url + ", user = " + user + ", password = " + password + "}";
    }
}
