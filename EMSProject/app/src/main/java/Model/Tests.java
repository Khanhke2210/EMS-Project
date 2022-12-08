package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tests implements Serializable {
    private String id;
    private String name;
    private String company;
    private String quantity;
    private List<Question> listOfQuestion;

    public Tests(String id, String name, String company, String quantity) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.quantity = quantity;
        this.listOfQuestion = new ArrayList<>();
    }

    public void addQuestion(Question quest) {
        listOfQuestion.add(quest);
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public List<Question> getListOfQuestion() {
        return listOfQuestion;
    }

    public void setListOfQuestion(List<Question> listOfQuestion) {
        this.listOfQuestion = listOfQuestion;
    }
}
