package pom.page;

import org.openqa.selenium.By;
import pom.common.WebUI;

public class CheckoutPage {

    private final By firstName   = By.id("first-name");
    private final By lastName    = By.id("last-name");
    private final By postalCode  = By.id("postal-code");
    private final By continueBtn = By.id("continue");
    private final By finishBtn         = By.id("finish");
    private final By completeHeader    = By.cssSelector(".complete-header");

    public CheckoutPage() {}

    public CheckoutPage fillFirstName(String v) {
        WebUI.setText(firstName, v);
        return this;
    }

    public CheckoutPage fillLastName(String v) {
        WebUI.setText(lastName, v);
        return this;
    }

    public CheckoutPage fillPostalCode(String v) {
        WebUI.setText(postalCode, v);
        return this;
    }

    public CheckoutPage continueCheckout() {
        WebUI.clickElement(continueBtn);
        return this;
    }

    public CheckoutPage finish() {
        WebUI.clickElement(finishBtn);
        return this;
    }

    public boolean isOrderComplete() {
        boolean headerOk = WebUI.checkElementExist(completeHeader);
        boolean urlOk = WebUI.getCurrentUrl().contains("checkout-complete.html");
        return headerOk && urlOk;
    }

}
