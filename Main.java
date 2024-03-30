import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        String url = "http://59.52.99.99:800/";//链接要以/结尾
        String user = "19338754";//账号（手机号）
        String password = "12345";//密码
        String mac = "4ce59c7c9d4f3";//物理地址（可不填）
        String acIp = "";//交流IP可不填

        String sb = url + "eportal/portal/login?" +
                "callback=liejiu&login_method=1&user_account=" +
                user +
                "%40telecom&user_password=" +
                password +
                "&wlan_user_ip=" +
                getIp() +
                "&wlan_user_ipv6=&wlan_user_mac=" +
                mac +
                "&wlan_ac_ip=" +
                acIp +
                "&wlan_ac_name=&jsVersion=4.2.1&terminal_type=1&lang=zh-cn&v=&lang=zh";
        String getStr = doGet(sb);
        System.out.println(getStr);
    }


    public static String getIp() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }

    public static String doGet(String pathUrl){
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(pathUrl);

            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设定请求的方法为"GET"，默认是GET
            //post与get的不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setRequestMethod("GET");

            //设置30秒连接超时
            conn.setConnectTimeout(30000);
            //设置30秒读取超时
            conn.setReadTimeout(30000);

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(false);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);

            // Post请求不能使用缓存(get可以不使用)
            conn.setUseCaches(false);

            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");  //维持长链接
            conn.setRequestProperty("Content-Type", "application/javascript; charset=utf-8");

            //连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
            conn.connect();

            /*
              下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null){
                result.append(str);
            }
            System.out.println(result);
            //关闭流
            is.close();
            //断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
