package com.vaadin.hibernate.front.yaziListesi;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.hibernate.back.service.Blog;
import com.vaadin.hibernate.back.service.BlogDaoImpl;
import com.vaadin.hibernate.front.anaPencere.AnaPencereMenuBar;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class YaziListesiGridDuzenlePopup {
    private VerticalLayout popupContent;
    private Panel panel;
    private Binder<Blog> binder;
    private Blog blog;
    private int blogId;

    public YaziListesiGridDuzenlePopup() {
        popupContent = new VerticalLayout();
        panel = new Panel();
    }

    Panel popupGetir(){
        panel.setStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
        panel.setWidth("50%");
        BlogDaoImpl blogDao=new BlogDaoImpl();
        blog=new Blog();
        blog=blogDao.getById(blogId);

        TextField textId=new TextField();
        textId.setWidth("100px");
        textId.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textId.setIcon(VaadinIcons.AT);
        textId.setEnabled(false);

        TextField textTarih=new TextField();
        textTarih.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textTarih.setIcon(VaadinIcons.CALENDAR);
        textTarih.setWidth("200px");
        textTarih.setEnabled(false);

        TextField textBaslik=new TextField();
        textBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textBaslik.setIcon(VaadinIcons.NEWSPAPER);
        textBaslik.setWidth("300px");

        RichTextArea richTextAreaIcerik=new RichTextArea();

        Button buttonGuncelle=new Button("Güncelle");
        buttonGuncelle.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        buttonGuncelle.setIcon(VaadinIcons.CHECK_CIRCLE_O);
        buttonGuncelle.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonGuncelle.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder=new Binder<>();
        binder.setBean(blog);

        binder.forField(textBaslik).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez!!",1,100
        )).bind(Blog::getBlogBaslik,Blog::setBlogBaslik);
        binder.forField(richTextAreaIcerik).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez!!",1,10000
        )).bind(Blog::getAciklama,Blog::setAciklama);


        if (blog!=null){
            textId.setValue(String.valueOf(blog.getBlogId()));
            textTarih.setValue(String.valueOf(blog.getTarih()));
            textBaslik.setValue(blog.getBlogBaslik());
            richTextAreaIcerik.setValue(blog.getAciklama());
        }
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.addComponent(textId);
        horizontalLayout.addComponent(textTarih);

        popupContent.addComponent(horizontalLayout);
        popupContent.addComponent(textBaslik);
        popupContent.addComponent(richTextAreaIcerik);
        popupContent.addComponent(buttonGuncelle);
        panel.setContent(popupContent);
        panel.setSizeUndefined();

        buttonClickGuncelle(blogDao, textBaslik, richTextAreaIcerik, buttonGuncelle);

        return panel;
    }

    private void buttonClickGuncelle(BlogDaoImpl blogDao, TextField textBaslik, RichTextArea richTextAreaIcerik, Button buttonGuncelle) {
        buttonGuncelle.addClickListener(event -> {
            try{
                Blog blog2=new Blog();
                blog2.setBlogId(blog.getBlogId());
                blog2.setKullaniciID(blog.getKullaniciID());
                blog2.setBlogBaslik(textBaslik.getValue());
                blog2.setAciklama(richTextAreaIcerik.getValue());
                blog2.setTarih(blog.getTarih());
                if (binder.isValid()){
                    blogDao.update(blog2);
                    new AnaPencereMenuBar().sayfaYonlendir(new YaziListesiGrid().blogGridGetir(),"top:10%");
                }
            }catch (NullPointerException e){
                textBaslik.setComponentError(new UserError("Bu alan boş geçilemez!!"));
                richTextAreaIcerik.setComponentError(new UserError("Bu alan boş geçilemez!!"));
            }

        });
    }

    public VerticalLayout getPopupContent() {
        return popupContent;
    }

    public void setPopupContent(VerticalLayout popupContent) {
        this.popupContent = popupContent;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
}
