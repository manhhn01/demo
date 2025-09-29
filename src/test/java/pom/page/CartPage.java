package pom.page;

import org.openqa.selenium.By;
import pom.common.WebUI;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private final By cartItems     = By.cssSelector(".cart_item");
    private final By itemNameInRow = By.cssSelector(".inventory_item_name");
    private final By checkoutBtn   = By.id("checkout");
    private final By continueShop  = By.id("continue-shopping");

    public CartPage() {}

    public List<String> getItemNames() {
        return WebUI.getWebElements(cartItems).stream()
                .map(e -> e.findElement(itemNameInRow).getText())
                .collect(Collectors.toList());
    }

    public CheckoutPage checkout() {
        WebUI.clickElement(checkoutBtn);
        return new CheckoutPage();
    }
}
