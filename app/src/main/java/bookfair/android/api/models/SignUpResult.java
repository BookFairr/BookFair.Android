package bookfair.android.api.models;

import java.util.List;

public class SignUpResult {

    private boolean status;
    private List<SignUp> data;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<SignUp> getData() {
        return data;
    }

    public void setData(List<SignUp> data) {
        this.data = data;
    }
}
