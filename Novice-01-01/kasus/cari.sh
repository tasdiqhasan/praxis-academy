#!/bin/bash
foldername=$1
if [[ $(find $foldername -name "*.java") ]]; then
echo "Ada file java pada direktori $foldername"
else
echo "Tidak ada file java pada direktori $foldername"
fi