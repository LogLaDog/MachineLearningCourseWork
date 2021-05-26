#------------------------------------------------------------#
#Logan Ladd & Asher Worley
#This Python program imports data from the data sets and exports the processed data into two excel files.
#The first excel file contains the original data with sets adding a header to run NB.
#The second file will do the same but with scrambled data.
#------------------------------------------------------------#


import random
from random import choice
import itertools
import pandas as pd
import numpy as np


class data: # Data function to process the data into csv format
    dataframe = pd.DataFrame()
    top = ''
    ID = ''
    
    def __init__(self, class_location, missing_value, binned, filename):
        global dataframe
        global ID
        global top
        
        ID = filename
        
        i = 0
        j = 0
        
        
        dataframe = pd.read_csv('OriginalDataFiles\\' + filename + '.data', top = None)
        
        data_sets = []       #sets and bins
        data_bins = []
        
        while(i < len(dataframe)):
            data_sets.append(j)
            j += 1
            if(j >= 10):
                j = 0
            i += 1
            
        dataframe.rename(columns={class_location:'Class'}, inplace=True)         #placing class column at the top of the csv
        
        order_column = ['Class']
        
        generated_col = order_column + (dataframe.columns.drop(order_column).tolist())
        dataframe = dataframe[generated_col]
 
        dataframe = dataframe.sample(frac=1).reset_index(drop=True)         #randomize the examples

        rig_columns = ['Class']         #rename attributes 0-num when moving class column to the top
        for i in range(len(dataframe.columns)-1):
            rig_columns.append(i)
        dataframe.columns = rig_columns
        
        dataframe.insert(1, 'Sets', data_sets)         #assigning sets to the examples 

        if binned: #number assignments for binned data
            for i in range(len(dataframe.columns)-2):
                num_bins = 5
                dataframe[i] = pd.qcut(dataframe[i], num_bins, labels=False, duplicates='drop')
                data_bins.append(str(num_bins))
        
        if missing_value:         #new number assignments for non-integer data (y,n,?)
            dataframe = dataframe.replace(to_replace ='?', value = '2')
            dataframe = dataframe.replace(to_replace ='y', value = '1')
            dataframe = dataframe.replace(to_replace = 'n', value = '0')



       else:         #generating a list with the number of bins for each attribute (data has to be pre-discretized)
        for i in range(len(dataframe.columns)-2):
                data_bins.append(str(int(dataframe[i].max()) + 1))
                
        class_final = (dataframe.Class.unique())         #converting class names to ints for processing by algorithm later 
        class_final = np.sort(class_final)
        
            for i in range(len(class_final)): # replacing loop for dataframes
                dataframe['Class'] = dataframe['Class'].replace(class_final[i], i)
 
        top_row = str(dataframe['Class'].nunique()) + "," + str(len(dataframe.columns)-2) + "," + str(len(dataframe)) + '\n' #setting row           
        top = top_row + '*,*,' + ','.join(map(str, data_bins)) + '\n' + ','.join(map(str, class_final)) + '\n'




    def scrambleddata(self):     #export data to excel file (10% attribute scrambled)
        global dataframe
        numtoscramble = 0
        if int((len(dataframe.columns)-2)/10) < 1: #logic to scramble data
            numtoscramble = 1
        else:
            numtoscramble = int((len(dataframe.columns)-2)/10)
        columnscramble = [random.randint(0,len(dataframe.columns)-3) for i in range(numtoscramble)] #scrabmling with random selection of attributes

        for column in columnscramble:
            dataframe[column] = dataframe[column].sample(frac=1).reset_index(drop=True)

        with open('ProcessedDataFiles\\' + ID + '-scrambled.csv', 'w') as file:
            file.write(top)
            file.write((dataframe.to_csv(index=False, top = False)))





    def originaldata(self):     #exporting original data to excel file (processed)
        with open('ProcessedDataFiles\\' + ID + '.csv', 'w') as file:
            file.write(top)
            file.write((dataframe.to_csv(index=False, top = False)))

            
breast-cancer = data(1, False, True, 'breast-cancer')
breast-cancer.originaldata()
breast-cancer.scrambleddata()



glass = data(10, False, True, 'glass')
glass.originaldata()
glass.scrambleddata()



housevotes = data(0, True, False, 'house-votes-84')
housevotes.originaldata()
housevotes.scrambleddata()



soybean = data(35, False, False, 'soybean-small')
soybean.originaldata()
soybean.scrambleddata()



iris = data(4, False, True, 'iris')
iris.originaldata()
iris.scrambleddata()


