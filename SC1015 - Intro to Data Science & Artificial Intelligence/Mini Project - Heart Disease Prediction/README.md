# 1015-Mini-Project
This is a repository for our mini project


## Practical Motivation

According to the singapore heart foundation, cardiovascular disease (heart disease and stroke) represents 32% of global deaths annually. Singapore has seen a increase of death due to cardiovascular disease as well. (Singapore Heart Foundation,2021)

It was found by the Centre of disease control and prevention that there are many factors as to why one may develop heart disease. 

As a major health hazard to the population and something that can happen to anyone, we want to understand how other health factors as well as lifestyle habits might influence the risk of one getting heart disease.

## Sample Collection
Size of dataset is too big and hence will need to be downloaded from Kaggle (account required)
Dataset is from Kaggle: https://www.kaggle.com/datasets/cdc/behavioral-risk-factor-surveillance-system?select=2015.csv 

This dataset is taken from the Behavioral Risk Factor Surveillance System(BFRSS) survey conducted by the Centre of Disease Control(CDC) yearly in the United States. The survey is conducted over telephone to gather the data on the health status and conditions of the US residents.

This dataset has a variety of variables and hence we have chosen to see if an individual has heart disease based on their health metrics. The dataset has many columns of data, however many of them are duplicates or are very similar and hence here are our chosen variables.

The dataset is high reliable and trustworthy as it is taken from Centre for Disease control and prevention which is a government agency in the United States of America.

## Data
In this section, we will mention our data, and what was the question asked to obtain this data.

### Numerical:

**Physical Health** - Now thinking about your physical health, which includes physical illness and injury, for how many days during the past 30 days was your physical health not good?

**Alcohol consumption** - During the past 30 days, how many days per week or per month did you have at least one drink of any alcoholic beverage such as beer, wine, a malt beverage or liquor?

**Mental Health** – Thinking about your mental health, for how many days during the past 30 days was your mental health not good? (0-30 days)

**Age** - Age

### Categorical

**General Health** – Would you say that in general your health is...

**Exercise Rate** - During the past month, other than your regular job, did you participate in any physical activities or exercises such as running, calisthenics, golf, gardening, or walking for exercise? 

**High Cholesterol** – Have you EVER been told by a doctor, nurse or other health professional that your blood cholesterol is high?

**High Blood Pressure** – Have you EVER been told by a doctor, nurse, or other health professional that you have high blood pressure?

**Gender** –  Gender

**Smoking Rate** – Do you now smoke cigarettes every day, some days, or not at all?

**Kidney Disease** – (Ever told) you have kidney disease? Do NOT include kidney stones, bladder infection or incontinence.

**Asthma** – (Ever told) you had asthma?

**Skin Cancer** – (Ever told) you had skin cancer

**Heart Attack** – (Ever told) you that you had a heart attack also called a myocardial infarction?

**Stroke** – (Ever told) you had a stroke?

**Checkup** - About how long has it been since you last visited a doctor for a routine checkup? A routine checkup is a general physical exam, not an exam for a specific injury, illness, or condition.

**Difficulty walking** - Do you have serious difficulty walking or climbing stairs?

**Heart Disease(response)** - (Ever told) you had angina or coronary heart disease?

We have a total of 19 variables, 4 of it are numerical 15 of them are categorical.

## Problem formulation
​
**Problem statement:** What are some important health factors or life habits in determining the risk of having a heart disease?
​
We want to link the different health factors and habit of individuals and use it to determine the risk of getting heart disease.
​
We will train a model to determine if an individual has heart disease or not. From that, we will be able to analyse the data and siphon out any important information from the different predictors that we have used. From this, we can find its correlation and determine if that health factor or individual habits, is important in relation to heart disease.

## Data Preparation

Our dataset has roughly 300 variables. However, we have reduced this to 19 variables as many of the data is either a duplicate or a very similar question asked to respondents. From the remaining dataset, we chose a variety of health factors as well as life habits of the individuals.

The dataset has a mixture of numerical and categorical data. However, there are much more categorical data than numerical. Many of the categorical data are numbered and hence we had to replace them with their original metric.

We have done this and displayed the data cleaning with graphs to make it easier to visualize

## Data Cleaning

In this section, we will be cleaning the data. Here are some of the steps that will be taken.

1. Selection of variables
2. Renaming of variables
3. Removal of NULL data
4. Removal of duplicate data
5. renaming of categorical variable values

## New tools implemented

**Machine Learning**:
1. Chi-square test of independance
2. Binary Cross entropy
3. Isotonic regression

**Data Processing**
1. Undersampling (Edited nearest neighbour)
2. Oversampling (Synthetic minority oversampling technique)

**Evaluation**
1. Precision-recall
2. Calibration graph
3. ROC-AUC

# Conclusion

We found that the original data was the most accurate with an accuracy of 0.92 and undersampled data had an accuracy of 0.918.However, the undersampled data had the lower GINI coefficient of 0.58 after isotonic regression which shows that the undersampled data has a lower data inequality. On top of that, the undersampled data has the lowest false negative rate and the importance of this has been explained above.

Looking at the Original data, feature of importances graph, we can see that the model placed a higher importance on Age, General Health,Difficulty Walking and High Blood Pressure where as the undersampled data, feature of importances graph placed a higher importance on High Cholestrol, Pneumonia Vaccine, Heart Attack and Age. Lastly, the oversampled data, feature of importances graph placed a higher importance on Heart Attack, General Health, Age and High Blood Pressure. From this, we can deduce that age is one of the most important factor in predicting heart disease, followed very closely by heart attack.

