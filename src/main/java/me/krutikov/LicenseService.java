package me.krutikov;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class LicenseService {


    public boolean getLicenseStatus() {
        return licenseStatus;
    }

    private boolean licenseStatus;

    public String getIp() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com/").openStream()))) {
            return br.readLine();
        } catch (Throwable e) {
            return null;
        }
    }
    public boolean isLicense(String projectName, String ip) {
        try{
            String url = "https://api.krutikov.space/license/check?project="+projectName + "&ip=" + ip;
            URLConnection openConnection = new URL(url).openConnection();
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Scanner scan = new Scanner((new InputStreamReader(openConnection.getInputStream())));
            String licenseJson = scan.nextLine();
            JsonObject jsonObj = (JsonObject) JsonParser.parseString(licenseJson);
            licenseStatus = jsonObj.get("license").getAsBoolean();
            return licenseStatus;
        }catch(Exception ignored) {
            return false;
        }
    }
}