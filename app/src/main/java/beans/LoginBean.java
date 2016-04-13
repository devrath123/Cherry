package beans;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 3/3/2016.
 */
public class LoginBean {

    boolean status;
    String message;
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

   public static class Data{

       public UserObj User;

       public UserObj getUser() {
           return User;
       }

       public void setUser(UserObj user) {
           User = user;
       }

       public static class UserObj{
           String id,email,name,password,designation,profile_image_path;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getDesignation() {
                return designation;
            }

            public void setDesignation(String designation) {
                this.designation = designation;
            }

            public String getProfile_image_path() {
                return profile_image_path;
            }

            public void setProfile_image_path(String profile_image_path) {
                this.profile_image_path = profile_image_path;
            }
        }



        public ArrayList<UserBranchObj> getUserBranch() {
            return UserBranch;
        }

        public void setUserBranch(ArrayList<UserBranchObj> userBranch) {
            UserBranch = userBranch;
        }

        public ArrayList<UserBranchObj> UserBranch;

        public static class UserBranchObj {
            String id, name, location, state,created_at,created_by,updated_by,updated_at,status;

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getCreated_by() {
                return created_by;
            }

            public void setCreated_by(String created_by) {
                this.created_by = created_by;
            }

            public String getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(String updated_by) {
                this.updated_by = updated_by;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
