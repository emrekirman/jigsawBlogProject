package com.vaadin.hibernate.front;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hibernate.front.anaPencere.AnaPencere;
import com.vaadin.hibernate.front.anaPencere.AnaPencereMenuBar;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI implements ErrorHandler {


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        new AnaPencere().getLayout().removeAllComponents();
        setContent(AnaPencere.anaPencereIzgara());

        AnaPencereMenuBar anaPencereMenuBar = new AnaPencereMenuBar();
        if (anaPencereMenuBar.isWindowDurum()) {
            addWindow(anaPencereMenuBar.getWindow());
        }
        getSession().setErrorHandler(this);
        setErrorHandler(this);

    }

    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        Notification.show("Hata!!");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
