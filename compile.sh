#!/bin/sh

files=$(ls *.java)

javac -source 1.8 -target 1.8 $files && java App
