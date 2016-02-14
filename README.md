# Attack Tree Transformation (ATT) project
## Description
This project contains a universal attack tree modeling language and transformations from/to concrete other attack tree data-standards.
It can be compiled into standalone .jar and can be used to transform an attack tree in a specific data format towards another data format.


## Overview
![Project Overview](/images/Metamodel-and-Transformations-80p.png)
The project contains two main components: A universal metamodel for attack trees (UAT) and a set of transformations that transform attack trees to differetn data-standards.

The transformations can be composed to produce a single transformation sequence, for example a transformation from the ATAnalyzer data-format to the ATCalc input format

## Get it
Simply download [the ATT JAR file](ATT.jar) of this project.

## Usage
The tranformations can be invoked by running the standalone jar from command line. 
It requires the following four paramters.
```
Usage: ATTMain source-language target-language source-model target-model(to be created)
Parameters: source, target chosen out of [ADTBin, ADTool, ATA, ATCalc, UATBin, UATMM]
```
The Source and Target langauge must be chosen from a set of predefined options, the source-model should refer to an existing file. The target-model is the name of the output file that is to be created in the transformation.


Some usage example are:
```
java -jar ATT.jar ADTool ADTBin ADToolFile.xml ADToolBinaryFile.xml
java -jar ATT.jar ADTool ATCalc ADToolFile.xml ATCalcInput.txt
java -jar ATT.jar ATA ADTool ATA.xml ADTool.xml
```