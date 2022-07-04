package edu.skku.map.project_2017312665.Data;

public class UserInfoData implements NetworkData {
    private boolean success;
    private String name;
    private String phone_num;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public UserInfoData(boolean success, String name, String phone_num) {
        this.success = success;
        this.name = name;
        this.phone_num = phone_num;
    }
}
