package ita.homi.component.admin;

import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Cliente;
import ita.homi.model.Comunicazione;
import ita.homi.utils.GestoreException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

public class ArchivioComunicazioni extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger
			.getLogger(ArchivioComunicazioni.class.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	final DateFormat formatData = new SimpleDateFormat(
			"  dd/MM/yyyy  hh:mm ");
	public static final Object[] COL_ORDER = new Object[] { "destinatario", "oggetto","data" };
	VerticalLayout rightLayout;
	VerticalLayout leftLayout;
	HorizontalSplitPanel composizione;
	Label anteprima;
	Table table;
	List<Comunicazione> elencodestinataricomunicazioni;
	List<Comunicazione> elencocomunicazioni;
	Comunicazione dettaglioComunicazione;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public ArchivioComunicazioni() {
		setSizeFull();
		addStyleName("dashboard-view");

		final VerticalLayout layoutPrincipale = new VerticalLayout();
		layoutPrincipale.setMargin(true);

		Label label = new Label("Archivio Comunicazioni", ContentMode.HTML);

		label.addStyleName("h2");
		label.addStyleName("ligth");
		layoutPrincipale.addComponent(label);

		addComponent(layoutPrincipale);

		leftLayout = new VerticalLayout();
		leftLayout.setMargin(true);

		final ComboBox combobox = new ComboBox("Seleziona Cliente");
		combobox.setInvalidAllowed(false);
		combobox.setNullSelectionAllowed(false);

		try {
			elencodestinataricomunicazioni = bd.getClientiPerComunicazioni();
			logger.log(Level.SEVERE,
					"Primo Run-->Selezionati TUTTI clienti --------------------");
		} catch (GestoreException e) {
			logger.notifyAll();
			e.printStackTrace();
		}

		// Add some items and specify their item ID.
		// The item ID is by default used as item caption.
		for (Comunicazione o : elencodestinataricomunicazioni) {

			// dettaglioComunicazione = new Comunicazione(o);

			combobox.addItem(o.getDestinatario());

		}

		Label inizio = new Label("Periodo");

		final PopupDateField dataInizio = new PopupDateField();
		dataInizio.setValue(new Date());
		dataInizio.setImmediate(true);
		dataInizio.setTimeZone(TimeZone.getTimeZone("UTC"));
		dataInizio.setLocale(Locale.ITALY);
		dataInizio.setResolution(Resolution.DAY);

		dataInizio.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
				Notification.show("Data Inizio:", valueString,
						Type.TRAY_NOTIFICATION);
			}
		});

		final PopupDateField dataFine = new PopupDateField();
		dataFine.setValue(new Date());
		dataFine.setImmediate(true);
		dataFine.setTimeZone(TimeZone.getTimeZone("UTC"));
		dataFine.setLocale(Locale.ITALY);
		dataFine.setResolution(Resolution.DAY);

		dataFine.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
				Notification.show("Data Fine:", valueString,
						Type.TRAY_NOTIFICATION);
			}
		});

		final Button aggiorna = new Button("Cerca");
		aggiorna.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(Button.ClickEvent event) {
				anteprima.setValue(" Messaggio Riferito a: "
						+ combobox.getValue() + " Data dal: "
						+ formatData.format(dataInizio.getValue()) + " al: "
						+ formatData.format(dataFine.getValue()));

				try {
					elencocomunicazioni = bd.getClienteComunicazioni(combobox
							.getValue().toString());
					logger.log(Level.SEVERE, "SELEZIONATI CLIENTI GRUPPO: "
							+ combobox.getValue() + "------------");
					System.out.println("CLIENTE------------"
							+ combobox.getValue() + " toString() -- "
							+ combobox.getValue().toString());
				} catch (GestoreException e) {
					logger.notifyAll();
					e.printStackTrace();
				}

				final Container container = new IndexedContainer();
				container.addContainerProperty("codice", Integer.class, "");
				container
						.addContainerProperty("destinatario", String.class, "");
				container.addContainerProperty("oggetto", String.class, "");
				container.addContainerProperty("corpo", String.class, "");
				container.addContainerProperty("data", Date.class, 0);

				for (Comunicazione o : elencocomunicazioni) {
					Item item = container.addItem(o);
					item.getItemProperty("codice").setValue(o.getCodice());
					item.getItemProperty("destinatario").setValue(
							o.getDestinatario());
					item.getItemProperty("oggetto").setValue(o.getOggetto());
					item.getItemProperty("corpo").setValue(o.getCorpo());
					item.getItemProperty("data").setValue(o.getDate());
				}

				table = new Table();
				table.addStyleName("borderless");
				table.setContainerDataSource(container);
				table.setVisibleColumns(COL_ORDER);
				table.setColumnHeader("destinatario", "Cliente");
				table.setColumnHeader("oggetto", "Oggetto");
				table.setColumnHeader("data", "Data");
				table.setColumnCollapsingAllowed(true);
				table.setColumnCollapsed("codice", true);
				// table.setColumnAlignment("Seats", Align.RIGHT);
				table.setSelectable(true);
				table.setMultiSelect(false);
				table.setImmediate(true);
				table.addListener(new ItemClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void itemClick(ItemClickEvent event) {

						Item item = table.getItem(event.getItemId());
						final Comunicazione comunicazioneSelezionata = new Comunicazione(
								((Integer) item.getItemProperty("codice")
										.getValue()).intValue(), (String) item
										.getItemProperty("destinatario")
										.getValue(), (String) item
										.getItemProperty("oggetto").getValue(),
								(String) item.getItemProperty("corpo")
										.getValue(), (Date) item
										.getItemProperty("data").getValue());
						System.out.println("Selezionata: "
								+ comunicazioneSelezionata.getOggetto()
								+ " Con codice: "
								+ comunicazioneSelezionata.getCodice());

						final Window w = new Window("Dettaglio Comunicazione");

						w.setModal(true);
						w.setClosable(true);
						w.setResizable(false);
						w.setWidth("600px");
						w.addStyleName("edit-dashboard");

						getUI().addWindow(w);

						// w.setContent(new ComunicazionePreview(w,
						// elencoclienti));

						w.setContent(new VerticalLayout() {
							private static final long serialVersionUID = 1L;
							{

								addComponent(new VerticalLayout() {
									{
										setWidth("100%");
										setMargin(true);
										addStyleName("labels");
										// aggiungo alla window

										Label titoloCliente = new Label();
										titoloCliente.addStyleName("h1");
										titoloCliente.setValue("Cliente: \n");
										addComponent(titoloCliente);

										Label clienteLabel = new Label();
										clienteLabel.setStyleName("h2");
										clienteLabel.addStyleName("light");
										clienteLabel
												.setValue(comunicazioneSelezionata
														.getDestinatario());
										addComponent(clienteLabel);

										Label titoloOggetto = new Label();
										titoloOggetto.addStyleName("h1");
										titoloOggetto.setValue("Oggetto: \n");
										addComponent(titoloOggetto);

										Label oggettoLabel = new Label();
										oggettoLabel.setStyleName("h2");
										oggettoLabel.addStyleName("light");
										oggettoLabel
												.setValue(comunicazioneSelezionata
														.getOggetto());
										addComponent(oggettoLabel);

										Label titoloTesto = new Label();
										titoloTesto.addStyleName("h1");
										titoloTesto
												.setValue("Testo Del Messaggio: \n");
										addComponent(titoloTesto);

										Label testoLabel = new Label();
										testoLabel.setStyleName("h5");
										testoLabel.addStyleName("light");
										testoLabel
												.setValue(comunicazioneSelezionata
														.getCorpo());
										addComponent(testoLabel);

										Label dataLabel = new Label();
										dataLabel.setStyleName("h2");
										dataLabel
												.setValue(formatData.format(comunicazioneSelezionata
														.getDate()));
										addComponent(dataLabel);

									}
								});
								
		                        addComponent(new HorizontalLayout() {
		                            {
		                                setMargin(true);
		                                setSpacing(true);
		                                addStyleName("footer");
		                                setWidth("100%");

		                                Button cancel = new Button("Cancel");
		                                cancel.addClickListener(new ClickListener() {
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

		                                Button ok = new Button("Save");
		                                ok.addStyleName("wide");
		                                ok.addStyleName("default");
		                                ok.addClickListener(new ClickListener() {
		                                    @Override
		                                    public void buttonClick(ClickEvent event) {
		                                        w.close();
		                                    }
		                                });
		                                ok.setClickShortcut(KeyCode.ENTER, null);
		                                addComponent(ok);
		                            }
		                        });
		                        
		                        
							}
						});

						Notification.show("Comunicazione del:", formatData
								.format(comunicazioneSelezionata.getDate()),
								Type.TRAY_NOTIFICATION);

					}
				});

				rightLayout.removeAllComponents();
				rightLayout.addComponent(anteprima);
				rightLayout.addComponent(table);

				update();

			}
		});

		// SplitPanel
		composizione = new HorizontalSplitPanel();
		composizione.setSizeFull();

		// aggiungo al pannello Principale
		layoutPrincipale.addComponent(composizione);

		leftLayout.addComponent(combobox);

		leftLayout.addComponent(inizio);

		leftLayout.addComponent(dataInizio);

		leftLayout.addComponent(dataFine);

		leftLayout.addComponent(aggiorna);

		rightLayout = new VerticalLayout();
		rightLayout.setWidth("100%");
		rightLayout.setMargin(true);
		rightLayout.setSpacing(true);
		rightLayout.addStyleName("labels");

		// Label ANTEPRIMA
		anteprima = new Label();
		anteprima.addStyleName("h2");
		anteprima.addStyleName("light");
		anteprima
				.setValue(" Seleziona Le Opzioni Nella Parte Sinistra Dello Schermo ");

		rightLayout.addComponent(anteprima);

		composizione.setFirstComponent(leftLayout);
		composizione.setSecondComponent(rightLayout);

	}

	public void update() {
		composizione.removeAllComponents();
		composizione.setFirstComponent(leftLayout);
		composizione.setSecondComponent(rightLayout);
	}

}
