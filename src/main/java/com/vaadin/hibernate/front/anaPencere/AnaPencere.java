package com.vaadin.hibernate.front.anaPencere;

import com.vaadin.hibernate.front.MyUI;
import com.vaadin.ui.*;

/**
 * @author Emre Kirman
 * @since 1.0
 */
public class AnaPencere extends MyUI {
    static  AbsoluteLayout layout;
    static  Label label;

    static {
        layout=new AbsoluteLayout();
        label=new Label();
    }

    public static AbsoluteLayout anaPencereIzgara(){//Anapencere View
        AnaPencereMenuBar anaPencereMenuBar=new AnaPencereMenuBar();
        layout.addComponent(anaPencereMenuBar.AnaPenceremenuBarGetir());

        AnaSayfaMenuBarIcerik anaSayfaMenuBarIcerik=new AnaSayfaMenuBarIcerik();
        layout.addComponent(anaSayfaMenuBarIcerik.anaSayfaIzgara(),"left:30%;right:30%");
        anaPencereMenuBar.labelOturumYaz();

        return layout;
    }

    public AbsoluteLayout getLayout() {
        return layout;
    }

    public void setLayout(AbsoluteLayout layout) {
        this.layout = layout;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}

