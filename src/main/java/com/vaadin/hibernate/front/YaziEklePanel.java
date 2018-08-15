package com.vaadin.hibernate.front;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.hibernate.back.service.Blog;
import com.vaadin.hibernate.back.service.BlogDaoImpl;
import com.vaadin.hibernate.back.service.Kullanicilar;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class YaziEklePanel {
    private TextField textYaziBaslik = new TextField();
    private Binder<Blog> binder;
    private Button buttonYaziEkle;

    public Panel yaziEklePanelGetir() {

        Panel panel = new Panel();
        panel.setCaption("Blog Yazısı Ekle");
        panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        panel.setIcon(VaadinIcons.ARCHIVES);
        panel.setWidth("100%");
        VerticalLayout layout = new VerticalLayout();

        RichTextArea richTextArea = new RichTextArea();
        richTextArea.setWidth("100%");

        textYaziBaslik.setPlaceholder("Yazı Başlığı");
        textYaziBaslik.setWidth("350px");

        binder = new Binder<>();
        Blog b = new Blog();
        binder.setBean(b);
        binder.forField(textYaziBaslik).asRequired("Bu alan boş geçilemez");
        binder.forField(richTextArea).asRequired("Bu alan boş geçilemez");
        binder.forField(textYaziBaslik)
                .withValidator(
                        new StringLengthValidator(
                                "1-55 Karakter arası girilebilir.",
                                1, 55))
                .bind(Blog::getBlogBaslik, Blog::setBlogBaslik);

        binder.forField(richTextArea).withValidator(
                new StringLengthValidator(
                        "Bu alan boş geçilemez",
                        1, 10000)).bind(Blog::getAciklama, Blog::setAciklama);

        buttonYaziEkle = new Button("Ekle");
        buttonYaziEkle.setWidth("100px");
        buttonYaziEkle.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        buttonYaziEkle.setIcon(VaadinIcons.HARDDRIVE_O);
        buttonYaziEkle.setStyleName(ValoTheme.BUTTON_PRIMARY);

        buttonYaziEkleClick(richTextArea, buttonYaziEkle);

        layout.addComponent(textYaziBaslik);
        layout.addComponent(richTextArea);
        layout.addComponent(buttonYaziEkle);

        layout.setComponentAlignment(buttonYaziEkle, Alignment.BOTTOM_RIGHT);
        panel.setContent(layout);
        return panel;
    }

    private void buttonYaziEkleClick(RichTextArea richTextArea, Button buttonYaziEkle){

        buttonYaziEkle.addClickListener(event -> {
            try {
                if (binder.isValid()) {
                    Blog blog = new Blog();
                    blog.setBlogBaslik(textYaziBaslik.getValue());
                    blog.setAciklama(richTextArea.getValue());

                    Kullanicilar kullanicilar = new Kullanicilar();
                    kullanicilar.setKullaniciID(OturumYonetimi.KullaniciID);
                    kullanicilar.setKullaniciIzin(OturumYonetimi.kullaniciIzin);
                    kullanicilar.setKullaniciSifre(OturumYonetimi.KullaniciSifre);
                    kullanicilar.setKullaniciEmail(OturumYonetimi.KullaniciEmail);
                    kullanicilar.setKullaniciAdSoyad(OturumYonetimi.KullaniciAdSoyad);

                    BlogDaoImpl blogDao = new BlogDaoImpl();
                    blog.setKullaniciID(kullanicilar);
                    blog.setTarih(blogDao.dateOlustur());
                    blogDao.blogEkle(blog);
                } else {
                    throw new IllegalStateException("Zorunlu alanlar doldurulmalıdır.");
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public Button getButtonYaziEkle() {
        return buttonYaziEkle;
    }
}
