package com.osama.answerwin.Models;

public class Questions_Model {
    private String main_question;
    private String t_answer_1;
    private String f_answer_2;
    private String f_answer_3;
    private String f_answer_4;

    public Questions_Model(String main_question, String t_answer_1, String f_answer_2, String f_answer_3, String f_answer_4) {
        this.main_question = main_question;
        this.t_answer_1 = t_answer_1;
        this.f_answer_2 = f_answer_2;
        this.f_answer_3 = f_answer_3;
        this.f_answer_4 = f_answer_4;
    }

    public Questions_Model() {
    }

    public String getMain_question() {
        return main_question;
    }

    public void setMain_question(String main_question) {
        this.main_question = main_question;
    }

    public String getT_answer_1() {
        return t_answer_1;
    }

    public void setT_answer_1(String t_answer_1) {
        this.t_answer_1 = t_answer_1;
    }

    public String getF_answer_2() {
        return f_answer_2;
    }

    public void setF_answer_2(String f_answer_2) {
        this.f_answer_2 = f_answer_2;
    }

    public String getF_answer_3() {
        return f_answer_3;
    }

    public void setF_answer_3(String f_answer_3) {
        this.f_answer_3 = f_answer_3;
    }

    public String getF_answer_4() {
        return f_answer_4;
    }

    public void setF_answer_4(String f_answer_4) {
        this.f_answer_4 = f_answer_4;
    }
}
