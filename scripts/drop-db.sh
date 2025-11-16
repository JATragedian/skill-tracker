#!/bin/bash

echo "Dropping DEV PostgreSQL schema..."
docker exec -i skilltracker-postgres psql -U postgres -d skilltracker <<EOF
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
EOF

echo "DEV database schema reset!"