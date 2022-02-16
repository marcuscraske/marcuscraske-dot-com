DIR="$( cd "$( dirname "$0" )" && pwd )"

echo "Building site..."
(cd "${DIR}/../../website" && bundle exec jekyll build)

echo "Syncing files..."
aws s3 sync "${DIR}/../../website/_site/" s3://marcuscraske.com/
