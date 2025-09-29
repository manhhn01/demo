package pom.page;

import org.openqa.selenium.By;
import pom.common.WebUI;

public class InventoryPage {
    private final By inventoryContainer = By.id("inventory_container");
    private final By cartLink = By.id("shopping_cart_container");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private long startNano;
    private long loadTimeMs;

    public InventoryPage(long startNano) {
      this.startNano = startNano;
    }

    public InventoryPage() {}
    public boolean isLoaded() {
        try {
            WebUI.getWebElement(inventoryContainer);
            return WebUI.getCurrentUrl().contains("inventory.html");
        } catch (Exception e) {
            return false;
        }
    }

    public long getLoadTimeMs() {
      System.out.println("[InventoryPage] debug" + startNano + " ms");
      if(loadTimeMs == 0) {
        WebUI.waitForElementVisible(inventoryContainer);
        long end = System.nanoTime();
        loadTimeMs = (end - startNano) / 1_000_000;
      }

      return loadTimeMs;
    }

    public boolean waitUntilLoaded(int timeout) {
        try {
            WebUI.waitForElementVisible(inventoryContainer, timeout);
            return WebUI.getWebElement(cartLink).isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            long end = System.nanoTime();
            long ms = (end - startNano) / 1_000_000;
            this.loadTimeMs = ms;
            System.out.println("[InventoryPage] Load wait took ~" + ms + " ms");
        }
    }
    public InventoryPage addItemToCart(String productName) {
        String addBtn = String.format(
                "//div[normalize-space()='%s']/ancestor::div[@class='inventory_item']//button[normalize-space()='Add to cart']",
                productName
        );
        WebUI.clickElement(By.xpath(addBtn));
        return this;
    }

    public CartPage goToCart() {
        WebUI.clickElement(cartLink);
        return new CartPage();
    }

    public int getCartBadgeCount() {
        if (!WebUI.checkElementExist(cartBadge)) return 0;
        String txt = WebUI.getWebElement(cartBadge).getText().trim();
        try {
            return Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
