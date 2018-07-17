package bookfair.android.api.models;

import java.util.List;

import bookfair.android.db.models.UserProfile;

public class LogInResult {

    private boolean success;
    private String error;
    private List<UserProfile> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<UserProfile> getData() {
        return data;
    }

    public void setData(List<UserProfile> data) {
        this.data = data;
    }
}
