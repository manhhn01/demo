package pom.page;

import org.openqa.selenium.By;
import pom.common.WebUI;
public class LoginPage {
    private By username = By.xpath("//input[@id='user-name']");
    private By password = By.xpath("//input[@id='password']");
    private By btnLogin = By.xpath("//input[@id='login-button']");
    private final By txtErrorMsg = By.xpath("//h3[@data-test='error']");
    public LoginPage() {}

    public LoginPage enterUsername(String user) {
        WebUI.setText(username, user);
        return this;
    }

    public LoginPage enterPassword(String pass) {
        WebUI.setText(password, pass);
        return this;
    }

    public void submit() {
        WebUI.clickElement(btnLogin);
    }

    public InventoryPage loginAs(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        submit();
        long startNano = System.nanoTime();
        return new InventoryPage(startNano);
    }

    public boolean isErrorVisible() {
        return WebUI.checkElementExist(txtErrorMsg);
    }

    public String getErrorText() {
        return WebUI.getElementText(txtErrorMsg);
    }
}
