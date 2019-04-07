package ita.homi.view.admin;

import ita.homi.component.admin.ArchivioComunicazioni;
import ita.homi.component.admin.ClientiTable;
import ita.homi.component.admin.ContattiClientiComponent;
import ita.homi.component.admin.GruppoSelect;
import ita.homi.component.calendari.CalendarHistory;
import ita.homi.model.Gruppo;
import ita.homi.utils.GestoreException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DashboardView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	final DateFormat formatData = new SimpleDateFormat("  dd/MM/yyyy  hh:mm ");

    Table t;
    

    public DashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");

        //Container TOP
        HorizontalLayout rowTop = new HorizontalLayout();
        rowTop.setSizeFull();
        rowTop.setMargin(new MarginInfo(true, true, false, true));
        rowTop.setSpacing(true);
        addComponent(rowTop);
        setExpandRatio(rowTop, 1.5f);

        //Pannello Sinistra
        rowTop.addComponent(createPanel(new ClientiTable(true)));
        
        //Pannello Destra
        TabSheet controller = new TabSheet();
        controller.setSizeFull();
		controller.setHeight(100.0f, Unit.PERCENTAGE);
		controller.addTab(new ArchivioComunicazioni(), "Comunicazioni");
		controller.addTab(new Label("Ordini"), "Ordini");
		controller.addTab(new Label("Fatture"), "Fatture");

        rowTop.addComponent(createPanel(controller));

        //Container BOTTOM
        HorizontalLayout rowBottom = new HorizontalLayout();
        rowBottom = new HorizontalLayout();
        rowBottom.setMargin(true);
        rowBottom.setSizeFull();
        rowBottom.setSpacing(true);
        addComponent(rowBottom);
        setExpandRatio(rowBottom, 2);
        
  
        rowBottom.addComponent(createPanel(new CalendarHistory(23)));


    }

    private CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        Button configure = new Button("configura");
        configure.addStyleName("configure");
        configure.addStyleName("icon-cog");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription("Configure");
        configure.addStyleName("small");
        configure.setIcon(new ThemeResource("img/configure.jpg"));
        configure.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        //panel.addComponent(configure);

        panel.addComponent(content);
        return panel;
    }

    @Override
    public void enter(ViewChangeEvent event) {
     
    }

}