However all of the features have importance levels that we cannot ignore. All the features importance are relatively high which shows all the variables have a role in predicting heart disease.

To answer our initial question, Age, General health, Heart attack, Physical Health, Difficulty walking, High blood pressure, High Cholestrol and Pneumonia Vaccine are health factors which are risk factors of heart disease with age being one of the most, if not the most important factor.

## References
Viadinugroho, R. a. A. (2022, January 6). Imbalanced Classification in Python: SMOTE-ENN Method. Medium. https://towardsdatascience.com/imbalanced-classification-in-python-smote-enn-method-db5db06b8d50

GeeksforGeeks. (2023). Isotonic Regression in Scikit Learn. GeeksforGeeks. https://www.geeksforgeeks.org/isotonic-regression-in-scikit-learn/

Brownlee, J. (2019). A Gentle Introduction to Dropout for Regularizing Deep Neural Networks. MachineLearningMastery.com. https://machinelearningmastery.com/dropout-for-regularizing-deep-neural-networks/

Brownlee, J. (2019b). A Gentle Introduction to Batch Normalization for Deep Neural Networks. MachineLearningMastery.com. https://machinelearningmastery.com/batch-normalization-for-training-of-deep-neural-networks/

McNeese, B. (2020, April 25). Are the Skewness and Kurtosis Useful Statistics? BPI Consulting. https://www.spcforexcel.com/knowledge/basic-statistics/are-skewness-and-kurtosis-useful-statistics#:~:text=The%20rule%20of%20thumb%20seems,the%20data%20are%20highly%20skewed

Jordan, J. (2018). Evaluating a machine learning model. Jeremy Jordan. https://www.jeremyjordan.me/evaluating-a-machine-learning-model/

Abhigyan. (2021, December 13). Calculating Accuracy of an ML Model. - Analytics Vidhya - Medium. Medium. https://medium.com/analytics-vidhya/calculating-accuracy-of-an-ml-model-8ae7894802e

sklearn.metrics.f1_score. (n.d.). Scikit-learn. https://scikit-learn.org/stable/modules/generated/sklearn.metrics.f1_score.html

Fig. 1: Differences between undersampling and oversampling. (n.d.-b). ResearchGate. https://www.researchgate.net/figure/Differences-between-undersampling-and-oversampling_fig1_341164819

Heart Disease and Stroke Prevention. (n.d.-b). https://www.health.ny.gov/diseases/cardiovascular/heart_disease/#:~:text=About%20697%2C000%20people%20die%20of,Americans%20have%20a%20heart%20attack.

Precision-Recall. (n.d.-c). Scikit-learn. https://scikit-learn.org/stable/auto_examples/model_selection/plot_precision_recall.html#:~:text=The%20precision%2Drecall%20curve%20shows,a%20low%20false%20negative%20rate.

Shin, T. (2022b, November 10). Understanding Feature Importance and How to Implement it in Python. Medium. https://towardsdatascience.com/understanding-feature-importance-and-how-to-implement-it-in-python-ff0287b20285

Narkhede, S. (2021b, June 15). Understanding Confusion Matrix - Towards Data Science. Medium. https://towardsdatascience.com/understanding-confusion-matrix-a9ad42dcfd62

Precision-Recall. (n.d.-d). Scikit-learn. http://scikit-learn.org/stable/auto_examples/model_selection/plot_precision_recall.html#:~:text=The%20precision%2Drecall%20curve%20shows,a%20low%20false%20negative%20rate.

Riva, M., & Riva, M. (2023). Batch Normalization in Convolutional Neural Networks | Baeldung on Computer Science. Baeldung on Computer Science. https://www.baeldung.com/cs/batch-normalization-cnn#:~:text=Batch%20Norm%20is%20a%20normalization,learning%20rates%2C%20making%20learning%20easier.

Doshi, K. (2022, January 6). Batch Norm Explained Visually — How it works, and why neural networks need it. Medium. https://towardsdatascience.com/batch-norm-explained-visually-how-it-works-and-why-neural-networks-need-it-b18919692739

Towards Data Science. (n.d.). Towards Data Science. https://towardsdatascience.com/dropout-in-neural-networks-47a162d621d9%20https://machinelearningmastery.com/dropout-for-regularizing-deep-neural-networks/

D’Agostino, A. (2023b, February 4). Get started with TensorFlow 2.0 — Introduction to deep learning. Medium. https://towardsdatascience.com/a-comprehensive-introduction-to-tensorflows-sequential-api-and-model-for-deep-learning-c5e31aee49fa#:~:text=The%20sequential%20model%20allows%20us,for%20building%20deep%20learning%20models.

Vishwakarma, S. (2023b). Why is Sigmoid Function Important in Artificial Neural Networks? Analytics Vidhya. https://www.analyticsvidhya.com/blog/2023/01/why-is-sigmoid-function-important-in-artificial-neural-networks/#:~:text=Source%3A%20Pexels-,The%20sigmoid%20function%20is%20commonly%20used%20as%20an%20activation%20function,non%2Dlinearity%20into%20the%20model.

Binary Cross Entropy: Where To Use Log Loss In Model Monitoring. (2023b, March 2). Arize AI. https://arize.com/blog-course/binary-cross-entropy-log-loss/#:~:text=What%20Is%20Binary%20Cross%20Entropy,equate%20to%20high%20accuracy%20values.

Binary Classification. (n.d.-b). https://www.learndatasci.com/glossary/binary-classification/#:~:text=each%20binary%20classifier-,What%20is%20Binary%20Classification%3F,Application
