package ita.homi.component.admin;

import ita.homi.delegate.BusinessDelegate;

import java.util.logging.Logger;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ArchivioOrdini extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger
			.getLogger(ArchivioOrdini.class.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	
	
	public ArchivioOrdini(){
		setSizeFull();
		addStyleName("dashboard-view");
		
		final VerticalLayout layoutPrincipale = new VerticalLayout();
		
		Label label = new Label("Archivio Comunicazioni", ContentMode.HTML);

		label.addStyleName("h2");
		label.addStyleName("ligth");
		layoutPrincipale.addComponent(label);

		addComponent(layoutPrincipale);
	
		
	}
	
}
