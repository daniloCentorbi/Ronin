package ita.homi.component.admin;

import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.Cliente;
import ita.homi.model.Comunicazione;
import ita.homi.model.Gruppo;
import ita.homi.utils.GestoreException;

import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("unchecked")
public class ContattiClientiComponent extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(ContattiClientiComponent.class
			.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	List<Cliente> elencoclienti;
	List<Cliente> destinatari;
	Gruppo gruppo = new Gruppo();
	Table table;
	HorizontalLayout bottomLayout;
	Label anteprima;
	String valueString;
	String oggetto;
	Button invio;

	public ContattiClientiComponent() {
		addComponent(new Label("OPS|!"));
	}

	@SuppressWarnings("deprecation")
	public ContattiClientiComponent(Gruppo gruppoinviato) {
		if (gruppoinviato == null) {
			gruppo = new Gruppo(1, "TUTTI", 0);
		} else {
			gruppo = gruppoinviato;
		}
		addStyleName("comunicazioni");

		if (gruppo.getCodice() == 1) {
			try {
				elencoclienti = bd.getAllClienti();
			} catch (GestoreException e) {
				logger.notifyAll();
				e.printStackTrace();
			}
		} else {
			try {
				elencoclienti = bd.getGruppoClienti(gruppo);
			} catch (GestoreException e) {
				logger.notifyAll();
				e.printStackTrace();
			}
		}

		final Container container = new IndexedContainer();
		container.addContainerProperty("codice", Integer.class, "");
		container.addContainerProperty("ragione", String.class, "");
		container.addContainerProperty("citta", String.class, "");
		container.addContainerProperty("piva", Integer.class, "");
		container.addContainerProperty("telefono", String.class, 0);
		container.addContainerProperty("mail", String.class, "");
		container.addContainerProperty("categoria", String.class, 0);

		for (Cliente o : elencoclienti) {
			Item item = container.addItem(o);
			item.getItemProperty("codice").setValue(o.getCodice());
			item.getItemProperty("ragione").setValue(o.getRagione());
			item.getItemProperty("citta").setValue(o.getCitta());
			item.getItemProperty("piva").setValue(o.getPiva());
			item.getItemProperty("telefono").setValue(o.getTelefono());
			item.getItemProperty("mail").setValue(o.getMail());
			item.getItemProperty("categoria").setValue(o.getCategoria());
		}

		table = new Table();
		table.addStyleName("borderless");
		table.setContainerDataSource(container);
		table.setColumnHeader("ragione", "Ragione Sociale");
		table.setColumnHeader("citta", "Città");
		table.setColumnHeader("telefono", "Telefono");
		table.setColumnHeader("mail", "E-Mail");
		table.setColumnHeader("categoria", "Categoria");
		table.setColumnCollapsingAllowed(true);
		table.setColumnCollapsed("codice", true);
		table.setColumnCollapsed("piva", true);
		// table.setColumnAlignment("Seats", Align.RIGHT);
		table.setSelectable(true);
		table.setMultiSelect(false);
		Properties props = new Properties();
		
		
		
		props.put("mail.pop3.host" , "pop.gmail.com");
		props.put("mail.pop3.user" , "username");
		// Start SSL connection
		props.put("mail.pop3.socketFactory" , 995 );
		props.put("mail.pop3.socketFactory.class" , "javax.net.ssl.SSLSocketFactory" );
		props.put("mail.pop3.port" , 995);
		
		
		
		table.setImmediate(true);
		table.setHeight("190px");
		table.setWidth("100%");
		table.addGeneratedColumn("", new ColumnGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component generateCell(final Table source,
					final Object itemId, final Object columnId) {
				final Cliente cliente = (Cliente) itemId;
				final CheckBox checkBox = new CheckBox();
				checkBox.setValue(true);
				checkBox.setImmediate(true);
				checkBox.addListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						cliente.setSelezionato((Boolean) event.getProperty()
								.getValue());
						System.out.println(cliente.getSelezionato()
								+ "  Ragione Sociale: " + cliente.getRagione());
						if (cliente.isSelezionato()) {
							checkBox.setValue(true);
							// Notification.show("SELEZIONATO");
						} else {
							checkBox.setValue(false);
							Notification.show("Cliente DESELEZIONATO");
						}
					}
				});
				return checkBox;
			}
		});
		table.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {

				Item item = table.getItem(event.getItemId());
				Cliente clienteSelezionato = new Cliente(((Integer) item
						.getItemProperty("codice").getValue()).intValue(),
						(String) item.getItemProperty("ragione").getValue(),
						((Integer) item.getItemProperty("piva").getValue())
								.intValue(), (String) item.getItemProperty(
								"telefono").getValue(), (String) item
								.getItemProperty("mail").getValue(),
						(String) item.getItemProperty("categoria").getValue());
				System.out.println("Selezionato: "
						+ clienteSelezionato.getRagione() + " Con codice: "
						+ clienteSelezionato.getCodice());
				Notification.show(" " + clienteSelezionato.getRagione());
			}
		});

		// BOTTONE INVIO
		invio = new Button("INVIO", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				final Window w = new Window("Anteprima Comunicazione");

				w.setModal(true);
				w.setClosable(true);
				w.setResizable(false);
				w.setWidth("600px");
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				// w.setContent(new ComunicazionePreview(w, elencoclienti));

				w.setContent(new VerticalLayout() {
					private static final long serialVersionUID = 1L;
					{

						VerticalLayout WindowsLabel = new VerticalLayout();
						WindowsLabel.setWidth("100%");
						WindowsLabel.setMargin(true);
						WindowsLabel.addStyleName("labels");
						// aggiungo alla window
						addComponent(WindowsLabel);

						Label titolo = new Label("Lista Destinatari \n");
						titolo.addStyleName("h1");
						WindowsLabel.addComponent(titolo);

						for (Cliente o : elencoclienti) {
							if (o.getSelezionato()) {
								Label destinatario = new Label(o.getRagione()
										+ "\n");
								destinatario.addStyleName("h4");
								// destinatari.add(o);
								WindowsLabel.addComponent(destinatario);
								System.out.println("SELEZIONATO: "
										+ o.getRagione());
							}
						}

						Label titoloOggetto = new Label();
						titoloOggetto.addStyleName("h1");
						titoloOggetto.setValue("Oggetto: \n");
						WindowsLabel.addComponent(titoloOggetto);

						Label oggettoLabel = new Label();
						oggettoLabel.setStyleName("h3");
						oggettoLabel.addStyleName("light");
						oggettoLabel.setValue(oggetto);
						WindowsLabel.addComponent(oggettoLabel);

						Label titoloTesto = new Label();
						titoloTesto.addStyleName("h1");
						titoloTesto.setValue("Testo Del Messaggio: \n");
						WindowsLabel.addComponent(titoloTesto);

						Label testoLabel = new Label(valueString,Label.CONTENT_XHTML.HTML);
						testoLabel.setStyleName("h5");
						testoLabel.addStyleName("light");
						//testoLabel.setValue(valueString);
						WindowsLabel.addComponent(testoLabel);

						addComponent(new HorizontalLayout() {
							private static final long serialVersionUID = 1L;
							{
								setMargin(true);
								setSpacing(true);
								addStyleName("footer");
								setWidth("100%");

							
								Button cancel = new Button("Cancel");
								cancel.addClickListener(new ClickListener() {
									private static final long serialVersionUID = 1L;

									@Override
									public void buttonClick(ClickEvent event) {
										w.close();
										Notification
												.show("Messaggio Non inviato");
									}
								});
								cancel.setClickShortcut(KeyCode.ESCAPE, null);
								addComponent(cancel);
								setExpandRatio(cancel, 1);
								setComponentAlignment(cancel,
										Alignment.TOP_RIGHT);

								Button ok = new Button("INVIO");
								ok.addStyleName("wide");
								ok.addStyleName("default");
								ok.addClickListener(new ClickListener() {
									private static final long serialVersionUID = 1L;

									@SuppressWarnings({ "static-access",
											"restriction" })
									@Override
									public void buttonClick(ClickEvent event) {
										Notification.show("Invio Messaggi");
										for (Cliente o : elencoclienti) {
											if (o.getSelezionato()) {
												System.out.println("MESSAGGIIIIIIIII A: "
														+ o.getRagione());
												// destinatari.add(o);
												addComponent(new Label(o
														.getRagione()));

												// SEND MAIL
												Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
												Properties props = new Properties();
												props.setProperty(
														"mail.transport.protocol",
														"smtp");
												props.setProperty("mail.host",
														"smtp.gmail.com");

												props.put("mail.smtp.auth",
														"true");
												props.put("mail.smtp.port",
														"465");

												props.put("mail.debug", "true");
												props.put(
														"mail.smtp.socketFactory.port",
														"465");

												props.put(
														"mail.smtp.socketFactory.class",
														"javax.net.ssl.SSLSocketFactory");
												props.put(
														"mail.smtp.socketFactory.fallback",
														"false");

												Session session = Session
														.getDefaultInstance(
																props,
																new javax.mail.Authenticator() {
																	protected PasswordAuthentication getPasswordAuthentication() {
																		return new PasswordAuthentication(
																				"danilo.centorby@gmail.com",
																				"ottovolante");
																	}
																});
												session.setDebug(true);
												try {
													Transport transport = session
															.getTransport();
													InternetAddress addressFrom = new InternetAddress(
															"danilo.centorby@gmail.com");
													MimeMessage message = new MimeMessage(
															session);
													message.setSender(addressFrom);
													message.setSubject(oggetto);
													message.setContent(valueString,
															"text/plain");
													String sendTo[] = { "danilo.centorbi@hotmail.it" };
													if (sendTo != null) {
														InternetAddress[] addressTo = new InternetAddress[sendTo.length];
														for (int i = 0; i < sendTo.length; i++) {
															addressTo[i] = new InternetAddress(
																	sendTo[i]);
														}
														message.setRecipients(
																Message.RecipientType.TO,
																addressTo);

													}
													transport.connect();
													transport.send(message);
													transport.close();
													System.out.println("Mail inviata a "
															+ o.getRagione());

													Date date = new Date();

													Comunicazione comunicazione = new Comunicazione(
															o.getRagione(),
															oggetto, valueString,
															date);
													try {
														Comunicazione inserita = bd
																.insert(comunicazione);
														logger.log(
																Level.SEVERE,
																"COMUNICAZIONE SALVATA --------------------"
																		+ inserita);
														Notification
																.show("Messaggio Inviato a:",
																		inserita.getDestinatario(),
																		Type.TRAY_NOTIFICATION);
													} catch (GestoreException e) {
														logger.notifyAll();
														e.printStackTrace();
													}

												} catch (MessagingException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}

											}
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

		addComponent(table);

		// senza anteprima non posso inviare
		invio.setVisible(false);

		// container principale basso
		bottomLayout = new HorizontalLayout();
		bottomLayout.setWidth("100%");
		bottomLayout.setMargin(true);

		addComponent(bottomLayout);

		// SplitPanel
		HorizontalSplitPanel composizione = new HorizontalSplitPanel();
		composizione.setSizeFull();
		bottomLayout.addComponent(composizione);

		// Layout SINISTRA(OGGETTO,CORPO,PULSANTI)
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setWidth("100%");
		leftLayout.setMargin(true);
		leftLayout.setSpacing(true);
		leftLayout.addStyleName("fields");

		final TextField areaoggetto = new TextField();
		areaoggetto.setValue("Oggetto");
		areaoggetto.setImmediate(true);
		areaoggetto.setColumns(30);
		areaoggetto.addTextChangeListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(final TextChangeEvent event) {
				oggetto = areaoggetto.getValue().toString();
				anteprima.setValue(" Oggetto Messaggio: " + oggetto
						+ " Testo Del Messaggio:  " + valueString);

			}
		});

		// Campo Corpo
		final String initialText = "";
		final RichTextArea testo = new RichTextArea(null, initialText);
		testo.setImmediate(true);
		testo.setSizeFull();
        testo.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                valueString = String.valueOf(event.getProperty()
                        .getValue());
                Notification.show("Value changed:", valueString,
                        Type.TRAY_NOTIFICATION);
            }
        });
        
		// Container Pulsanti Anteprima/Invio
		HorizontalLayout buttonContainer = new HorizontalLayout();
		final Button button = new Button("Anteprima");
		button.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(Button.ClickEvent event) {
				oggetto = areaoggetto.getValue().toString();
				valueString = testo.getValue().toString();
				
				anteprima.setValue(" Oggetto Messaggio: " + oggetto
						+ " Testo Del Messaggio: " + valueString + "  ");

				// Adesso il pulsante 'invio ' è visibile
				invio.setVisible(true);
				System.out
						.println("richTextArea.getValue().toString()" + valueString);
				// String tutto = areacorpo.getValue();
				// System.out.println("richTextArea.getValue()" + tutto);

			}
		});

		buttonContainer.addComponent(button);
		buttonContainer.addComponent(invio);

		leftLayout.addComponent(areaoggetto);
		leftLayout.addComponent(testo);
		leftLayout.addComponent(buttonContainer);

		// Layout Destra(OGGETTO,CORPO,PULSANTI)
		VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.setWidth("100%");
		rightLayout.setMargin(true);
		rightLayout.setSpacing(true);
		rightLayout.addStyleName("labels");

		// Label ANTEPRIMA
		anteprima = new Label();
		anteprima.addStyleName("h2");
		anteprima.addStyleName("light");
		anteprima.setValue(" Componi Messaggio Per i Clienti del Gruppo: "
				+ gruppo.getTitolo());

		rightLayout.addComponent(anteprima);

		composizione.setFirstComponent(leftLayout);
		composizione.setSecondComponent(rightLayout);

	}

}
