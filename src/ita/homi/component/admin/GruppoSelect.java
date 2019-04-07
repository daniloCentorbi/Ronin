package ita.homi.component.admin;
import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Cliente;
import ita.homi.model.Gruppo;
import ita.homi.model.Relati;
import ita.homi.utils.GestoreException;

import java.util.List;
import java.util.logging.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GruppoSelect extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(GruppoSelect.class
			.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	HorizontalLayout prime = new HorizontalLayout();
	Window window = new Window();
	Gruppo gruppo = new Gruppo();
	Table listaClienti;
	Table listaclientigruppo;
	List<Cliente> elencoclienti;
	List<Cliente> elencoclientigruppo;
	Cliente cliente = new Cliente();
	Cliente clienteGruppo = new Cliente();
	Item item;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public GruppoSelect(Window modalwindow, Gruppo grup) {

		window = modalwindow;
		gruppo = grup;
		// Pannello PRINCIPALE
		addComponent(prime);
		prime.setSizeUndefined();
		prime.setMargin(true);

		initLeftTable();
		initRigthTable();
		initButton();

	}

	//TABELLA SINISTRA
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void initLeftTable() {
		try {
			// Lista Clienti Immissibili
			elencoclienti = bd.getNonGruppoClienti(gruppo);
			System.out.println("-----     SINISTRA       -------"
					+ elencoclienti);
		} catch (GestoreException e) {
			logger.notifyAll();
			e.printStackTrace();
		}

		final IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("codice", Integer.class, "");
		container.addContainerProperty("ragione", String.class, "");
		container.addContainerProperty("categoria", String.class, 0);

		for (Cliente o : elencoclienti) {
			System.out.println(o.getRagione());
			Item item = container.addItem(o);
			item.getItemProperty("codice").setValue(o.getCodice());
			item.getItemProperty("ragione").setValue(o.getRagione());
			item.getItemProperty("categoria").setValue(o.getCategoria());
		}

		listaClienti = new Table() {
			private static final long serialVersionUID = 1L;

			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property<?> property) {
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		listaClienti.setContainerDataSource(container);
		listaClienti.setSelectable(true);
		listaClienti.setMultiSelect(true);
		listaClienti.setImmediate(true);
		listaClienti.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void itemClick(ItemClickEvent event) {
				Relati relati = new Relati();
				item = listaClienti.getItem(event.getItemId());
				cliente.setRagione(((String) item.getItemProperty("ragione")
						.getValue()));
				cliente.setCodice(((Integer) item.getItemProperty("codice")
						.getValue()).intValue());
				relati.setCodicegruppo(gruppo.getCodice());
				relati.setCodicepersona(cliente.getCodice());

				try {
					bd.update(relati);
					Notification.show("Aggiunto:", cliente.getRagione(),
							Type.TRAY_NOTIFICATION);
					update();
				} catch (GestoreException e) {
					logger.notifyAll();
					e.printStackTrace();
				}

			}
		});

		// PANNELLO SINISTRA
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setMargin(true);
		leftLayout.addComponent(listaClienti);
		leftLayout.setHeight("400px");

		prime.addComponent(leftLayout);

		leftLayout.setExpandRatio(listaClienti, 1);
		listaClienti.setSizeFull();

	}

	
	//TABELLA DESTRA
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void initRigthTable() {
		try {
			elencoclientigruppo = bd.getGruppoClienti(gruppo);
			System.out.println("------     DESTRA        ------"
					+ elencoclienti);
		} catch (GestoreException e) {
			logger.notifyAll();
			e.printStackTrace();
		}

		final IndexedContainer containergruppo = new IndexedContainer();

		containergruppo.addContainerProperty("codice", Integer.class, "");
		containergruppo.addContainerProperty("ragione", String.class, "");

		for (Cliente o : elencoclientigruppo) {
			Item item = containergruppo.addItem(o);
			item.getItemProperty("codice").setValue(o.getCodice());
			item.getItemProperty("ragione").setValue(o.getRagione());
		}

		listaclientigruppo = new Table("");
		listaclientigruppo.setContainerDataSource(containergruppo);
		listaclientigruppo.setSelectable(true);
		listaclientigruppo.setMultiSelect(true);
		listaclientigruppo.setImmediate(true);
		listaclientigruppo.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void itemClick(ItemClickEvent event) {
				Relati relati = new Relati();
				item = listaclientigruppo.getItem(event.getItemId());
				clienteGruppo.setRagione(((String) item.getItemProperty(
						"ragione").getValue()));
				clienteGruppo.setCodice(((Integer) item.getItemProperty(
						"codice").getValue()).intValue());
				relati.setCodicegruppo(gruppo.getCodice());
				relati.setCodicepersona(clienteGruppo.getCodice());
				try {
					bd.delete(relati);
					Notification.show("Eliminato:", clienteGruppo.getRagione(),
							Type.TRAY_NOTIFICATION);
					update();
				} catch (GestoreException e) {
					logger.notifyAll();
					e.printStackTrace();
				}

			}

		});

		// Pannello DESTRO
		VerticalLayout rigthLayout = new VerticalLayout();
		rigthLayout.setMargin(true);
		rigthLayout.addComponent(listaclientigruppo);
		rigthLayout.setHeight("400px");

		prime.addComponent(rigthLayout);

		rigthLayout.setExpandRatio(listaclientigruppo, 1);
		listaclientigruppo.setSizeFull();

	}

	public void initButton() {
		HorizontalLayout ButtonLayout = new HorizontalLayout();
		ButtonLayout.addStyleName("footer");
		ButtonLayout.setWidth("100%");
        Button cancel = new Button("Annulla");
        cancel.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
            public void buttonClick(ClickEvent event) {
                window.close();
            }
        });
        cancel.setClickShortcut(KeyCode.ESCAPE, null);
        ButtonLayout.addComponent(cancel);

        Button ok = new Button("Salva");
        //ok.addStyleName("wide");
        //ok.addStyleName("default");
        ok.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
            public void buttonClick(ClickEvent event) {
                window.close();
            }
        });
        ok.setClickShortcut(KeyCode.ENTER, null);
        ButtonLayout.addComponent(ok);
        
		prime.addComponent(ButtonLayout);
	}

	public void update() {
		prime.removeAllComponents();
		initLeftTable();
		initRigthTable();
		initButton();
	}


}