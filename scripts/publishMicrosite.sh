#!/bin/bash
set -e

git config --global user.email "llfrometa@gmail.com"
git config --global user.name "llfrometa89"
git config --global push.default simple

sbt docs/publishMicrosite
