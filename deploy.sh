#set -v

ENVNAME=$1
if [ -z ${ENVNAME} ]; then
	ENVNAME=prd
fi

echo "Build for env: ${ENVNAME}"

PROJECTDIR=$(dirname "$(readlink -f "$0")")

cd ${PROJECTDIR}
git pull

gradle clean
gradle deploy -PEnv=${ENVNAME}

RET=$?
if [ ${RET} -ne 0 ]; then
   echo "Build error ${RET}"
   exit ${RET}
fi

echo "Build completed"