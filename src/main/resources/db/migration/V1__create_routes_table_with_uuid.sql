-- Create routes table if it doesn't exist
CREATE TABLE IF NOT EXISTS routes (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    length_km DOUBLE,
    length_hours TIME,
    difficulty VARCHAR(50),
    region VARCHAR(50)
);