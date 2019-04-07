package ita.homi.window;

import ita.homi.model.Cliente;

import java.util.List;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ComunicazionePreview extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	List<Cliente> elencoclienti;
	Window window;

	public ComunicazionePreview(Window modalwindow, List<Cliente> listaclienti ){
		elencoclienti = listaclienti;
		window = modalwindow;
		
        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        addComponent(labels);
		
        
        
        
        
	}

}
