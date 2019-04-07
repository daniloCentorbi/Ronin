package ita.homi.component.calendari;

import ita.homi.delegate.BusinessDelegate;
import ita.homi.model.CalendarTestEvent;
import ita.homi.utils.GestoreException;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

public class CalendarHistory extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	protected BusinessDelegate bd = new BusinessDelegate();
	private List<CalendarTestEvent> elenco;

	private enum Mode {
		MONTH, DAY, WEEK;
	}

	private GregorianCalendar calendar;
	/** Target calendar component that this test application is made for. */
	private Calendar calendarComponent;
	private Date currentMonthsFirstDate;
	private final Label captionLabel = new Label("");
	private Button monthButton;
	private Button weekButton;
	private Button nextButton;
	private Button prevButton;
	private Mode viewMode = Mode.MONTH;
	private BasicEventProvider dataSource;
	private boolean showWeeklyView;
	private int numero;

	// Reference to main window
	public CalendarHistory(int numero) {
		// getWindow().setTheme("calendartest");
		initCalendar();
		initLayoutContent();
		addInitialEvents();
	}

	// pulsanti manage calendario
	@SuppressWarnings("serial")
	public void initLayoutContent() {
		monthButton = new Button("Vista Mese", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				switchToMonthView();
				monthButton.setVisible(true);
				weekButton.setVisible(true);
			}
		});
		weekButton = new Button("Vista Settimana", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				switchToWeekView();
				monthButton.setVisible(true);
				weekButton.setVisible(true);
			}
		});
		nextButton = new Button("Next", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				handleNextButtonClick();
			}
		});
		prevButton = new Button("Prev", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				handlePreviousButtonClick();
			}
		});
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidth("100%");
		hl.setSpacing(true);
		hl.addComponent(prevButton);
		hl.addComponent(captionLabel);
		hl.addComponent(monthButton);
		hl.addComponent(weekButton);
		hl.addComponent(nextButton);
		hl.setComponentAlignment(prevButton, Alignment.MIDDLE_LEFT);
		hl.setComponentAlignment(captionLabel, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(monthButton, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(weekButton, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(nextButton, Alignment.MIDDLE_RIGHT);

		monthButton.setVisible(true);

		addComponent(hl);
		addComponent(calendarComponent);

	}
	

	@SuppressWarnings("deprecation")
	public void initCalendar() {
		dataSource = new BasicEventProvider();
		calendarComponent = new Calendar(dataSource);

		calendarComponent.setFirstVisibleDayOfWeek(1);
		calendarComponent.setLastVisibleDayOfWeek(5);
		calendarComponent.setFirstVisibleHourOfDay(8);
		calendarComponent.setLastVisibleHourOfDay(10);
		calendarComponent.setWeeklyCaptionFormat("dd/MM");
		calendarComponent.setLocale(Locale.ITALY);
		calendarComponent.setImmediate(true);

		calendarComponent.setSizeFull();

		Date today = getToday();
		calendar = new GregorianCalendar(Locale.ITALY);
		calendar.setTime(today);
		DateFormatSymbols s = new DateFormatSymbols(Locale.ITALY);
		updateCaptionLabel(GregorianCalendar.WEEK_OF_MONTH);
		addCalendarEventListeners();

	}

	private void rollMonth(int direction) {
		calendar.setTime(calendar.getTime());
		System.out.println("  RollMONTH  " + GregorianCalendar.MONTH);
		System.out.println("  ---------------  " + GregorianCalendar.MONTH);
		System.out.println("________________________________________________ "
				+ GregorianCalendar.WEEK_OF_YEAR);
		updateCaptionLabel(GregorianCalendar.MONTH);
		calendar.add(GregorianCalendar.MONTH, direction);
		monthButton.setVisible(true);
		weekButton.setVisible(true);
	}
	
	public Date calendarGetTime(){
	return calendar.getTime();
	}

	private void rollWeek(int direction) {
		calendar.setTime(calendar.getTime());
		System.out.println("   RollWEEK  " + GregorianCalendar.WEEK_OF_MONTH);
		System.out.println("ROLLWEEK "
				+ GregorianCalendar.WEEK_OF_MONTH);
		System.out.println("________________________________________________ "
				+ GregorianCalendar.WEEK_OF_YEAR);
		calendarComponent.setStartDate(calendar.getTime());
		updateCaptionLabelWeek(GregorianCalendar.WEEK_OF_MONTH);
		calendar.add(GregorianCalendar.WEEK_OF_MONTH, direction);
		resetCalendarTime(true);
	}

	private void rollDate(int direction) {
		calendar.add(GregorianCalendar.DATE, direction);
		System.out.print("ROLL DAY  ---------------  "
				+ GregorianCalendar.DATE);
		System.out.println("________________________________________________ "
				+ GregorianCalendar.WEEK_OF_YEAR);
		resetCalendarTime(true);
	}

	private void updateCaptionLabel(int a) {
		DateFormatSymbols s = new DateFormatSymbols(Locale.ITALY);
		captionLabel.setValue(s.getShortMonths()[calendar.get(a)] + " "
				+ calendar.get(GregorianCalendar.YEAR));
	}

	private void updateCaptionLabelWeek(int a) {
		DateFormatSymbols s = new DateFormatSymbols(Locale.ITALY);
		System.out.print("calendar.get(GregorianCalendar.WEEK)------"
				+ calendar.get(GregorianCalendar.WEEK_OF_MONTH));
		captionLabel.setValue(s.getWeekdays()[calendar.get(a)] + " "
				+ calendar.get(GregorianCalendar.YEAR));

	}

	private CalendarTestEvent getNewEvent(String caption, Date start, Date end) {
		CalendarTestEvent event = new CalendarTestEvent();
		event.setCaption(caption);
		event.setDescription("Cliente");
		event.setStart(start);
		event.setEnd(end);
		return event;
	}

	/*
	 * Switch the Calendar component's start and end date range to the target
	 * month only. (sample range: 01.01.2010 00:00.000 - 31.01.2010 23:59.999)
	 */
	public void switchToMonthView() {
		int rollAmount = calendar.get(GregorianCalendar.DAY_OF_MONTH) - 1;
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -rollAmount);
		resetTime(false);
		currentMonthsFirstDate = calendar.getTime();
		calendarComponent.setStartDate(currentMonthsFirstDate);
		calendar.add(GregorianCalendar.MONTH, 1);
		calendar.add(GregorianCalendar.DATE, -1);
		calendarComponent.setEndDate(calendar.getTime());
		
		Notification.show("Vista Mese");
		

		monthButton.setVisible(false);
		weekButton.setVisible(true);

	}

	/*
	 * Switch the Calendar component's start and end date range to the target
	 * month only. (sample range: 01.01.2010 00:00.000 - 31.01.2010 23:59.999)
	 */
	public void switchToWeekView() {
		DateFormatSymbols s = new DateFormatSymbols(Locale.ITALY);
		removeAllComponents();
		initCalendar();
		initLayoutContent();
		addInitialEvents();
		System.out.println("s.getShortWeekdays() ---------------  "
				+ s.getShortWeekdays().toString());
		System.out.println("s.getWeekdays() ---------------  "
				+ s.getWeekdays());
		System.out.println("_________________________________________________________");

		monthButton.setVisible(true);
		weekButton.setVisible(false);

		Notification.show("Vista Settimana");

	}

	/*
	 * Switch to day view (week view with a single day visible).
	 */
	public void switchToDayView() {
		viewMode = Mode.DAY;
		Notification.show("Vista Dettaglio" + new Date());
		monthButton.setVisible(true);
		weekButton.setVisible(true);
	}

	private void resetCalendarTime(boolean resetEndTime) {
		resetTime(resetEndTime);
		if (resetEndTime) {
			calendarComponent.setEndDate(calendar.getTime());
		} else {
			calendarComponent.setStartDate(calendar.getTime());
			updateCaptionLabelWeek(GregorianCalendar.WEEK_OF_MONTH);
		}
	}

	/*
	 * Resets the calendar time (hour, minute second and millisecond) either to
	 * zero or maximum value.
	 */
	private void resetTime(boolean max) {
		if (max) {
			calendar.set(GregorianCalendar.HOUR_OF_DAY,
					calendar.getMaximum(GregorianCalendar.HOUR_OF_DAY));
			calendar.set(GregorianCalendar.MINUTE,
					calendar.getMaximum(GregorianCalendar.MINUTE));
			calendar.set(GregorianCalendar.SECOND,
					calendar.getMaximum(GregorianCalendar.SECOND));
			calendar.set(GregorianCalendar.MILLISECOND,
					calendar.getMaximum(GregorianCalendar.MILLISECOND));
		} else {
			calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
			calendar.set(GregorianCalendar.MINUTE, 0);
			calendar.set(GregorianCalendar.SECOND, 0);
			calendar.set(GregorianCalendar.MILLISECOND, 0);
		}
	}

	private Date getToday() {
		return new Date();
	}

	@SuppressWarnings("serial")
	private void addCalendarEventListeners() {
		/*
		 * calendarComponent.setHandler(new EventClickHandler() { public void
		 * eventClick(EventClick event) { showEventPopup((CalendarTestEvent)
		 * event.getCalendarEvent(), false); } });
		 */
		calendarComponent.setHandler(new BasicDateClickHandler() {
			@Override
			public void dateClick(DateClickEvent event) {
				super.dateClick(event);
				switchToDayView();
			}
		});

	}

	private void handleNextButtonClick() {
		switch (viewMode) {
		case MONTH:
			nextMonth();
			break;
		case WEEK:
			nextWeek();
			break;
		case DAY:
			nextDay();
			break;
		}
	}

	private void handlePreviousButtonClick() {
		switch (viewMode) {
		case MONTH:
			previousMonth();
			break;
		case WEEK:
			previousWeek();
			break;
		case DAY:
			previousDay();
			break;
		}
	}

	private void nextMonth() {
		rollMonth(1);
	}

	private void previousMonth() {
		rollMonth(-1);
	}

	private void nextWeek() {
		rollWeek(1);
	}

	private void previousWeek() {
		rollWeek(-1);
	}

	private void nextDay() {
		rollDate(1);
	}

	private void previousDay() {
		rollDate(-1);
	}

	public void addInitialEvents() {
		Date originalDate = calendar.getTime();
		Date today = getToday();
		Date start;
		Date end;
		CalendarTestEvent event;
		String state;

		start = calendarGetTime();
		end = new Date();

		for (int g = 1; g < 13; g++) {
			event = getNewEvent("DA LISTA", start, end);
			event = getNewEvent("DA LISTA", start, end);
			event.setCaption(" 00/11/13 " + (g + g * 13));
			event.setDescription("DESCRIPTION" + (g + g * 3));
			event.setStyleName("color2");

			event.setAllDay(true);
			dataSource.addEvent(event);
		}

		// recupero feedback lasciati da me
		try {
			elenco = bd.getEvent(numero);
		} catch (GestoreException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < elenco.size(); i++) {
			if (today.getTime() < elenco.get(i).getStart().getTime()) {

			} else if (today.getTime() > elenco.get(i).getStart().getTime()) {
				if (elenco.get(i).getState().equals("completato")) {
					start = elenco.get(i).getStart();
					end = elenco.get(i).getEnd();
					event = getNewEvent("DA LISTA", start, end);
					state = elenco.get(i).getState();
					event.setCaption("VOTO: " + elenco.get(i).getCaption());
					event.setDescription(elenco.get(i).getDescription());
					event.setState(elenco.get(i).getState());
					event.setStyleName("color2");
					dataSource.addEvent(event);
				}
			}
		}

		/*
		 * // recupero feedback da utenti per i miei annunci try { elenco =
		 * bd.getEventFromUser(userName); } catch (GestoreException e) {
		 * e.printStackTrace(); }
		 * 
		 * for (int i = 0; i < elenco.size(); i++) { if (today.getTime() <
		 * elenco.get(i).getStart().getTime()) {
		 * 
		 * } else if (today.getTime() > elenco.get(i).getStart().getTime()) { if
		 * (elenco.get(i).getState().equals("completato")) { start =
		 * elenco.get(i).getStart(); end = elenco.get(i).getEnd(); event =
		 * getNewEvent("DA LISTA", start, end); state =
		 * elenco.get(i).getState(); event.setCaption("VOTO: " +
		 * elenco.get(i).getCodicecliente());
		 * event.setDescription(elenco.get(i).getDescription());
		 * event.setState(elenco.get(i).getState());
		 * event.setStyleName("color2"); dataSource.addEvent(event); } } }
		 */
		calendar.setTime(originalDate);

	}

}