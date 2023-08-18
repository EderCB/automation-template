package com.moon.tests;

import com.moon.utils.TestListeners;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListeners.class)
public class NewTest extends BaseTest{

    @Test(priority = 1, description = "Buscar la palabra selenium en el buscador")
    public void buscarPalabra() {
        googlePage.setPalabraABuscar("Selenium");
    }
}
