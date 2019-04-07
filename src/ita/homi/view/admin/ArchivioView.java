package ita.homi.view.admin;

import ita.homi.component.admin.ArchivioComunicazioni;
import ita.homi.component.admin.ArchivioOrdini;
import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Comunicazione;

import java.util.List;
import java.util.logging.Logger;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ArchivioView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(ArchivioView.class
			.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	HorizontalLayout layoutPrincipale;
	private TabSheet controller;
	List<Comunicazione> elencocomunicazioni;

	public ArchivioView() {
		setSizeFull();
		addStyleName("reports");

		controller = new TabSheet();
		controller.setHeight(100.0f, Unit.PERCENTAGE);

		
		controller.addTab(new ArchivioComunicazioni(), "Comunicazioni");
				
		controller.addTab(new ArchivioOrdini(), "Ordini");

		
		final VerticalLayout layoutFatture = new VerticalLayout();
		Label label1 = new Label("Archivio Fatture <span> Sospeso </span>",
				ContentMode.HTML);
		layoutFatture.addComponent(label1);
		layoutFatture.setMargin(true);
		controller.addTab(layoutFatture, "Fatture");
		
		
		addComponent(controller);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
