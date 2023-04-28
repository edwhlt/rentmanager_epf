package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private final VehicleDao vehicleDao;
	private final ReservationDao reservationDao;
	
	private VehicleService(VehicleDao vehicleDao, ReservationDao reservationDao) {
		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try{
			if(vehicle.getConstructor() == null || vehicle.getConstructor().isEmpty()) throw new ServiceException("Le constructeur ne doit pas être vide !");
			if(vehicle.getSeats() > 9 || vehicle.getSeats() < 2) throw new ServiceException("Le nombre de sièges doit être compris entre 2 et 9 !");
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public long update(long id, String modele, String constructeur, int seats) throws ServiceException {
		try{
			if(constructeur == null || constructeur.isEmpty()) throw new ServiceException("Le constructeur ne doit pas être vide !");
			if(seats > 9 || seats < 2) throw new ServiceException("Le nombre de sièges doit être compris entre 2 et 9 !");
			return vehicleDao.update(id, modele, constructeur, seats);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(long id) throws ServiceException {
		try{
			vehicleDao.delete(id);
			reservationDao.deleteFromVehicule(id);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
	public long count() throws ServiceException {
		try {
			return this.vehicleDao.count();
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
