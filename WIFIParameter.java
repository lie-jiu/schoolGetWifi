package SchoolWIFI;

public class WIFIParameter {
    private static String  url;
    private String user;
    private String password;

    private String log;

    public WIFIParameter() {
    }


    public WIFIParameter(String url, String user, String password) {
        setUrl(url);
        setUser(user);
        this.password = password;
    }

    public WIFIParameter(String user, String password) {
        setUser(user);
        this.password = password;
    }

    /**
     * 获取
     * @return url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * 设置
     */
    public static void setUrl(String url) {
        if(url.endsWith("/")) {
            WIFIParameter.url = url;
        }else {
            WIFIParameter.url = url+"/";
        }
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
     */
    public void setUser(String user) {
        if(user.length()==11) {
            this.user = user;
        }else{
            System.out.println("账号格式错误");
        }
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
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return log
     */
    public String getLog() {
        return log;
    }

    /**
     * 设置
     */
    public void setLog(String log) {
        this.log = log;
    }

    public String toString() {
        return "WIFIParameter{url = " + url + ", user = " + user + ", password = " + password + ", log = " + log + "}";
    }
}
