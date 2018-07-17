package bookfair.android.api.models;

import java.util.List;

import bookfair.android.db.models.UserProfile;

public class LogInResult {

    private boolean status;
    private List<UserProfile> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<UserProfile> getData() {
        return data;
    }

    public void setData(List<UserProfile> data) {
        this.data = data;
    }
}
