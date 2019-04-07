package ita.homi.view.admin;

import java.util.List;

import ita.homi.component.admin.ClientiTable;
import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Produttori;
import ita.homi.utils.GestoreException;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CatalogoView extends VerticalLayout implements View{
	private static final long serialVersionUID = 1L;
	protected BusinessDelegate bd = new BusinessDelegate();
	private GridLayout catalogo;
	private VerticalLayout loginLayout;
	List<Produttori> elenco;
	public CatalogoView() {
		setSizeFull();
		addStyleName("dashboard-view");
	
		initContent();  
        buildLoginView(true);
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	private void initContent() {
		
		Produttori azienda = new Produttori();
		try {
			elenco = bd.getAllProduttori();
		} catch (GestoreException e) {
			e.printStackTrace();
		} 

		catalogo = new GridLayout();
		catalogo.addStyleName("outlined");
		catalogo.setSizeFull();
		
    	catalogo.setRows(3);
    	catalogo.setColumns(3);

    	for (int i = 0; i < elenco.size(); i++) {
           
                final Label child = new Label("Azienda" + elenco.get(i).getProduttore());
                Image logo = new Image(
						null,
						new ThemeResource("logos/"+elenco.get(i).getFoto()+".jpg"));
				logo.setWidth("100px");
                catalogo.addComponent(logo);
                catalogo.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
                catalogo.setColumnExpandRatio(i, 0.0f);
                catalogo.addListener(new LayoutClickListener() {
					private static final long serialVersionUID = 1L;
					public void layoutClick(LayoutClickEvent event) {
						removeAllComponents();
					addComponent(new ClientiTable(true));
					}
				});
        }
        
	}

	private void buildLoginView(boolean exit) {
		if (exit) {
			removeAllComponents();
		}

		addStyleName("login");

		loginLayout = new VerticalLayout();
		loginLayout.setSizeFull();
		loginLayout.addStyleName("login-layout");
		addComponent(loginLayout);

		final CssLayout loginPanel = new CssLayout();
		loginPanel.addStyleName("login-panel");
		loginPanel.setWidth("600");
		loginPanel.setHeight("400");


	        loginPanel.addComponent(catalogo);
		

		loginLayout.addComponent(loginPanel);
		loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
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
		// TODO Auto-generated method stub
		
	}

}
