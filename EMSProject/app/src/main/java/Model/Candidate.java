package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Candidate implements Serializable {
    private String id;
    private String name;
    private String gender;
    private String age;
    private String major;
    private String mobile;
    private List<String> listOfTest;
    private Map<String, String> candidateMap;

    public Candidate(String id, String name, String gender, String age, String major, String mobile) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.mobile = mobile;
        this.listOfTest = new ArrayList<>();
        this.candidateMap = new HashMap<>();
    }

    public Map<String, String> getCandidateMap() {
        candidateMap.put("FullName", name);
        candidateMap.put("Gender", gender);
        candidateMap.put("Age", age);
        candidateMap.put("Major", major);
        candidateMap.put("Mobile", mobile);
        return candidateMap;
    }

    public void addTest(String id) {
        listOfTest.add(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
