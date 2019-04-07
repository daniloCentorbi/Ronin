package ita.homi.view.clienti;

import ita.homi.delegate.BusinessDelegate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ArchivioClientiView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	protected BusinessDelegate bd = new BusinessDelegate();
	final DateFormat formatData = new SimpleDateFormat("  dd/MM/yyyy  hh:mm ");

	final HorizontalLayout layoutPrincipale;

	public ArchivioClientiView() {
		setSizeFull();
		addStyleName("dashboard-view");

		layoutPrincipale = new HorizontalLayout();
		layoutPrincipale.setMargin(true);
		addComponent(layoutPrincipale);

		Label titolo = new Label("ORDINI");
		layoutPrincipale.addComponent(titolo);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	
}
