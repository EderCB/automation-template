package com.moon.pages;

import org.openqa.selenium.By;

public class GooglePage extends BasePage{
    By buscador = By.name("q");
    public void setPalabraABuscar(String palabra) {
        find(buscador).click();
        find(buscador).clear();
        find(buscador).sendKeys(palabra);
    }
}
