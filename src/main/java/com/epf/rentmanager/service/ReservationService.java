package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try{
            List<Reservation> reservations = findAll().stream().filter(r -> r.getVehicleId() == reservation.getVehicleId()).collect(Collectors.toList());
            reservations.sort(Comparator.comparing(Reservation::getStart));

            if(!reservations.isEmpty() && needPauseVehicule(reservations)) throw new ServiceException("Un vehicule doit faire une pause (il a été utilisé 30jours) !");
            for(Reservation reserv : reservations){
                if(reservation.getEnd().isAfter(reserv.getStart()) || reservation.getStart().isBefore(reserv.getEnd())) {
                    throw new ServiceException("Un vehicule ne peux pas être réservé 2 fois en même temps !");
                }
            }
            if(Period.between(reservation.getStart(), reservation.getEnd()).getDays() > 7) throw new ServiceException("Une voiture ne peux pas être réservée plus de 7 jours !");

            reservationDao.create(reservation);
        }catch (DaoException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void update(long id, long clientId, long vehiculeId, LocalDate start, LocalDate end) throws ServiceException {
        try{
            List<Reservation> reservations = findAll().stream().filter(r -> r.getVehicleId() == vehiculeId).collect(Collectors.toList());
            reservations.sort(Comparator.comparing(Reservation::getStart));

            if(needPauseVehicule(reservations)) throw new ServiceException("Un vehicule doit faire une pause (il a été utilisé 30jours) !");
            for(Reservation reserv : reservations){
                if(end.isAfter(reserv.getStart()) || start.isBefore(reserv.getEnd())) {
                    throw new ServiceException("Un vehicule ne peux pas être réservé 2 fois en même temps !");
                }
            }
            if(Period.between(start, end).getDays() > 7) throw new ServiceException("Une voiture ne peux pas être réservée plus de 7 jours !");

            reservationDao.update(id, clientId, vehiculeId, start, end);
        }catch (DaoException e){
            throw new RuntimeException(e);
        }
    }


    public void delete(long id) throws ServiceException {
        try{
            reservationDao.delete(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
    public long count() throws ServiceException {
        try {
            return this.reservationDao.count();
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }



    private boolean needPauseVehicule(List<Reservation> reservationsVehicule){
        Reservation lastStartWithoutPause = reservationsVehicule.get(0);
        for (int i = 0; i < reservationsVehicule.size() - 1; i++) {
            Reservation current = reservationsVehicule.get(i);
            Reservation next = reservationsVehicule.get(i + 1);

            Duration pause30 = Duration.between(lastStartWithoutPause.getStart().atStartOfDay(), current.getEnd().atStartOfDay());
            if (pause30.toDays() >= 30) return true;

            Duration pause = Duration.between(current.getEnd().atStartOfDay(), next.getStart().atStartOfDay());
            if (pause.toDays() > 1) {
                lastStartWithoutPause = next;
            }
        }
        return false;
    }
}
