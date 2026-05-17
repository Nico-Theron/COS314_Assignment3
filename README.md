COS314 Assignment 3 - Genetic Programming Classification

Frikkie Malan (u14439141)
Nico Theron (u23565722)
Daniel D. Erasmus (u24586189)

Requirements:
Java 17 or newer.

Files required in the same folder as the JAR:
- Breast_train.csv
- Breast_test.csv
- wilcoxon_critical_values.csv

How to run:
1. Open a terminal or command prompt in the folder containing the JAR file.
2. Run the following command:

   java -jar "Code.jar"

Output files:
The program generates the following CSV files:
- Logical_GP_results.csv
- Symbolic_GP_results.csv
- Wilcoxon_Test_Accuracy.csv
- Wilcoxon_Test_FMeasure.csv

Program description:
The program runs two Genetic Programming classifiers on the Breast Cancer dataset:
1. Logical GP using evolved decision tree structures.
2. Symbolic GP using arithmetic expression trees.

Each algorithm is executed for 30 independent runs using 30 seeds following the inputed seed.
For each run, the program records training accuracy, training F-measure, test accuracy, test F-measure, and runtime.

