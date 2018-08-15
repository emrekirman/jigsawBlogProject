package com.vaadin.hibernate.front;


import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.hibernate.back.service.Kullanicilar;
import com.vaadin.hibernate.back.service.KullanicilarDaoImpl;
import com.vaadin.hibernate.front.anaPencere.AnaPencereMenuBar;
import com.vaadin.hibernate.front.anaPencere.AnaSayfaMenuBarIcerik;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class KullaniciGirisPanel {
    private String compenentWidth="250px";
    private Binder<Kullanicilar> binder;

    public Panel girisPanelGetir(){
        Panel panel=new Panel();
        panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        panel.setIcon(VaadinIcons.UNLOCK);
        panel.setCaption("Giriş Yap");
        panel.setSizeUndefined();

        VerticalLayout layout=new VerticalLayout();

        TextField textEmail=new TextField();
        textEmail.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textEmail.setIcon(VaadinIcons.MAILBOX);
        textEmail.setPlaceholder("E-mail");
        textEmail.setWidth(compenentWidth);

        PasswordField textSifre=new PasswordField();
        textSifre.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textSifre.setIcon(VaadinIcons.KEY);
        textSifre.setPlaceholder("Şifre");
        textSifre.setWidth(compenentWidth);

        Button buttonGiris=new Button("Giriş Yap");
        buttonGiris.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        buttonGiris.setIcon(VaadinIcons.ARROW_CIRCLE_RIGHT);
        buttonGiris.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonGiris.setWidth(compenentWidth);
        buttonGiris.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        layout.addComponent(textEmail);
        layout.addComponent(textSifre);
        layout.addComponent(buttonGiris);

        buttonGirisClick(textEmail, textSifre, buttonGiris);

        binder=new Binder<>();
        Kullanicilar k=new Kullanicilar();
        binder.setBean(k);
        binder.forField(textEmail).withValidator(new RegexpValidator(
                "Mail adresi ifadesi yanlış!!","^\\S+@\\S+\\.\\S+$"))
                .bind(Kullanicilar::getKullaniciEmail,Kullanicilar::setKullaniciEmail);
        binder.forField(textSifre).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez!!",1,100
        )).bind(Kullanicilar::getKullaniciSifre,Kullanicilar::setKullaniciEmail);

        panel.setContent(layout);
        return panel;
    }


    private void buttonGirisClick(TextField textEmail, PasswordField textSifre, Button buttonGiris) {
        buttonGiris.addClickListener(clickEvent -> {
            List<Kullanicilar> list;
            KullanicilarDaoImpl kullanicilarDao=new KullanicilarDaoImpl();
            Kullanicilar kullanicilar=new Kullanicilar();
            kullanicilar.setKullaniciEmail(textEmail.getValue());
            kullanicilar.setKullaniciSifre(textSifre.getValue());
            if (binder.isValid()){
                list= kullanicilarDao.getByEmailPassword(kullanicilar);
                if (!list.isEmpty()){
                    OturumYonetimi.oturumBaslat(list);
                    AnaPencereMenuBar anaPencereMenuBar=new AnaPencereMenuBar();
                    anaPencereMenuBar.sayfaYonlendir(new AnaSayfaMenuBarIcerik().anaSayfaIzgara(),"left:30%");
                }
                else {
                    Notification.show("Hatalı Giriş");
                }
            }else {
                textEmail.setComponentError(new UserError("Bu alan boş geçilemez!!"));
                textSifre.setComponentError(new UserError("Bu alan boş geçilemez!!"));
            }

        });
    }
}
