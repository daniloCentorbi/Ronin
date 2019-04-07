package ita.homi.utils;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

@SuppressWarnings("serial")
public abstract class ContactFilter implements Filter {
	private String needle;

	public ContactFilter(String needle) {
		this.needle = needle.toLowerCase();
	}

	public boolean passesFilter(Object itemId, Item item) {
		String haystack = ("" + item.getItemProperty("ragione").getValue()).toLowerCase();
		return haystack.contains(needle);
	}

	public boolean appliesToProperty(Object id) {
		return true;
	}
}
