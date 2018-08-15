package com.vaadin.hibernate.front.kayitOl;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.hibernate.back.service.Kullanicilar;
import com.vaadin.hibernate.back.service.KullanicilarDaoImpl;
import com.vaadin.hibernate.front.anaPencere.AnaPencereMenuBar;
import com.vaadin.hibernate.front.KullaniciGirisPanel;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class KayitOlPanel {
    private Panel panel;
    Binder<Kullanicilar> binder;

    public KayitOlPanel() {
        panel=new Panel();
    }

    public Panel registerPanel (){
        panel.setCaption("Kayıt ol");
        panel.setStyleName(ValoTheme.PANEL_WELL);
        panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        panel.setIcon(VaadinIcons.SIGN_IN);
        panel.setSizeUndefined();
        VerticalLayout layout=new VerticalLayout();
        KayitOlPanelRadioGrubu kayitOlPanelRadioGrubu=new KayitOlPanelRadioGrubu();


        TextField textEmail=new TextField();
        textEmail.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textEmail.setIcon(VaadinIcons.MAILBOX);
        textEmail.setPlaceholder("E-mail");
        textEmail.setWidth("250px");

        PasswordField textSifre=new PasswordField();
        textSifre.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textSifre.setIcon(VaadinIcons.KEY);
        textSifre.setPlaceholder("Şifre");
        textSifre.setWidth("250px");

        TextField textAdSoyad=new TextField();
        textAdSoyad.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textAdSoyad.setIcon(VaadinIcons.USER);
        textAdSoyad.setPlaceholder("Ad Soyad");
        textAdSoyad.setWidth("250px");

        Button buttonKayitOl=new Button("Kayıt Ol");
        buttonKayitOl.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonKayitOl.setSizeFull();
        buttonKayitOl.setIcon(VaadinIcons.ARROW_CIRCLE_RIGHT);
        buttonKayitOl.setWidth("250px");
        buttonKayitOl.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        buttonKayitClick(kayitOlPanelRadioGrubu, textEmail, textSifre, textAdSoyad, buttonKayitOl);

        /* BINDER BASLANGIC--VALIDATION */
        Kullanicilar k=new Kullanicilar();
        binder=new Binder<>();
        binder.setBean(k);

        binder.forField(textAdSoyad).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez!!",1,100
        )).bind(Kullanicilar::getKullaniciAdSoyad,Kullanicilar::setKullaniciAdSoyad);

        binder.forField(textEmail).withValidator(new RegexpValidator(
                "Mail adresi ifadesi yanlış!!","^\\S+@\\S+\\.\\S+$"
        )).bind(Kullanicilar::getKullaniciEmail,Kullanicilar::setKullaniciEmail);

        binder.forField(textSifre).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez!!",1,100
        )).bind(Kullanicilar::getKullaniciSifre,Kullanicilar::setKullaniciSifre);
        /* BINDER BITIS */



        layout.addComponent(textAdSoyad);
        layout.addComponent(textEmail);
        layout.addComponent(textSifre);
        layout.addComponent(kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup());
        layout.addComponent(buttonKayitOl);
        panel.setContent(layout);
        return panel;
    }

    private void buttonKayitClick(KayitOlPanelRadioGrubu kayitOlPanelRadioGrubu, TextField textEmail,
                                         PasswordField textSifre, TextField textAdSoyad, Button buttonKayitOl) {
        buttonKayitOl.addClickListener(clickEvent -> {
            try{
                Kullanicilar kullanicilar = new Kullanicilar();
                kullanicilar.setKullaniciAdSoyad(textAdSoyad.getValue());
                kullanicilar.setKullaniciEmail(textEmail.getValue());
                kullanicilar.setKullaniciIzin(kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup().getValue().getAd());
                kullanicilar.setKullaniciSifre(textSifre.getValue());
                KullanicilarDaoImpl kullanicilarDao = new KullanicilarDaoImpl();

                if(binder.isValid() && !kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup().isEmpty()){
                    kullanicilarDao.create(kullanicilar);
                    AnaPencereMenuBar anaPencereMenuBar=new AnaPencereMenuBar();
                    anaPencereMenuBar.sayfaYonlendir(new KullaniciGirisPanel().girisPanelGetir(),"top:20%;left:40%");
                }
            }catch (NullPointerException e){
                e.printStackTrace();
                textAdSoyad.setComponentError(new UserError("Alanlar boş geçilemez!!"));
                textEmail.setComponentError(new UserError("Alanlar boş geçilemez!!"));
                textSifre.setComponentError(new UserError("Alanlar boş geçilemez!!"));
                kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup().setComponentError(new UserError("Alanlar boş geçilemez!!"));
            }

        });
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }
}



