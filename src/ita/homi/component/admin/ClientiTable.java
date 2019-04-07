package ita.homi.component.admin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ita.homi.delegate.BusinessDelegate;
import ita.homi.form.ClientiFormLayout;
import ita.homi.model.Cliente;
import ita.homi.utils.GestoreException;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ClientiTable extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(ClientiTable.class
			.getName());
	protected BusinessDelegate bd = new BusinessDelegate();
	List<Cliente> elencoclienti;
	Table table;
	@SuppressWarnings("deprecation")
	Form clientiForm;

	@SuppressWarnings({ "unchecked", "deprecation", "serial" })
	public ClientiTable() {
		try {
			elencoclienti = bd.getAllClienti();
		} catch (GestoreException e) {
			logger.notifyAll();
			e.printStackTrace();
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
		table.setPageLength(12);
		// table.setColumnAlignment("Seats", Align.RIGHT);
		table.setSelectable(true);
		table.setMultiSelect(false);
		table.addGeneratedColumn("SEND MAIL", new ColumnGenerator() {	
			@Override
			public Component generateCell(Table source, Object itemId, Object columnId) {
				Button button = new Button();
				button.setIcon(new ThemeResource("img/messaggi.jpg"));
				button.setData(itemId);
				button.addListener(ClickEvent.class, ClientiTable.this, "sendMail");
				return button;
			}
		});
		table.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				Item item = table.getItem(event.getItemId());
				final Cliente clienteSelezionato = new Cliente(((Integer) item
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
				

				
				final Window w = new Window("Modifica Cliente");
				w.setWidth("680px");
				w.setHeight("600px");
				w.setModal(true);
				w.setClosable(true);
				w.setResizable(true);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					{
						addComponent(new VerticalLayout() {
							{

								// Wrap it in a BeanItem
								BeanItem<Cliente> itemBean = new BeanItem<Cliente>(
										clienteSelezionato);

								// the Form
								clientiForm = new ClientiFormLayout(itemBean,clienteSelezionato);

								addComponent(clientiForm);

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
										clientiForm.commit();
										
										Notification.show(clienteSelezionato.getRagione() + "-----"
												+ clienteSelezionato.getCategoria());
										
										
										
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

		// BOTTONE ADD CLIENTE
		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setSpacing(true);
		top.addStyleName("toolbar");
		addComponent(top);

		Button aggiungi = new Button();
		aggiungi.setDescription("Nuovo Cliente");
		aggiungi.addStyleName("borderless");
		aggiungi.addStyleName("notifications");
		aggiungi.addStyleName("icon-only");
		aggiungi.addStyleName("icon-bell");

		aggiungi.setIcon(new ThemeResource("img/nuovoGruppo.jpg"));
		aggiungi.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// ((IsoUI) getUI()).clearDashboardButtonBadge();

				final Window w = new Window("Nuovo Cliente");
				w.setWidth("680px");
				w.setHeight("600px");
				w.setModal(true);
				w.setClosable(true);
				w.setResizable(true);
				w.addStyleName("edit-dashboard");

				getUI().addWindow(w);

				w.setContent(new VerticalLayout() {
					{
						addComponent(new FormLayout() {
							{
								// Create an instance of the bean
								Cliente cliente = new Cliente();

								// Wrap it in a BeanItem
								BeanItem<Cliente> item = new BeanItem<Cliente>(
										cliente);

								// the Form
								clientiForm = new ClientiFormLayout(item,cliente);

								addComponent(clientiForm);
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
										clientiForm.commit();
										
										Notification.show(clientiForm.getData() + "-----");
										
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

		top.addComponent(aggiungi);
		
		addComponent(table);
		

	}

	
	@SuppressWarnings("unchecked")
	public ClientiTable(boolean flag){
		try {
			elencoclienti = bd.getAllClienti();
		} catch (GestoreException e) {
			logger.notifyAll();
			e.printStackTrace();
		}

		final Container container = new IndexedContainer();
		container.addContainerProperty("codice", Integer.class, "");
		container.addContainerProperty("ragione", String.class, "");
		container.addContainerProperty("telefono", String.class, 0);
		
		for (Cliente o : elencoclienti) {
			Item item = container.addItem(o);
			item.getItemProperty("codice").setValue(o.getCodice());
			item.getItemProperty("ragione").setValue(o.getRagione());
			item.getItemProperty("telefono").setValue(o.getTelefono());
			
		}

		table = new Table();
		table.addStyleName("borderless");
		table.setContainerDataSource(container);
		table.setPageLength(5); 
		table.setColumnHeader("ragione", "Ragione Sociale");
		table.setColumnCollapsingAllowed(true);
		table.setColumnCollapsed("codice", true);
		table.setSelectable(true);
		table.setMultiSelect(false);
		table.addGeneratedColumn("Mail", new ColumnGenerator() {	
			@Override
			public Component generateCell(Table source, Object itemId, Object columnId) {
				Button button = new Button();
				button.setDescription("Invia Mail");
		        button.addStyleName("borderless");
		        button.addStyleName("icon-only");
		        button.addStyleName("icon-bell");
				button.setIcon(new ThemeResource("img/messaggi.jpg"));
				button.setData(itemId);
				button.addListener(ClickEvent.class, ClientiTable.this, "sendMail");
				return button;
			}
		});
		table.setColumnWidth("Mail",89);
		
		addComponent(table);
		
	}
	
	public void sendMail(ClickEvent event) {
		Object itemId = event.getButton().getData();
		Cliente cliente = (Cliente) itemId;
		
		Notification.show(cliente.getRagione());
		
		
		//container.removeItem(itemId);
		/*
		try {
			 bd.delete(message);
			_log.info("messaggio eliminato");
		} catch (AdviceException e) {
			_log.error(e);
			e.printStackTrace();
		}
		*/
	}
	
	
	
	public void delete(ClickEvent event) {
		Object itemId = event.getButton().getData();
		Cliente cliente = (Cliente) itemId;
		
		
		
		
		//container.removeItem(itemId);
	/*	
		try {
			 bd.delete(message);
			
		} catch (GestoreException e) {
			;
			e.printStackTrace();
		}
		*/
	}
	
}
