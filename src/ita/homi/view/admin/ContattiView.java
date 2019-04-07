package ita.homi.view.admin;

import ita.homi.component.admin.ArchivioComunicazioni;
import ita.homi.component.admin.ClientiTable;
import ita.homi.component.admin.ContattiClientiComponent;
import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Gruppo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ContattiView extends VerticalLayout implements View{
	private static final long serialVersionUID = 1L;
	protected BusinessDelegate bd = new BusinessDelegate();
	private TabSheet controller;
	
	
	public ContattiView(){
		setSizeFull();
		addStyleName("dashboard-view");
	
		controller = new TabSheet();
		controller.setHeight(100.0f, Unit.PERCENTAGE);
		
        Gruppo nuovo = new Gruppo();
        nuovo.setCodice(1);
        
		controller.addTab(new ClientiTable(), "Clienti");
		
		

		final VerticalLayout layoutFatture = new VerticalLayout();
		Label label1 = new Label("Aziende <span> </span>",
				ContentMode.HTML);
		layoutFatture.addComponent(label1);
		layoutFatture.setMargin(true);
		controller.addTab(layoutFatture, "Aziende");

		addComponent(controller);

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
