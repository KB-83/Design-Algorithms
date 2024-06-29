import numpy as np
import pandas as pd

# Example DataFrame
data = {
    'A': [1, 2, 3, 4, 5],
    'B': [2, 3, 4, 5, 6],
    'C': [5, 4, 3, 2, 1]
}
df = pd.DataFrame(data)

# Manually calculate the correlation matrix
def calculate_correlation_matrix(df):
    # Step 1: Calculate the means
    means = df.mean()

    # Step 2: Center the data
    centered_data = df - means

    # Step 3: Calculate the covariance matrix
    covariance_matrix = centered_data.cov()

    # Step 4: Calculate the standard deviations
    std_devs = df.std()

    # Step 5: Calculate the correlation matrix
    correlation_matrix = covariance_matrix / np.outer(std_devs, std_devs)

    return correlation_matrix

# Using pandas built-in method
print(df.corr())

# Using our manual calculation
print(calculate_correlation_matrix(df))
