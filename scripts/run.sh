#set -v

APPDIR=$(dirname "$0")

cd ${APPDIR}

set -o pipefail

/usr/bin/java -Dwebdriver.chrome.logfile=`pwd`/chromelog.txt -Dwebdriver.chrome.verboseLogging=true -cp "conf:libs/*" io.github.micwan88.homeconf.ConfDaemon | tee -a home-conf.log