
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GoogleTranslateToken {

    String[] tkk;

    public boolean Token() {
        return getTKK();
    }

    private boolean getTKK() {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("https://translate.google.cn/");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestProperty("User-Agent","translator");
            int responseCode = c.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                int index = response.indexOf("tkk:'") + 5;
                tkk = response.substring(index, response.indexOf("'", index)).split("\\.");
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getToken(String str) {

        if (tkk == null && !getTKK()) return null;

        long b = Long.parseLong(tkk[0]);

        ArrayList<Long> e = new ArrayList<>();

        for (int g = 0; g < str.length(); g++) {
            long k = str.charAt(g);
            if (k < 128) {
                e.add(k);
            } else {
                if (k < 2048) {
                    e.add(k >> 6 | 192);
                } else {
                    if (55296 == (k & 64512) && g + 1 < str.length() && 56320 == (str.charAt(g + 1) & 64512)) {
                        k = 65536 + ((k & 1023) << 10) + (str.charAt(++g) & 1023);
                        e.add(k >> 18 | 240);
                        e.add(k >> 12 & 63 | 128);
                    } else {
                        e.add(k >> 12 | 224);
                        e.add(k >> 6 & 63 | 128);
                    }
                }
                e.add(k & 63 | 128);
            }
        }
        int a = (int)b;
        for (int f = 0; f < e.size(); f++) {
            a += e.get(f);
            a = go(a, "+-a^+6");
        }
        a = go(a, "+-3^+b+-f");
        a ^= (int)Long.parseLong(tkk[1]);
        if (0 > a) {
            long aL = a & Integer.MAX_VALUE + 0x80000000L;
            aL %= 1e6;
            return aL + "." + (aL ^ b);
        }
        a %= 1000000;

        return a + "." + (a ^ b);
    }

    private int go(long in, String b) {
        int out = (int) in;
        for (int c = 0; c < b.length() - 2; c += 3) {
            int d = b.charAt(c + 2);
            d = 'a' <= d ? d - 87 : Integer.parseInt(String.valueOf((char) d));
            d = '+' == b.charAt(c + 1) ? out >>> d : out << d;
            out = '+' == b.charAt(c) ? out + d : out ^ d;
        }
        return out;
    }
}