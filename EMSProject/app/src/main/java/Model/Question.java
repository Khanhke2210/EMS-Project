package Model;

import java.io.Serializable;

public class Question implements Serializable {
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String questContent;
    private String type;
    private String selected;
    private double point;

    public Question(String optionA, String optionB, String optionC, String optionD, String answer, String questContent, String type) {
        this.answer = answer;
        this.questContent = questContent;
        this.type = type;
        this.selected = "";
        this.point = 10;

        if (type.equalsIgnoreCase("M")){
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
        } else {
            this.optionA = "";
            this.optionB = "";
            this.optionC = "";
            this.optionD = "";
        }
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestContent() {
        return questContent;
    }

    public void setQuestContent(String questContent) {
        this.questContent = questContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
