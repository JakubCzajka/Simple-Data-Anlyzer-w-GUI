# Simple-Data-Anlyzer-w-GUI

## Description

A simple tool providing statistics for time series data.

## Datasets

### Overview

The program takes CSV files as input, with user-specified delimiter. Exactly one field
in the dataset should contain timestamp, and all of the other fields should be
numeric. The only acceptable decimal separator is a dot, and as such it can’t be
used as CSV file delimiter.

If a field name is missing, program automatically generates gem using the
following pattern : Field0, Field 1, Field2… .

If a timestamp is missing or invalid, it is replaced with the minimal
timestamp, and it’s record is not used in any statistics.

If a number is missing or otherwise invalid it is marked as “Not a number”,
and it does not affect any of the statistics.

### Timestamps

Date in both input files and user should be
provided in the following format:
      YYYY-MM-DDThh:mm:ss.msZ
For example:
      2019-10-01T09:25:24.00Z
      
      
 
