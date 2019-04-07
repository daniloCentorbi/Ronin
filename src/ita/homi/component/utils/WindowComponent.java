package ita.homi.component.utils;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class WindowComponent extends VerticalLayout{

	public WindowComponent(){
		
		final Window w = new Window("Seleziona Azienda");

		w.setModal(true);
		w.setClosable(true);
		w.setResizable(false);
		w.addStyleName("edit-dashboard");

		getUI().addWindow(w);

		w.setContent(new VerticalLayout() {
			//Label name = new Label("Gestione Ordine    " + numeroOrdine);
			{

				addComponent(new VerticalLayout() {
					{
						// combobox = new ComboBox("Seleziona azienda");

						addComponent(new Label("_________---------_________------"));
						addComponent(new Label("_________]]]]]]]]]_________------"));
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
								w.close();
							}
						});
						cancel.setClickShortcut(KeyCode.ESCAPE, null);
						addComponent(cancel);
						setExpandRatio(cancel, 1);
						setComponentAlignment(cancel,
								Alignment.TOP_RIGHT);

						Button ok = new Button("Procedi");
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
		
	}
}
