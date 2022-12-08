package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Company implements Serializable {
    private String name;
    private String address;
    private String major;
    private String mobile;
    private Map<String, String> companyMap;

    public Company(String name, String address, String major, String mobile) {
        this.name = name;
        this.address = address;
        this.major = major;
        this.mobile = mobile;
        this.companyMap = new HashMap<>();
    }

    public Map<String, String> getCompanyMap() {
        companyMap.put("Name", name);
        companyMap.put("Address", address);
        companyMap.put("Major", major);
        companyMap.put("Mobile", mobile);
        return companyMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
