package ca.mcgill.ecse321.eventregistration.controller;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class TestEventRegistrationController {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		// clear all registrations
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.delete();
	}

	@Test
	public void testCreateParticipant() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());

		String name = "Kevin";

		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant(name);
		} catch (InvalidInputException e) {
			fail(e.getMessage());
		}

		checkResultParticipant(name, rm);

		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();

		checkResultParticipant(name, rm2);
	}

	@Test
	public void testCreateEvent() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());

		String name = "Concert";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.SEPTEMBER, 15, 10, 0, 0);
		Time endTime = new Time(c.getTimeInMillis());

		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			fail(e.getMessage());
		}

		checkResultEvent(name, eventDate, startTime, endTime, rm);

		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();

		checkResultEvent(name, eventDate, startTime, endTime, rm2);
	}

	@Test
	public void testCreateRegistration() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getRegistrations().size());

		// initializing event
		String name = "Concert";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.SEPTEMBER, 15, 10, 0, 0);
		Time endTime = new Time(c.getTimeInMillis());
		Event e = new Event(name, eventDate, startTime, endTime);
		// initializing participant
		Participant p = new Participant("Kevin");

		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent(e.getName(), e.getDate(), e.getStartTime(), e.getEndTime());
			erc.createParticipant(p.getName());
			erc.createRegistration(p, e);
		} catch (InvalidInputException ex) {
			fail(ex.getMessage());
		}

		checkResultRegistration(p, e, rm);

		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();

		checkResultRegistration(p, e, rm2);
	}

	@Test
	public void testCreateParticipantNull() {
		// initialize registration manager, assert that it is emty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());

		String name = null;
		String error = null;

		// create participant with name null
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Participant name cannot be empty.", error);
		// check participant wasn't added
		assertEquals(0, rm.getParticipants().size());
	}

	@Test
	public void testCreateParticipantEmpty() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());

		String name = "";
		String error = null;

		// create participant with empty name
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Participant name cannot be empty.", error);
		// check participant wasn't added
		assertEquals(0, rm.getParticipants().size());
	}

	@Test
	public void testCreateParticipantSpaces() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());

		String name = " ";
		String error = null;

		// create participant with empty (space) name
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Participant name cannot be empty.", error);
		// check participant wasn't added
		assertEquals(0, rm.getParticipants().size());
	}

	@Test
	public void testCreateEventNull() {
		// initialize registration manager, assert that it is emty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());

		String name = null;
		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.SEPTEMBER, 15, 10, 0, 0);
		Time endTime = new Time(c.getTimeInMillis());

		// create event with name null
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Event name cannot be empty.", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());
	}

	@Test
	public void testCreateEventEmpty() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());

		String name = "";
		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.SEPTEMBER, 15, 10, 0, 0);
		Time endTime = new Time(c.getTimeInMillis());

		// create event with empty name
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Event name cannot be empty.", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());
	}

	@Test
	public void testCreateEventSpaces() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());

		String name = " ";
		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.SEPTEMBER, 15, 10, 0, 0);
		Time endTime = new Time(c.getTimeInMillis());

		// create event with empty (space) name
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Event name cannot be empty.", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());
	}

	@Test
	public void testCreateEventNullDate() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		Date eventDate = null;
		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.SEPTEMBER, 15, 10, 0, 0);
		Time endTime = new Time(c.getTimeInMillis());
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent("Test", eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error message
		assertEquals("Event date cannot be empty.", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());

	}
	
	@Test
	public void testCreateEventNullStartTime() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		Time startTime = null;
		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time endTime = new Time(c.getTimeInMillis());
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent("Test", eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error message
		assertEquals("Event start time cannot be empty.", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());

	}
	
	@Test
	public void testCreateEventNullEndTime() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		Time endTime = null;
		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent("Test", eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error message
		assertEquals("Event end time cannot be empty.", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());

	}
	
	@Test
	public void testCreateEventStartTimeAfterEndTime() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());

		String error = null;
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.SEPTEMBER, 15, 8, 30, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		//set end time earlier than start time, say 6:30
		c.set(2016, Calendar.SEPTEMBER, 15, 6, 30, 0);
		Time endTime = new Time(c.getTimeInMillis());

		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent("Test", eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error message
		assertEquals("Event cannot end before it starts!", error);
		// check event wasn't added
		assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateRegistrationNoParticipant() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getRegistrations().size());
		
		String error = null;
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent("Test", new Date(0L), new Time(0L), new Time(1L));
			erc.createRegistration(null, rm.getEvent(0));
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error message
		assertEquals("A registration must include a participant!", error);
		// check registration wasn't added
		assertEquals(0, rm.getRegistrations().size());
	}
	
	@Test
	public void testCreateRegistrationNoEvent() {
		// initialize registration manager, assert that it is empty
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getRegistrations().size());
		
		String error = null;
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant("Test");
			erc.createRegistration(rm.getParticipant(0), null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error message
		assertEquals("A registration must include an event!", error);
		// check registration wasn't added
		assertEquals(0, rm.getRegistrations().size());
	}

	private void checkResultRegistration(Participant p, Event e, RegistrationManager rm) {
		// check file contents
		assertEquals(1, rm.getRegistrations().size());
		assertEquals(p.getName(), rm.getRegistration(0).getParticipant().getName());
		assertEquals(e.getName(), rm.getEvent(0).getName());
		assertEquals(e.getDate().toString(), rm.getRegistration(0).getEvent().getDate().toString());
		assertEquals(e.getStartTime().toString(), rm.getRegistration(0).getEvent().getStartTime().toString());
		assertEquals(e.getEndTime().toString(), rm.getRegistration(0).getEvent().getEndTime().toString());
		assertEquals(1, rm.getEvents().size());
		assertEquals(1, rm.getParticipants().size());

	}

	private void checkResultParticipant(String name, RegistrationManager rm) {
		// check file contents
		assertEquals(1, rm.getParticipants().size());
		assertEquals(name, rm.getParticipant(0).getName());
		assertEquals(0, rm.getEvents().size());
		assertEquals(0, rm.getRegistrations().size());
	}

	private void checkResultEvent(String name, Date eventDate, Time startTime, Time endTime, RegistrationManager rm) {
		// check file contents
		assertEquals(1, rm.getEvents().size());
		assertEquals(name, rm.getEvent(0).getName());
		assertEquals(eventDate.toString(), rm.getEvent(0).getDate().toString());
		assertEquals(startTime.toString(), rm.getEvent(0).getStartTime().toString());
		assertEquals(endTime.toString(), rm.getEvent(0).getEndTime().toString());
		assertEquals(0, rm.getParticipants().size());
		assertEquals(0, rm.getRegistrations().size());
	}

}
