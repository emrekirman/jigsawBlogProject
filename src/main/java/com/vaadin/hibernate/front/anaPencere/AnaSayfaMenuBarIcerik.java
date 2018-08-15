package com.vaadin.hibernate.front.anaPencere;

import com.vaadin.hibernate.back.service.Blog;
import com.vaadin.hibernate.back.service.BlogDaoImpl;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class AnaSayfaMenuBarIcerik {
    VerticalLayout layout;


    public AnaSayfaMenuBarIcerik() {
        layout=new VerticalLayout();
    }

   public VerticalLayout anaSayfaIzgara(){
        BlogDaoImpl blogDao=new BlogDaoImpl();
        List<Blog> blogList=new ArrayList<>();

        blogList.addAll(blogDao.getAll());
        for(int i=0;i<blogList.size();i++){
            if (blogList.get(i).getAciklama()==null || blogList.get(i).getBlogBaslik()==null){
                continue;
            }
            else {
                Panel panel = new Panel();
                panel.setCaptionAsHtml(true);
                VerticalLayout panelIcerik=new VerticalLayout();
                panel.setWidth("600px");
                panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
                panel.setIcon(VaadinIcons.PAPERPLANE);
                panel.setCaption(blogList.get(i).getBlogBaslik());
                panel.setStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);

                Label icerik = new Label(blogList.get(i).getAciklama());
                Label yazar=new Label("<b/>Yazar: "+blogList.get(i).getKullaniciID().getKullaniciAdSoyad());
                Label yatayCizgi=new Label("<hr color='#e9e6e6'>");

                Label blogEklenmeTarih=new Label();
                blogEklenmeTarih.setValue("<b/>Eklenme Tarihi: "+blogList.get(i).getTarih());

                icerik.setSizeFull();

                blogEklenmeTarih.setContentMode(ContentMode.HTML);
                icerik.setContentMode(ContentMode.HTML);
                yazar.setContentMode(ContentMode.HTML);
                yatayCizgi.setContentMode(ContentMode.HTML);
                yatayCizgi.setWidth("100%");


                panelIcerik.addComponent(icerik);
                panelIcerik.addComponent(yatayCizgi);
                panelIcerik.addComponent(blogEklenmeTarih);
                panelIcerik.addComponent(yazar);

                panelIcerik.setComponentAlignment(blogEklenmeTarih,Alignment.BOTTOM_LEFT);
                panelIcerik.setComponentAlignment(yazar,Alignment.BOTTOM_LEFT);
                panel.setContent(panelIcerik);
                layout.addComponent(panel);
            }
        }

            return layout;
    }

}
