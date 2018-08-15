package com.vaadin.hibernate.front.anaPencere;

import com.vaadin.hibernate.front.KullaniciGirisPanel;
import com.vaadin.hibernate.front.OturumYonetimi;
import com.vaadin.hibernate.front.YaziEklePanel;
import com.vaadin.hibernate.front.yaziListesi.YaziListesiGrid;
import com.vaadin.hibernate.front.kayitOl.KayitOlPanel;
import com.vaadin.hibernate.front.kullaniciListesi.KullaniciListesiGrid;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Emre Kirman
 * @since 1.0
 */
public class AnaPencereMenuBar {
    private MenuBar menuBar;
    private Label label;
    private Window window;
    private boolean windowDurum = false;

    void labelOturumYaz() {
        if (OturumYonetimi.KullaniciAdSoyad != null) {
            label = new Label();
            Button buttonCikis = new Button("Çıkış Yap");

            buttonCikis.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
            buttonCikis.setIcon(VaadinIcons.SIGN_OUT);
            buttonCikis.setStyleName(ValoTheme.BUTTON_PRIMARY);

            label.setValue("Kullanıcı: " + OturumYonetimi.KullaniciAdSoyad);
            AnaPencere.layout.addComponent(label, "right:5px;top:0.5%");
            AnaPencere.layout.addComponent(buttonCikis, "right:0;top:5%");

            buttonCikis.addClickListener(event -> {
                OturumYonetimi.oturumSonlandir();
                AnaPencere.layout.removeAllComponents();
                sayfaYonlendir(new KullaniciGirisPanel().girisPanelGetir(), "top:20%;left:40%");
            });

        }

    }

    public void sayfaYonlendir(Component gidilecekSayfa, String cssPosizyon) {
        AnaPencere anaPencere = new AnaPencere();
        anaPencere.getLayout().removeAllComponents();
        AnaPencereMenuBar anaPencereMenuBar = new AnaPencereMenuBar();
        anaPencere.getLayout().addComponent(anaPencereMenuBar.AnaPenceremenuBarGetir());
        anaPencere.getLayout().addComponent(gidilecekSayfa, cssPosizyon);
        labelOturumYaz();
    }


    MenuBar AnaPenceremenuBarGetir() {//AnaPencereMenuBar View
        menuBar = new MenuBar();
        menuBar.setWidth("100%");
        menuBar.setAutoOpen(true);
        MenuBar.Command command = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                switch (selectedItem.getText()) {
                    case ("Anasayfa"):
                        sayfaYonlendir(new AnaSayfaMenuBarIcerik().anaSayfaIzgara(), "left:30%");
                        break;
                    case ("Kayıt Ol"):
                        KayitOlPanel kayitOlPanel = new KayitOlPanel();
                        sayfaYonlendir(kayitOlPanel.registerPanel(), "top:20%;left:40%");
                        break;
                    case ("Giriş Yap"):
                        KullaniciGirisPanel kullaniciGirisPanel = new KullaniciGirisPanel();
                        sayfaYonlendir(kullaniciGirisPanel.girisPanelGetir(), "top:20%;left:40%");
                        break;
                    case ("Kullanıcı Listesi"):
                        try {

                            if (OturumYonetimi.kullaniciIzin.equals("Blog Sahibi")) {
                                KullaniciListesiGrid kullaniciListesiGrid = new KullaniciListesiGrid();
                                sayfaYonlendir(kullaniciListesiGrid.kullaniciListesiGridTablo(), "top:10%");
                            } else {
                                Notification.show("Sadece Yetkili Kullanıcılar Girebilir");
                            }
                        } catch (NullPointerException e) {
                            sayfaYonlendir(new KullaniciGirisPanel().girisPanelGetir(), "top:20%;left:40%");
                        }
                        break;

                    case ("Yazı Ekle"):
                        if (OturumYonetimi.KullaniciEmail != null) {
                            YaziEklePanel yaziEklePanel = new YaziEklePanel();
                            window = new Window();
                            window.setContent(yaziEklePanel.yaziEklePanelGetir());
                            window.center();
                            window.setModal(true);
                            windowDurum = true;
                            UI.getCurrent().addWindow(window);
                            windowDurum = false;

                            yaziEklePanel.getButtonYaziEkle().addClickListener((Button.ClickListener) event -> {
                                window.close();
                                sayfaYonlendir(new AnaSayfaMenuBarIcerik().anaSayfaIzgara(), "left:30%;");

                            });

                        } else {
                            sayfaYonlendir(new KullaniciGirisPanel().girisPanelGetir(), "top:20%;left:40%");
                        }
                        break;

                    case ("Yazılarım"):
                        if (OturumYonetimi.KullaniciEmail != null) {
                            YaziListesiGrid yaziListesiGrid = new YaziListesiGrid();
                            sayfaYonlendir(yaziListesiGrid.blogGridGetir(), "top:10%");
                        } else {
                            sayfaYonlendir(new KullaniciGirisPanel().girisPanelGetir(), "top:20%;left:40%");
                        }
                        break;
                }
            }
        };
        MenuBar.MenuItem menuItem;
        menuItem = menuBar.addItem("Anasayfa", VaadinIcons.HOME, command);
        menuItem = menuBar.addItem("Kayıt Ol", VaadinIcons.PLUS, command);
        menuItem = menuBar.addItem("Giriş Yap", VaadinIcons.UNLOCK, command);
        menuItem = menuBar.addItem("Kullanıcı İşlemleri", VaadinIcons.PENCIL, null);
        menuItem.addItem("Yazı Ekle", command);
        menuItem.addItem("Kullanıcı Listesi", command);
        menuItem.addItem("Yazılarım", command);

        return menuBar;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }


    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public boolean isWindowDurum() {
        return windowDurum;
    }

    public void setWindowDurum(boolean windowDurum) {
        this.windowDurum = windowDurum;
    }
}
