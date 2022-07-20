package edu.skku.map.project_2017312665.Data;

public class UserInfoData implements NetworkData {
    private final boolean success;
    private final String name;
    private final String phone_num;

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public UserInfoData(boolean success, String name, String phone_num) {
        this.success = success;
        this.name = name;
        this.phone_num = phone_num;
    }
}
