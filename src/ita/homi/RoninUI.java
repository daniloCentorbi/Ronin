package ita.homi;

import ita.homi.model.Cliente;
import ita.homi.view.admin.ArchivioView;
import ita.homi.view.admin.CalendarioView;
import ita.homi.view.admin.CatalogoView;
import ita.homi.view.admin.ComunicazioniView;
import ita.homi.view.admin.ContattiView;
import ita.homi.view.admin.DashboardView;
import ita.homi.view.admin.NuovoView;
import ita.homi.view.admin.ReportsView;
import ita.homi.view.clienti.ArchivioClientiView;
import ita.homi.view.clienti.ComunicazioniClientiView;
import ita.homi.view.clienti.OrdiniClientiView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@Theme("dashboard")
@SuppressWarnings("serial")
public class RoninUI extends UI {

	private static final long serialVersionUID = 1L;

	CssLayout root = new CssLayout();
	VerticalLayout loginLayout;

	CssLayout menu = new CssLayout();
	CssLayout content = new CssLayout();
	
	Cliente logggato = new Cliente();

	HashMap<String, Class<? extends View>> routesAdmin = new HashMap<String, Class<? extends View>>() {
		{
			put("/pannello", DashboardView.class);
			put("/calendario", CalendarioView.class);	
			put("/contatti", ContattiView.class);			
			put("/comunicazioni", ComunicazioniView.class);		
			put("/catalogo", CatalogoView.class);
			put("/archivio", ArchivioView.class);
			put("/nuovo", NuovoView.class);
		}
	};

	HashMap<String, Class<? extends View>> routesClient = new HashMap<String, Class<? extends View>>() {
		{
			put("/comunicazioni", ComunicazioniClientiView.class);
			put("/calendario", OrdiniClientiView.class);
			put("/archivio", ArchivioClientiView.class);
		}
	};

	HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();

	private Navigator nav;

	@Override
	protected void init(VaadinRequest request) {

		setLocale(Locale.ITALIAN);
		setContent(root);
		root.addStyleName("root");
		root.setSizeFull();
		// Unfortunate to use an actual widget here, but since CSS generated
		// elements can't be transitioned yet, we must
		Label bg = new Label();
		bg.setSizeUndefined();
		bg.addStyleName("login-bg");
		root.addComponent(bg);

		buildLoginView(false);
	}

