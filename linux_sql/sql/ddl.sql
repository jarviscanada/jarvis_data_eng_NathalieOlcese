-- Create database
CREATE DATABASE IF NOT EXISTS host_agent;

-- Switch to the host_agent database
\c host_agent;

-- Create host_info table 
CREATE TABLE IF NOT EXISTS host_info (
    id SERIAL PRIMARY KEY,
    hostname VARCHAR(255) NOT NULL UNIQUE,
    cpu_number INTEGER NOT NULL CHECK (cpu_number > 0),
    cpu_architecture VARCHAR(255) NOT NULL,
    cpu_model VARCHAR(255) NOT NULL,
    cpu_mhz NUMERIC NOT NULL CHECK (cpu_mhz > 0),
    L2_cache INTEGER NOT NULL CHECK (L2_cache >= 0),
    total_mem INTEGER NOT NULL CHECK (total_mem > 0),
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

-- Create host_usage table
CREATE TABLE IF NOT EXISTS host_usage (
    id SERIAL PRIMARY KEY,
    host_id INTEGER NOT NULL REFERENCES host_info(id) ON DELETE CASCADE,
    memory_free INTEGER NOT NULL CHECK (memory_free >= 0),
    cpu_idle NUMERIC NOT NULL CHECK (cpu_idle >= 0 AND cpu_idle <= 100),
    cpu_kernel NUMERIC NOT NULL CHECK (cpu_kernel >= 0 AND cpu_kernel <= 100),
    disk_io INTEGER NOT NULL CHECK (disk_io >= 0),
    disk_available INTEGER NOT NULL CHECK (disk_available >= 0),
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);
