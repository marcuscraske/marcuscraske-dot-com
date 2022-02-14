DIR="$( cd "$( dirname "$0" )" && pwd )"

echo "Building site..."
(cd "${DIR}/../../website" && bundle exec jekyll build)

echo "Deploying..."
terraform apply
