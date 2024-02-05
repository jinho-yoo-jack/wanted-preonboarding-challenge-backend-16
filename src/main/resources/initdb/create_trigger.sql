DELIMITER //
CREATE TRIGGER trg_deletedReservation
    AFTER DELETE ON reservation
    FOR EACH ROW
BEGIN
   INSERT INTO canceled_reservation(id, performance_id, user_id, round, gate, line, seat, created_at)
    VALUES (
            OLD.id,
            OLD.performance_id,
            OLD.user_id,
            OLD.round,
            OLD.gate,
            OLD.line,
            OLD.seat,
            OLD.created_at
    );
END //
DELIMITER ;