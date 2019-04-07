package ita.homi.view.admin;

import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Cliente;
import ita.homi.model.Comunicazione;
import ita.homi.model.DettaglioOrdine;
import ita.homi.model.Ordine;
import ita.homi.model.Prodotto;
import ita.homi.utils.GestoreException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NuovoView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	protected BusinessDelegate bd = new BusinessDelegate();

	final DateFormat formatData = new SimpleDateFormat("  dd/MM/yyyy  hh:mm ");

	HorizontalLayout rowLeft = new HorizontalLayout();

	Button nuovo;

	List<Cliente> elencoclienti;
	List<Prodotto> elencoprodotti;
	List<DettaglioOrdine> elencoordine;

	Table listaProdotti;
	Table listaordineattuale;

	int numeroOrdine;

	Item itemProdotto;
	Item itemOrdine;
	Prodotto prodotto = new Prodotto();

	Double preventivo = new Double(0);

	ComboBox combobox = new ComboBox();
	String aziendaSelezionata = new String("");

	public NuovoView() {
		setSizeFull();
		addStyleName("dashboard-view");

		try {
			// Lista Clienti Immissibili
			numeroOrdine = bd.countAllOrdini();
			System.out.println("Conto totale ORDINI" + numeroOrdine);
			numeroOrdine = numeroOrdine + 1;
			System.out.println("ORDINE ATTUALE----------" + numeroOrdine);
		} catch (GestoreException e) {

			e.printStackTrace();
		}

		initTopComponent();

	}

	private void initTopComponent() {

		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.addStyleName("toolbar");
		addComponent(top);
		final Label title = new Label("Gestione Ordine  " + numeroOrdine);
		title.setSizeUndefined();
		title.addStyleName("h1");
		top.addComponent(title);
		top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		top.setExpandRatio(title, 1);

		Button ultimoOrdine = new Button("Ordini Recenti");
		ultimoOrdine.setDescription("Ordini Recenti");
		ultimoOrdine.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				if (notifications != null && notifications.getUI() != null)
					notifications.close();
				else {
					buildNotifications(event);
					getUI().addWindow(notifications);
					notifications.focus();
					((CssLayout) getUI().getContent())
							.addLayoutClickListener(new LayoutClickListener() {
								@Override
								public void layoutClick(LayoutClickEvent event) {
									notifications.close();
									((CssLayout) getUI().getContent())
											.removeLayoutClickListener(this);
								}
							});
				}

			}
		});
		top.addComponent(ultimoOrdine);
		top.setComponentAlignment(ultimoOrdine, Alignment.MIDDLE_LEFT);

		nuovo = new Button("Nuovo");
		nuovo.addStyleName("default");
		nuovo.setDescription("Nuovo Ordine");
		nuovo.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final Window w = new Window("Seleziona Azienda");

				w.setModal(true);
				w.setClosable(true);
				w.setResizable(false);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					Label name = new Label("Gestione Ordine    " + numeroOrdine);
					{

						addComponent(new VerticalLayout() {
							{
								// combobox = new ComboBox("Seleziona azienda");

								combobox.setInvalidAllowed(false);
								combobox.setNullSelectionAllowed(false);

								for (int i = 0; i < 10; i++) {

									combobox.addItem("Azienda " + "Azienda" + i);
								}
								combobox.setValue(combobox.getItemIds()
										.iterator().next());
								setSizeUndefined();
								setMargin(true);
								addComponent(combobox);

								final ComboBox comboboxClienti = new ComboBox(
										"Seleziona Cliente");
								comboboxClienti.setInvalidAllowed(false);
								comboboxClienti.setNullSelectionAllowed(false);

								try {
									elencoclienti = bd.getAllClienti();
								} catch (GestoreException e) {
									e.printStackTrace();
								}

								// Add some items and specify their item ID.
								// The item ID is by default used as item
								// caption.
								for (Cliente o : elencoclienti) {

									// dettaglioComunicazione = new
									// Comunicazione(o);

									comboboxClienti.addItem(o.getRagione());

								}

								addComponent(comboboxClienti);

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

								Button ok = new Button("Vai");
								ok.addStyleName("wide");
								ok.addStyleName("default");
								ok.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {

										nuovo.setEnabled(false);

										title.setValue(name.getValue());
										aziendaSelezionata = combobox
												.getValue().toString();
										System.out.println(combobox.getValue()
												.toString());
										initLeftTable();

										initRightTable();

										initBoard();

										initBottomComponent();

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
		top.addComponent(nuovo);
		top.setComponentAlignment(nuovo, Alignment.MIDDLE_LEFT);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void initLeftTable() {

		try {
			// Lista Clienti Immissibili
			elencoprodotti = bd.getAllProdottiNonOrdine(numeroOrdine);
			System.out.println("-----ELENCO PRODOTTI RECUPERATI-------");
		} catch (GestoreException e) {

			e.printStackTrace();
		}

		final IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("codice", Integer.class, "");
		container.addContainerProperty("descrizione", String.class, "");
		container.addContainerProperty("confezione", String.class, "");
		container.addContainerProperty("unita", Integer.class, "");
		container.addContainerProperty("prezzo", Double.class, 0);

		for (Prodotto o : elencoprodotti) {
			Item item = container.addItem(o);
			item.getItemProperty("codice").setValue(o.getCodice());
			item.getItemProperty("descrizione").setValue(o.getDescrizione());
			item.getItemProperty("confezione").setValue(o.getConfezione());
			item.getItemProperty("unita").setValue(o.getUnita());
			item.getItemProperty("prezzo").setValue(o.getPrezzo());
		}

		listaProdotti = new Table("Descrizione Prodotti") {
			private static final long serialVersionUID = 1L;

			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property<?> property) {
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		listaProdotti.setContainerDataSource(container);
		listaProdotti.setValue(20);
		listaProdotti.setSelectable(true);
		listaProdotti.setMultiSelect(false);
		listaProdotti.setImmediate(true);
		listaProdotti.setColumnCollapsingAllowed(true);
		listaProdotti.setColumnCollapsed("codice", true);
		listaProdotti.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {

				itemProdotto = listaProdotti.getItem(event.getItemId());

				final Prodotto prodottoSelezionato = new Prodotto(
						((Integer) itemProdotto.getItemProperty("codice")
								.getValue()).intValue(), (String) itemProdotto
								.getItemProperty("descrizione").getValue(),
						(String) itemProdotto.getItemProperty("confezione")
								.getValue(), ((Integer) itemProdotto
								.getItemProperty("unita").getValue())
								.intValue(), ((Double) itemProdotto
								.getItemProperty("prezzo").getValue())
								.doubleValue());

				final Window w = new Window("Aggiungi");

				w.setModal(true);
				w.setClosable(true);
				w.setResizable(false);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					final ObjectProperty<Integer> qt = new ObjectProperty<Integer>(
							5);
					TextField quantità = new TextField("Quantità", qt);
					final ObjectProperty<Double> listino = new ObjectProperty<Double>(
							prodottoSelezionato.getPrezzo(), Double.class);
					final TextField prezzo = new TextField("Prezzo", listino);

					{

						addComponent(new VerticalLayout() {
							{
								// combobox = new ComboBox("Seleziona azienda");

								addComponent(quantità);
								addComponent(prezzo);
								quantità.focus();
								quantità.setWidth("80px");
								quantità.setMaxLength(5);
								prezzo.setWidth("80px");
								setSizeUndefined();
								setMargin(true);
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

								Button ok = new Button("Vai");
								ok.addStyleName("wide");
								ok.addStyleName("default");
								ok.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										aziendaSelezionata = combobox
												.getValue().toString();
										System.out.println(combobox.getValue()
												.toString());

										Ordine ordineInserimento = new Ordine();
										ordineInserimento
												.setNumero(numeroOrdine);
										ordineInserimento
												.setCodiceprodotto(prodottoSelezionato
														.getCodice());

								
										int qnt = 0;
										String s = quantità.getValue();
										try {
											qnt = new Integer(Integer
													.parseInt(s));

											ordineInserimento.setQuantita(qnt);

											ordineInserimento
													.setQuantita(qnt);

										} catch (NumberFormatException ex) {
											System.out
													.println("ERRORE QUANTITA: "
															+ s);
											Notification
													.show("Quantità espressa in Pezzi");

										}

										Double resultRounded = listino
												.getValue();
										try {
											System.out
													.println("resultRounded: "
															+ resultRounded);
											ordineInserimento
													.setPrezzo(resultRounded
															.doubleValue());
										} catch (NumberFormatException ex) {
											System.out.println("prezzo: "
													+ resultRounded
															.doubleValue());
											System.out
													.println("ERRORE PREZZO: "
															+ ex);
											Notification
													.show("Prezzo in Formato '7,80' ");
										}

										preventivo = preventivo
												+ qnt
												* prodottoSelezionato
														.getUnita()
												* resultRounded.doubleValue();

										try {
											bd.insert(ordineInserimento);
											Notification.show(
													"Aggiunto Prodotto a ordine:"
															+ numeroOrdine,
													Type.TRAY_NOTIFICATION);
											update();
										} catch (GestoreException e) {
											// logger.notifyAll();
											e.printStackTrace();
										}

										Notification
												.show(" AGGIUNTO Prodotto CoDICE"
														+ prodottoSelezionato
																.getCodice());

										update();

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

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void initRightTable() {

		try {

			// ADESSO PRENDO LIST DI DettaglioOrdine
			elencoordine = bd.getDettaglioProdottiOrdine(numeroOrdine);
		} catch (GestoreException e) {
			e.printStackTrace();
		}

		final IndexedContainer containerOrdine = new IndexedContainer();

		containerOrdine.addContainerProperty("numeroordine", Integer.class, "");
		containerOrdine.addContainerProperty("codiceprodotto", Integer.class,
				"");
		containerOrdine.addContainerProperty("descrizioneprodotto",
				String.class, "");
		containerOrdine.addContainerProperty("prezzoprodotto", Double.class, 0);
		containerOrdine
				.addContainerProperty("unitaprodotto", Integer.class, "");
		containerOrdine.addContainerProperty("quantita", Integer.class, 0);
		containerOrdine.addContainerProperty("importo", Double.class, 0);

		for (DettaglioOrdine o : elencoordine) {
			Item item = containerOrdine.addItem(o);
			item.getItemProperty("numeroordine").setValue(o.getNumeroordine());
			item.getItemProperty("codiceprodotto").setValue(
					o.getCodiceprodotto());
			item.getItemProperty("descrizioneprodotto").setValue(
					o.getDescrizioneprodotto());
			item.getItemProperty("prezzoprodotto").setValue(
					o.getPrezzoprodotto());
			item.getItemProperty("unitaprodotto")
					.setValue(o.getUnitaprodotto());
			item.getItemProperty("quantita").setValue(o.getQuantita());

			item.getItemProperty("importo").setValue(
					o.getQuantita() * o.getUnitaprodotto()
							* o.getPrezzoprodotto());

		}

		listaordineattuale = new Table("Prodotti Selezionati");
		listaordineattuale.setEditable(false);
		listaordineattuale.setValue(12);
		listaordineattuale.setContainerDataSource(containerOrdine);
		listaordineattuale.setSelectable(true);
		listaordineattuale.setMultiSelect(false);
		listaordineattuale.setImmediate(true);
		listaordineattuale.setColumnCollapsingAllowed(true);
		listaordineattuale.setColumnHeader("prezzoprodotto", "prezzo");
		listaordineattuale
				.setColumnHeader("descrizioneprodotto", "descrizione");
		listaordineattuale.setColumnWidth("prezzoprodotto", 63);
		listaordineattuale.setColumnWidth("quantita", 63);
		listaordineattuale.setColumnCollapsed("numeroordine", true);
		listaordineattuale.setColumnCollapsed("codiceprodotto", true);
		listaordineattuale.setColumnCollapsed("unitaprodotto", true);
		listaordineattuale.addListener(new Table.ValueChangeListener() {
			public void valueChange(
					final com.vaadin.data.Property.ValueChangeEvent event) {
				Notification
						.show("YES " + numeroOrdine, Type.TRAY_NOTIFICATION);
			}
		});
		listaordineattuale.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {

				itemOrdine = listaordineattuale.getItem(event.getItemId());
				final Ordine prodottoOrdinato = new Ordine(
						((Integer) itemOrdine.getItemProperty("numero")
								.getValue()).intValue(), ((Integer) itemOrdine
								.getItemProperty("codicecliente").getValue())
								.intValue(), ((Integer) itemOrdine
								.getItemProperty("codiceprodotto").getValue())
								.intValue(), ((Integer) itemOrdine
								.getItemProperty("quantita").getValue())
								.intValue(), ((Double) itemOrdine
								.getItemProperty("prezzo").getValue())
								.doubleValue());

				final Window w = new Window("Modifica");

				w.setModal(true);
				w.setClosable(true);
				w.setResizable(false);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					final ObjectProperty<Integer> qt = new ObjectProperty<Integer>(
							prodottoOrdinato.getQuantita());
					TextField quantità = new TextField("Quantità", qt);
					final ObjectProperty<Double> listino = new ObjectProperty<Double>(
							prodottoOrdinato.getPrezzo(), Double.class);
					final TextField prezzo = new TextField("Prezzo", listino);

					{

						addComponent(new VerticalLayout() {
							{
								// combobox = new ComboBox("Seleziona azienda");

								addComponent(quantità);
								addComponent(prezzo);
								quantità.focus();
								quantità.setWidth("80px");
								quantità.setMaxLength(5);
								prezzo.setWidth("80px");
								setSizeUndefined();
								setMargin(true);
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

								Button ok = new Button("Modifica");
								ok.addStyleName("wide");
								ok.addStyleName("default");
								ok.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										aziendaSelezionata = combobox
												.getValue().toString();
										System.out.println(combobox.getValue()
												.toString());

										Ordine ordineInserimento = new Ordine();
										ordineInserimento
												.setNumero(numeroOrdine);
										ordineInserimento.setCodicecliente(1);
										ordineInserimento
												.setCodiceprodotto(prodottoOrdinato
														.getCodiceprodotto());

										int i = 0;
										String s = quantità.getValue();
										try {
											i = new Integer(Integer.parseInt(s));

											ordineInserimento.setQuantita(i);

										} catch (NumberFormatException ex) {
											System.out
													.println("s was not integer: "
															+ s);
											Notification
													.show("Quantità espressa in interi");

										}

										Double resultRounded = listino
												.getValue();
										try {
											System.out
													.println("resultRounded: "
															+ resultRounded);
											ordineInserimento
													.setPrezzo(resultRounded
															.doubleValue());
										} catch (NumberFormatException ex) {
											System.out.println("prezzo: "
													+ resultRounded
															.doubleValue());
											System.out.println("exit: " + ex);
											Notification
													.show("Impossibile modificare il Prezzo");
										}


										try {
											bd.update(ordineInserimento);
											Notification.show(
													"Modificato  Prodotto Codice:"
															+ prodottoOrdinato.getCodiceprodotto(),
													Type.TRAY_NOTIFICATION);
											update();
										} catch (GestoreException e) {
											Notification.show(" Modificato Ordine Prodotto");

											e.printStackTrace();
										}

										update();
										updateTableFooter();
										w.close();
									}
								});
								ok.setClickShortcut(KeyCode.ENTER, null);
								addComponent(ok);

								Button delete = new Button("Elimina");
								delete.addStyleName("wide");
								delete.addStyleName("attention");
								delete.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										try {
											bd.deleteProdottoOrdinato(
													numeroOrdine,
													prodottoOrdinato
															.getCodiceprodotto());
											Notification
													.show("Eliminato prodotto CODICE: ",
															prodottoOrdinato
																	.getCodiceprodotto()
																	+ "dell' ordine: "
																	+ numeroOrdine,
															Type.TRAY_NOTIFICATION);
											update();
										} catch (GestoreException e) {
											e.printStackTrace();
										}
										w.close();
									}
								});
								addComponent(delete);
							}
						});

					}
				});

			}

		});
		updateTableFooter();

	}

	private void updateTableFooter() {
		// calcolo preventivo

		try {
			elencoordine = bd.getDettaglioProdottiOrdine(numeroOrdine);
		} catch (GestoreException e) {
			e.printStackTrace();
		}
		preventivo = 0.0;
		BigDecimal resultRounded = null;
		for (DettaglioOrdine o : elencoordine) {
			preventivo = preventivo + o.getQuantita() * o.getUnitaprodotto()
					* o.getPrezzoprodotto();
			resultRounded = new BigDecimal(preventivo).setScale(2,
					BigDecimal.ROUND_HALF_UP);
		}
		// Set the footers
		listaordineattuale.setFooterVisible(true);
		listaordineattuale.setColumnFooter("descrizione", "Totale");
		listaordineattuale.setColumnFooter("importo",
				String.valueOf(resultRounded));
	}

	private void initBottomComponent() {

		HorizontalLayout bottom = new HorizontalLayout();
		bottom.setWidth("100%");
		bottom.addStyleName("toolbar");
		addComponent(bottom);
		final Label title = new Label(aziendaSelezionata);
		title.setSizeUndefined();
		title.addStyleName("h2");
		bottom.addComponent(title);
		bottom.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		bottom.setExpandRatio(title, 1);

		Button cancel = new Button("Cancella");
		cancel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					bd.deleteOrdine(numeroOrdine);
					Notification.show(" Ok ");
					update();
				} catch (GestoreException e) {
					e.printStackTrace();
				}
				cancel();
			}
		});
		bottom.addComponent(cancel);
		bottom.setComponentAlignment(cancel, Alignment.MIDDLE_LEFT);

		Button invia = new Button("Invia");
		invia.addStyleName("default");
		invia.setDescription("Invia Ordine");
		invia.addClickListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				final Window w = new Window("Invio Ordine");

				w.setModal(true);
				w.setClosable(false);
				w.setResizable(false);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					Label name = new Label("Dettagli Ordine: " + numeroOrdine);

					PopupDateField datatassativa = new PopupDateField(
							"Consegna Tassativa");
					final ObjectProperty<Integer> numeroRif = new ObjectProperty<Integer>(
							numeroOrdine);
					TextField numeroRiferimento = new TextField(
							"Cod. Riferimento", numeroRif);
					TextArea note = new TextArea("Note");

					{
						addComponent(new FormLayout() {
							{
								setSizeUndefined();
								setMargin(true);
								addComponent(name);

								try {
									elencoordine = bd.getDettaglioProdottiOrdine(numeroOrdine);
									System.out
											.println("------     DESTRA        ------"
													+ elencoordine);
								} catch (GestoreException e) {
									e.printStackTrace();
								}

								final IndexedContainer containerOrdine = new IndexedContainer();

								containerOrdine.addContainerProperty("numero",
										Integer.class, "");
								containerOrdine.addContainerProperty(
										"codiceprodotto", Integer.class, "");
								containerOrdine
										.addContainerProperty(
												"descrizioneprodotto",
												String.class, "");
								containerOrdine.addContainerProperty(
										"prezzoprodotto", Double.class, 0);
								containerOrdine.addContainerProperty(
										"unitaprodotto", Integer.class, "");
								containerOrdine.addContainerProperty(
										"quantita", Integer.class, 0);
								containerOrdine.addContainerProperty("importo",
										Double.class, 0);

								for (DettaglioOrdine o : elencoordine) {
									Item item = containerOrdine.addItem(o);
									item.getItemProperty("numero").setValue(
											o.getNumeroordine());
									item.getItemProperty("numero").setReadOnly(
											true);
									item.getItemProperty("codiceprodotto")
											.setValue(o.getCodiceprodotto());
									item.getItemProperty("codiceprodotto")
											.setReadOnly(true);
									item.getItemProperty("descrizioneprodotto")
											.setValue(
													o.getDescrizioneprodotto());
									item.getItemProperty("descrizioneprodotto")
											.setReadOnly(true);
									item.getItemProperty("prezzoprodotto")
											.setValue(o.getPrezzoprodotto());
									item.getItemProperty("prezzoprodotto")
											.setReadOnly(true);
									item.getItemProperty("unitaprodotto")
											.setValue(o.getUnitaprodotto());
									item.getItemProperty("unitaprodotto")
											.setReadOnly(true);
									item.getItemProperty("quantita").setValue(
											o.getQuantita());
									item.getItemProperty("importo").setValue(
											o.getQuantita()
													* o.getUnitaprodotto()
													* o.getPrezzoprodotto());
									item.getItemProperty("importo")
											.setReadOnly(true);

								}

								listaordineattuale.setEditable(false);
								listaordineattuale.setPageLength(9);
								listaordineattuale
										.setContainerDataSource(containerOrdine);
								listaordineattuale.setSelectable(true);
								listaordineattuale.setMultiSelect(false);
								listaordineattuale.setImmediate(true);
								listaordineattuale
										.setColumnCollapsingAllowed(true);
								listaordineattuale.setColumnHeader(
										"prezzoprodotto", "prezzo");
								listaordineattuale.setColumnHeader(
										"descrizioneprodotto", "descrizione");
								listaordineattuale.setColumnWidth(
										"prezzoprodotto", 63);
								listaordineattuale.setColumnWidth("quantita",
										63);
								listaordineattuale.setColumnCollapsed("numero",
										true);
								listaordineattuale.setColumnCollapsed(
										"codiceprodotto", true);
								listaordineattuale.setColumnCollapsed(
										"unitaprodotto", true);

								addComponent(listaordineattuale);

								datatassativa.setValue(new Date());
								datatassativa.setImmediate(true);
								datatassativa.setTimeZone(TimeZone
										.getTimeZone("UTC"));
								datatassativa.setLocale(Locale.ITALY);
								datatassativa.setResolution(Resolution.DAY);
								addComponent(datatassativa);

								addComponent(numeroRiferimento);
								addComponent(note);

							}
						});

						addComponent(new HorizontalLayout() {
							{
								setMargin(true);
								setSpacing(true);
								addStyleName("footer");
								setWidth("100%");

								Button cancel = new Button("Annulla");
								cancel.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										update();
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
										title.setValue(name.getValue());
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
		bottom.addComponent(invia);
		bottom.setComponentAlignment(invia, Alignment.MIDDLE_LEFT);
	}

	public void initBoard() {

		rowLeft = new HorizontalLayout();
		rowLeft.setSizeFull();
		rowLeft.setMargin(new MarginInfo(true, true, false, true));
		rowLeft.setSpacing(true);
		addComponent(rowLeft);
		setExpandRatio(rowLeft, 1.5f);

		// PANNELLO SINISTRA
		CssLayout leftPanel = createPanel(listaProdotti);
		rowLeft.addComponent(leftPanel);

		// Pannello DESTRO
		CssLayout rightPanel = createPanel(listaordineattuale);
		rowLeft.addComponent(rightPanel);

	}

	public void update() {
		removeAllComponents();
		initTopComponent();
		nuovo.setEnabled(false);
		initLeftTable();
		initRightTable();
		initBoard();
		initBottomComponent();
	}

	public void cancel() {
		removeAllComponents();
		initTopComponent();
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
		configure.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Not implemented in this demo");
			}
		});
		panel.addComponent(configure);

		panel.addComponent(content);
		return panel;
	}

	Window notifications;

	private void buildNotifications(ClickEvent event) {
		notifications = new Window("Ordini");
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);
		notifications.setContent(l);
		notifications.setWidth("300px");
		notifications.addStyleName("notifications");
		notifications.setClosable(false);
		notifications.setResizable(false);
		notifications.setDraggable(false);
		notifications.setPositionX(event.getClientX() - event.getRelativeX());
		notifications.setPositionY(event.getClientY() - event.getRelativeY());
		notifications.setCloseShortcut(KeyCode.ESCAPE, null);

		Label label = new Label("<hr><b>" + "17/08/2013" + " " + "CdS" + " ",
				ContentMode.HTML);
		l.addComponent(label);

		label = new Label(
				"<hr><b>" + "17/08/2013" + " " + "" + " Borgo D' ora",
				ContentMode.HTML);
		l.addComponent(label);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
