package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import static com.epf.rentmanager.persistence.ConnectionManager.getConnection;

@Repository
public class ReservationDao {
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS reservationsCount FROM Reservation;";
	private static final String UPDATE_VEHICLES_QUERY = "UPDATE Reservation set client_id=?, vehicule_id=?, debut=?, fin=? where id=?";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATION_QUERY);
			statement.setLong(1, reservation.getClientId());
			statement.setLong(2, reservation.getVehicleId());
			statement.setDate(3, Date.valueOf(reservation.getStart()));
			statement.setDate(4, Date.valueOf(reservation.getEnd()));
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservation.getId();
	}

	public void update(long id, long clientId, long vehiculeId, LocalDate start, LocalDate end) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(UPDATE_VEHICLES_QUERY);
			statement.setLong(1, clientId);
			statement.setLong(2, vehiculeId);
			statement.setDate(3, Date.valueOf(start));
			statement.setDate(4, Date.valueOf(end));
			statement.setLong(5, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	
	public void delete(long id) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			statement.setLong(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public void deleteFromClient(long clientId) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement("DELETE FROM Reservation WHERE client_id=?;");
			statement.setLong(1, clientId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public void deleteFromVehicule(long vehiculeId) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement("DELETE FROM Reservation WHERE vehicle_id=?;");
			statement.setLong(1, vehiculeId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			statement.setLong(1, clientId);

			ResultSet rs = statement.executeQuery();

			List<Reservation> reservations = new ArrayList<>();

			while (rs.next()) {
				long id = rs.getLong("id");
				long vehicleId = rs.getLong("vehicle_id");
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();

				reservations.add(new Reservation(id, clientId, vehicleId, start, end));
			}
			return reservations;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Reservation> findResaByReservationId(long vehicleId) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			statement.setLong(1, vehicleId);

			ResultSet rs = statement.executeQuery();

			List<Reservation> reservations = new ArrayList<>();

			while (rs.next()) {
				long id = rs.getLong("id");
				long clientId = rs.getLong("client_id");
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();

				reservations.add(new Reservation(id, clientId, vehicleId, start, end));
			}
			return reservations;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			List<Reservation> reservations = new ArrayList<>();

			while (rs.next()) {
				long id = rs.getLong("id");
				long clientId = rs.getLong("client_id");
				long vehicleId = rs.getLong("vehicle_id");
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();

				reservations.add(new Reservation(id, clientId, vehicleId, start, end));
			}
			return reservations;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Reservation findById(long id) throws DaoException{
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATION_QUERY);

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			rs.next();

			long reservationClientId = rs.getLong("client_id");
			long reservationVehicleId = rs.getLong("vehicle_id");
			LocalDate reservationDebut = rs.getDate("debut").toLocalDate();
			LocalDate reservationFin = rs.getDate("fin").toLocalDate();

			return new Reservation(id, reservationClientId, reservationVehicleId, reservationDebut, reservationFin);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public long count() throws DaoException {
		int reservationsCount = 1;
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_RESERVATIONS_QUERY);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				reservationsCount = rs.getInt(reservationsCount);
			}

			return reservationsCount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
