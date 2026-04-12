-- Create tables
BEGIN;

CREATE TABLE IF NOT EXISTS station (
    station_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    is_abandoned BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS tunnel (
    tunnel_id SERIAL PRIMARY KEY,
    station_from_id INTEGER NOT NULL REFERENCES station(station_id),
    station_to_id INTEGER NOT NULL REFERENCES station(station_id),
    distance NUMERIC(10,2) NOT NULL CHECK (distance > 0),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT ck_tunnel_diff_stations 
    CHECK (station_from_id <> station_to_id)
);

CREATE TABLE IF NOT EXISTS vehicle (
    vehicle_id SERIAL PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    max_speed NUMERIC(6,2) NOT NULL CHECK (max_speed > 0),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE IF NOT EXISTS score_board (
    scoreboard_id SERIAL PRIMARY KEY,
    station_id INTEGER NOT NULL REFERENCES station(station_id) ON DELETE CASCADE,
    last_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    is_working BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS trip (
    trip_id SERIAL PRIMARY KEY,
    vehicle_id INTEGER NOT NULL REFERENCES vehicle(vehicle_id),
    tunnel_id INTEGER NOT NULL REFERENCES tunnel(tunnel_id),
    sending_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP CHECK (arrival_time > sending_time),
    current_speed NUMERIC(6,2) CHECK (current_speed >= 0),
    status VARCHAR(20) NOT NULL DEFAULT 'in_progress'
                   CHECK (status IN ('scheduled', 'in_progress', 'completed', 'cancelled'))
);

CREATE TABLE IF NOT EXISTS score_board_trip (
    scoreboard_id INTEGER NOT NULL REFERENCES score_board(scoreboard_id) ON DELETE CASCADE,
    trip_id INTEGER NOT NULL REFERENCES trip(trip_id) ON DELETE CASCADE,
    displayed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (scoreboard_id, trip_id)
);


-- Test datas

INSERT INTO station (name, is_abandoned) 
VALUES
    ('Горьковская', FALSE),
    ('Невский проспект', FALSE),
    ('Звенигородская', TRUE),
    ('Пушкинская', FALSE),
    ('Владимирская', FALSE),
    ('Площадь Восстания', FALSE),
    ('Лиговский проспект', FALSE),
    ('Обводный канал', FALSE),
    ('Балтийская', FALSE),
    ('Фрунзенская', FALSE);
 
INSERT INTO tunnel (station_from_id, station_to_id, distance, is_active) 
VALUES
    (1, 3, 12400.00, TRUE),
    (3, 2, 8750.50, TRUE),
    (2, 4, 9300.00, TRUE),
    (4, 5, 11000.00, TRUE),
    (5, 6, 10500.00, TRUE),
    (6, 7, 9800.00, TRUE),
    (7, 8, 10250.00, TRUE),
    (8, 9, 11500.00, TRUE),
    (9, 10, 12000.00, TRUE),
    (10, 1, 12500.00, TRUE);
 
INSERT INTO vehicle (model, max_speed, is_active) 
VALUES
    ('Номерной', 900.00, TRUE),
    ('Ока', 950.00, TRUE),
    ('Русич', 880.00, FALSE),
    ('Вагонетка', 920.00, TRUE),
    ('Сапсан', 1000.00, TRUE),
    ('Ласточка', 980.00, TRUE),
    ('Гранд', 970.00, TRUE),
    ('Экспресс', 940.00, TRUE),
    ('Стрела', 930.00, TRUE),
    ('Метеор', 910.00, FALSE);
 
INSERT INTO score_board (station_id, last_updated_at, is_working) 
VALUES
    (1, '2026-02-22 07:55:00', TRUE),
    (3, '2026-02-22 07:58:00', TRUE),
    (2, '2026-02-22 08:01:00', TRUE),
    (4, '2026-02-22 08:05:00', FALSE),
    (5, '2026-02-22 08:08:00', TRUE),
    (6, '2026-02-22 08:11:00', TRUE),
    (7, '2026-02-22 08:14:00', TRUE),
    (8, '2026-02-22 08:17:00', TRUE),
    (9, '2026-02-22 08:20:00', TRUE),
    (10, '2026-02-22 08:23:00', TRUE);
 
INSERT INTO trip (vehicle_id, tunnel_id, sending_time, arrival_time, current_speed, status) 
VALUES
    (1, 1, '2026-02-22 07:58:00', '2026-02-22 07:59:30', 928.00, 'completed'),
    (1, 2, '2026-02-22 08:00:00', '2026-02-22 08:01:30', 945.00, 'in_progress'),
    (2, 3, '2026-02-22 08:03:00', NULL, NULL, 'scheduled'),
    (3, 4, '2026-02-22 08:04:00', '2026-02-22 08:12:48', NULL, 'completed'),
    (4, 5, '2026-02-22 08:02:00', '2026-02-22 08:02:30', NULL, 'completed'),
    (5, 6, '2026-02-22 08:03:00', NULL, NULL, 'in_progress'),
    (6, 7, '2026-02-22 08:04:00', NULL, NULL, 'scheduled'),
    (7, 8, '2026-02-22 08:05:00', NULL, NULL, 'in_progress'),
    (8, 9, '2026-02-22 08:06:00', '2026-02-22 08:07:30', NULL, 'completed'),
    (9, 10, '2026-02-22 08:07:00', NULL, NULL, 'scheduled');
 
INSERT INTO score_board_trip (scoreboard_id, trip_id, displayed_at) 
VALUES
    (1, 1, '2026-02-22 07:57:50'),
    (2, 1, '2026-02-22 07:58:30'),
    (2, 2, '2026-02-22 07:58:55'),
    (3, 2, '2026-02-22 07:59:00'),
    (3, 3, '2026-02-22 08:00:00'),
    (4, 4, '2026-02-22 08:04:30'),
    (5, 5, '2026-02-22 08:02:15'),
    (6, 6, '2026-02-22 08:03:30'),
    (7, 7, '2026-02-22 08:04:30'),
    (8, 8, '2026-02-22 08:05:30'),
    (9, 9, '2026-02-22 08:06:30'),
    (10, 10, '2026-02-22 08:07:30');



END;
