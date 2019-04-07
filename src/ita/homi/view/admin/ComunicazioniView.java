package ita.homi.view.admin;

import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Cliente;
import ita.homi.model.Gruppo;
import ita.homi.utils.GestoreException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ita.homi.component.admin.ContattiClientiComponent;
import ita.homi.component.admin.GruppoSelect;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class ComunicazioniView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(ComunicazioniView.class
			.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	private VerticalLayout leftLayout;
	VerticalLayout rightLayout = new VerticalLayout();
	private HorizontalSplitPanel horizontalSplit;
	private HorizontalLayout bottomLeftCorner = new HorizontalLayout();
	private Button contactAddButton;
	private Button contactEditButton;
	private Table table;
	private ComunicazioniView app;
	// private Window mywindow;
	public static final Object[] GRUPPI_ORDER = new Object[] { "titolo",
			"numero" };
	public static final Object[] CLIENTI_ORDER = new Object[] {
			"ragione sociale", "mail" };
	List<Gruppo> elencogruppi;
	List<Cliente> elencoclienti;
	int numeroclienti;
	int numeroclientigruppo;
	Gruppo gruppo;
	Item item;
	ContattiClientiComponent layoutClienti;

	public ComunicazioniView() {
		setSizeFull();
		addStyleName("dashboard-view");
		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setMargin(true);
		// top.addStyleName("toolbar");
		addComponent(top);

		app = this;
		initTable();
		buildMainLayout();
		initContactAddRemoveButtons();

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void initTable() {
		// Recupero elenco gruppi
		try {
			elencogruppi = bd.getAllGruppi();
			numeroclienti = bd.CountClienti();
			logger.log(Level.SEVERE, "Elenco Acquisito");
		} catch (GestoreException e) {
			logger.notifyAll();
			e.printStackTrace();
		}

		final Container containergruppo = new IndexedContainer();
		containergruppo.addContainerProperty("codice", Integer.class, "");
		containergruppo.addContainerProperty("titolo", String.class, "");
		containergruppo.addContainerProperty("numero", Integer.class, "");

		for (Gruppo o : elencogruppi) {
			// Primo run: Selected TUTTI quindi setto 'numeroclienti' TOTALE
			if (o.getTitolo().equals("TUTTI")) {
				Item item = containergruppo.addItem(o);
				item.getItemProperty("codice").setValue(o.getCodice());
				item.getItemProperty("titolo").setValue(o.getTitolo());
				item.getItemProperty("numero").setValue(numeroclienti);
			} else {
				try {
					numeroclientigruppo = bd.CountClientiGruppo(o);
					logger.log(Level.SEVERE, "Lista Gruppi Acquisita");
				} catch (GestoreException e) {
					e.printStackTrace();
				}
				Item item = containergruppo.addItem(o);
				item.getItemProperty("codice").setValue(o.getCodice());
				item.getItemProperty("titolo").setValue(o.getTitolo());
				item.getItemProperty("numero").setValue(numeroclientigruppo);
			}
		}
		table = new Table();
		table.setContainerDataSource(containergruppo);
		if (table.getContainerDataSource().size() == 0)
			return;
		table.setVisibleColumns(GRUPPI_ORDER);
		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true);
		table.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				item = table.getItem(event.getItemId());
				System.out.println(item);
				Gruppo nuovo = new Gruppo(((Integer) item.getItemProperty(
						"codice").getValue()).intValue(), (String) item
						.getItemProperty("titolo").getValue(), numeroclienti);
				System.out.println("SELEZIONATO " + nuovo.getTitolo()
						+ "CON CODICE: " + nuovo.getCodice());

				// aggiorno tabella clienti di destra con obj appena creato
				updateRight(nuovo);

				// pulsante modifica gruppo
				contactEditButton.setVisible(true);

			}
		});

	}

	@SuppressWarnings("deprecation")
	void buildMainLayout() {

		HorizontalLayout row = new HorizontalLayout();
		row.setSizeFull();
		row.setMargin(new MarginInfo(true, true, false, true));
		row.setSpacing(true);
		addComponent(row);
		setExpandRatio(row, 1.5f);

		horizontalSplit = new HorizontalSplitPanel();
		horizontalSplit.setSizeFull();
		horizontalSplit.addStyleName("gruppi");
		row.addComponent(horizontalSplit);

		leftLayout = new VerticalLayout();
		leftLayout.setMargin(true);
		// leftLayout.addStyleName("leftview");
		leftLayout.setSizeFull();
		bottomLeftCorner.setWidth("100%");
		// bottomLeftCorner.setStyleName("toolbar");
		leftLayout.addComponent(bottomLeftCorner);
		leftLayout.addComponent(table);
		leftLayout.setExpandRatio(table, 1);

		System.out.println("Selesionato Gruppo:   " + gruppo + " -- ");
		
		rightLayout.addComponent(new ContattiClientiComponent(gruppo));

		horizontalSplit.setSplitPosition(230, Sizeable.UNITS_PIXELS);
		horizontalSplit.setFirstComponent(leftLayout);
		horizontalSplit.setSecondComponent(rightLayout);
	}

	// CREA NUOVO GRUPPO
	public void initContactAddRemoveButtons() {

		contactAddButton = new Button();
		contactAddButton.setDescription("Crea Nuovo Gruppo");
		contactAddButton.addStyleName("borderless");
		contactAddButton.addStyleName("notifications");
		contactAddButton.addStyleName("icon-only");
		contactAddButton.addStyleName("icon-bell");
		contactAddButton.setIcon(new ThemeResource("img/nuovoGruppo.jpg"));
		contactAddButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				final Window w = new Window("Crea nuovo gruppo");

				w.setModal(true);
				w.setClosable(true);
				w.setResizable(false);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					private static final long serialVersionUID = 1L;
					TextField name = new TextField("Nome Gruppo");
					{
						addComponent(new FormLayout() {
							private static final long serialVersionUID = 1L;
							{
								setSizeUndefined();
								setMargin(true);
								addComponent(name);
								name.focus();
								name.selectAll();
							}
						});
						addComponent(new HorizontalLayout() {
							private static final long serialVersionUID = 1L;
							{
								setMargin(true);
								setSpacing(true);
								addStyleName("footer");
								setWidth("100%");

								Button cancel = new Button("Annulla");
								cancel.addClickListener(new ClickListener() {
									private static final long serialVersionUID = 1L;

									@Override
									public void buttonClick(ClickEvent event) {
										w.close();
									}
								});
								cancel.setClickShortcut(KeyCode.ESCAPE, null);
								addComponent(cancel);
								setExpandRatio(cancel, 1);
								setComponentAlignment(cancel,
										Alignment.TOP_RIGHT);

								Button ok = new Button("Salva");
								ok.addStyleName("wide");
								ok.addStyleName("default");
								ok.addClickListener(new ClickListener() {
									private static final long serialVersionUID = 1L;

									@Override
									public void buttonClick(ClickEvent event) {
										Gruppo gruppo = new Gruppo(name
												.getValue());
										try {
											bd.insert(gruppo);
											Notification.show("Gruppo:"
													+ name.getValue()
													+ " Creato");
											update();
										} catch (GestoreException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										w.close();
									}
								});
								ok.setClickShortcut(KeyCode.ENTER, null);
								addComponent(ok);
							}
						});

					}
				});

			}
		});

		bottomLeftCorner.addComponent(contactAddButton);

		// Edit item button
		contactEditButton = new Button();
		contactEditButton.setDescription("Aggiungi Clienti");
		contactEditButton.addStyleName("borderless");
		contactEditButton.addStyleName("notifications");
		contactEditButton.addStyleName("icon-only");
		contactEditButton.addStyleName("icon-bell");
		contactEditButton.setIcon(new ThemeResource("img/editGruppo.jpg"));
		contactEditButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// ((IsoUI) getUI()).clearDashboardButtonBadge();
				// event.getButton().setDescription("Messaggi");

				Gruppo nuovo = new Gruppo(((Integer) item.getItemProperty(
						"codice").getValue()).intValue(), (String) item
						.getItemProperty("titolo").getValue(), numeroclienti);
				System.out.println("Quindi " + nuovo.getTitolo() + "Codice"
						+ nuovo.getCodice());

				final Window w = new Window("Gestisci gruppo");
				w.setModal(true);
				w.setClosable(true);
				w.setResizable(false);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);
				w.setContent(new GruppoSelect(w, nuovo));

			}
		});

		contactEditButton.setVisible(false);
		bottomLeftCorner.addComponent(contactEditButton);

	}

	public void update() {
		leftLayout.removeComponent(table);
		leftLayout.removeComponent(bottomLeftCorner);
		initTable();
		leftLayout.addComponent(bottomLeftCorner);
		leftLayout.addComponent(table);
		leftLayout.setExpandRatio(table, 1);
	}

	public void updateRight(Gruppo gruppo) {
		rightLayout.removeAllComponents();
		rightLayout.addComponent(new ContattiClientiComponent(gruppo));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}