#!/bin/bash

function print_usage {
  echo "Usage: $0 <psql_host> <psql_port> <db_name> <psql_user> <psql_password>"
}


if [ "$#" -ne 5 ]; then
  echo "Error: number of parameters is invalid"
  print_usage
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if ! [[ "$psql_port" =~ ^[0-9]+$ ]]; then
  echo "Error: psql_port must be a valid one."
  print_usage
  exit 1
fi


lscpu_out=$(lscpu)
meminfo_out=$(cat /proc/meminfo)

function get_lscpu_value {
  local regex=$1
  echo "$lscpu_out" | grep -E "$regex" | awk -F ': +' '{print $2}'
}

hostname=$(hostname)
cpu_number=$(get_lscpu_value "^CPU\(s\):")
cpu_architecture=$(get_lscpu_value "^Architecture:")
cpu_model=$(get_lscpu_value "^Model name:")
cpu_mhz=$(get_lscpu_value "^CPU MHz:")
L2_cache=$(get_lscpu_value "^L2 cache:" | sed 's/K//')
total_mem=$(echo "$meminfo_out" | grep -E "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date -u --rfc-3339=seconds)

insert_stmt=$(cat <<-END
INSERT INTO host_info (
  hostname, cpu_number, cpu_architecture,
  cpu_model, cpu_mhz, L2_cache, total_mem,
  timestamp
)
VALUES
  (
    '$hostname', '$cpu_number', '$cpu_architecture',
    '$cpu_model', '$cpu_mhz', '$L2_cache',
    '$total_mem', '$timestamp'
  );
END
)

export PGPASSWORD="$psql_password"


psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"

if [ $? -ne 0 ]; then
  echo "Error: couldn't insert host info into the database."
  exit 1
fi

echo "Host information was inserted."

exit 0
  
