package dz.univoran.amd.objects;

/**
 * Created by Ikram.
 */

public class Question {

    private String qNumber;
    private String qAnswer;

    public Question(String qNumber, String qAnswer) {
        this.qNumber = qNumber;
        this.qAnswer = qAnswer;
    }

    public String getqNumber() {
        return qNumber;
    }

    public String getqAnswer() {
        return qAnswer;
    }
}
