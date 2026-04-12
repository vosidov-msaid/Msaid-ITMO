-- Запрос 1. Выводить список активных машин и отсортировать их по скорости
SELECT * FROM vehicle
WHERE is_active = TRUE
ORDER BY max_speed DESC;

-- Запрос 2. Выводить топ 3 самые длинные туннели
SELECT * FROM tunnel
WHERE is_active = TRUE
ORDER BY distance DESC
LIMIT 3;

-- Запрос 3. Выводить статистику поездок по их статусам
SELECT status, COUNT(*) as trip_count
FROM trip
GROUP BY status;

-- Запрос 4. Выводить все не работающие табло и их последнее время обновления
SELECT scoreboard_id, station_id, last_updated_at, is_working FROM score_board
WHERE is_working = FALSE
ORDER BY last_updated_at DESC;

-- Запрос 5. Выводить поездки без значения скорости или времени прибытия
SELECT * FROM trip
WHERE current_speed IS NULL OR arrival_time IS NULL
ORDER BY sending_time DESC;
