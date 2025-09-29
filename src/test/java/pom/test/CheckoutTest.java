package pom.test;

import org.testng.annotations.Test;
import pom.common.BaseSetup;
import pom.page.CartPage;
import pom.page.CheckoutPage;
import pom.page.InventoryPage;
import pom.page.LoginPage;
import org.testng.Assert;

public class CheckoutTest extends BaseSetup {
    @Test(description = "Happy path: add 2 items then checkout successfully")
    public void testCheckoutFlow() {
        LoginPage login = new LoginPage();
        InventoryPage inventory = login.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(inventory.isLoaded(), "Inventory page should load after login");

        inventory.addItemToCart("Sauce Labs Backpack")
                .addItemToCart("Sauce Labs Bike Light");

        Assert.assertEquals(inventory.getCartBadgeCount(), 2, "Cart badge should show 2 items");

        CartPage cart = inventory.goToCart();
        Assert.assertTrue(cart.getItemNames().contains("Sauce Labs Backpack"));
        Assert.assertTrue(cart.getItemNames().contains("Sauce Labs Bike Light"));

        CheckoutPage checkout = cart.checkout();
        checkout.fillFirstName("John")
                .fillLastName("Doe")
                .fillPostalCode("12345")
                .continueCheckout()
                .finish();

        Assert.assertTrue(checkout.isOrderComplete(),
                "Expected checkout-complete page with thank-you header");
    }
}
