#!/usr/bin/env bash

# Fancy script title
IFS='' read -r -d '' SCRIPT_HEADER <<'EOF'

 ██████╗ ███████╗██████╗ ██╗      ██████╗ ██╗   ██╗███╗   ███╗███████╗███╗   ██╗████████╗
 ██╔══██╗██╔════╝██╔══██╗██║     ██╔═══██╗╚██╗ ██╔╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
 ██║  ██║█████╗  ██████╔╝██║     ██║   ██║ ╚████╔╝ ██╔████╔██║█████╗  ██╔██╗ ██║   ██║
 ██║  ██║██╔══╝  ██╔═══╝ ██║     ██║   ██║  ╚██╔╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║
 ██████╔╝███████╗██║     ███████╗╚██████╔╝   ██║   ██║ ╚═╝ ██║███████╗██║ ╚████║   ██║
 ╚═════╝ ╚══════╝╚═╝     ╚══════╝ ╚═════╝    ╚═╝   ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝
EOF

echo -e "\033[1;32m${SCRIPT_HEADER}\033[1;32m"

# Define paths
PATH_CURR=$(pwd)
PATH_BASE=$(dirname "${PATH_CURR}")

# Determine inventory file to use
INVENTORY="hosts_inventory"

# Build extra vars
# -- Path to base of all files
EXTRA_VARS+="root_path=\"${PATH_BASE}\" "

# Attach any args after first as variables
EXTRA_VARS+="${@:2} "

# Build tags
if [[ -z "${1}" || "${1}" == "all" ]]; then

    if [[ -z "${1}" ]]; then
        echo "Available tags:"
        echo "$(ls roles)"
        echo ""
        echo "...or use tag 'all'."
        echo ""
        echo "Example usages:"
        echo "./deploy.sh all"
        echo "./deploy.sh copy-files"

        exit 1
    fi

else
    DEPLOY_TAGS="${1}"
fi

# Reset terminal colour
echo -e "\033[0m"

# Check user wants to continue
echo "You are about to perform an automated deployment with the following tags:"
echo -e "\033[1;31m${DEPLOY_TAGS}\033[0m"
echo ""
echo "And the following variables:"
echo "${EXTRA_VARS}"
echo ""
echo "Running deployment..."


# Run deployment
ansible-playbook deploy.yml -vvvv -i "${INVENTORY}" --extra-vars "${EXTRA_VARS}" --tags "${DEPLOY_TAGS}"
