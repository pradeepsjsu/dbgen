#!/bin/bash
ECODE_SIZE=0;
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
	let ECODE_SIZE="$ECODE_SIZE*$sf";
	if [[ "$tbl" = 'n' || "$tbl" = 'nation' ]] ; then
		ECODE_SIZE=$n_x;
	fi
	if [[ "$tbl" = 'r' || "$tbl" = 'region' ]] ; then
		ECODE_SIZE=$r_x;
	fi
	return 1;
}

function gendata () {
	tbl=$1;
	scale_factor=$2;
	if [[ $tbl = '' ]] ; then
		echo "# invalid table name ('$tbl')"
	fi
	if [[ $scale_factor = '' ]] ; then
		scale_factor=1;
	fi
	size $tbl $scale_factor; res=$?
	if [[ $res -eq "1" ]] ; then
		num_recs="$ECODE_SIZE";
		echo "# gendata ('$tbl'): $num_recs recs ";
		$exec_dbgen $tbl $num_recs >& stdout
	fi
	return 0;
}

# -- main

exec_dbgen="run";
pwd="$PWD";
if [ ! -s "$pwd/$exec_dbgen" ] ; then
	pwd="$pwd/bin";
fi
if [ ! -s "$pwd/$exec_dbgen" ] ; then
	echo "# error: unable to find dbgen 'run' script";
	exit;
fi
exec_dbgen="$pwd/$exec_dbgen";
SF=$1;

if [[ $SF = '' ]] ; then
	echo "usage: $0 <scale-factor>";
	exit;
fi
echo ""
echo "== tpch data generator ==";
date
echo ""
echo "# scale_factor: $SF";
rm -f $pwd/stdout*

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
gendata $n $SF
gendata $r $SF
gendata $c $SF;
gendata $o $SF;
gendata $li $SF;
gendata $p $SF;
gendata $ps $SF;
gendata $s $SF; 
echo ""
date
exit;
