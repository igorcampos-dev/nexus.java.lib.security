#!/bin/bash

if [ -d "target" ]; then
    rm -rf target
    echo "Removed 'target' folder."
fi

git init
echo "Initialized a new Git repository."

git add .

echo "Digite a mensagem do commit:"
read commit_message

git commit -m "$commit_message"
echo "Committed changes with message: $commit_message"

git push -u origin master
