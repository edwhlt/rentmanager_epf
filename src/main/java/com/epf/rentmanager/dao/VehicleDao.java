package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import static com.epf.rentmanager.persistence.ConnectionManager.getConnection;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(modele, constructeur, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, modele, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, modele, constructeur, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS vehiclesCount FROM Vehicle;";
	private static final String UPDATE_VEHICLES_QUERY = "UPDATE Vehicle set modele=?, constructeur=?, nb_places=? where id=?";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_QUERY);
			statement.setString(1, vehicle.getModele());
			statement.setString(2, vehicle.getConstructor());
			statement.setInt(3, vehicle.getSeats());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicle.getId();
	}

	public long update(long id, String modele, String constructeur, int seats) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(UPDATE_VEHICLES_QUERY);
			statement.setString(1, modele);
			statement.setString(2, constructeur);
			statement.setInt(3, seats);
			statement.setLong(4, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return id;
	}

	public void delete(long id) throws DaoException {
		try {
			Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			statement.setLong(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicle = null;

		try {
			Connection connection = getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while(rs.next()){
				if (rs.getLong("id")==id){
					String modele = rs.getString("modele");
					String constructor = rs.getString("constructeur");
					int seats = rs.getInt("nb_places");
					vehicle =  new Vehicle(id, modele, constructor, seats);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try {
			Connection connection = getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while(rs.next()){
				long id = rs.getLong("id");
				String modele = rs.getString("modele");
				String constructor = rs.getString("constructeur");
				int seats = rs.getInt("nb_places");

				vehicles.add( new Vehicle(id, modele, constructor, seats));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return vehicles;
	}
	public long count() throws DaoException {
		int vehiclesCount=1;
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_VEHICLES_QUERY);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				vehiclesCount = rs.getInt(vehiclesCount);
			}

			return vehiclesCount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
