package ca.mcgill.ecse321.eventregistration.controller;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class EventRegistrationController {
	

	public void createParticipant(String name) throws InvalidInputException{
		
		//checking for invalid input
		if(name == null || name.isEmpty() || name.trim().isEmpty()) {
			throw new InvalidInputException("Participant name cannot be empty.");
		}
		
		Participant p = new Participant(name);	//create participant
		RegistrationManager rm = RegistrationManager.getInstance();	//get registration manager
		rm.addParticipant(p);	//add it to registration manager
		PersistenceXStream.saveToXMLwithXStream(rm);	//write persistently to file
	}

	public void createEvent(String name, Date eventDate, Time startTime, Time endTime) throws InvalidInputException {
		
		// checking for invalid input
		if (name == null || name.isEmpty() || name.trim().isEmpty()) {
			throw new InvalidInputException("Event name cannot be empty.");
		}
		
		if(eventDate == null) {
			throw new InvalidInputException("Event date cannot be empty.");
		}
		
		if(startTime == null)  {
			throw new InvalidInputException("Event start time cannot be empty.");
		}
		
		if(endTime == null)  {
			throw new InvalidInputException("Event end time cannot be empty.");
		}
		
		if(startTime.compareTo(endTime) > 0) {
			throw new InvalidInputException("Event cannot end before it starts!");
		}
		
		Event e = new Event(name, eventDate, startTime, endTime);	//create participant
		RegistrationManager rm = RegistrationManager.getInstance();	//get registration manager
		rm.addEvent(e);	//add event to registration manager
		PersistenceXStream.saveToXMLwithXStream(rm);	//write persistently to file
	}

	public void createRegistration(Participant p, Event e) throws InvalidInputException {
		
		//checking for invalid input
		if(p == null) {
			throw new InvalidInputException("A registration must include a participant!");
		}
		
		if (e == null) {
			throw new InvalidInputException("A registration must include an event!");
		}
		Registration reg = new Registration(p, e);	//create participant
		RegistrationManager rm = RegistrationManager.getInstance();	//get registration manager
		rm.addRegistration(reg);	//add it to registration manager
		PersistenceXStream.saveToXMLwithXStream(rm);	//write persistently to file
		
	}

}
