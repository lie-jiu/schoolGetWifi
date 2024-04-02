package SchoolWIFI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean log = true;//日志开关
        long[] times = {0,0,0};//分别是天，小时，分钟。都为0则不注销

        WIFIParameter.setUrl("http://59.52.20.94:801/");//登陆网址

        WIFIParameter[] para = new WIFIParameter[1];//有几个账号就写多少数字
        //有几个账号就new几个变量
        para[0]= new WIFIParameter("19314674105","197355");

        if (!WIFIParameter.getUrl().startsWith("http://") && WIFIParameter.getUrl().isEmpty()) {
            System.out.println("网址错误");
            return;
        }

        for (WIFIParameter parameter : para) {

            InetAddress addr = InetAddress.getLocalHost();
            StringBuilder url = new StringBuilder(WIFIParameter.getUrl());
            url.append("eportal/portal/login?callback=liejiu&login_method=1&user_account=");
            url.append(parameter.getUser());
            url.append("%40telecom&user_password=");
            url.append(parameter.getPassword());
            url.append("&wlan_user_ip=");
            url.append(addr.getHostAddress());
            String rt = doGet(url.toString());
            rt = getSubString(rt, "liejiu(", ");");
            parameter.setLog(rt);
            if(log){
                System.out.println(parameter.getLog());
            }
            if (rt.contains("已经在线")||rt.contains("成功")) {
                System.out.println("登陆成功");
                signOut(times);
            }else {
                System.out.println("登陆失败");
            }

        }
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
            conn.setUseCaches(true);

            //设置通用的请求属性
            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
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

    public static void signOut(long[] time) throws InterruptedException{
        if(time[0]+time[1]+time[2]>0) {
            TimeUnit.DAYS.sleep(time[0]);
            TimeUnit.HOURS.sleep(time[1]);
            TimeUnit.MINUTES.sleep(time[2]);

            String str = doGet(WIFIParameter.getUrl() + "eportal/portal/logout");
            System.out.println(getSubString(str, "jsonpReturn(", ");"));
        }
        System.exit(0);
    }

    public static String getSubString(String text, String left, String right) {//取中间
        String result;
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if ((yLen < 0) || (right == null) || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }


}
