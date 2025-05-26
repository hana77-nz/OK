package models;

public abstract class AccountInfo {
    public String email;
    public String password;

    public abstract boolean isUser();
    public abstract boolean isStore();



    public String getEmail() {
        return this.email;
    }

    public boolean setEmail(String email) {
        if(!User.validationOfEmail(email)) {
            return false;
        }
        this.email = email;

        return true;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean setPassword(String password) {
        if(!User.validationOfPassword(password)) {
            return false;
        }
        this.password = password;
        return true;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


}
