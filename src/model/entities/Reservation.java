package model.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.exceptions.DomainException;

public class Reservation {
	private Integer roomNumber;
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Reservation() {
		
	}
	
	public Reservation(Integer roomNumber, LocalDate checkIn, LocalDate checkOut) throws DomainException {
		if (!checkOut.isAfter(checkIn)){
			throw new DomainException("Error in reservation: Check out date must be after check in date");
		}
		this.roomNumber = roomNumber;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}
	
	public long duration() {
		return Duration.between(checkIn.atStartOfDay(), checkOut.atStartOfDay()).toDays();
	}
	
	public void updateDates (LocalDate checkIn, LocalDate checkOut) throws DomainException{
		LocalDate now = LocalDate.now();
		if (checkIn.isBefore(now) || checkOut.isBefore(now)) {
			// Exceção para argumentos incorretos fornecidos para o método
			throw new DomainException("Error in reservation: Reservation dates for update must be future dates") ;
		}
		if (!checkOut.isAfter(checkIn)){
			throw new DomainException("Error in reservation: Check out date must be after check in date");
		}
		
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Room " + roomNumber);
		sb.append(", check in: ");
		sb.append(checkIn.format(dtf));
		sb.append(", check out: ");
		sb.append(checkOut.format(dtf));
		sb.append(", ");
		sb.append(duration());
		sb.append(" nigths");
		return sb.toString();
	}
}
