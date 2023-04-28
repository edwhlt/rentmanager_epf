package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.ClientDao;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private final ClientDao clientDao;
	private final ReservationDao reservationDao;
	
	private ClientService(ClientDao clientDao, ReservationDao reservationDao) {
		this.clientDao = clientDao;
		this.reservationDao = reservationDao;
	}
	
	
	public void create(Client client) throws ServiceException {
		try {
			if(client.getFirstName().length() < 3 || client.getLastName().length() < 3) throw new ServiceException("Le nom et/ou le prénom ne doivent faire minium 3 caractères !");
			if(Period.between(client.getBirthDate(), LocalDate.now()).getYears() < 18) throw new ServiceException("Le client doit avoir 18ans !");
			client.setLastName(client.getLastName().toUpperCase());
			clientDao.create(client);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public long update(long id, String last_name, String first_name, String email, LocalDate date) throws ServiceException {
		try {
			if(first_name.length() < 3 || last_name.length() < 3) throw new ServiceException("Le nom et/ou le prénom ne doivent faire minium 3 caractères !");
			if(Period.between(date, LocalDate.now()).getYears() < 18) throw new ServiceException("Le client doit avoir 18ans !");
			return clientDao.update(id, last_name.toUpperCase(), first_name, email, date);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(long id) throws ServiceException {
		try{
			clientDao.delete(id);
			reservationDao.deleteFromClient(id);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}


	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public long count() throws ServiceException {
		try {
			return this.clientDao.count();
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
