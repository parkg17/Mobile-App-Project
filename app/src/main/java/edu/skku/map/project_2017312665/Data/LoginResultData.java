package edu.skku.map.project_2017312665.Data;

public class LoginResultData {
    private final boolean success;
    private final String result_detail;

    public LoginResultData(boolean success, String result_detail) {
        this.success = success;
        this.result_detail = result_detail;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResult_detail() {
        return result_detail;
    }

}
