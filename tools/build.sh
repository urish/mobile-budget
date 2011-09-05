#!/bin/sh
PROJECT_NAME="mobile-budget"

MYDIR=$(cd "`dirname $0`"; pwd)
TIPROJROOT=$(cd "`dirname $0`/.."; pwd)
PROJDIR="${TIPROJROOT}/target/mobile-budget-1.0-titanium/build/iphone"
REVISION=`git rev-parse HEAD | cut -b 1-7`
TARGET_NAME="${PROJECT_NAME}-universal"
TARGET_SDK="iphoneos4.3"
PROJECT_BUILDDIR="${PROJDIR}/build/Release-iphoneos"
BUILD_HISTORY_DIR="${HOME}/Builds/${PROJECT_NAME}"
BUILD_HISTORY_SYMBOLS_DIR="${BUILD_HISTORY_DIR}/Symbols"
DEVELOPER_NAME="iPhone Distribution: Uri Shaked"
PROVISONING_PROFILE="${MYDIR}/adhoc.mobileprovision"
IPA_NAME="${BUILD_HISTORY_DIR}/${PROJECT_NAME}-${REVISION}.ipa"
SYMBOLS_NAME="${BUILD_HISTORY_SYMBOLS_DIR}/${PROJECT_NAME}-${REVISION}.tgz"
CODE_SIGN_ENTITLEMENTS="Resources/Entitlements.plist"

cp "${MYDIR}/Entitlements.plist" "${PROJDIR}/${CODE_SIGN_ENTITLEMENTS}"

# compile project
echo Building Project
cd "${PROJDIR}"
touch Classes/ApplicationRouting.m
xcodebuild -target "${TARGET_NAME}" -sdk "${TARGET_SDK}" -configuration Release "CODE_SIGN_ENTITLEMENTS=${CODE_SIGN_ENTITLEMENTS}" DEPLOYMENT_POSTPROCESSING=YES

#Check if build succeeded
if [ $? != 0 ]
then
  exit 1
fi

mkdir -p "${BUILD_HISTORY_DIR}"
mkdir -p "${BUILD_HISTORY_SYMBOLS_DIR}"

echo "Build IPA"

/usr/bin/xcrun -sdk iphoneos PackageApplication -v "${PROJECT_BUILDDIR}/${PROJECT_NAME}.app" -o "${IPA_NAME}" --sign "${DEVELOPER_NAME}" --embed "${PROVISONING_PROFILE}"
if [ $? != 0 ]
then
  exit 1
fi

echo "Archiving symbols..."
tar zcf "${SYMBOLS_NAME}" -C "${PROJECT_BUILDDIR}" "${PROJECT_NAME}.app.dSYM"

echo "Sending final mail..."
python "${MYDIR}/mailer.py" "doron@watchdox.com" "${PROJECT_NAME} revision ${REVISION}" "Built at `date`, enjoy!" "${IPA_NAME}"

echo "Autobuild: DONE!"
echo " output=${IPA_NAME}"
