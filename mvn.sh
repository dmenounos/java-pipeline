#!/bin/bash

# clean lifecycle phase
CLP=""

# default lifecycle phase
DLP=""

# skip tests
STS=""

# test class
TST=""

# profile name
PRF=""

# module name
MDL="."

while getopts ":cd:st:p:m:h" opt; do
	case $opt in
	"c")
		CLP="clean"
		;;
	"d")
		case $OPTARG in
		"compile"|"test"|"package"|"install")
			DLP="$OPTARG"
			;;
		*)
			echo "Valid arguments for -$opt: compile | test | package | install" >&2
			exit 1
			;;
		esac
		;;
	"s")
		STS="-DskipTests"
		;;
	"t")
		TST="-Dtest=$OPTARG"
		;;
	"p")
		PRF="-P$OPTARG"
		;;
	"m")
		if [ ! -d $OPTARG ]; then
			echo "Directory $OPTARG does not exist. Operation aborted." >&2
			exit 1
		fi

		MDL="$OPTARG"
		;;
	"h")
		echo "Usage: $0 [OPTIONS]" >&2
		echo "OPTIONS" >&2
		echo "  -c clean lifecycle phase" >&2
		echo "  -d default lifecycle phase" >&2
		echo "  -s skip tests" >&2
		echo "  -t test class" >&2
		echo "  -p profile name" >&2
		echo "  -h this information" >&2
		echo "EXAMPLE" >&2
		echo "$0 -c" >&2
		echo "$0 -d compile" >&2
		echo "$0 -d test" >&2
		echo "$0 -d test -t com.example.*.*" >&2
		echo "$0 -c -d package" >&2
		echo "$0 -c -d install" >&2
		exit 1
		;;
	":")
		echo "Option -$OPTARG requires an argument" >&2
		exit 1
		;;
	"?")
		echo "Invalid option: -$OPTARG" >&2
		exit 1
		;;
	esac
done

if [ "$#" -eq "0" ]; then
	$0 -h
	exit 1
fi

if [ -z "$CLP" ] && [ -z "$DLP" ]; then
	$0 -h
	exit 1
fi

CMD="cd $MDL"
echo $CMD
eval $CMD

CMD="mvn $CLP $DLP $STS $TST $PRF"
echo $CMD
eval $CMD
