path_to_sql="./pay_sys_dump.sql"

if (( $# < 1 )); then
    echo "Default path is current directory."
    echo "You can pass the path to the *.sql script to restore database"
    echo "./restore_pay_sys.sh /some/path/to/script [linux | mac]"
    echo ""
else
    path_to_sql="$1/pay_sys_dump.sql"
fi
echo $path_to_sql

os_type="linux"
if (( $# == 2 )); then
    echo "Choosed $2 system"
    os_type=$2
else
    echo "Choosed default system (linux)"
fi

if [[ $os_type == "mac" ]]; then
    psql -f $path_to_sql 2>&1 >/dev/null \
        && echo 'Database payment_system recovery completed successfully!' \
        || echo 'Database payment_system recovery failed!'
else 
    restore_cmd="psql -f $path_to_sql 2>&1 >/dev/null \
    && echo 'Database payment_system recovery completed successfully!' \
    || echo 'Database payment_system recovery failed!'"

    sudo su postgres -c "$restore_cmd"
fi
