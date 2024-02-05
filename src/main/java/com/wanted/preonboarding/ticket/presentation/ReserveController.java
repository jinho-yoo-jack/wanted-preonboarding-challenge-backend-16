package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.exception.AlreadyReservationException;
import com.wanted.preonboarding.ticket.domain.exception.NotEnoughAmountException;
import com.wanted.preonboarding.ticket.presentation.request.CreateReserveRequest;
import com.wanted.preonboarding.ticket.presentation.request.ReadReservationRequest;
import com.wanted.preonboarding.ticket.presentation.response.CreateReservationResponse;
import com.wanted.preonboarding.ticket.presentation.response.ReadReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    @PostMapping("")
    public ResponseEntity<ResponseHandler<CreateReservationResponse>> reservation(@RequestBody CreateReserveRequest createReserveRequest) {

        System.out.println("reservation");

        try {
            CreateReservationResponse reserveResponse = ticketSeller.reserve(createReserveRequest);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseHandler.<CreateReservationResponse>builder()
                            .message("Success")
                            .statusCode(HttpStatus.OK)
                            .data(reserveResponse)
                            .build());
        } catch (AlreadyReservationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseHandler.<CreateReservationResponse>builder()
                            .message(e.getMessage())
                            .statusCode(HttpStatus.CONFLICT)
                            .data(null)
                            .build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseHandler.<CreateReservationResponse>builder()
                            .message(e.getMessage())
                            .statusCode(HttpStatus.NOT_FOUND)
                            .data(null)
                            .build());
        } catch (NotEnoughAmountException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(ResponseHandler.<CreateReservationResponse>builder()
                            .message(e.getMessage())
                            .statusCode(HttpStatus.FORBIDDEN)
                            .data(null)
                            .build());
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseHandler<List<ReadReservationResponse>>> readReserve(ReadReservationRequest readReservationRequest) {
        System.out.println("readReserve");
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<ReadReservationResponse>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getReservations(readReservationRequest))
                        .build()
                );
    }

}
