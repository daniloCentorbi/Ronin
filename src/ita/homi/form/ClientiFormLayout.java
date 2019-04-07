package ita.homi.form;

import java.util.Arrays;

import ita.homi.model.Cliente;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@SuppressWarnings("unchecked")
public class ClientiFormLayout extends Form {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public ClientiFormLayout(BeanItem<Cliente> itemBean, Cliente cliente) {
		{

			setCaption("Gestione Cliente");

			// Set up buffering
			setInvalidCommitted(false); // no invalid values in datamodel

			// FieldFactory for customizing the fields and adding validators
			setFormFieldFactory(new MessageFieldFactory());
			setItemDataSource(itemBean); // bind to POJO via BeanItem

			// Determines which properties are shown, and in which order:
			setVisibleItemProperties(Arrays.asList(new String[] { "ragione",
					"indirizzo", "cap", "citta", "provincia",
					"piva", "codicefiscale", "telefono", "fax",
					"mail", "pec", "sito", "categoria" , "username" , "password"
					}));
		}

	}
}

@SuppressWarnings("unchecked")
class MessageFieldFactory extends DefaultFieldFactory {

	private static final long serialVersionUID = 1L;
	private static final String COMMON_FIELD_WIDTH = "12em";
	private static final String[] CATEGORIAList = new String[] { "INGROSSO",
			"G.D.O.", "AMBULANTE", "DETTAGLIO", "SUP.AFFILIATO",
			"SUP.INDIPENDENTE", "SUP.ASSOCIATO", "RISTORAZIONE COLLETTIVA",
			"RISTORANTE", "CASEIFICIO", "PASTIFICIO", "SALUMIFICIO" };
	final ComboBox categoriaSelect = new ComboBox("Categoria");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Field createField(Item item, Object propertyId,
			Component uiContext) {
		Field f;
		
		
		for (int i = 0; i < CATEGORIAList.length; i++) {
			categoriaSelect.addItem(CATEGORIAList[i]);
		}
	

		// formatto i campi
		if ("categoria".equals(propertyId)) {
			categoriaSelect.setRequired(true);
			categoriaSelect.setRequiredError("Seleziona la categoria");
			categoriaSelect.setInputPrompt("Seleziona");
			categoriaSelect.setWidth(COMMON_FIELD_WIDTH);
			return categoriaSelect;
		} else {
			// Use the super class to create a suitable field base on the
			// property type.
			f = super.createField(item, propertyId, uiContext);
		}
		
		if ("ragione".equals(propertyId)) {
			TextField tf = (TextField) f;
			tf.setRequired(true);
			tf.setRequiredError("Inserisci una Ragione Sociale");
			tf.setWidth(COMMON_FIELD_WIDTH);
			tf.addValidator(new StringLengthValidator(
					"Inserisci un nome tra 3-50 caratteri", 3, 50, false));
		}  
		return f;
	}
}
