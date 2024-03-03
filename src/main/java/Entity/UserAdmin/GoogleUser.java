package Entity.UserAdmin;

import org.json.JSONException;
import org.json.JSONObject;

public class GoogleUser {
    private String name;
    private String id;
    private boolean verified_email;
    private String given_name;
    private String locale;
    private String family_name;
    private String email;
    private String picture;

    public GoogleUser(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            this.name = jsonObject.getString("name");
            this.id = jsonObject.getString("id");
            this.verified_email = jsonObject.getBoolean("verified_email");
            this.given_name = jsonObject.getString("given_name");
            this.locale = jsonObject.getString("locale");
            this.family_name = jsonObject.getString("family_name");
            this.email = jsonObject.getString("email");
            this.picture = jsonObject.getString("picture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getLocale() {
        return locale;
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }
}
