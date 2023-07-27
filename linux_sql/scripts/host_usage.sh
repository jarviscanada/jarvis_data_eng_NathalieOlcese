#!/bin/bash

function print_usage {
  echo "Usage: $0 <psql_host> <psql_port> <db_name> <psql_user> <psql_password>"
}

# Check the number of arguments
if [ "$#" -ne 5 ]; then
  echo "Error: number of parameters is not valid"
  print_usage
  exit 1
fi

# ... (other argument validations)

# Memory and disk I/O info
vmstat_out=$(vmstat --unit M -t)
df_out=$(df -BM /)

function get_vmstat_info {
  local column=$1
  echo "$vmstat_out" | awk "FNR == 3 {print \$$column}"
}

# ... (extracting CPU, memory, and disk usage data)

# Construct the INSERT statement with the timestamp representing the current time
insert_stmt=$(cat <<-END
INSERT INTO host_usage (
  timestamp, host_id, memory_free, cpu_idle,
  cpu_kernel, disk_io, disk_available
)
VALUES
  (
    '$timestamp',
    (
      SELECT
        id
      FROM
        host_info
      WHERE
        hostname = '$hostname'
    ),
    '$memory_free',
    '$cpu_idle',
    '$cpu_kernel',
    '$disk_io',
    '$disk_available'
  );
END
)

# ... (database connection and insertion)

exit 0

