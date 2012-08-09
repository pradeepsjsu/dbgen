#!/bin/bash
ECODE_SIZE=0;

# tpch-globals
p="part";
s="supplier";
ps="partsupp";
c="customer";
o="orders";
li="lineitem";
n="nation";
r="region";
s_x="10000";
c_x="150000";
p_x="200000";
ps_x="800000";
o_x="1500000";
li_x="6000000";
n_x="25";
r_x="5";
# gmm-globals
data="data";
cluster="cluster";
data_x="1000000";
cluster_x="10";

function size () {
	tbl="$1";
	sf="$2";

	if [[ $tbl = '' ]] ; then
		ECODE_SIZE=0;
	fi
	if [[ $sf = '' ]] ; then
		sf=1;
	fi
	if [[ "$tbl" = 'p' || "$tbl" = 'part' ]] ; then
		ECODE_SIZE=$p_x;
	fi
	if [[ "$tbl" = 'ps' || "$tbl" = 'partsupp' ]] ; then
		ECODE_SIZE=$ps_x;
	fi
	if [[ "$tbl" = 's' || "$tbl" = 'supplier' ]] ; then
		ECODE_SIZE=$s_x;
	fi
	if [[ "$tbl" = 'c' || "$tbl" = 'customer' ]] ; then
		ECODE_SIZE=$c_x;
	fi
	if [[ "$tbl" = 'o' || "$tbl" = 'orders' ]] ; then
		ECODE_SIZE=$o_x;
	fi
	if [[ "$tbl" = 'li' || "$tbl" = 'lineitem' ]] ; then
		ECODE_SIZE=$li_x;
	fi
	if [[ "$tbl" = 'data' ]] ; then
		ECODE_SIZE=$data_x;
	fi
	let ECODE_SIZE="$ECODE_SIZE*$sf";
	# the constant tables
	if [[ "$tbl" = 'n' || "$tbl" = 'nation' ]] ; then
		ECODE_SIZE=$n_x;
	fi
	if [[ "$tbl" = 'r' || "$tbl" = 'region' ]] ; then
		ECODE_SIZE=$r_x;
	fi
	if [[ "$tbl" = 'cluster' ]] ; then
		ECODE_SIZE=$cluster_x;
	fi
	return 1;
	return 1;
}

function header () {
	date
	echo ""
	echo "# scale_factor: $sf";
	echo ""
}

function footer () {
	echo ""
	date
	exit;
}

function gendata () {
	tbl=$1;
	if [[ $tbl = '' ]] ; then
		echo "# invalid table name ('$tbl')"
	fi
	if [[ $sf = '' ]] ; then
		sf=1;
	fi
	size $tbl $sf; res=$?
	if [[ $res = "1" ]] ; then
		num_recs="$ECODE_SIZE";
		dest="$db-$sf";
		echo "# gendata ('$tbl'): $num_recs recs ";
		echo "$exec_dbgen $db $tbl $num_recs $dest >& .stdout"
		$exec_dbgen $db $tbl $num_recs $dest >& .stdout
	fi
	return 0;
}

function exec_tpch {
	echo "== tpch data generator ==";
	rm -f $pwd/.stdout*
	gendata $n
	gendata $r
	gendata $c
	gendata $o
	gendata $li
	gendata $p
	gendata $ps
	gendata $s
	return 0;
}

function exec_gmm {
	echo "== gmm data generator ==";
	gendata $data;
	return 0;
}

# -- main
exec_dbgen="rexec";
pwd="$PWD";
if [ ! -s "$pwd/$exec_dbgen" ] ; then
	pwd="$pwd/bin";
fi
if [ ! -s "$pwd/$exec_dbgen" ] ; then
	echo "# error: unable to find 'rexec' script";
	exit;
fi
exec_dbgen="$pwd/$exec_dbgen";
sf=$1;
db=$2;

if [ "$sf" = '' -o "$db" = '' ] ; then
	echo "usage: $0 <scale-factor> <db-name>";
	exit;
fi

if [ "$db" = 'tpch' ] ; then
	header;
	exec_tpch
	footer;
elif [ "$db" = 'gmm' ] ; then
	header;
	exec_gmm
	footer;
else 
	echo "Invalid db-name ('$db')";
fi

