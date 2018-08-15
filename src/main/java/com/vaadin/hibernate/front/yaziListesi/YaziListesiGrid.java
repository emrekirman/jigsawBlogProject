package com.vaadin.hibernate.front.yaziListesi;

import com.vaadin.hibernate.back.service.Blog;
import com.vaadin.hibernate.back.service.BlogDaoImpl;
import com.vaadin.hibernate.front.anaPencere.AnaPencere;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class YaziListesiGrid {
    private Grid<Blog> grid;

    public Grid<Blog> blogGridGetir(){
        grid=new Grid<>();
        BlogDaoImpl blogDao=new BlogDaoImpl();
        List<Blog> blogList=new ArrayList<>();

        blogList.addAll(blogDao.getByKullaniciID());

        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        grid.setItems(blogList);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addColumn(Blog::getBlogId).setCaption("No").setWidth(80);
        grid.addColumn(Blog::getBlogBaslik).setCaption("Başlık");
        grid.addColumn(Blog::getAciklama).setCaption("İçerik").setWidth(900);
        grid.addColumn(Blog::getTarih).setCaption("Eklenme Tarihi");

        gridTiklanmaOlayi();

        return grid;
    }

    private void gridTiklanmaOlayi() {
        grid.addItemClickListener(event -> {
            try {
                YaziListesiGridDuzenlePopup yaziEkleGridDuzenlePopup=new YaziListesiGridDuzenlePopup();
                yaziEkleGridDuzenlePopup.setBlogId(event.getItem().getBlogId());
                PopupView popupView=new PopupView(null,yaziEkleGridDuzenlePopup.popupGetir());
                popupView.setPopupVisible(true);
                popupView.setHideOnMouseOut(false);

                new AnaPencere().getLayout().addComponent(popupView,"left:50%");
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }

        });
    }

}
