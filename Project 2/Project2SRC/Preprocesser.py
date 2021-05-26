# Logan Ladd and Asher Worley
# Preproccesser to create two csv files, One with original data with s for 10 fold CV, very similar to prgm1

import pandas as pandas
import numpy as numpy
import random
import csv

class data:
    def __init__(self, classLocation, rm, missing, classify, file, split): #start data frame
        dataFrame = pandas.DataFrame()
        fileName = file #variables
        s=[] #sets
        b=[] #bins
        i=0  #iterators
        j=0
        dataFrame=pandas.read_csv(file+'.data',skiprows=rm,sep=split,header=None) #read file
 
        while j<len(dataFrame): #loop through file
            s.append(i)
            i+=1
            if i>=10:
                i=0
            j+=1

        dataFrame.rename(columns={classLocation: 'Class'}, inumpylace=True) #rename 
        column = ['Class']
        
        newColumn = column + (dataFrame.columns.drop(column).tolist())
        
        dataFrame = dataFrame[newColumn]
        
        dataFrame = dataFrame.sample(frac=1).reset_index(drop=True) #randomize examples

        columnFIX=['Class'] #rename Attr
        for j in range(len(dataFrame.columns)-1):
            columnFIX.append(j)
        dataFrame.columns = columnFIX

        if classify: #convert names to ints
            tmp=(dataFrame.Class.unique())
            tmp=numpy.sort(tmp)
            for j in range(len(tmp)):
                dataFrame['Class'] = dataFrame['Class'].replace(tmp[j], j)

        else:
            classB = dataFrame['Class'] #bin regression values
            dataFrame['Class'] = pandas.cut(dataFrame['Class'], 10, labels=False)

        dataFrame.insert(1, 's', s) #Assign s

        cat=[] #convert cat varaiables to ints and index, also track attr for cat or numeric
        catVarCount=0
        
        for j in range(len(dataFrame.columns)-2):
            if dataFrame[j].dtype == object:
                original = dataFrame[j].unique()
                replace = dict(zip(original, range(len(original))))
                dataFrame[j]=dataFrame[j].map(replace)
                cat.append("0")
                catVarCount += 1
            else:
                cat.append("1")
        matrix = ''
        
        for j in range(len(cat)): # matrix for distance metric for cat variables
            if cat[j]=='0':
                matrix+=str(j)+','+str(len(dataFrame[j].unique()))+','+str(len(dataFrame.Class.unique()))+'\n'
                for i in dataFrame[j].unique():
                    for b in numpy.sort(dataFrame.Class.unique()):
                        matrix += str(len(dataFrame[(dataFrame['Class']==b)&(dataFrame[j]==i)])/len(dataFrame[dataFrame['Class']==b]))+','
                    matrix+='\n'

            else:
                if (dataFrame[j].max()==dataFrame[j].min()):
                    dataFrame[j].values[:]=0
                else:
                    dataFrame[j]=dataFrame[j].apply(lambda x: (x-dataFrame[j].min())/(dataFrame[j].max()-dataFrame[j].min()))


        if classify:
            header=str(len(dataFrame.columns)-2)+','+str(len(dataFrame))+','+str(
                dataFrame['Class'].nunique())+','+str(catVarCount)+'\n'+matrix+','.join(map(str, tmp))+','+'\n'
        else:
            dataFrame['Class']=classB
            header = str(len(dataFrame.columns)-2)+','+str(len(dataFrame))+','+'-1'+','+ str(catVarCount)+'\n'+matrix
        with open(fileName+'.csv','w') as fp:
            fp.write(header)
            fp.write((dataFrame.to_csv(index=False, header=False)))
            fp.close


#data(12, 1, False, False, 'forestfires', ',')