	private void buildLoginView(boolean exit) {
		if (exit) {
			root.removeAllComponents();
		}

		addStyleName("login");

		loginLayout = new VerticalLayout();
		loginLayout.setSizeFull();
		loginLayout.addStyleName("login-layout");
		root.addComponent(loginLayout);

		final CssLayout loginPanel = new CssLayout();
		loginPanel.addStyleName("login-panel");

		HorizontalLayout labels = new HorizontalLayout();
		labels.setWidth("100%");
		labels.setMargin(true);
		labels.addStyleName("labels");
		loginPanel.addComponent(labels);

		Label welcome = new Label("Benvenuto");
		welcome.setSizeUndefined();
		welcome.addStyleName("h4");
		labels.addComponent(welcome);
		labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

		Label title = new Label("Inserisci Credenziali");
		title.setSizeUndefined();
		title.addStyleName("h2");
		title.addStyleName("light");
		labels.addComponent(title);
		labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.setMargin(true);
		fields.addStyleName("fields");

		final TextField username = new TextField("Username");
		username.focus();
		fields.addComponent(username);

		final PasswordField password = new PasswordField("Password");
		fields.addComponent(password);

		final Button signin = new Button("Accedi");
		signin.addStyleName("default");
		fields.addComponent(signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		final ShortcutListener enter = new ShortcutListener("Accedi",
				KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				signin.click();
			}
		};

		signin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (username.getValue() != null
						&& username.getValue().equals("")
						&& password.getValue() != null
						&& password.getValue().equals("")) {
					signin.removeShortcutListener(enter);
					buildAdminView();
					
				} else {
					if (username.getValue() != null
							&& username.getValue().equals("cliente")
							&& password.getValue() != null
							&& password.getValue().equals("cliente")) {
						signin.removeShortcutListener(enter);
						buildClientView();
					}

					else {
						if (loginPanel.getComponentCount() > 2) {
							// Remove the previous error message
							loginPanel.removeComponent(loginPanel
									.getComponent(2));
						}
						// Add new error message
						Label error = new Label(
								"username o password Non Validi. <span>Riprova</span>",
								ContentMode.HTML);
						error.addStyleName("error");
						error.setSizeUndefined();
						error.addStyleName("light");
						// Add animation
						error.addStyleName("v-animate-reveal");
						loginPanel.addComponent(error);
						username.focus();
					}
				}
			}
		});

		signin.addShortcutListener(enter);

		loginPanel.addComponent(fields);

		loginLayout.addComponent(loginPanel);
		loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
	}

	private void buildAdminView() {

		nav = new Navigator(this, content);

		for (String route : routesAdmin.keySet()) {
			nav.addView(route, routesAdmin.get(route));
		}

		removeStyleName("login");
		root.removeComponent(loginLayout);

		root.addComponent(new HorizontalLayout() {
			{
				setSizeFull();
				addStyleName("main-view");
				addComponent(new VerticalLayout() {
					// Sidebar
					{
						addStyleName("sidebar");
						setWidth(null);
						setHeight("100%");

						/*
						 * // Branding element
						addComponent(new CssLayout() {
							{
								addStyleName("branding");
								Label logo = new Label("Benvenuto",
										ContentMode.HTML);
								logo.setSizeUndefined();
								addComponent(logo);
								 addComponent(new Image(null, new
								 ThemeResource("img/branding.png")));
							}
						});
						*/

						// Main menu
						addComponent(menu);
						setExpandRatio(menu, 1);

						// User menu
						addComponent(new VerticalLayout() {
							{
								setSizeUndefined();
								addStyleName("user");
								Image profilePic = new Image(
										null,
										new ThemeResource("img/profile-pic.png"));
								profilePic.setWidth("34px");
								addComponent(profilePic);
								Label userName = new Label("Peppe");
								userName.setSizeUndefined();
								addComponent(userName);

								Command cmd = new Command() {
									@Override
									public void menuSelected(
											MenuItem selectedItem) {
										Notification.show("Da implementare");
									}
								};
								MenuBar settings = new MenuBar();
								MenuItem settingsMenu = settings.addItem("",
										null);
								settingsMenu.setStyleName("icon-cog");
								settingsMenu.addItem("Personalizza", cmd);
								 settingsMenu.setIcon(new
								 ThemeResource("img/configure.jpg"));
								settingsMenu.addItem("Preferenze", cmd);
								settingsMenu.addSeparator();
								settingsMenu.addItem("Help", cmd);
								addComponent(settings);

								Button exit = new NativeButton("Exit");
								exit.setStyleName("icon-cog");
								exit.setIcon(new
								ThemeResource("img/ext.jpg"));
								exit.setDescription("Sign Out");
								addComponent(exit);
								exit.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										buildLoginView(true);
									}
								});
							}
						});
					}
				});
				// Content
				addComponent(content);
				content.setSizeFull();
				content.addStyleName("view-content");
				setExpandRatio(content, 1);
			}

		});

		menu.removeAllComponents();

		for (final String view : new String[] { "pannello", "calendario", "contatti", "comunicazioni",
				 "catalogo", "archivio", "nuovo" }) {
			Button b = new NativeButton(view.substring(0, 1).toUpperCase()
					+ view.substring(1).replace('-', ' '));
			b.addStyleName("nob");

			b.setIcon(new ThemeResource("img/" + view + ".jpg"));
			System.out.println(view);
			b.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					clearMenuSelection();
					event.getButton().addStyleName("selected");
					if (!nav.getState().equals("/" + view))
						nav.navigateTo("/" + view);
				}
			});

			menu.addComponent(b);

			viewNameToMenuButton.put("/" + view, b);
		}
		menu.addStyleName("menu");
		menu.setHeight("100%");

		String f = Page.getCurrent().getUriFragment();
		if (f != null && f.startsWith("!")) {
			f = f.substring(1);
		}
		if (f == null || f.equals("") || f.equals("/")) {
			nav.navigateTo("/pannello");
			menu.getComponent(0).addStyleName("selected");
		} else {
			nav.navigateTo(f);
			viewNameToMenuButton.get(f).addStyleName("selected");
		}

		nav.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				View newView = event.getNewView();
			}
		});

	}

	private void buildClientView() {

		nav = new Navigator(this, content);

		for (String route : routesClient.keySet()) {
			nav.addView(route, routesClient.get(route));
		}

		removeStyleName("login");
		root.removeComponent(loginLayout);

		root.addComponent(new HorizontalLayout() {
			{
				setSizeFull();
				addStyleName("main-view");
				addComponent(new VerticalLayout() {
					// Sidebar
					{
						addStyleName("sidebar");
						setWidth(null);
						setHeight("100%");

						// Branding element
						addComponent(new CssLayout() {
							{
								addStyleName("branding");
								Label logo = new Label("Benvenuto",
										ContentMode.HTML);
								logo.setSizeUndefined();
								addComponent(logo);
								// addComponent(new Image(null, new
								// ThemeResource("img/build.jpeg")));
							}
						});

						// Main menu
						addComponent(menu);
						setExpandRatio(menu, 1);

						// User menu
						addComponent(new VerticalLayout() {
							{
								setSizeUndefined();
								addStyleName("user");
								Image profilePic = new Image(
										null,
										new ThemeResource("img/profile-pic.png"));
								profilePic.setWidth("34px");
								addComponent(profilePic);
								Label userName = new Label("Loggato");
								userName.setSizeUndefined();
								addComponent(userName);

								Command cmd = new Command() {
									@Override
									public void menuSelected(
											MenuItem selectedItem) {
										Notification.show("Da implementare");
									}
								};
								MenuBar settings = new MenuBar();
								MenuItem settingsMenu = settings.addItem("",
										null);
								settingsMenu.setStyleName("icon-cog");
								settingsMenu.addItem("Personalizza", cmd);
								// settingsMenu.setIcon(new
								// ThemeResource("img/setting.jpg"));
								settingsMenu.addItem("Preferenze", cmd);
								settingsMenu.addSeparator();
								settingsMenu.addItem("Help", cmd);
								addComponent(settings);

								Button exit = new NativeButton("Exit");
								// exit.addStyleName("icon-cancel");
								exit.addStyleName("borderless");
								// exit.setIcon(new
								// ThemeResource("img/exit.jpg"));
								exit.setDescription("Sign Out");
								addComponent(exit);
								exit.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										buildLoginView(true);
									}
								});
							}
						});
					}
				});
				// Content
				addComponent(content);
				content.setSizeFull();
				content.addStyleName("view-content");
				setExpandRatio(content, 1);
			}

		});

		menu.removeAllComponents();

		for (final String view : new String[] { "comunicazioni", "ordini",
				"archivio" }) {
			Button b = new NativeButton(view.substring(0, 1).toUpperCase()
					+ view.substring(1).replace('-', ' '));
			b.addStyleName("nob");

			b.setIcon(new ThemeResource("img/" + view + ".jpg"));
			System.out.println(view);
			b.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					clearMenuSelection();
					event.getButton().addStyleName("selected");
					if (!nav.getState().equals("/" + view))
						nav.navigateTo("/" + view);
				}
			});

			menu.addComponent(b);

			viewNameToMenuButton.put("/" + view, b);
		}
		menu.addStyleName("menu");
		menu.setHeight("100%");

		String f = Page.getCurrent().getUriFragment();
		if (f != null && f.startsWith("!")) {
			f = f.substring(1);
		}
		if (f == null || f.equals("") || f.equals("/")) {
			nav.navigateTo("/comunicazioni");
			menu.getComponent(0).addStyleName("selected");
		} else {
			nav.navigateTo(f);
			viewNameToMenuButton.get(f).addStyleName("selected");
		}

		nav.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				View newView = event.getNewView();
			}
		});

	}

	private void clearMenuSelection() {
		for (Iterator<Component> it = menu.getComponentIterator(); it.hasNext();) {
			Component next = it.next();
			if (next instanceof NativeButton) {
				next.removeStyleName("selected");
			} else if (next instanceof DragAndDropWrapper) {
				// Wow, this is ugly (even uglier than the rest of the code)
				((DragAndDropWrapper) next).iterator().next()
						.removeStyleName("selected");
			}
		}
	}

	void clearDashboardButtonBadge() {
		viewNameToMenuButton.get("/pannello").setCaption("pannello");
	}

}