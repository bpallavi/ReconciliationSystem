#!/usr/bin/env python
# coding: utf-8




from fuzzywuzzy import fuzz
from fuzzywuzzy import process
import numpy
import pandas as pd
import json

#from JythonExecutor import Reconciliation
import os
import sys
import filecmp
import stat
import time
import subprocess
import datetime
import shutil
#from java.util import HashMap





def initialize(path1,path2,path3):
    print(path1)
    #path1 = "melbourne.csv"
    #path2 = "new_york.csv"
    #path3 = ""
    output = get_data(path1,path2,path3)
    print (output)
    return output

        

def get_columns( file1_data, file2_data, file1_dict, file2_dict, userdefined):
    matched_columns = {}
    for f1 in file1_data.columns:
        for f2 in file2_data.columns:
            if(file1_dict[f1] == file2_dict[f2]):
                matched_token=fuzz.partial_ratio(f1, f2)
                if matched_token > 80:
                    if f1 in matched_columns:
                        matched_columns[f1].append(f2)
                    else:
                        matched_columns[f1] = [f2]
        if f1 in userdefined:
            if f1 in matched_columns:
                for item in userdefined[f1]:
                    matched_columns[f1].append(item)
            else:
                matched_columns[f1] = userdefined[f1]

    return matched_columns
        
        

def get_data(path1, path2,path3):
    p1_data = pd.read_csv(path1)
    #create a dictionary of columns 
    num_col_p1 = len(p1_data.columns)
    p1_dict = {}
    for i in range(num_col_p1):
        p1_dict[p1_data.columns[i]] = p1_data.dtypes[i]
        
    p2_data = pd.read_csv(path2)
        #create a dictionary of columns 
    num_col_p2 = len(p2_data.columns)
    p2_dict = {}
    for i in range(num_col_p2):
        p2_dict[p2_data.columns[i]] = p2_data.dtypes[i]
        
    #Getting Json values
    with open(path3) as f:
        userdefined = json.load(f)
    
            
    if(num_col_p1 >= num_col_p2):
        return get_columns(p1_data, p2_data, p1_dict, p2_dict, userdefined)
    else:
        return get_columns(p2_data, p1_data, p2_dict, p1_dict, userdefined)

arg1 =  sys.argv[1]
arg2 =  sys.argv[2]
arg3 =  sys.argv[3]
print("Printing Args...."+sys.argv[1])
initialize(arg1,arg2,arg3)





