#!/bin/bash


psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Validate arguments
if [ "$#" -ne 5 ]; then
  echo "Error: The number of parameters is not valid"
  echo "USAGE: $0 [psql_host] [psql_port] [db_name] [psql_user] [psql_password]"
  exit 1
fi


vmstat_out=$(vmstat --unit M -t)
df_out=$(df -BM /)

function get_vmstat_info {
  local column=$1
  echo "$vmstat_out" | awk "FNR == 3 {print \$$column}"
}


hostname=$(hostname -f)
timestamp=$(date '+%Y-%m-%d %H:%M:%S %Z')
memory_free=$(get_vmstat_info 4)
cpu_idle=$(get_vmstat_info 15)
cpu_kernel=$(get_vmstat_info 14)
disk_io=$(echo "$vmstat_out" | awk "FNR == 3 {sum=\$9+\$10} END {print sum}")
disk_available=$(echo "$df_out" | awk "FNR == 2 {print \$4}" | sed 's/M//')

insert_stmt=$(cat <<-END
INSERT INTO host_usage (
  timestamp, host_id, memory_free, cpu_idle,
  cpu_kernel, disk_io, disk_available
)
VALUES
  (
    '$timestamp',
    (
      SELECT id FROM host_info WHERE hostname = '$hostname'
    ),
    $memory_free,
    $cpu_idle,
    $cpu_kernel,
    $disk_io,
    $disk_available
  );
END
)


export PGPASSWORD="$psql_password"


psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"


if [ $? -ne 0 ]; then
  echo "Error: Failed to insert host usage into the database."
  exit 1
fi



exit 0
