package ca.mcgill.ecse321.eventregistration.controller;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class EventRegistrationController {
	

	public void createParticipant(String name) {
		Participant p = new Participant(name);	//create participant
		RegistrationManager rm = RegistrationManager.getInstance();	//get registration manager
		rm.addParticipant(p);	//add it to registration manager
		PersistenceXStream.saveToXMLwithXStream(rm);	//write persistently to file
	}

	public void createEvent(String name, Date eventDate, Time startTime, Time endTime) {
		Event e = new Event(name, eventDate, startTime, endTime);	//create participant
		RegistrationManager rm = RegistrationManager.getInstance();	//get registration manager
		rm.addEvent(e);	//add event to registration manager
		PersistenceXStream.saveToXMLwithXStream(rm);	//write persistently to file
	}

	public void createRegistration(Participant p, Event e) {
		Registration reg = new Registration(p, e);	//create participant
		RegistrationManager rm = RegistrationManager.getInstance();	//get registration manager
		rm.addRegistration(reg);	//add it to registration manager
		PersistenceXStream.saveToXMLwithXStream(rm);	//write persistently to file
		
	}

}
