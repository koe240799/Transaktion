package com.example.application.views.transaktion;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Transaktion")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.ALIGN_CENTER_SOLID)
public class TransaktionView extends VerticalLayout {

    public TransaktionView() {
        setSpacing(false);

    }

}
