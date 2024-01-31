package com.wanted.preonboarding.reservation.framwork.jpaadapter.repository;

import com.wanted.preonboarding.reservation.domain.Reservation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

	@Query("SELECT m FROM Reservation m WHERE m.namePhone.name =:name AND m.namePhone.phoneNumber =:phone")
	Optional<Reservation> findByUserNameAndPhoneNubmer(@Param(value = "name") String name, @Param(value = "phone") String phone);

//	Optional<Reservation> findByIdAndReservationStatus(
//		UUID reservationId,
//		ReservationStatus reservationStatus);
//
//	List<Reservation> findByNameAndPhoneNumber(String userName, String phoneNumber);
}
