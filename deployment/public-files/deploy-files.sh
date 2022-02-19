DIR="$( cd "$( dirname "$0" )" && pwd )"

echo "Build index..."
../../public-files/build.sh

echo "Syncing files..."
aws s3 sync "${DIR}/../../public-files/output" s3://files.marcuscraske.com/
