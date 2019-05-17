package applicationFiles.app_manager.model_data;

public class SignupPageData {

    //Company block
    private String companyName;
    //Administrator account details block
    private String adminEmail;
    private String adminName;
    private String adminPhone;

    public String getCompanyName() {
        return companyName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public SignupPageData setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public SignupPageData setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
        return this;
    }

    public SignupPageData setAdminName(String adminName) {
        this.adminName = adminName;
        return this;
    }

    public SignupPageData setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
        return this;
    }
}
