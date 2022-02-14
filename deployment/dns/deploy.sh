DIR="$( cd "$( dirname "$0" )" && pwd )"

echo "Sourcing auth..."
source "${DIR}/auth.sh"

echo "Deploying..."
terraform apply
