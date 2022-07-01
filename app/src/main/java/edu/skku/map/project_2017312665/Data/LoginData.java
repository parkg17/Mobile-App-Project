package edu.skku.map.project_2017312665.Data;

public class LoginData {
    private boolean success;
    private String result_detail;

    public LoginData(boolean success, String result_detail) {
        this.success = success;
        this.result_detail = result_detail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult_detail() {
        return result_detail;
    }

    public void setResult_detail(String result_detail) {
        this.result_detail = result_detail;
    }
}
