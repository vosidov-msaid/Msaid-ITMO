-- 2. При деактивации тоннеля отменить все активные рейсы

CREATE OR REPLACE FUNCTION fn_cancel_trips()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    IF OLD.is_active = TRUE AND NEW.is_active = FALSE THEN
        UPDATE trip
           SET status = 'cancelled'
         WHERE tunnel_id = NEW.tunnel_id
           AND status IN ('scheduled', 'in_progress');
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_cancel_trips
BEFORE UPDATE ON tunnel
FOR EACH ROW
EXECUTE FUNCTION fn_cancel_trips();

-- Проверка
-- UPDATE tunnel SET is_active = FALSE WHERE tunnel_id = 6;