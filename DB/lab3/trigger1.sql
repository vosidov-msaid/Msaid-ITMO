-- 1. Запрет одному транспорту находиться в двух тоннелях

CREATE OR REPLACE FUNCTION check_vehicle()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    count INTEGER;
BEGIN
    SELECT COUNT(*)
      INTO count
      FROM trip
     WHERE vehicle_id = NEW.vehicle_id
       AND status = 'in_progress';

    IF count > 0 THEN
        RAISE EXCEPTION
            'Транспортное средство (vehicle_id=%) уже находится в активном рейсе.',
            NEW.vehicle_id;
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_check_vehicle
BEFORE INSERT OR UPDATE ON trip
FOR EACH ROW
WHEN (NEW.status = 'in_progress')
EXECUTE FUNCTION check_vehicle();

-- Проверка
-- INSERT INTO trip (vehicle_id, tunnel_id, sending_time, status)
-- VALUES (5, 6, NOW(), 'in_progress');