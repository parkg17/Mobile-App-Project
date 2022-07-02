package edu.skku.map.project_2017312665.Data;

public class UserLoginNetworkData implements NetworkData {
    private String id;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return password;
    }

    public void setPw(String password) {
        this.password = password;
    }
}
