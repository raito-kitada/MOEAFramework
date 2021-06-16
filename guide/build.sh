#!/bin/bash

VERSION=2.13lab

if [ ! -e ../guide_output ]; then
  echo "make ../guide_output"
  mkdir ../guide_output
fi

sed s/%VERSION%/${VERSION}/ installation.tex > ../guide_output/installation.tex
sed s/%VERSION%/${VERSION}/ style.tex > ../guide_output/style.tex
sed s/%VERSION%/${VERSION}/ title.tex > ../guide_output/title.tex

cd ../guide_output
ln -s ../guide/guide.tex .
ln -s ../guide/*.png .

pdflatex guide.tex